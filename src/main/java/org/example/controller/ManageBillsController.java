package org.example.controller;

import org.example.dao.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.model.*;
import org.example.view.ManageBillsView;

import java.io.IOException;
import java.util.ArrayList;

public class ManageBillsController {
    private static ObservableList<String> permissionsCombo;
    private static ObservableList<Author> authors;
    private static ObservableList<Book> books;
    private static ObservableList<Bill> bills;
    private static ObservableList<UsersOfTheSystem> users;
    private static ObservableList<BooksOrdered> orders;
    private final ManageBillsView view;
    private final BillsDAO billsDAO;
    private final FilteredList<Bill> filteredBills;
    private final BillPrintingDAO billPrintingDAO;
    private final Stage stage;
    private final BooksDAO booksDAO;
    private final AuthorsDAO authorsDAO;
    private final UsersDAO usersDAO;
    private final UsersOfTheSystem user;
    private final ObservableList<Bill> billsBetweenDates;
    private int indexOfTheBill = -1;
    private double revenue;

    public ManageBillsController(Stage stage, UsersOfTheSystem user) {
        this.stage = stage;
        this.view = new ManageBillsView();
        billsDAO = new BillsDAO();
        booksDAO = new BooksDAO();
        authorsDAO = new AuthorsDAO();
        usersDAO = new UsersDAO();
        billPrintingDAO = new BillPrintingDAO();
        billsBetweenDates = FXCollections.observableArrayList();
        this.user = user;
        PermissionsDAO permissionsDAO = new PermissionsDAO();
        permissionsCombo = FXCollections.observableArrayList(permissionsDAO.getAll());
        authors = FXCollections.observableArrayList(authorsDAO.getAll());
        books = FXCollections.observableArrayList(booksDAO.getAll());
        bills = FXCollections.observableArrayList(billsDAO.getAll());
        users = FXCollections.observableArrayList(usersDAO.getAll());
        orders = FXCollections.observableArrayList();
        filteredBills = new FilteredList<>(bills, b -> true);

        this.view.getTableViewOfBills().setItems(billsDAO.getAll());
        this.view.getTableViewOfBooksOrdered().setItems(orders);

        this.view.getReturnButton().setOnAction(this::Back);
        this.view.getBtnRemove().setOnAction(this::onOrderRemove);

        revenue = 0;
        for (Bill bill : bills) {
            revenue += bill.getTotalAmount();
        }

        this.view.setRevenueTf(String.valueOf(revenue));


        setEditListeners();
    }

    public static boolean checkQuantity(int quantity, Book book) {
        try {
            if (quantity <= 0 || quantity > book.getQuantity()) {
                Alert alertForQuantity;
                alertForQuantity = new Alert(Alert.AlertType.ERROR);
                alertForQuantity.setContentText("Quantity error!");
                alertForQuantity.setTitle("Check the quantity of the book!");
                alertForQuantity.show();
                return false;
            }
            return true;
        } catch (NumberFormatException ex) {
            Alert alertForQuantity;
            alertForQuantity = new Alert(Alert.AlertType.ERROR);
            alertForQuantity.setContentText("Quantity error!");
            alertForQuantity.setTitle("Check the quantity of the book!");
            alertForQuantity.show();
            return false;
        }
    }

    public ManageBillsView getView() {
        return view;
    }

    public void getItem(MouseEvent event) {

        try{
            orders.clear();
            Bill bill = this.view.getTableViewOfBills().getSelectionModel().getSelectedItem();
            indexOfTheBill = bills.indexOf(bill);
            orders.addAll(bill.getBooks());
            this.view.getTableViewOfBooksOrdered().setItems(orders);

        }catch (NullPointerException ex){
            ex.fillInStackTrace();
        }
    }

    private void setEditListeners() {

        this.view.getQuantityToOrderColumn().setOnEditCommit(e -> {

            BooksOrdered bookOrdered = view.getTableViewOfBooksOrdered().getSelectionModel().getSelectedItem();
            int indexOfTheBook = -1;
            int indexOfAuthor;
            int indexOfUser = -1;

            for (Book book : books) {
                if (book.getIsbn().equals(bookOrdered.getIsbn())) {
                    indexOfTheBook = books.indexOf(book);
                    break;
                }
            }

            if (indexOfTheBook != -1) {

                int quantity = 0;

                if (e.getNewValue() > e.getOldValue()) {
                    quantity = e.getNewValue() - e.getOldValue();
                } else if (e.getNewValue() < e.getOldValue() && e.getNewValue() > 0) {
                    quantity = e.getNewValue() - e.getOldValue();
                } else {
                    Alert alert;
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Check the quantity entered!");
                    alert.setTitle("Quantity error");
                    alert.show();
                }

                if(e.getNewValue()>=0){
                    if (checkQuantity(Math.abs(quantity), books.get(indexOfTheBook))) {

                        orders.get(e.getTablePosition().getRow()).setQuantityToOrder(e.getNewValue());
                        indexOfAuthor = authors.indexOf(books.get(indexOfTheBook).getAuthor());
                        String userID = bills.get(indexOfTheBill).getUserID();
                        for (UsersOfTheSystem user : users) {
                            if (user.getUserID().equals(userID)) {
                                indexOfUser = users.indexOf(user);
                            }
                        }

                        authors.get(indexOfAuthor).setNrOfBooksSold(quantity);
                        books.get(indexOfTheBook).setCopiesSold(quantity);
                        books.get(indexOfTheBook).setQuantity(books.get(indexOfTheBook).getQuantity() - quantity);


                        users.get(indexOfUser).setNrOfBooksSold(quantity);
                        users.get(indexOfUser).setRevenueMade((quantity) * books.get(indexOfTheBook).getPrice());

                        bills.get(indexOfTheBill).getBooks().clear();
                        ArrayList<BooksOrdered> ordersToBills = new ArrayList<>(orders);
                        bills.get(indexOfTheBill).setBooks(ordersToBills);
                        bills.get(indexOfTheBill).setTotalAmount(ordersToBills);

                    } else {
                        orders.get(e.getTablePosition().getRow()).setQuantityToOrder(e.getOldValue());
                    }
                }
            } else {
                Alert alert;
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Book not found in the system");
                alert.setTitle("Book not found");
                alert.show();
            }
        });

        try {
            this.view.getTableViewOfBills().setOnMouseClicked(this::getItem);
        } catch (NullPointerException ex) {
            ex.fillInStackTrace();
        }


        this.view.getTotalAmountColumn().setOnEditCommit(e -> {
            ArrayList<BooksOrdered> ord = new ArrayList<>(orders);
            billsDAO.getAll().get(e.getTablePosition().getRow()).setTotalAmount(ord);
        });

        this.view.getIdUserColumn().setEditable(false);
        this.view.getDateBillColumn().setEditable(false);
        this.view.getIdBillColumn().setEditable(false);

        this.view.getSearchBarTf().textProperty().addListener((observable, oldValue, newValue) ->
                filteredBills.setPredicate(book -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    return book.getBillID().contains(newValue);
                }));

        this.view.getTableViewOfBills().setItems(filteredBills);

        this.view.getBtnUpdate().setOnAction(e -> {
            Alert alert;
            if (this.billsDAO.updateAll() && this.booksDAO.updateAll() && this.usersDAO.updateAll() && this.authorsDAO.updateAll()) {

                orders.clear();

                revenue = 0;
                for (Bill bill : bills) {
                    revenue += bill.getTotalAmount();
                }

                this.view.setRevenueTf(String.valueOf(revenue));

                try {
                    this.billPrintingDAO.addBillToFile(bills);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Updated successfully!");
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Update failed!");
            }
            alert.setTitle("Update result");
            alert.show();
        });

        this.view.getSearchButton2().setOnAction(e -> {

            try {
                if (!billsBetweenDates.isEmpty()) {
                    billsBetweenDates.clear();
                }

                if (this.getView().getFromDate().compareTo(this.view.getToDate()) < 0) {
                    for (Bill bill : bills) {
                        if (bill.getDate().compareTo(this.view.getFromDate()) >= 0 && bill.getDate().compareTo(this.view.getToDate()) < 1) {
                            billsBetweenDates.add(bill);
                        }
                    }
                    this.view.setSearchBarTf();
                    revenue = 0;
                    for (Bill bill : billsBetweenDates) {
                        revenue += bill.getTotalAmount();
                    }
                    this.view.setRevenueTf(String.valueOf(revenue));
                    this.view.getTableViewOfBills().setItems(billsBetweenDates);
                } else {
                    Alert alert;
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.setContentText("Check the dates entered!");
                    alert.setTitle("Date mismatch");
                    alert.show();
                }
            } catch (NullPointerException ex) {
                ex.fillInStackTrace();
                Alert alert;
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Enter the dates in both fields!");
                alert.setTitle("Date Missing");
                alert.show();
            }
        });

        this.view.getShowAllButton().setOnAction(e -> {
            orders.clear();
            if (!billsBetweenDates.isEmpty()) {
                billsBetweenDates.clear();
            }
            this.view.setSearchBarTf();
            revenue = 0;
            for (Bill bill : bills) {
                revenue += bill.getTotalAmount();
            }

            this.view.setSearchBarTf();
            this.view.setRevenueTf(String.valueOf(revenue));
            this.view.getTableViewOfBills().setItems(filteredBills);
            this.view.setFromDate();
            this.view.setToDate();
        });

    }

    public void onOrderRemove(ActionEvent event) {

        try {
            BooksOrdered bookOrdered = this.view.getTableViewOfBooksOrdered().getSelectionModel().getSelectedItem();
            Alert alert;

            if (orders.isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No book to remove from the recipe!");
            }


            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Book removed successfully!");


            int indexOfTheBook = -1;
            int indexOfAuthor;
            int indexOfUser = -1;

            for (Book book : books) {
                if (book.getIsbn().equals(bookOrdered.getIsbn())) {
                    indexOfTheBook = books.indexOf(book);
                    break;
                }
            }

            indexOfAuthor = authors.indexOf(books.get(indexOfTheBook).getAuthor());
            authors.get(indexOfAuthor).setNrOfBooksSold(-bookOrdered.getQuantityToOrderOfBookOrdered());
            books.get(indexOfTheBook).setCopiesSold(-bookOrdered.getQuantityToOrderOfBookOrdered());
            books.get(indexOfTheBook).setQuantity(books.get(indexOfTheBook).getQuantity() + bookOrdered.getQuantityToOrderOfBookOrdered());

            String userID = bills.get(indexOfTheBill).getUserID();
            for (UsersOfTheSystem user : users) {
                if (user.getUserID().equals(userID)) {
                    indexOfUser = users.indexOf(user);
                }
            }

            users.get(indexOfUser).setNrOfBooksSold(-bookOrdered.getQuantityToOrderOfBookOrdered());
            users.get(indexOfUser).setRevenueMade(-bookOrdered.getQuantityToOrderOfBookOrdered() * books.get(indexOfTheBook).getPrice());

            orders.remove(bookOrdered);

            bills.get(indexOfTheBill).getBooks().clear();
            ArrayList<BooksOrdered> ordersToBills = new ArrayList<>(orders);
            bills.get(indexOfTheBill).setBooks(ordersToBills);
            bills.get(indexOfTheBill).setTotalAmount(ordersToBills);

            alert.setTitle("Remove result");
            alert.show();

        } catch (NullPointerException | IndexOutOfBoundsException ex1) {

            if(ex1 instanceof NullPointerException){
                (ex1).fillInStackTrace();
            }
            else{
                Alert alert;
                alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("This item of the bill cannot be deleted!");
                alert.setTitle("Book not found");
                alert.show();
            }
        }
    }

    public void Back(ActionEvent event) {
        if (user.getRole() == Role.ADMIN) {
            AdminMenuController controller = new AdminMenuController(stage, user, true);
            stage.getScene().setRoot(controller.getView());
        } else if (user.getRole() == Role.MANAGER) {
            backFunction(0);
        } else {
            backFunction(1);
        }
    }
    private void backFunction(int i){
        BackFunctionMenu.mainMenuBack(i, permissionsCombo, stage, user);
    }

}
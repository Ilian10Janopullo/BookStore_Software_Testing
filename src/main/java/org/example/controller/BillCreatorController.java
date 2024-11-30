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
import org.example.view.BillCreatorView;

import java.io.IOException;
import java.util.ArrayList;

public class BillCreatorController {
    private static ObservableList<String> permissionsCombo;
    private final BillCreatorView view;
    private final BillsDAO billsDAO;
    private final FilteredList<Book> filteredBooks;
    private final BillPrintingDAO billPrintingDAO;
    private final Stage stage;
    private final BooksDAO booksDAO;
    private final AuthorsDAO authorsDAO;
    private final UsersDAO usersDAO;
    private final ObservableList<Author> authors;

    private final ObservableList<Book> books;
    private final ObservableList<Bill> bill;
    private final UsersOfTheSystem user;
    private ObservableList<BooksOrdered> orders;
    private double price = 0;

    public BillCreatorController(Stage stage, UsersOfTheSystem user) {
        this.stage = stage;
        this.view = new BillCreatorView();
        billsDAO = new BillsDAO();
        booksDAO = new BooksDAO();
        authorsDAO = new AuthorsDAO();
        usersDAO = new UsersDAO();
        billPrintingDAO = new BillPrintingDAO();
        PermissionsDAO permissionsDAO = new PermissionsDAO();
        permissionsCombo = FXCollections.observableArrayList(permissionsDAO.getAll());
        this.user = user;
        authors = FXCollections.observableArrayList(authorsDAO.getAll());
        books = FXCollections.observableArrayList(booksDAO.getAll());
        bill = FXCollections.observableArrayList(billsDAO.getAll());
        orders = FXCollections.observableArrayList();
        filteredBooks = new FilteredList<>(books, b -> true);

        this.view.setTotalAmountFieldLb(0);

        this.view.getTableViewOgBooksInStock().setItems(booksDAO.getAll());
        this.view.getTableViewOfBooksToOrder().setItems(orders);
        this.view.getReturnButton().setOnAction(this::Back);
        this.view.getSubmitBtn().setOnAction(this::Submit);
        this.view.getBtnRemove().setOnAction(this::onBookRemove);
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

    public BillCreatorView getView() {
        return view;
    }

    public void getItem(MouseEvent event) {
        Book book = this.view.getTableViewOgBooksInStock().getSelectionModel().getSelectedItem();
        boolean stop = false;
        for (BooksOrdered ordered : orders) {
            if (ordered.getIsbnOfBookOrdered().equals(book.getIsbn())) {
                stop = true;
                break;
            }
        }
        if (!stop) {
            if (book != null) {
                if (orders == null) {
                    orders = FXCollections.observableArrayList();
                }

                BooksOrdered order = new BooksOrdered(book.getIsbn(), book.getTitle(), book.getPrice());
                orders.add(order);
                this.view.getTableViewOfBooksToOrder().setItems(orders);

            }
        } else {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Add error!");
            alert.setTitle("Cannot add the same book twice!");
            alert.show();
        }
    }

    private void setEditListeners() {

        this.view.getQuantityToOrderColumn().setOnEditCommit(e -> {
            if (checkQuantity((e.getNewValue()), view.getTableViewOgBooksInStock().getSelectionModel().getSelectedItem())) {
                orders.get(e.getTablePosition().getRow()).setQuantityToOrder(e.getNewValue());

                price += orders.get(e.getTablePosition().getRow()).getPrice() * orders.get(e.getTablePosition().getRow()).getQuantityToOrderOfBookOrdered();
                this.view.setTotalAmountFieldLb(price);

            } else {
                orders.get(e.getTablePosition().getRow()).setQuantityToOrder(e.getOldValue());
            }
        });

        this.view.getTableViewOgBooksInStock().setOnMouseClicked(this::getItem);

        this.view.getSearchBarTf().textProperty().addListener((observable, oldValue, newValue) ->
                filteredBooks.setPredicate(book -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilterEntered = newValue.toLowerCase();
                    if (book.getTitle().toLowerCase().contains(lowerCaseFilterEntered)) {
                        return true;
                    } else if (book.getIsbn().toLowerCase().contains(lowerCaseFilterEntered)) {
                        return true;
                    } else if (book.getGenres().toString().toLowerCase().contains(lowerCaseFilterEntered)) {
                        return true;
                    } else if ((book.getPrice() + "").contains(lowerCaseFilterEntered)) {
                        return true;
                    } else 
                        return book.getAuthor().toString().toLowerCase().contains(lowerCaseFilterEntered);
                }));

        this.view.getTableViewOgBooksInStock().setItems(filteredBooks);

        this.view.getResetBtn().setOnAction(e -> {
            price = 0;
            this.view.setTotalAmountFieldLb(price);
            orders.clear();
        });

    }

    public void onBookRemove(ActionEvent event) {

        BooksOrdered book = this.view.getTableViewOfBooksToOrder().getSelectionModel().getSelectedItem();


        try {
            Alert alert;
            if (orders.isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("No book to remove from the recipe!");
            }

            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Book removed successfully!");
            price -= book.getPrice() * book.getQuantityToOrderOfBookOrdered();
            this.view.setTotalAmountFieldLb(price);
            orders.remove(book);

            alert.setTitle("Remove result");
            alert.show();

        } catch (NullPointerException ex) {
            ex.fillInStackTrace();
        }
    }


    public void Submit(ActionEvent event) {

        try {
            ArrayList<BooksOrdered> ordersToProceed = new ArrayList<>(orders);

            boolean result = false;

            for (Book book : books) {
                for (BooksOrdered ordered : ordersToProceed) {
                    if (ordered.getIsbn().equals(book.getIsbn())) {
                        result = checkQuantity(ordered.getQuantityToOrderOfBookOrdered(), book);
                        if (!result) {
                            break;
                        }
                    }
                }
            }

            if (result) {
                Bill billToProceed = new Bill(ordersToProceed, user.getUserID());
                boolean res = billsDAO.addToFile(billToProceed);
                if (!res) {
                    Alert alert;
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Book registering failed!");
                    alert.setTitle("Registering result!");
                    alert.show();
                } else {

                    for (Book book : books) {
                        for (BooksOrdered orderedBook : orders) {
                            if (book.getIsbn().equals(orderedBook.getIsbn())) {
                                book.setQuantity(book.getQuantity() - orderedBook.getQuantityToOrderOfBookOrdered());
                                book.setCopiesSold(orderedBook.getQuantityToOrderOfBookOrdered());
                                user.setNrOfBooksSold(orderedBook.getQuantityToOrderOfBookOrdered());
                                user.setRevenueMade(orderedBook.getQuantityToOrderOfBookOrdered() * book.getPrice());
                                for (Author author : authors) {
                                    if (author.getFullName().equals(book.getAuthor().getFullName())) {
                                        author.setNrOfBooksSold(orderedBook.getQuantityToOrderOfBookOrdered());
                                    }
                                }
                            }
                        }
                    }

                    bill.add(billToProceed);
                    booksDAO.updateAll();
                    authorsDAO.updateAll();
                    usersDAO.updateAll();
                    billPrintingDAO.addBillToFile(bill);
                    billsDAO.updateAll();
                    price = 0;
                    this.view.setTotalAmountFieldLb(price);
                    view.getTableViewOgBooksInStock().refresh();
                    orders.clear();
                }
            }

        } catch (NullPointerException ex) {
            ex.fillInStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
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


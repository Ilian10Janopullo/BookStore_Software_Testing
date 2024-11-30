package org.example.controller;


import org.example.dao.BillsDAO;
import org.example.dao.BooksDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.model.Bill;
import org.example.model.Book;
import org.example.model.UsersOfTheSystem;
import org.example.view.PermissionCombo8;

public class PermissionCombo8Controller {
    private static ObservableList<Bill> bills;
    private static ObservableList<Book> books;
    private final PermissionCombo8 view;
    private final Stage stage;
    private final UsersOfTheSystem user;

    public PermissionCombo8Controller(Stage stage, UsersOfTheSystem user) {
        this.user = user;
        this.stage = stage;
        this.view = new PermissionCombo8(stage);
        BillsDAO billsDAO = new BillsDAO();
        BooksDAO booksDAO = new BooksDAO();
        books = FXCollections.observableArrayList(booksDAO.getAll());
        bills = FXCollections.observableArrayList(billsDAO.getAll());


        this.view.getLogout().setOnAction(e -> LogOut());
        this.view.getSellBooks().setOnAction(e -> Sell());
        this.view.getManageBills().setOnAction(e -> ManageBills());
    }

    public PermissionCombo8 getView() {
        return view;
    }


    public void LogOut() {
        LoginController controller = new LoginController(stage);
        stage.getScene().setRoot(controller.getView());
    }

    public void Sell() {

        boolean checkForBooksInTheSystem = false;
        for (Book book : books) {
            if (book.getQuantity() > 0) {
                checkForBooksInTheSystem = true;
                break;
            }
        }

        if (!books.isEmpty() || checkForBooksInTheSystem) {
            BillCreatorController controller = new BillCreatorController(stage, user);
            stage.getScene().setRoot(controller.getView());
        } else {
            Alert alert;
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You cannot sell any book, because there is no book, or any copy of any book in the system!");
            alert.setTitle("Warning");
            alert.show();
        }
    }

    public void ManageBills() {
        if (!bills.isEmpty()) {
            ManageBillsController controller = new ManageBillsController(stage, user);
            stage.getScene().setRoot(controller.getView());
        } else {
            Alert alert;
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You cannot manage any bill, because there is no bill in the system!");
            alert.setTitle("Warning");
            alert.show();
        }
    }
}

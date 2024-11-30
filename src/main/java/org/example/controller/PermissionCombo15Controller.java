package org.example.controller;

import org.example.dao.AuthorsDAO;
import org.example.dao.BillsDAO;
import org.example.dao.BooksDAO;
import org.example.dao.UsersDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.model.Author;
import org.example.model.Bill;
import org.example.model.Book;
import org.example.model.UsersOfTheSystem;
import org.example.view.PermissionCombo15;

public class PermissionCombo15Controller {
    private static ObservableList<Author> authors;
    private static ObservableList<Bill> bills;
    private static ObservableList<Book> books;
    private static ObservableList<UsersOfTheSystem> users;
    private final UsersDAO usersDAO;
    private final PermissionCombo15 view;
    private final AuthorsDAO authorsDAO;
    private final BillsDAO billsDAO;
    private final BooksDAO booksDAO;
    private final Stage stage;
    private final UsersOfTheSystem user;

    public PermissionCombo15Controller(Stage stage, UsersOfTheSystem user) {
        this.user = user;
        this.stage = stage;
        this.view = new PermissionCombo15(stage);
        billsDAO = new BillsDAO();
        authorsDAO = new AuthorsDAO();
        booksDAO = new BooksDAO();
        usersDAO = new UsersDAO();
        users = FXCollections.observableArrayList(usersDAO.getAll());
        books = FXCollections.observableArrayList(booksDAO.getAll());
        authors = FXCollections.observableArrayList(authorsDAO.getAll());
        bills = FXCollections.observableArrayList(billsDAO.getAll());

        int counter = 0;
        for (Book book : books) {
            if (book.getQuantity() < 5) {
                counter++;
            }
        }

        if (counter > 0) {
            Alert alert;
            alert = new Alert(Alert.AlertType.WARNING);
            if (counter == 1) {
                alert.setContentText("There is 1 book with less than 5 copies for sale!");
            } else {
                alert.setContentText("There are " + counter + " books with less than 5 copies for sale!");
            }
            alert.setTitle("Reminder");
            alert.show();
        }

        this.view.getManageAuthors().setOnAction(e -> ManageAuthors());
        this.view.getManageBooks().setOnAction(e -> ManageBooks());
        this.view.getCheckUsers().setOnAction(e -> CheckUsers());
        this.view.getLogout().setOnAction(e -> LogOut());
        this.view.getSellBooks().setOnAction(e -> Sell());
        this.view.getManageBills().setOnAction(e -> ManageBills());
    }

    public PermissionCombo15Controller(Stage stage, UsersOfTheSystem user, boolean ignoredBool) {
        this.user = user;
        this.stage = stage;
        this.view = new PermissionCombo15(stage);
        billsDAO = new BillsDAO();
        authorsDAO = new AuthorsDAO();
        booksDAO = new BooksDAO();
        usersDAO = new UsersDAO();
        users = FXCollections.observableArrayList(usersDAO.getAll());
        books = FXCollections.observableArrayList(booksDAO.getAll());
        authors = FXCollections.observableArrayList(authorsDAO.getAll());
        bills = FXCollections.observableArrayList(billsDAO.getAll());

        this.view.getManageAuthors().setOnAction(e -> ManageAuthors());
        this.view.getManageBooks().setOnAction(e -> ManageBooks());
        this.view.getCheckUsers().setOnAction(e -> CheckUsers());
        this.view.getLogout().setOnAction(e -> LogOut());
        this.view.getSellBooks().setOnAction(e -> Sell());
        this.view.getManageBills().setOnAction(e -> ManageBills());
    }


    public PermissionCombo15 getView() {
        return view;
    }

    public void ManageAuthors() {
        ManagingAuthorsController controller = new ManagingAuthorsController(stage, user);
        stage.getScene().setRoot(controller.getView());
    }

    public void ManageBooks() {
        if (!authors.isEmpty()) {
            ManagingBooksController controller = new ManagingBooksController(stage, user);
            stage.getScene().setRoot(controller.getView());
        } else {
            Alert alert;
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You cannot add any book, because there is no author in the system!");
            alert.setTitle("Warning");
            alert.show();
        }
    }

    public void CheckUsers() {
        if (!users.isEmpty()) {
            CheckUsersController controller = new CheckUsersController(stage, user);
            stage.getScene().setRoot(controller.getView());
        } else {
            Alert alert;
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No users in the system!");
            alert.setTitle("Warning");
            alert.show();
        }
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

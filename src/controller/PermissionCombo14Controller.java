package controller;

import dao.BooksDAO;
import dao.UsersDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Book;
import model.UsersOfTheSystem;
import view.PermissionCombo14;

public class PermissionCombo14Controller {
    private static ObservableList<UsersOfTheSystem> users;
    private static ObservableList<Book> books;
    private final PermissionCombo14 view;
    private final Stage stage;
    private final UsersOfTheSystem user;

    public PermissionCombo14Controller(Stage stage, UsersOfTheSystem user) {
        this.user = user;
        this.stage = stage;
        this.view = new PermissionCombo14(stage);
        BooksDAO booksDAO = new BooksDAO();
        UsersDAO usersDAO = new UsersDAO();
        users = FXCollections.observableArrayList(usersDAO.getAll());
        books = FXCollections.observableArrayList(booksDAO.getAll());



        this.view.getLogout().setOnAction(e -> LogOut());
        this.view.getSellBooks().setOnAction(e -> Sell());
        this.view.getCheckUsers().setOnAction(e -> CheckUsers());
        this.view.getManageBills().setOnAction(e -> ManageBills());
    }

    public PermissionCombo14 getView() {
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

    public void ManageBills() {
        if (!users.isEmpty()) {
            CheckUsersController controller = new CheckUsersController(stage, user);
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

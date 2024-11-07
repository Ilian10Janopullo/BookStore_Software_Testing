package controller;

import dao.BooksDAO;
import dao.UsersDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Book;
import model.UsersOfTheSystem;
import view.PermissionCombo9;

public class PermissionCombo9Controller {
    private static ObservableList<Book> books;
    private static ObservableList<UsersOfTheSystem> users;
    private final PermissionCombo9 view;
    private final Stage stage;
    private final UsersOfTheSystem user;

    public PermissionCombo9Controller(Stage stage, UsersOfTheSystem user) {
        this.user = user;
        this.stage = stage;
        this.view = new PermissionCombo9(stage);
        UsersDAO usersDAO = new UsersDAO();
        BooksDAO booksDAO = new BooksDAO();
        books = FXCollections.observableArrayList(booksDAO.getAll());
        users = FXCollections.observableArrayList(usersDAO.getAll());


        this.view.getLogout().setOnAction(e -> LogOut());
        this.view.getSellBooks().setOnAction(e -> Sell());
        this.view.getCheckUsers().setOnAction(e -> CheckUsers());
    }

    public PermissionCombo9 getView() {
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
            alert.setContentText("No users in the system!!");
            alert.setTitle("Warning");
            alert.show();
        }
    }
}

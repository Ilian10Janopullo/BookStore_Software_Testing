package controller;

import dao.AuthorsDAO;
import dao.BooksDAO;
import dao.UsersDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Author;
import model.Book;
import model.UsersOfTheSystem;
import view.PermissionCombo7;

public class PermissionCombo7Controller {
    private static ObservableList<Author> authors;
    private static ObservableList<UsersOfTheSystem> users;
    private static ObservableList<Book> books;
    private final PermissionCombo7 view;
    private final AuthorsDAO authorsDAO;
    private final BooksDAO booksDAO;
    private final Stage stage;
    private final UsersDAO usersDAO;
    private final UsersOfTheSystem user;

    public PermissionCombo7Controller(Stage stage, UsersOfTheSystem user) {
        this.user = user;
        this.stage = stage;
        this.view = new PermissionCombo7(stage);
        authorsDAO = new AuthorsDAO();
        booksDAO = new BooksDAO();
        usersDAO = new UsersDAO();
        books = FXCollections.observableArrayList(booksDAO.getAll());
        users = FXCollections.observableArrayList(usersDAO.getAll());
        authors = FXCollections.observableArrayList(authorsDAO.getAll());

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
        this.view.getLogout().setOnAction(e -> LogOut());
        this.view.getCheckUsers().setOnAction(e -> CheckUsers());
    }

    public PermissionCombo7Controller(Stage stage, UsersOfTheSystem user, boolean ignoredBool) {
        this.user = user;
        this.stage = stage;
        this.view = new PermissionCombo7(stage);
        authorsDAO = new AuthorsDAO();
        booksDAO = new BooksDAO();
        usersDAO = new UsersDAO();
        books = FXCollections.observableArrayList(booksDAO.getAll());
        users = FXCollections.observableArrayList(usersDAO.getAll());
        authors = FXCollections.observableArrayList(authorsDAO.getAll());

        this.view.getManageAuthors().setOnAction(e -> ManageAuthors());
        this.view.getManageBooks().setOnAction(e -> ManageBooks());
        this.view.getLogout().setOnAction(e -> LogOut());
        this.view.getCheckUsers().setOnAction(e -> CheckUsers());
    }

    public PermissionCombo7 getView() {
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

    public void LogOut() {
        LoginController controller = new LoginController(stage);
        stage.getScene().setRoot(controller.getView());
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
}

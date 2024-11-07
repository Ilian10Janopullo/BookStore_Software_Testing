package controller;

import dao.AuthorsDAO;
import dao.BillsDAO;
import dao.BooksDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Author;
import model.Bill;
import model.Book;
import model.UsersOfTheSystem;
import view.AdminMenuView;


public class AdminMenuController {
    private static ObservableList<Author> authors;
    private static ObservableList<Bill> bills;
    private static ObservableList<Book> books;
    private final AdminMenuView view;
    private final AuthorsDAO authorsDAO;
    private final BillsDAO billsDAO;
    private final BooksDAO booksDAO;
    private final Stage stage;
    private final UsersOfTheSystem user;

    public AdminMenuController(Stage stage, UsersOfTheSystem user) {
        this.user = user;
        this.stage = stage;
        this.view = new AdminMenuView(stage);
        billsDAO = new BillsDAO();
        authorsDAO = new AuthorsDAO();
        booksDAO = new BooksDAO();
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
        this.view.getManageUsers().setOnAction(e -> ManageUsers());
        this.view.getLogout().setOnAction(e -> LogOut());
        this.view.getSellBooks().setOnAction(e -> Sell());
        this.view.getManageBills().setOnAction(e -> ManageBills());
        this.view.getControlPermissions().setOnAction(e->ControlPermissions());
    }

    public AdminMenuController(Stage stage, UsersOfTheSystem user, boolean ignoredBool) {
        this.user = user;
        this.stage = stage;
        this.view = new AdminMenuView(stage);
        billsDAO = new BillsDAO();
        authorsDAO = new AuthorsDAO();
        booksDAO = new BooksDAO();
        books = FXCollections.observableArrayList(booksDAO.getAll());
        authors = FXCollections.observableArrayList(authorsDAO.getAll());
        bills = FXCollections.observableArrayList(billsDAO.getAll());

        this.view.getManageAuthors().setOnAction(e -> ManageAuthors());
        this.view.getManageBooks().setOnAction(e -> ManageBooks());
        this.view.getManageUsers().setOnAction(e -> ManageUsers());
        this.view.getLogout().setOnAction(e -> LogOut());
        this.view.getSellBooks().setOnAction(e -> Sell());
        this.view.getManageBills().setOnAction(e -> ManageBills());
        this.view.getControlPermissions().setOnAction(e->ControlPermissions());
    }


    public AdminMenuView getView() {
        return view;
    }

    public void ManageAuthors() {
        ManagingAuthorsController controller = new ManagingAuthorsController(stage, user);
        stage.getScene().setRoot(controller.getView());
    }

    public void ManageBooks() {
        if (authors.isEmpty()) {
            Alert alert;
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You cannot add any book, because there is no author in the system!");
            alert.setTitle("Warning");
            alert.show();
        } else {
            ManagingBooksController controller = new ManagingBooksController(stage, user);
            stage.getScene().setRoot(controller.getView());
        }
    }

    public void ManageUsers() {
        ManageUsersController controller = new ManageUsersController(stage, user);
        stage.getScene().setRoot(controller.getView());
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

        if (books.isEmpty() && !checkForBooksInTheSystem) {
            Alert alert;
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You cannot sell any book, because there is no book, or any copy of any book in the system!");
            alert.setTitle("Warning");
            alert.show();
        } else {
            BillCreatorController controller = new BillCreatorController(stage, user);
            stage.getScene().setRoot(controller.getView());
        }
    }

    public void ManageBills() {
        if (bills.isEmpty()) {
            Alert alert;
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You cannot manage any bill, because there is no bill in the system!");
            alert.setTitle("Warning");
            alert.show();
        } else {
            ManageBillsController controller = new ManageBillsController(stage, user);
            stage.getScene().setRoot(controller.getView());
        }
    }

    public void ControlPermissions(){
        SetUpPermissionsController controller = new SetUpPermissionsController(user, stage);
        stage.getScene().setRoot(controller.getView());
    }
}
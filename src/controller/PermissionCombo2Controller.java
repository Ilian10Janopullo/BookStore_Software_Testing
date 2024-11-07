package controller;

import dao.BooksDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Book;
import model.UsersOfTheSystem;
import view.PermissionCombo2;

public class PermissionCombo2Controller {
    private static ObservableList<Book> books;
    private final PermissionCombo2 view;
    private final Stage stage;
    private final UsersOfTheSystem user;

    public PermissionCombo2Controller(Stage stage, UsersOfTheSystem user) {
        this.user = user;
        this.stage = stage;
        this.view = new PermissionCombo2(stage);
        BooksDAO booksDAO = new BooksDAO();
        books = FXCollections.observableArrayList(booksDAO.getAll());


        this.view.getLogout().setOnAction(e -> LogOut());
        this.view.getSellBooks().setOnAction(e -> Sell());

    }

    public PermissionCombo2 getView() {
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


}

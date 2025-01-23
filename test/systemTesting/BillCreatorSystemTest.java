package systemTesting;

import controller.BillCreatorController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import model.*;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import dao.BooksDAO;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BillCreatorSystemTest extends ApplicationTest {

    private BillCreatorController controller;

    @Override
    public void start(Stage stage) {
        Calendar calendar = new GregorianCalendar();
        UsersOfTheSystem user = new Librarian("Jane", "Doe", calendar.getTime(), Gender.FEMALE, "janedoe", "password", Role.LIBRARIAN, "janedoe@example.com", "987654321", 800);

        controller = new BillCreatorController(stage, user);
        stage.setScene(new javafx.scene.Scene(controller.getView(), 1520, 600));
        stage.show();
    }

    @BeforeEach
    public void setUp() throws InterruptedException {
        controller.getView().getTableViewOgBooksInStock().refresh();
        controller.getView().getTableViewOfBooksToOrder().refresh();
        Thread.sleep(3000);
    }

    @Test
    @Order(1)
    public void testAddBookToOrderFlow() throws InterruptedException {
        TableView<Book> booksTable = controller.getView().getTableViewOgBooksInStock();
        TableView<BooksOrdered> tableView1 = controller.getView().getTableViewOfBooksToOrder();
        ObservableList<Book> books = FXCollections.observableArrayList(new BooksDAO().getAll());
        booksTable.setItems(books);


        clickOn(booksTable).type(KeyCode.DOWN).press(MouseButton.PRIMARY).release(MouseButton.PRIMARY);
        clickOn(tableView1);
        type(KeyCode.RIGHT, 3).press(KeyCode.ENTER).release(KeyCode.ENTER);
        write("2").press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn("#submitButton");

        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);
    }

    @Test
    @Order(2)
    public void testRemoveBookFromOrderFlow() throws InterruptedException {
        TableView<Book> orderTable = controller.getView().getTableViewOgBooksInStock();
        TableView<BooksOrdered> tableView1 = controller.getView().getTableViewOfBooksToOrder();
//        ObservableList<BooksOrdered> orders = FXCollections.observableArrayList(new BooksDAO().getAll());

        clickOn(orderTable).type(KeyCode.DOWN).press(MouseButton.PRIMARY).release(MouseButton.PRIMARY);
        clickOn(tableView1);
        press(KeyCode.ENTER).release(KeyCode.ENTER).press(MouseButton.PRIMARY).release(MouseButton.PRIMARY);

        clickOn("#removeButton");

        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);
    }


    @Test
    @Order(3)
    public void testResetOrderFlow() throws InterruptedException {

        TableView<Book> orderTable = controller.getView().getTableViewOgBooksInStock();
        TableView<BooksOrdered> tableView1 = controller.getView().getTableViewOfBooksToOrder();

        clickOn(orderTable);
        type(KeyCode.DOWN, 1).press(MouseButton.PRIMARY).release(MouseButton.PRIMARY);
        Thread.sleep(500);
        type(KeyCode.DOWN, 2).press(MouseButton.PRIMARY).release(MouseButton.PRIMARY);

        clickOn("#resetButton");

        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);
    }

    @Test
    @Order(4)
    public void testBackNavigationFlow() throws InterruptedException {
        clickOn("#returnButton");

        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);
    }
}

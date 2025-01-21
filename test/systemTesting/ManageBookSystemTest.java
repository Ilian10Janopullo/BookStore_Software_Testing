package systemTesting;

import dao.BooksDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.*;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import controller.ManagingBooksController;

import java.util.Calendar;
import java.util.GregorianCalendar;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ManageBookSystemTest extends ApplicationTest {

    private ManagingBooksController controller;

    @Override
    public void start(Stage stage) {

        Calendar calendar = new GregorianCalendar();
        UsersOfTheSystem user = new Librarian("Ilian", "Janopullo", calendar.getTime(), Gender.MALE, "ijanopullo22", "1234", Role.ADMIN, "ijanopullo22@gmail.com", "0695383073", 700);

        controller = new ManagingBooksController(stage, user);
        stage.setScene(new javafx.scene.Scene(controller.getView(), 1520, 600));
        stage.show();
    }

    @BeforeEach
    public void setUp() throws InterruptedException {
        controller.getView().getTableView().refresh();
        Thread.sleep(3000);
    }

    @Test
    @Order(1)
    public void testAddBookFlow() throws InterruptedException {
        clickOn("#titleTextField").write("Test Book");
        clickOn("#isbnTextField").write("978-3-16-148410-0");
        clickOn("#priceTextField").write("15.99");
        clickOn("#quantityTextField").write("50");
        clickOn("#descriptionTextArea").write("This is a test book description.");
        clickOn("#authorComboBox").press(KeyCode.DOWN).release(KeyCode.DOWN).press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn("#paperbackRadioButton");
        clickOn("#genreCheckComboBox").clickOn("FANTASY").clickOn("HISTORICAL").press(KeyCode.ESCAPE).release(KeyCode.ESCAPE);
        clickOn("#submitButton");

        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);
    }

    @Test
    @Order(2)
    public void testEditBookFlow() throws InterruptedException {
        TableView<Book> tableView = controller.getView().getTableView();
        ObservableList<Book> books = FXCollections.observableArrayList(new BooksDAO().getAll());

        Book targetBook = books.stream().filter(book -> book.getTitle().equals("Test Book")).findFirst().orElse(null);

        int bookIndex = books.indexOf(targetBook);
        clickOn(tableView);
        type(KeyCode.DOWN, bookIndex);
        type(KeyCode.RIGHT, 6);

        press(KeyCode.ENTER).release(KeyCode.ENTER);
        write("20.99").press(KeyCode.ENTER).release(KeyCode.ENTER);

        clickOn("#updateButton");
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);

    }

    @Test
    @Order(3)
    public void testDeleteBookFlow() throws InterruptedException {
        TableView<Book> tableView = controller.getView().getTableView();
        ObservableList<Book> books = FXCollections.observableArrayList(new BooksDAO().getAll());
        Book targetBook = books.stream().filter(book -> book.getTitle().equals("Test Book")).findFirst().orElse(null);

        int bookIndex = books.indexOf(targetBook);
        clickOn(tableView);
        type(KeyCode.DOWN, bookIndex);

        clickOn("#deleteButton");
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);

    }

    @Test
    @Order(4)
    public void testSearchAndBackFunction() throws InterruptedException {
        clickOn("#searchBarTextField").write("Kesh");
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);


        clickOn("#returnButton");
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);

    }
}

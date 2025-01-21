package systemTesting;

import dao.AuthorsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.*;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import controller.ManagingAuthorsController;

import java.util.Calendar;
import java.util.GregorianCalendar;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ManageAuthorSystemTest extends ApplicationTest {

    private ManagingAuthorsController controller;

    @Override
    public void start(Stage stage) {
        Calendar calendar = new GregorianCalendar();
        UsersOfTheSystem user = new Librarian("Ilian", "Janopullo", calendar.getTime(), Gender.MALE, "ijanopullo22", "1234", Role.ADMIN, "ijanopullo22@gmail.com", "0695383073", 700);

        controller = new ManagingAuthorsController(stage, user);
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
    public void testAddAuthorFlow() throws InterruptedException {
        clickOn("#firstNameTextField").write("John");
        clickOn("#lastNameTextField").write("Doe");

        clickOn("#genderComboBox").press(KeyCode.DOWN).release(KeyCode.DOWN).press(KeyCode.ENTER).release(KeyCode.ENTER);

        clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);

    }

    @Test
    @Order(2)
    public void testEditAuthorFlow() throws InterruptedException {
        TableView<Author> tableView = controller.getView().getTableView();
        int authorIndex = -1;

        ObservableList<Author> list = FXCollections.observableArrayList(new AuthorsDAO().getAll());
        for (Author author : list) {
            if (author.getFirstName().equals("John") && author.getLastName().equals("Doe")) {
                authorIndex = list.indexOf(author);
                break;
            }
        }

        clickOn(tableView);
        type(KeyCode.DOWN, authorIndex);
        type(KeyCode.RIGHT, 1);

        press(KeyCode.ENTER).release(KeyCode.ENTER);
        write("Michael").press(KeyCode.ENTER).release(KeyCode.ENTER);
        Thread.sleep(1000);

        clickOn("#updateButton");
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);

    }

    @Test
    @Order(3)
    public void testDeleteAuthorFlow() throws InterruptedException {
        TableView<Author> tableView = controller.getView().getTableView();
        int authorIndex = -1;

        ObservableList<Author> list = FXCollections.observableArrayList(new AuthorsDAO().getAll());
        for (Author author : list) {
            if (author.getFirstName().equals("John") && author.getLastName().equals("Doe")) {
                authorIndex = list.indexOf(author);
                break;
            }
        }

        if (authorIndex == -1) {
            throw new AssertionError("Author 'John Doe' not found in the table.");
        }

        clickOn(tableView);
        type(KeyCode.DOWN, authorIndex);

        clickOn("#deleteButton");
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);

    }

    @Test
    @Order(4)
    public void testSearchAndBackFunction() throws InterruptedException {
        clickOn("#searchBarTextField").write("Ism");
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);

        TableView<Author> tableView = controller.getView().getTableView();

        clickOn("#returnButton");
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);

    }
}

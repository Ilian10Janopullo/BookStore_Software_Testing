package systemTesting;

import controller.ManageBillsController;
import dao.BillsDAO;
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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ManageBillsSystemTest extends ApplicationTest {

    private ManageBillsController controller;

    @Override
    public void start(Stage stage) {
        Calendar calendar = new GregorianCalendar();
        UsersOfTheSystem user = new Manager("John", "Doe", calendar.getTime(), Gender.MALE, "johndoe", "password", Role.MANAGER, "johndoe@example.com", "123456789", 1000);

        controller = new ManageBillsController(stage, user);
        stage.setScene(new javafx.scene.Scene(controller.getView(), 1520, 600));
        stage.show();
    }

    @BeforeEach
    public void setUp() throws InterruptedException {
        controller.getView().getTableViewOfBills().refresh();
        Thread.sleep(3000);
    }

    @Test
    @Order(1)
    public void testAddBillFlow() throws InterruptedException {

        clickOn("#fromDate").write("17/01/2025");
        clickOn("#toDate").write("24/01/2025");
        clickOn("#searchButton2");

        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);
    }

    @Test
    @Order(2)
    public void testEditBillFlow() throws InterruptedException {
        TableView<Bill> tableView = controller.getView().getTableViewOfBills();
        TableView<BooksOrdered> tableView1 = controller.getView().getTableViewOfBooksOrdered();
        ObservableList<Bill> bills = FXCollections.observableArrayList(new BillsDAO().getAll());

        clickOn(tableView).type(KeyCode.DOWN, 4).press(MouseButton.PRIMARY).release(MouseButton.PRIMARY);

        Thread.sleep(1500);

        clickOn(tableView1);
        type(KeyCode.RIGHT, 3).press(KeyCode.ENTER).release(KeyCode.ENTER);
        write("3").press(KeyCode.ENTER).release(KeyCode.ENTER);

        clickOn("#btnUpdate");
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);
    }

    @Test
    @Order(3)
    public void testDeleteBillFlow() throws InterruptedException {
        TableView<Bill> tableView = controller.getView().getTableViewOfBills();
        ObservableList<Bill> bills = FXCollections.observableArrayList(new BillsDAO().getAll());

        TableView<BooksOrdered> tableView1 = controller.getView().getTableViewOfBooksOrdered();

        int billIndex = bills.size()-1;

        clickOn(tableView);
        type(KeyCode.DOWN, billIndex).press(MouseButton.PRIMARY).release(MouseButton.PRIMARY);

        clickOn(tableView1).press(KeyCode.DOWN).release(KeyCode.DOWN).press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn("#btnRemove");

        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);
    }

    @Test
    @Order(4)
    public void testSearchBillFlow() throws InterruptedException {
        clickOn("#searchBarTf").write("71c5");
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);

        clickOn("#showAllButton");
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);
    }

    @Test
    @Order(5)
    public void testBackNavigation() throws InterruptedException {
        clickOn("#returnButton");
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);
    }
}

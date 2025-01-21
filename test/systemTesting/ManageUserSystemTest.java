package systemTesting;

import dao.UsersDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.*;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import controller.ManageUsersController;

import java.util.Calendar;
import java.util.GregorianCalendar;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ManageUserSystemTest extends ApplicationTest {

    private ManageUsersController controller;

    @Override
    public void start(Stage stage) {
        Calendar calendar = new GregorianCalendar();
        UsersOfTheSystem user = new Librarian("Ilian", "Janopullo", calendar.getTime(), Gender.MALE, "ijanopullo22", "1234", Role.ADMIN, "ijanopullo22@gmail.com", "0695383073", 700);

        controller = new ManageUsersController(stage, user);
        stage.setScene(new javafx.scene.Scene(controller.getView(), 1520, 600));
        stage.show();
    }

    @BeforeEach
    public void setUp() throws InterruptedException {
        controller.getView().getTableView().refresh();
        Thread.sleep(1000);
    }

    @Test
    @Order(1)
    public void testAddUserFlow() throws InterruptedException {

        clickOn("#firstNameTextField").write("John");
        clickOn("#middleNameTextField").write("Michael");
        clickOn("#lastNameTextField").write("Doe");

        clickOn("#genderComboBox").press(KeyCode.DOWN).release(KeyCode.DOWN).press(KeyCode.ENTER).release(KeyCode.ENTER);
        clickOn("#roleComboBox").press(KeyCode.DOWN).release(KeyCode.DOWN).press(KeyCode.ENTER).release(KeyCode.ENTER);;

        clickOn("#birthDateDayTextField").write("15");
        clickOn("#birthDateMonthTextField").write("06");
        clickOn("#birthDateYearTextField").write("1990");

        clickOn("#usernameTextField").write("johndoe");
        clickOn("#passwordTextField").write("Password@123");
        clickOn("#emailTextField").write("johndoe@test.com");
        clickOn("#phoneNumberTextField").write("0698765432");
        clickOn("#salaryTextField").write("4000");

        clickOn("#submitButton");
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(1000);

    }


    @Test
    @Order(2)
    public void testEditUserFlow() throws InterruptedException {
        clickOn(controller.getView().getTableView());
        int userIndex = -1;

        ObservableList<UsersOfTheSystem> list = FXCollections.observableArrayList(new UsersDAO().getAll());
        for(UsersOfTheSystem user : list){
            if(user.getUsername().equals("johndoe")){
                userIndex = list.indexOf(user);
                break;
            }
        }

        if(userIndex == -1){
            return;
        }

        type(KeyCode.DOWN, userIndex);
        type(KeyCode.RIGHT, 11);

        press(KeyCode.ENTER).release(KeyCode.ENTER);
        write("4500").press(KeyCode.ENTER).release(KeyCode.ENTER);

        Thread.sleep(1000);

        clickOn("#updateButton");
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);

    }



    @Test
    @Order(3)
    public void testDeleteUserFlow() throws InterruptedException {
        TableView<UsersOfTheSystem> tableView = controller.getView().getTableView();
        int userIndex = -1;

        ObservableList<UsersOfTheSystem> list = FXCollections.observableArrayList(new UsersDAO().getAll());
        for (UsersOfTheSystem user : list) {
            if (user.getUsername().equals("johndoe")) {
                userIndex = list.indexOf(user);
                break;
            }
        }

        if (userIndex == -1) {
            throw new AssertionError("User 'johndoe' not found in the table.");
        }


        clickOn(tableView);
        type(KeyCode.DOWN, userIndex);

        clickOn("#deleteButton");
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(1000);

    }

    @Test
    @Order(4)
    public void testSearchAndBackFunction() throws InterruptedException {

        clickOn("#searchBarTextField").write("Ili");
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);

        clickOn("#returnButton");
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);
    }


}

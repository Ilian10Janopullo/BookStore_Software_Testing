package systemTesting;

import javafx.collections.ObservableList;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import model.Gender;
import model.Librarian;
import model.Role;
import model.UsersOfTheSystem;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;
import controller.CheckUsersController;
import java.util.Calendar;
import java.util.GregorianCalendar;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CheckUsersSystemTest extends ApplicationTest {

    private CheckUsersController controller;

    @Override
    public void start(Stage stage) {
        Calendar calendar = new GregorianCalendar();
        UsersOfTheSystem user = new Librarian("Ilian", "Janopullo", calendar.getTime(), Gender.MALE, "ijanopullo22", "1234", Role.MANAGER, "ijanopullo22@gmail.com", "0695383073", 700);


        controller = new CheckUsersController(stage, user);
        stage.setScene(new javafx.scene.Scene(controller.getView(), 1520, 600));
        stage.show();
    }

    @BeforeEach
    public void setUp() {
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    @Order(1)
    public void testSearchResetAndBackFlow() throws InterruptedException {

        clickOn("#searchBarTextField").write("Alt");
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);

        clickOn("#resetButton");
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);


        clickOn("#returnButton");
        WaitForAsyncUtils.waitForFxEvents();
        Thread.sleep(2000);
    }
}

package systemTesting;

import controller.LoginController;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginSystemTest extends ApplicationTest {

    private LoginController loginController;

    @Override
    public void start(Stage stage) {
        loginController = new LoginController(stage);
        stage.setScene(new javafx.scene.Scene(loginController.getView(), 1525, 600));
        stage.show();
    }

    @BeforeEach
    public void setup() {
        clickOn("#enterUserName").eraseText(10);
        clickOn("#enterPassword").eraseText(10);
    }

    @Test
    @Order(1)
    public void testInvalidLogin() throws InterruptedException {
        clickOn("#enterUserName").write("ijanopullo23");
        clickOn("#enterPassword").write("@Ilian2003");
        clickOn("#enterBtn");

        Thread.sleep(3000);
    }

    @Test
    @Order(2)
    public void testAdminLogin() throws InterruptedException {
        clickOn("#enterUserName").write("ijanopullo22");
        clickOn("#enterPassword").write("@Ilian2003");
        clickOn("#enterBtn");

        Thread.sleep(3000); // Wait for 3 seconds
    }

    @Test
    @Order(3)
    public void testManagerLogin() throws InterruptedException {
        clickOn("#enterUserName").write("asamarxhiu22");
        clickOn("#enterPassword").write("@Arion2001");
        clickOn("#enterBtn");

        Thread.sleep(3000); // Wait for 3 seconds
    }

    @Test
    @Order(4)
    public void testLibrarianLogin() throws InterruptedException {
        clickOn("#enterUserName").write("eshehdula22");
        clickOn("#enterPassword").write("@Enkel2003");
        clickOn("#enterBtn");

        Thread.sleep(3000); // Wait for 3 seconds
    }
}

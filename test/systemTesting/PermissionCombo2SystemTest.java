package systemTesting;

import controller.PermissionCombo2Controller;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.UsersOfTheSystem;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;

import static org.mockito.Mockito.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PermissionCombo2SystemTest extends ApplicationTest {

    private PermissionCombo2Controller permissionCombo2Controller;
    @Mock private UsersOfTheSystem mockUser;
    @Mock private Stage mockStage;

    @Override
    public void start(Stage stage) {
        MockitoAnnotations.openMocks(this);
        permissionCombo2Controller = new PermissionCombo2Controller(stage, mockUser);
        stage.setScene(new javafx.scene.Scene(permissionCombo2Controller.getView(), 1525, 600));
        stage.show();
    }

    @BeforeEach
    public void setup() throws InterruptedException {
        reset(mockUser, mockStage);
        Thread.sleep(3000);
    }

    @Test
    @Order(2)
    public void testLogOutButton() throws InterruptedException {
        permissionCombo2Controller.getView().getLogout().fire();
        Thread.sleep(3000);
    }

    @Test
    @Order(1)
    public void testSellBooksButtonWithNoBooks() throws InterruptedException {
        permissionCombo2Controller.getView().getSellBooks().fire();
        Thread.sleep(3000);
    }

}

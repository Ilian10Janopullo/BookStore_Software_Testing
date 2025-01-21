package systemTesting;

import controller.PermissionCombo11Controller;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.UsersOfTheSystem;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;

import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PermissionCombo11SystemTest extends ApplicationTest {

    private PermissionCombo11Controller permissionCombo11Controller;
    @Mock private UsersOfTheSystem mockUser;
    @Mock private Stage mockStage;

    @Override
    public void start(Stage stage) {
        MockitoAnnotations.openMocks(this);
        permissionCombo11Controller = new PermissionCombo11Controller(stage, mockUser);
        stage.setScene(new javafx.scene.Scene(permissionCombo11Controller.getView(), 1525, 600));
        stage.show();
    }

    @BeforeEach
    public void setup() throws InterruptedException {
        reset(mockUser, mockStage);
        Thread.sleep(3000);
    }

    @Test
    @Order(5)
    public void testLogOutButton() throws InterruptedException {
        permissionCombo11Controller.getView().getLogout().fire();
        Thread.sleep(3000);
    }

    @Test
    @Order(1)
    public void testManageAuthorsButtonWithAuthors() throws InterruptedException {
        permissionCombo11Controller.getView().getManageAuthors().fire();
        Thread.sleep(3000);
    }

    @Test
    @Order(2)
    public void testManageBooksButtonWithAuthors() throws InterruptedException {
        permissionCombo11Controller.getView().getManageBooks().fire();
        Thread.sleep(3000);
    }

    @Test
    @Order(3)
    public void testManageBillsButtonWithBills() throws InterruptedException {
        permissionCombo11Controller.getView().getManageBills().fire();
        Thread.sleep(3000);
    }

    @Test
    @Order(4)
    public void testCheckUsersButtonWithUsers() throws InterruptedException {
        permissionCombo11Controller.getView().getCheckUsers().fire();
        Thread.sleep(3000);
    }
}

package systemTesting;

import controller.AdminMenuController;
import javafx.stage.Stage;
import model.UsersOfTheSystem;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;

import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminMenuSystemTest extends ApplicationTest {

    private AdminMenuController adminMenuController;
    @Mock private UsersOfTheSystem mockUser;
    @Mock private Stage mockStage;

    @Override
    public void start(Stage stage) {
        MockitoAnnotations.openMocks(this);
        adminMenuController = new AdminMenuController(stage, mockUser);
        stage.setScene(new javafx.scene.Scene(adminMenuController.getView(), 1525, 600));
        stage.show();
    }

    @BeforeEach
    public void setup() throws InterruptedException {
        reset(mockUser, mockStage);
        Thread.sleep(3000);
    }

    @Test
    @Order(1)
    public void testManageAuthorsButton() throws InterruptedException {
        adminMenuController.getView().getManageAuthors().fire();
        Thread.sleep(3000);
    }

    @Test
    @Order(2)
    public void testManageBooksButtonWithAuthors() throws InterruptedException {
        adminMenuController.getView().getManageBooks().fire();
        Thread.sleep(3000);
    }

    @Test
    @Order(3)
    public void testManageUsersButton() throws InterruptedException {
        adminMenuController.getView().getManageUsers().fire();
        Thread.sleep(3000);
    }

    @Test
    @Order(4)
    public void testSellBooksButtonWithBooks() throws InterruptedException {
        adminMenuController.getView().getSellBooks().fire();
        Thread.sleep(3000);
    }

    @Test
    @Order(5)
    public void testManageBillsButtonWithBills() throws InterruptedException {
        adminMenuController.getView().getManageBills().fire();
        Thread.sleep(3000);
    }

    @Test
    @Order(6)
    public void testControlPermissionsButton() throws InterruptedException {
        adminMenuController.getView().getControlPermissions().fire();
        Thread.sleep(3000);
    }

    @Test
    @Order(7)
    public void testLogOutButton() throws InterruptedException {
        adminMenuController.getView().getLogout().fire();
        Thread.sleep(3000);
    }
}

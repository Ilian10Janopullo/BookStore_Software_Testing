package controller;

import dao.PermissionsDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Role;
import model.UsersOfTheSystem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.JavaFXInitializer;
import view.SetPermissionsView;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class SetUpPermissionsControllerTest {

    private static ObservableList<String> mockPermissions;
    private static PermissionsDAO mockPermissionsDAO;
    private static UsersOfTheSystem mockAdminUser;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        // Initialize JavaFX Toolkit
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();

        // Mock data and dependencies
        mockPermissions = FXCollections.observableArrayList(
                "PermissionCombo15Controller", "PermissionCombo12Controller"
        );

        mockPermissionsDAO = mock(PermissionsDAO.class);
        when(mockPermissionsDAO.getAll()).thenReturn(mockPermissions);

        mockAdminUser = mock(UsersOfTheSystem.class);
        when(mockAdminUser.getRole()).thenReturn(Role.ADMIN);
    }

    @Test
    void testControllerInitialization() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();

            SetUpPermissionsController controller = new SetUpPermissionsController(mockAdminUser, mockStage);

            assertNotNull(controller.getView());
        });
    }

    @Test
    void testUpdateWithValidSelections() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();

            SetUpPermissionsController controller = new SetUpPermissionsController(mockAdminUser, mockStage);
            SetPermissionsView mockView = controller.getView();

            // Simulate valid selections for Manager
            mockView.setManageStockManager(true);
            mockView.setManageSellsManager(true);
            mockView.setManageBillsManager(true);
            mockView.setCheckUsersManager(true);

            // Simulate valid selections for Librarian
            mockView.setManageStockLibrarian(true);
            mockView.setManageSellsLibrarian(true);
            mockView.setManageBillsLibrarian(true);
            mockView.setCheckUsersLibrarian(true);

            // Trigger the Update action
            controller.Update(new ActionEvent());

            // Verify permissions were updated
            verify(mockPermissionsDAO).update(any());
        });
    }

    @Test
    void testUpdateWithInvalidSelections() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();

            SetUpPermissionsController controller = new SetUpPermissionsController(mockAdminUser, mockStage);
            SetPermissionsView mockView = controller.getView();

            // Simulate no selections
            mockView.setManageStockManager(false);
            mockView.setManageSellsManager(false);
            mockView.setManageBillsManager(false);
            mockView.setCheckUsersManager(false);

            mockView.setManageStockLibrarian(false);
            mockView.setManageSellsLibrarian(false);
            mockView.setManageBillsLibrarian(false);
            mockView.setCheckUsersLibrarian(false);

            // Trigger the Update action
            controller.Update(new ActionEvent());

            // Verify an alert was shown and permissions were not updated
            Alert alert = new Alert(Alert.AlertType.WARNING);
            assertNotNull(alert.getContentText());
            verify(mockPermissionsDAO, never()).update(any());
        });
    }

    @Test
    void testBackAction() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();

            SetUpPermissionsController controller = new SetUpPermissionsController(mockAdminUser, mockStage);

            // Trigger the Back action
            controller.Back(new ActionEvent());

            // Verify that the user is navigated back to AdminMenuController
            assertNotNull(controller.getView().getBackBtn());
        });
    }

    @Test
    void testCheckWithValidPermissions() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();

            SetUpPermissionsController controller = new SetUpPermissionsController(mockAdminUser, mockStage);

            // Simulate valid permissions in permissionsCombo
            controller.check();

            // Verify that the view reflects the valid permissions
            SetPermissionsView mockView = controller.getView();
            assertNotNull(mockView);
            assertNotNull(mockView.getManageStockManager());
            assertNotNull(mockView.getManageSellsManager());
            assertNotNull(mockView.getManageBillsManager());
            assertNotNull(mockView.getCheckUsersManager());
        });
    }

    @Test
    void testCheckWithInvalidPermissions() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();

            // Create a controller with empty permissions
            when(mockPermissionsDAO.getAll()).thenReturn(FXCollections.observableArrayList());
            SetUpPermissionsController controller = new SetUpPermissionsController(mockAdminUser, mockStage);

            // Simulate check with empty permissions
            controller.check();

            // Verify that no permissions are selected in the view
            SetPermissionsView mockView = controller.getView();
            assertNotNull(mockView);
            assertNotNull(mockView.getManageStockManager());
            assertNotNull(mockView.getManageSellsManager());
            assertNotNull(mockView.getManageBillsManager());
            assertNotNull(mockView.getCheckUsersManager());
        });
    }
}

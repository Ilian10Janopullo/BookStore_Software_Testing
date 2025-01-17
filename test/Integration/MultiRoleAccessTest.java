package Integration;

import controller.AdminMenuController;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MultiRoleAccessTest {
    private AdminMenuController adminMenuController;
    private Stage mockStage;

    @BeforeEach
    void setup() throws Exception {
        // Ensure JavaFX is initialized
        if (!Platform.isFxApplicationThread()) {
            CountDownLatch latch = new CountDownLatch(1);
            Platform.startup(() -> latch.countDown());
            latch.await(); // Wait for JavaFX to initialize
        }

        // Mock the Stage
        mockStage = mock(Stage.class);
    }

    @Test
    void testAdminAccessToAllFunctions() throws Exception {
        // Create an Admin user
        UsersOfTheSystem admin = new Admin("John", "Doe", null, Gender.MALE, "admin", "admin123", Role.ADMIN, "admin@example.com", "123456789", 1000);

        // Initialize the controller on the JavaFX Application Thread
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                adminMenuController = new AdminMenuController(mockStage, admin);

                // Test Admin access to all functions
                assertDoesNotThrow(() -> adminMenuController.ManageUsers());
                assertDoesNotThrow(() -> adminMenuController.ManageBooks());
            } finally {
                latch.countDown();
            }
        });
        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("Test timed out");
        }
    }

    @Test
    void testManagerRestrictedAccess() throws Exception {
        // Create a Manager user
        UsersOfTheSystem manager = new Manager("Jane", "Smith", null, Gender.FEMALE, "manager", "manager123", Role.MANAGER, "manager@example.com", "987654321", 800);

        // Initialize the controller on the JavaFX Application Thread
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                adminMenuController = new AdminMenuController(mockStage, manager);

                // Test Manager restricted access
                assertThrows(Exception.class, () -> adminMenuController.ManageUsers());
                assertDoesNotThrow(() -> adminMenuController.ManageBooks());
            } finally {
                latch.countDown();
            }
        });
        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("Test timed out");
        }
    }
}

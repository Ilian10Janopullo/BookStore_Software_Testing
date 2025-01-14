package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.UsersOfTheSystem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BackFunctionMenuTest {

    private static Stage mockStage;
    private static UsersOfTheSystem mockUser;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        // Initialize JavaFX Toolkit
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();

        // Mock dependencies
        mockStage = mock(Stage.class);
        mockUser = mock(UsersOfTheSystem.class);
    }

    @Test
    void testMainMenuBackWithValidPermissions() throws Exception {
        ObservableList<String> permissionsCombo = FXCollections.observableArrayList(
                "PermissionCombo1Controller",
                "PermissionCombo5Controller",
                "PermissionCombo15Controller"
        );

        for (int i = 0; i < permissionsCombo.size(); i++) {
            int index = i; // To use inside lambda
            executeOnFXThread(() -> {
                BackFunctionMenu.mainMenuBack(index, permissionsCombo, mockStage, mockUser);

                // Verify that the scene was set
                verify(mockStage, times(1)).setScene(any());
            });
        }
    }

    @Test
    void testMainMenuBackWithInvalidPermission() throws Exception {
        ObservableList<String> permissionsCombo = FXCollections.observableArrayList("InvalidController");

        executeOnFXThread(() -> {
            assertThrows(IllegalStateException.class, () ->
                    BackFunctionMenu.mainMenuBack(0, permissionsCombo, mockStage, mockUser));
        });
    }

    @Test
    void testMainMenuBackWithEmptyPermissions() throws Exception {
        ObservableList<String> permissionsCombo = FXCollections.observableArrayList();

        executeOnFXThread(() -> {
            assertThrows(IndexOutOfBoundsException.class, () ->
                    BackFunctionMenu.mainMenuBack(0, permissionsCombo, mockStage, mockUser));
        });
    }

    /**
     * Utility method to execute a block of code on the JavaFX Application Thread
     */
    private void executeOnFXThread(Runnable action) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                latch.countDown();
            }
        });
        latch.await(); // Wait for the JavaFX thread to finish
    }
}

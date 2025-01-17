package Integration;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.UsersOfTheSystem;
import org.junit.jupiter.api.Test;
import util.JavaFXInitializer;
import controller.BackFunctionMenu;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

class BackFunctionMenuTest {

    @Test
    void testNavigateBackWithPermissions() throws Exception {
        // Initialize JavaFX runtime
        JavaFXInitializer initializer = new JavaFXInitializer();
        initializer.init();

        // Mock Stage and User
        Stage stage = mock(Stage.class);
        UsersOfTheSystem user = mock(UsersOfTheSystem.class);

        ObservableList<String> permissionsCombo = FXCollections.observableArrayList(
                "PermissionCombo1Controller",
                "PermissionCombo2Controller"
        );

        // Run the test on the JavaFX thread
        runOnJavaFXThread(() -> {
            BackFunctionMenu.mainMenuBack(0, permissionsCombo, stage, user);

            // Verify navigation to the correct controller
            verify(stage.getScene(), times(1)).setRoot(any());
        });
    }
    private void runOnJavaFXThread(Runnable task) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                task.run();
            } finally {
                latch.countDown();
            }
        });
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new Exception("Timeout while waiting for JavaFX task to complete");
        }
    }
}

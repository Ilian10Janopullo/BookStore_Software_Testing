package unitTesting.view;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import view.SetPermissionsView;

import static org.junit.jupiter.api.Assertions.*;

class SetPermissionsViewTest extends ApplicationTest {

    private SetPermissionsView setPermissionsView;

    @Override
    public void start(Stage stage) {
        Platform.runLater(() -> {
            setPermissionsView = new SetPermissionsView();
            Scene scene = new Scene(setPermissionsView, 800, 600);
            stage.setScene(scene);
            stage.show();
        });
    }

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> setPermissionsView = new SetPermissionsView());
    }

    @Test
    void testComponentInitialization() {
        Platform.runLater(() -> {
            // Test if all buttons and checkboxes are initialized
            assertNotNull(setPermissionsView.getBackBtn());
            assertNotNull(setPermissionsView.getUpdateBtn());
            assertNotNull(setPermissionsView.getCheckUsersLibrarian());
            assertNotNull(setPermissionsView.getCheckUsersManager());
            assertNotNull(setPermissionsView.getManageBillsLibrarian());
            assertNotNull(setPermissionsView.getManageBillsManager());
            assertNotNull(setPermissionsView.getManageSellsLibrarian());
            assertNotNull(setPermissionsView.getManageSellsManager());
            assertNotNull(setPermissionsView.getManageStockLibrarian());
            assertNotNull(setPermissionsView.getManageStockManager());
        });
    }

    @Test
    void testDefaultCheckboxStates() {
        Platform.runLater(() -> {
            // Test default state of all checkboxes
            assertFalse(setPermissionsView.getCheckUsersLibrarian().isSelected());
            assertFalse(setPermissionsView.getCheckUsersManager().isSelected());
            assertFalse(setPermissionsView.getManageBillsLibrarian().isSelected());
            assertFalse(setPermissionsView.getManageBillsManager().isSelected());
            assertFalse(setPermissionsView.getManageSellsLibrarian().isSelected());
            assertFalse(setPermissionsView.getManageSellsManager().isSelected());
            assertFalse(setPermissionsView.getManageStockLibrarian().isSelected());
            assertFalse(setPermissionsView.getManageStockManager().isSelected());
        });
    }

    @Test
    void testSetAndGetCheckboxes() {
        Platform.runLater(() -> {
            // Test setting and getting values for each checkbox
            setPermissionsView.setCheckUsersLibrarian(true);
            assertTrue(setPermissionsView.getCheckUsersLibrarian().isSelected());

            setPermissionsView.setCheckUsersManager(true);
            assertTrue(setPermissionsView.getCheckUsersManager().isSelected());

            setPermissionsView.setManageBillsLibrarian(true);
            assertTrue(setPermissionsView.getManageBillsLibrarian().isSelected());

            setPermissionsView.setManageBillsManager(true);
            assertTrue(setPermissionsView.getManageBillsManager().isSelected());

            setPermissionsView.setManageSellsLibrarian(true);
            assertTrue(setPermissionsView.getManageSellsLibrarian().isSelected());

            setPermissionsView.setManageSellsManager(true);
            assertTrue(setPermissionsView.getManageSellsManager().isSelected());

            setPermissionsView.setManageStockLibrarian(true);
            assertTrue(setPermissionsView.getManageStockLibrarian().isSelected());

            setPermissionsView.setManageStockManager(true);
            assertTrue(setPermissionsView.getManageStockManager().isSelected());
        });
    }

    @Test
    void testButtonProperties() {
        Platform.runLater(() -> {
            // Verify button properties
            assertEquals(90, setPermissionsView.getBackBtn().getPrefWidth());
            assertNotNull(setPermissionsView.getBackBtn().getGraphic());

            assertEquals(90, setPermissionsView.getUpdateBtn().getPrefWidth());
            assertNotNull(setPermissionsView.getUpdateBtn().getGraphic());
        });
    }

    @Test
    void testLayoutProperties() {
        Platform.runLater(() -> {
            // Verify layout properties
            assertEquals(Pos.CENTER, ((VBox) setPermissionsView.getCenter()).getAlignment());
            assertEquals("-fx-font-family: 'Times New Roman' ; -fx-font-size: 16; -fx-font-weight: BOLD;",
                    setPermissionsView.getStyle());
        });
    }
}

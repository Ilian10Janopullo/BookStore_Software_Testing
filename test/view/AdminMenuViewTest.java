package view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class AdminMenuViewTest extends ApplicationTest {

    private AdminMenuView adminMenuView;

    @Override
    public void start(Stage stage) {
        Platform.runLater(() -> {
            adminMenuView = new AdminMenuView(stage);
            Scene scene = new Scene(adminMenuView, 800, 600);
            stage.setScene(scene);
            stage.show();
        });
    }

    @BeforeEach
    void setup() {
        Platform.runLater(() -> adminMenuView = new AdminMenuView(new Stage()));
    }

    @Test
    void testButtonInitialization() {
        // Assert button dimensions
        assertEquals(200, adminMenuView.getSellBooks().getPrefWidth());
        assertEquals(200, adminMenuView.getSellBooks().getPrefHeight());
        assertEquals(100, adminMenuView.getLogout().getPrefWidth());
        assertEquals(100, adminMenuView.getLogout().getPrefHeight());

        // Assert button text (empty since buttons use icons)
        assertEquals("", adminMenuView.getSellBooks().getText());
        assertEquals("", adminMenuView.getLogout().getText());
    }

    @Test
    void testLayoutSetup() {
        // Assert the center layout is an HBox
        assertTrue(adminMenuView.getCenter() instanceof javafx.scene.layout.HBox);

        // Assert the bottom layout is the logout button
        assertEquals(adminMenuView.getLogout(), adminMenuView.getBottom());
    }

    @Test
    void testGetterMethods() {
        // Assert getters return correct buttons
        assertNotNull(adminMenuView.getLogout());
        assertNotNull(adminMenuView.getManageBooks());
        assertNotNull(adminMenuView.getManageAuthors());
        assertNotNull(adminMenuView.getManageUsers());
        assertNotNull(adminMenuView.getSellBooks());
        assertNotNull(adminMenuView.getManageBills());
        assertNotNull(adminMenuView.getControlPermissions());
    }

    @Test
    void testButtonIcons() {
        // Assert that buttons have icons (ImageView)
        assertTrue(adminMenuView.getLogout().getGraphic() instanceof javafx.scene.image.ImageView);
        assertTrue(adminMenuView.getSellBooks().getGraphic() instanceof javafx.scene.image.ImageView);
        assertTrue(adminMenuView.getManageBooks().getGraphic() instanceof javafx.scene.image.ImageView);
        assertTrue(adminMenuView.getManageAuthors().getGraphic() instanceof javafx.scene.image.ImageView);
        assertTrue(adminMenuView.getManageUsers().getGraphic() instanceof javafx.scene.image.ImageView);
        assertTrue(adminMenuView.getManageBills().getGraphic() instanceof javafx.scene.image.ImageView);
        assertTrue(adminMenuView.getControlPermissions().getGraphic() instanceof javafx.scene.image.ImageView);
    }

    @Test
    void testStyleApplication() {
        // Assert the overall style of the BorderPane
        String expectedStyle = "-fx-font-family: 'Times New Roman' ; -fx-font-size: 20; -fx-font-weight: BOLD ;-fx-border-width: 2 ; -fx-border-color: BLACK;";
        assertEquals(expectedStyle, adminMenuView.getStyle());
    }
}

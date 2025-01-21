package unitTesting.view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import view.LoginView;

import static org.junit.jupiter.api.Assertions.*;

class LoginViewTest extends ApplicationTest {

    private LoginView loginView;

    @Override
    public void start(Stage stage) {
        Platform.runLater(() -> {
            loginView = new LoginView();
            Scene scene = new Scene(loginView, 400, 300);
            stage.setScene(scene);
            stage.show();
        });
    }

    @BeforeEach
    void setup() {
        Platform.runLater(() -> loginView = new LoginView());
    }

    @Test
    void testComponentInitialization() {
        // Verify components are initialized
        assertNotNull(loginView.getEnterBtn(), "Enter button should not be null");
        assertNotNull(loginView.getEnterUserName(), "Username field should not be null");
        assertNotNull(loginView.getEnterPassword(), "Password field should not be null");
    }

    @Test
    void testButtonProperties() {
        // Verify button properties
        assertEquals(100, loginView.getEnterBtn().getPrefWidth(), "Enter button should have a width of 100");
        assertNotNull(loginView.getEnterBtn().getGraphic(), "Enter button should have a graphic (icon)");
        assertEquals(javafx.scene.control.ContentDisplay.RIGHT, loginView.getEnterBtn().getContentDisplay(), "Enter button should display content on the right");
    }

    @Test
    void testLayoutProperties() {
        // Verify layout properties
        assertEquals(10, loginView.getHgap(), "GridPane horizontal gap should be 10");
        assertEquals(10, loginView.getVgap(), "GridPane vertical gap should be 10");
        assertEquals(javafx.geometry.Pos.CENTER, loginView.getAlignment(), "GridPane alignment should be centered");
    }

    @Test
    void testStyles() {
        // Verify styles
        String expectedStyle = "-fx-font-family: 'Times New Roman' ; -fx-font-size: 17 ; -fx-font-weight : BOLD ; -fx-border-width: 2 ; -fx-border-color: BLACK;";
        assertEquals(expectedStyle, loginView.getStyle(), "GridPane style should match the expected style");
    }

    @Test
    void testGetterMethods() {
        Platform.runLater(() -> {
            // Set text in text fields
            loginView.setEnterUserName("TestUser");
            loginView.setEnterPassword("TestPassword");

            // Assert getters return correct values
            assertEquals("TestUser", loginView.getEnterUserName(), "Username getter should return the correct value");
            assertEquals("TestPassword", loginView.getEnterPassword(), "Password getter should return the correct value");
        });
    }

    @Test
    void testSetterMethods() {
        Platform.runLater(() -> {
            // Set text in text fields
            loginView.setEnterUserName("TestUser");
            loginView.setEnterPassword("TestPassword");

            // Clear fields using setters
            loginView.setEnterUserName();
            loginView.setEnterPassword();

            // Assert fields are cleared
            assertEquals("", loginView.getEnterUserName(), "Username field should be cleared");
            assertEquals("", loginView.getEnterPassword(), "Password field should be cleared");
        });
    }
}

//Added to 2 new setters for the username and password in which I can pass any argument and use for testing!

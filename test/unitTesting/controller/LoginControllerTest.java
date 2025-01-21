package unitTesting.controller;

import controller.LoginController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.JavaFXInitializer;
import view.LoginView;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoginControllerTest {

    private static LoginController controller;
    private static ObservableList<Object> mockUsers;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        new JavaFXInitializer().init(); // Initialize JavaFX for UI components
        mockUsers = FXCollections.observableArrayList();

        // Create a real instance of LoginController
        controller = new LoginController(new javafx.stage.Stage());
        mockUsers.add(new Admin("Admin", "User", new Date(), model.Gender.MALE, "admin", "password", Role.ADMIN, "admin@example.com", "1234567890", 5000));
        mockUsers.add(new Manager("Manager", "User", new Date(), model.Gender.FEMALE, "manager", "password123", Role.MANAGER, "manager@example.com", "0987654321", 4000));
        mockUsers.add(new Librarian("Librarian", "User", new Date(), Gender.MALE, "librarian", "libPass", Role.LIBRARIAN, "librarian@example.com", "1122334455", 3000));
    }

    @ParameterizedTest
    @DisplayName("Test Enter() with Valid Credentials")
    @CsvSource({
            "admin, password, true",
            "manager, password123, true",
            "librarian, libPass, true"
    })
    void testEnterValid(String username, String password, boolean expected) {
        Platform.runLater(() -> {
            // Mock view behavior
            LoginView mockView = mock(LoginView.class);
            when(mockView.getEnterUserName()).thenReturn(username);
            when(mockView.getEnterPassword()).thenReturn(password);
            doNothing().when(mockView).setEnterPassword();
            doNothing().when(mockView).setEnterUserName();

            controller = new LoginController(new javafx.stage.Stage());
            controller.getView().getEnterBtn().setOnAction(e -> controller.Enter());
            boolean actual = false;

            for (Object userObj : mockUsers) {
                if (userObj instanceof Admin admin && username.equals(admin.getUsername()) && password.equals(admin.getPassword())) {
                    actual = true;
                    break;
                } else if (userObj instanceof Manager manager && username.equals(manager.getUsername()) && password.equals(manager.getPassword())) {
                    actual = true;
                    break;
                } else if (userObj instanceof Librarian librarian && username.equals(librarian.getUsername()) && password.equals(librarian.getPassword())) {
                    actual = true;
                    break;
                }
            }

            assertEquals(expected, actual);
        });
    }

    @ParameterizedTest
    @DisplayName("Test Enter() with Invalid Credentials")
    @CsvSource({
            "admin, wrongpass, false",
            "unknown, password123, false",
            "librarian, wronglibPass, false"
    })
    void testEnterInvalid(String username, String password, boolean expected) {
        Platform.runLater(() -> {
            // Mock view behavior
            LoginView mockView = mock(LoginView.class);
            when(mockView.getEnterUserName()).thenReturn(username);
            when(mockView.getEnterPassword()).thenReturn(password);
            doNothing().when(mockView).setEnterPassword();
            doNothing().when(mockView).setEnterUserName();

            controller = new LoginController(new javafx.stage.Stage());
            controller.getView().getEnterBtn().setOnAction(e -> controller.Enter());
            boolean actual = false;

            for (Object userObj : mockUsers) {
                if (userObj instanceof Admin admin && username.equals(admin.getUsername()) && password.equals(admin.getPassword())) {
                    actual = true;
                    break;
                } else if (userObj instanceof Manager manager && username.equals(manager.getUsername()) && password.equals(manager.getPassword())) {
                    actual = true;
                    break;
                } else if (userObj instanceof Librarian librarian && username.equals(librarian.getUsername()) && password.equals(librarian.getPassword())) {
                    actual = true;
                    break;
                }
            }

            assertEquals(expected, actual);
        });
    }

    @ParameterizedTest
    @DisplayName("Test Enter() with Inactive User")
    @CsvSource({
            "admin, password, false",
            "manager, password123, false",
            "librarian, libPass, false"
    })
    void testEnterInactive(String username, String password, boolean expected) {
        Platform.runLater(() -> {
            // Add inactive user to mockUsers
            Admin inactiveAdmin = new Admin("Admin", "Inactive", new Date(), model.Gender.MALE, username, password, Role.ADMIN, "inactive@example.com", "0000000000", 5000);
            inactiveAdmin.setStatus("PASIVE");
            mockUsers.add(inactiveAdmin);

            // Mock view behavior
            LoginView mockView = mock(LoginView.class);
            when(mockView.getEnterUserName()).thenReturn(username);
            when(mockView.getEnterPassword()).thenReturn(password);
            doNothing().when(mockView).setEnterPassword();
            doNothing().when(mockView).setEnterUserName();

            controller = new LoginController(new javafx.stage.Stage());
            controller.getView().getEnterBtn().setOnAction(e -> controller.Enter());
            boolean actual = false;

            for (Object userObj : mockUsers) {
                if (userObj instanceof Admin admin && username.equals(admin.getUsername()) && password.equals(admin.getPassword()) && admin.getStatus() == Status.ACTIVE) {
                    actual = true;
                    break;
                } else if (userObj instanceof Manager manager && username.equals(manager.getUsername()) && password.equals(manager.getPassword()) && manager.getStatus() == Status.ACTIVE) {
                    actual = true;
                    break;
                } else if (userObj instanceof Librarian librarian && username.equals(librarian.getUsername()) && password.equals(librarian.getPassword()) && librarian.getStatus() == Status.ACTIVE) {
                    actual = true;
                    break;
                }
            }

            assertEquals(expected, actual);
        });
    }
}

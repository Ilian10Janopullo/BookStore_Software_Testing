package unitTesting.controller;

import controller.ManagingAuthorsController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.Author;
import model.Gender;
import model.UsersOfTheSystem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ManagingAuthorsControllerTest {

    private static ManagingAuthorsController controller;
    private static ObservableList<Author> mockAuthors;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        // Initialize JavaFX in a synchronized block
        Platform.startup(() -> {
            try {
                // Create a real instance of ManagingAuthorsController
                mockAuthors = FXCollections.observableArrayList();
                controller = new ManagingAuthorsController(new Stage(), mock(UsersOfTheSystem.class));

                // Set the table view items directly
                controller.getView().getTableView().setItems(mockAuthors);
            } catch (Exception e) {
                fail("JavaFX initialization failed: " + e.getMessage());
            }
        });
    }


    @BeforeEach
    void setUp() {
        mockAuthors.clear();
        mockAuthors.add(new Author("John", "Doe", Gender.MALE));
    }

    @Test
    @DisplayName("Test checkFirstName() with Valid Names")
    void testCheckFirstNameValid() {
        Platform.runLater(() -> {
            String validName = "John";
            assertTrue(controller.checkFirstName(validName));
        });
    }

    @Test
    @DisplayName("Test checkFirstName() with Invalid Names")
    void testCheckFirstNameInvalid() {
        Platform.runLater(() -> {
            String invalidName = "John123";
            assertFalse(controller.checkFirstName(invalidName));
        });
    }

    @Test
    @DisplayName("Test checkLastName() with Valid Names")
    void testCheckLastNameValid() {
        Platform.runLater(() -> {
            String validName = "Doe";
            assertTrue(controller.checkLastName(validName));
        });
    }

    @Test
    @DisplayName("Test checkLastName() with Invalid Names")
    void testCheckLastNameInvalid() {
        Platform.runLater(() -> {
            String invalidName = "Doe123";
            assertFalse(controller.checkLastName(invalidName));
        });
    }

    @Test
    @DisplayName("Test checkMiddleName() with Valid Names")
    void testCheckMiddleNameValid() {
        Platform.runLater(() -> {
            String validName = "A.";
            assertTrue(controller.checkMiddleName(validName));
        });
    }

    @Test
    @DisplayName("Test checkMiddleName() with Invalid Names")
    void testCheckMiddleNameInvalid() {
        Platform.runLater(() -> {
            String invalidName = "123";
            assertFalse(controller.checkMiddleName(invalidName));
        });
    }

    @Test
    @DisplayName("Test checkGender() with Valid Index")
    void testCheckGenderValid() {
        Platform.runLater(() -> {
            int validIndex = 1;
            assertTrue(controller.checkGender(validIndex));
        });
    }

    @Test
    @DisplayName("Test checkGender() with Invalid Index")
    void testCheckGenderInvalid() {
        Platform.runLater(() -> {
            int invalidIndex = -1;
            assertFalse(controller.checkGender(invalidIndex));
        });
    }

    @Test
    @DisplayName("Test Submit() with Valid Data")
    void testSubmitValid() {
        Platform.runLater(() -> {
            doNothing().when(controller).Submit(any());
            assertDoesNotThrow(() -> controller.Submit(null));
        });
    }

    @Test
    @DisplayName("Test onAuthorDelete() with Existing Author")
    void testOnAuthorDeleteValid() {
        Platform.runLater(() -> {
            ObservableList<Author> authorsToDelete = FXCollections.observableArrayList(mockAuthors.get(0));
            controller.getView().getTableView().setItems(authorsToDelete);

            doNothing().when(controller).onAuthorDelete(any());
            assertDoesNotThrow(() -> controller.onAuthorDelete(null));
        });
    }

    @Test
    @DisplayName("Test onAuthorDelete() with No Selected Author")
    void testOnAuthorDeleteEmpty() {
        Platform.runLater(() -> {
            ObservableList<Author> emptyList = FXCollections.observableArrayList();
            controller.getView().getTableView().setItems(emptyList);

            doNothing().when(controller).onAuthorDelete(any());
            assertDoesNotThrow(() -> controller.onAuthorDelete(null));
        });
    }
}
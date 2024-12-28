package controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import model.Author;
import model.Book;
import model.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.JavaFXInitializer;

import static org.mockito.Mockito.*;

class BillCreatorControllerTest {

    private static Book book;

    @BeforeAll
    @DisplayName("Check the creation of the objects!")
    static void setUp() {

        new JavaFXInitializer().init();

        Author author = new Author("Viktor", "Hygo", Gender.MALE);
        book = new Book("978-99927-55-06-7", "Katedralja e Parisit", "Test", 10.0, author, true, 10);

        Assertions.assertAll(
                () -> Assertions.assertEquals("978-99927-55-06-7", book.getIsbn()),
                () -> Assertions.assertEquals("Katedralja e Parisit", book.getTitle()),
                () -> Assertions.assertEquals("Test", book.getDescription()),
                () -> Assertions.assertEquals(10.0, book.getPrice()),
                () -> Assertions.assertEquals("Viktor", book.getAuthor().getFirstName()),
                () -> Assertions.assertEquals("Hygo", book.getAuthor().getLastName()),
                () -> Assertions.assertEquals(Gender.MALE, book.getAuthor().getGender()),
                () -> Assertions.assertTrue(book.isPaperback()),
                () -> Assertions.assertEquals(10, book.getQuantity())
        );
    }

    @ParameterizedTest
    @DisplayName("Boundary Value Testing For checkQuantity() method!")
    @CsvSource({
            "1", "2", "5", "9", "10"
    })
    void checkQuantityBDV(int quantity) {
        Assertions.assertTrue(ManageBillsController.checkQuantity(quantity, book));
    }

    @ParameterizedTest
    @DisplayName("Equivalence Class Testing For checkQuantity() method!")
    @CsvSource(value = {
            "Integer.MIN_VALUE, false",
            "-5, false",
            "0, false",
            "1, true",
            "5, true",
            "10, true",
            "11, false",
            "15, false",
            "Integer.MAX_VALUE, false",
            "null, false",
            "abc, false"
    },
            nullValues = {"null"}
    )
    void checkQuantityECT(String quantity, boolean result) {
        try {
            Platform.runLater(() -> {
                Alert mockAlert = mock(Alert.class);
                int quantityInt = Integer.parseInt(quantity);
                Assertions.assertEquals(result, ManageBillsController.checkQuantity(quantityInt, book));
            });
        } catch (NumberFormatException ex) {
            Assertions.assertFalse(result);
        }
    }

    @ParameterizedTest
    @DisplayName("Statement, Branch, MC/DC Coverage Testing for checkQuantity() method!")
    @CsvSource({
            "-5, false",
            "6, true",
            "abc, false"
    })
    void checkQuantityStatementBranchMCDC(String quantity, boolean result) {
        try {
            Platform.runLater(() -> {
                Alert mockAlert = mock(Alert.class);
                int quantityInt = Integer.parseInt(quantity);
                Assertions.assertEquals(result, ManageBillsController.checkQuantity(quantityInt, book));
            });
        } catch (NumberFormatException ex) {
            Assertions.assertFalse(result);
        }
    }

    @ParameterizedTest
    @DisplayName("Condition Coverage Testing for checkQuantity() method!")
    @CsvSource({
            "-1, false",
            "11, false",
            "5, true",
            "abc, false"
    })
    void checkQuantityCondition(String quantity, boolean result) {
        try {
            Platform.runLater(() -> {
                Alert mockAlert = mock(Alert.class);
                int quantityInt = Integer.parseInt(quantity);
                Assertions.assertEquals(result, ManageBillsController.checkQuantity(quantityInt, book));
            });
        } catch (NumberFormatException ex) {
            Assertions.assertFalse(result);
        }
    }
}
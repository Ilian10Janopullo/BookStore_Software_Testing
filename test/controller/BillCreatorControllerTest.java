package controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import model.Book;
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
    static void setUp() {

        new JavaFXInitializer().init();
        book = mock(Book.class, CALLS_REAL_METHODS);
        when(book.getQuantity()).thenReturn(10);
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
            "-2147483648, false",
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
    @DisplayName("Statement, Branch, Coverage Testing for checkQuantity() method!")
    @CsvSource({
            "-5, false",
            "6, true",
            "abc, false"
    })
    void checkQuantityStatementBranch(String quantity, boolean result) {
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

    @ParameterizedTest
    @DisplayName("MC/DC Coverage Testing for checkQuantity() method!")
    @CsvSource({
            "11, false",
            "5, true"
    })
    void checkQuantityMCDC(String quantity, boolean result) {
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
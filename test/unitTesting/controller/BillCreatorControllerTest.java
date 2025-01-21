package unitTesting.controller;

import controller.BillCreatorController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Book;
import model.BooksOrdered;
import model.UsersOfTheSystem;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.JavaFXInitializer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BillCreatorControllerTest {

    private static Book book;
    private BillCreatorController controller;
    private Stage mockStage;
    private UsersOfTheSystem mockUser;

    @BeforeAll
    static void setUpClass() {
        new JavaFXInitializer().init();
        book = mock(Book.class, CALLS_REAL_METHODS);
        when(book.getQuantity()).thenReturn(10);
    }

    @BeforeEach
    void setUp() {
        mockStage = mock(Stage.class);
        mockUser = mock(UsersOfTheSystem.class);
        controller = new BillCreatorController(mockStage, mockUser);
    }

    @ParameterizedTest
    @DisplayName("Boundary Value Testing For checkQuantity() method!")
    @CsvSource({
            "1", "2", "5", "9", "10"
    })
    void checkQuantityBDV(int quantity) {
        Assertions.assertTrue(BillCreatorController.checkQuantity(quantity, book));
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
                Assertions.assertEquals(result, BillCreatorController.checkQuantity(quantityInt, book));
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
                Assertions.assertEquals(result, BillCreatorController.checkQuantity(quantityInt, book));
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
                Assertions.assertEquals(result, BillCreatorController.checkQuantity(quantityInt, book));
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
                Assertions.assertEquals(result, BillCreatorController.checkQuantity(quantityInt, book));
            });
        } catch (NumberFormatException ex) {
            Assertions.assertFalse(result);
        }
    }

    @Test
    @DisplayName("Test Submit() with Valid Orders")
    void testSubmitValidOrders() {
        Platform.runLater(() -> {
            BooksOrdered mockOrder = mock(BooksOrdered.class);
            when(mockOrder.getIsbn()).thenReturn("978-3-16-148410-0");
            when(mockOrder.getQuantityToOrderOfBookOrdered()).thenReturn(2);

            ObservableList<BooksOrdered> mockOrders = FXCollections.observableArrayList(mockOrder);
            controller.getView().getTableViewOfBooksToOrder().setItems(mockOrders);

            assertDoesNotThrow(() -> controller.Submit(null));
        });
    }

    @Test
    @DisplayName("Test Submit() with Invalid Orders")
    void testSubmitInvalidOrders() {
        Platform.runLater(() -> {
            BooksOrdered mockOrder = mock(BooksOrdered.class);
            when(mockOrder.getIsbn()).thenReturn("978-3-16-148410-0");
            when(mockOrder.getQuantityToOrderOfBookOrdered()).thenReturn(20); // Exceeds book quantity

            ObservableList<BooksOrdered> mockOrders = FXCollections.observableArrayList(mockOrder);
            controller.getView().getTableViewOfBooksToOrder().setItems(mockOrders);

            assertDoesNotThrow(() -> controller.Submit(null));
        });
    }

    @Test
    @DisplayName("Test Back() Navigation")
    void testBackNavigation() {
        Platform.runLater(() -> {
            assertDoesNotThrow(() -> controller.Back(null));
        });
    }

    @Test
    @DisplayName("Test onBookRemove() with Orders")
    void testOnBookRemoveWithOrders() {
        Platform.runLater(() -> {
            BooksOrdered mockOrder = mock(BooksOrdered.class);
            when(mockOrder.getIsbn()).thenReturn("978-3-16-148410-0");

            ObservableList<BooksOrdered> mockOrders = FXCollections.observableArrayList(mockOrder);
            controller.getView().getTableViewOfBooksToOrder().setItems(mockOrders);

            assertDoesNotThrow(() -> controller.onBookRemove(null));
        });
    }

    @Test
    @DisplayName("Test onBookRemove() with Empty Orders")
    void testOnBookRemoveWithEmptyOrders() {
        Platform.runLater(() -> {
            ObservableList<BooksOrdered> emptyOrders = FXCollections.observableArrayList();
            controller.getView().getTableViewOfBooksToOrder().setItems(emptyOrders);

            assertDoesNotThrow(() -> controller.onBookRemove(null));
        });
    }
}

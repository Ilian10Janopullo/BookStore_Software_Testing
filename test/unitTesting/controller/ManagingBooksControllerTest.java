package unitTesting.controller;

import controller.ManagingAuthorsController;
import controller.ManagingBooksController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Book;
import model.Role;
import model.UsersOfTheSystem;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.JavaFXInitializer;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

class ManagingBooksControllerTest {

    private static ObservableList<Book> books;
    private static Stage mockStage;
    private static UsersOfTheSystem mockUser;
    private static ManagingBooksController controller;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {

        Platform.startup(() -> {
            new JavaFXInitializer().init();
            books = FXCollections.observableArrayList();
            books.add(mock(Book.class, CALLS_REAL_METHODS));
            when(mock(Book.class, CALLS_REAL_METHODS).getIsbn()).thenReturn("9780-59-65-2068-8");

            mockStage = mock(Stage.class);
            mockUser = mock(UsersOfTheSystem.class);
            when(mockUser.getRole()).thenReturn(Role.ADMIN);
        });

    }

    @BeforeEach
    void setUp() {
        controller = new ManagingBooksController(mockStage, mockUser);
    }

    @ParameterizedTest
    @DisplayName("Boundary Value Testing for checkISBN() in ManagingBookController!")
    @CsvSource( value = {

            //Valid Test Cases
            "978-0-596-52068-7, true", //Valid isbn13
            "878-5473-5200, true", //Valid isbn10

            //Invalid Test Cases
            "978-0-596-52068, false", //Invalid isbn13 with 12 digits
            "978-0-596-52068-78, false", //Invalid isbn13 with 13 digits
            "878-5473-520, false", //Invalid isbn10 with 9 digits
            "878-5473-52005, false", //Invalid isbn10 with 11 digits
        }
    )

    void checkIsbnBVT(String isbn, boolean expected) {

        Platform.runLater(() -> {
            Alert mockAlert = mock(Alert.class);
            when(ManagingBooksController.CheckISBN10(isbn)).thenReturn(true);
            when(ManagingBooksController.CheckISBN13(isbn)).thenReturn(true);
            Assertions.assertEquals(expected, ManagingBooksController.checkIsbn(isbn, books));
        });

    }


    @ParameterizedTest
    @DisplayName("Equivalence Class Testing for checkISBN() in ManagingBookController!")
    @CsvSource( value = {

            //Valid Test Cases
            "978-0-596-52068-7, true", //Valid isbn13
            "878-5473-5200, true", //Valid isbn10

            //Invalid Test Cases
            "978-0-596, false", //Invalid short isbn
            "978-0-596-52068-7854, false", //Invalid long isbn13
            "978-0-596-52068-8, false", // Duplicate in list
            " null , false" // Empty ISBN
    },
            nullValues = {"null"}
    )

    void checkIsbnECT(String isbn, boolean expected) {

        Platform.runLater(() -> {
            Alert mockAlert = mock(Alert.class);
            when(ManagingBooksController.CheckISBN10(isbn)).thenReturn(true);
            when(ManagingBooksController.CheckISBN13(isbn)).thenReturn(true);
            Assertions.assertEquals(expected, ManagingBooksController.checkIsbn(isbn, books));
        });

    }


    @ParameterizedTest
    @DisplayName("Statement and Branch Coverage Testing for checkISBN() in ManagingBookController!")
    @CsvSource( value = {

            //Valid Test Cases
            "978-0-596-52068-8, false", //Valid isbn13, duplicate
            "878-5473-5200, true", //Valid isbn10
            "978-0-596-52068-7854, false" //Invalid isbn
    })

    void checkIsbnStatementBranch(String isbn, boolean expected) {

        Platform.runLater(() -> {
            Alert mockAlert = mock(Alert.class);
            when(ManagingBooksController.CheckISBN10(isbn)).thenReturn(true);
            when(ManagingBooksController.CheckISBN13(isbn)).thenReturn(true);
            Assertions.assertEquals(expected, ManagingBooksController.checkIsbn(isbn, books));
        });

    }

    @ParameterizedTest
    @DisplayName("Branch Coverage Testing with empty ObservableList for checkISBN() in ManagingBookController!")
    @CsvSource( value = {

            //Valid Test Cases
            "978-0-596-52068-8", //Valid isbn13
            "878-5473-5200", //Valid isbn10
    })

    void checkIsbnBranch(String isbn) {

        ObservableList<Book> booksTest = FXCollections.observableArrayList();

        Platform.runLater(() -> {
            Alert mockAlert = mock(Alert.class);
            when(ManagingBooksController.CheckISBN10(isbn)).thenReturn(true);
            when(ManagingBooksController.CheckISBN13(isbn)).thenReturn(true);
            Assertions.assertTrue(ManagingBooksController.checkIsbn(isbn, booksTest));
        });

    }


    @ParameterizedTest
    @DisplayName("Condition Coverage Testing with empty ObservableList for checkISBN() in ManagingBookController!")
    @CsvSource( value = {

            //Valid Test Cases
            "978-0-596-52068-7, true", //Valid isbn13
            "878-5473-5200, true", //Valid isbn10
            "878-5473-5200-0, false", //Invalid isbn
            "978-0-596-52068-8, false", // Duplicate in list
            "null, false" //Invalid

    }, nullValues = {"null"} )

    void checkIsbnCondition(String isbn, boolean expected) {

        Platform.runLater(() -> {
            Alert mockAlert = mock(Alert.class);
            when(ManagingBooksController.CheckISBN10(isbn)).thenReturn(true);
            when(ManagingBooksController.CheckISBN13(isbn)).thenReturn(true);
            Assertions.assertEquals(expected, ManagingBooksController.checkIsbn(isbn, books));
        });

    }

    @ParameterizedTest
    @DisplayName("MC/DC Coverage Testing with empty ObservableList for checkISBN() in ManagingBookController!")
    @CsvSource( value = {

            //Valid Test Cases
            "978-0-596-52068-7, true", //Valid isbn13
            "878-5473-5200-0, false", //Invalid isbn
            "978-0-596-52068-8, false", // Duplicate in list
            "null, false" //Invalid

    }, nullValues = {"null"} )

    void checkIsbnMCDC(String isbn, boolean expected) {

        Platform.runLater(() -> {
            Alert mockAlert = mock(Alert.class);
            when(ManagingBooksController.CheckISBN10(isbn)).thenReturn(true);
            when(ManagingBooksController.CheckISBN13(isbn)).thenReturn(true);
            Assertions.assertEquals(expected, ManagingBooksController.checkIsbn(isbn, books));
        });

    }

    @Test
    @DisplayName("Test Back() for Admin Role")
    void testBackForAdmin() {
        Platform.runLater(() -> {
            controller.Back(null);
            verify(mockStage, atLeastOnce()).getScene();
        });
    }

    @Test
    @DisplayName("Test Back() for Manager Role")
    void testBackForManager() {
        when(mockUser.getRole()).thenReturn(Role.MANAGER);

        Platform.runLater(() -> {
            controller.Back(null);
            // Ensure backFunction is called with correct role indicator
            assertDoesNotThrow(() -> controller.Back(null));
        });
    }

    @Test
    @DisplayName("Test onBookDelete() with Selected Books")
    void testOnBookDelete() {
        ObservableList<Book> selectedBooks = FXCollections.observableArrayList();
        Book mockBook = mock(Book.class);
        selectedBooks.add(mockBook);

        controller.getView().getTableView().getSelectionModel().select(mockBook);

        Platform.runLater(() -> {
            assertDoesNotThrow(() -> controller.onBookDelete(null));
        });
    }

    @Test
    @DisplayName("Test Submit() with Valid Data")
    void testSubmitWithValidData() {
        Platform.runLater(() -> {
            controller.getView().setIsbnTF("978-3-16-148410-0");
            controller.getView().setTitleTF("Test Book");
            controller.getView().setPriceTF("100");
            controller.getView().setDescriptionTA("Test Description");
            controller.getView().setAuthorComboBox(); // Simulate author selection

            assertDoesNotThrow(() -> controller.Submit(null));
        });
    }

    @Test
    @DisplayName("Test Submit() with Invalid Data")
    void testSubmitWithInvalidData() {
        Platform.runLater(() -> {
            controller.getView().setIsbnTF("");
            controller.getView().setTitleTF("");
            controller.getView().setPriceTF("-10"); // Invalid price

            assertDoesNotThrow(() -> controller.Submit(null));
        });
    }

    @Test
    @DisplayName("Test checkPrice() with Invalid Price")
    void testCheckPriceInvalid() {
        Platform.runLater(() -> {
            boolean result = ManagingBooksController.checkPrice("-1");
            assertFalse(result);
        });
    }

    @Test
    @DisplayName("Test checkPrice() with Valid Price")
    void testCheckPriceValid() {
        Platform.runLater(() -> {
            boolean result = ManagingBooksController.checkPrice("100");
            assertTrue(result);
        });
    }

    @Test
    @DisplayName("Test checkDescription() with Empty Description")
    void testCheckDescriptionEmpty() {
        Platform.runLater(() -> {
            boolean result = ManagingBooksController.checkDescription("");
            assertFalse(result);
        });
    }

    @Test
    @DisplayName("Test checkDescription() with Valid Description")
    void testCheckDescriptionValid() {
        Platform.runLater(() -> {
            boolean result = ManagingBooksController.checkDescription("This is a valid description.");
            assertTrue(result);
        });
    }

    @Test
    @DisplayName("Test checkGenres() with No Genre Selected")
    void testCheckGenresNone() {
        Platform.runLater(() -> {
            boolean result = ManagingBooksController.checkGenres(0);
            assertFalse(result);
        });
    }

    @Test
    @DisplayName("Test checkGenres() with Genres Selected")
    void testCheckGenresSelected() {
        Platform.runLater(() -> {
            boolean result = ManagingBooksController.checkGenres(2);
            assertTrue(result);
        });
    }

    @Test
    @DisplayName("Test checkQuantity() with Invalid Quantity")
    void testCheckQuantityInvalid() {
        Platform.runLater(() -> {
            boolean result = ManagingBooksController.checkQuantity("-5");
            assertFalse(result);
        });
    }

    @Test
    @DisplayName("Test checkQuantity() with Valid Quantity")
    void testCheckQuantityValid() {
        Platform.runLater(() -> {
            boolean result = ManagingBooksController.checkQuantity("10");
            assertTrue(result);
        });
    }

}
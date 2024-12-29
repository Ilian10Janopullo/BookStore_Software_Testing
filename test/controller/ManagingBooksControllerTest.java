package controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.JavaFXInitializer;
import static org.mockito.Mockito.*;

class ManagingBooksControllerTest {

    private static ObservableList<Book> books;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {

        new JavaFXInitializer().init();
        books = FXCollections.observableArrayList();
        books.add(mock(Book.class, CALLS_REAL_METHODS));
        when(mock(Book.class, CALLS_REAL_METHODS).getIsbn()).thenReturn("9780-59-65-2068-8");

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

}
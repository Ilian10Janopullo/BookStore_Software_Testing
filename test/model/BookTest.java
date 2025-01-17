package model;

import dao.AuthorsDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import util.JavaFXInitializer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookTest {

    private Author mockAuthor;
    private AuthorsDAO mockAuthorsDAO;

    @BeforeEach
    void setup() {
        mockAuthor = new Author("John", "Doe", Gender.MALE); // Updated mockAuthor
        mockAuthorsDAO = Mockito.mock(AuthorsDAO.class);
        new JavaFXInitializer().init();
    }

    @Test
    void testConstructor() {
        // Arrange
        Book book = new Book("12345", "Test Book", "A great book", 19.99, mockAuthor, true, 10);

        // Assert
        assertEquals("12345", book.getIsbn());
        assertEquals("Test Book", book.getTitle());
        assertEquals("A great book", book.getDescription());
        assertEquals(19.99, book.getPrice());
        assertEquals(mockAuthor, book.getAuthor());
        assertTrue(book.isPaperback());
        assertEquals(10, book.getQuantity());
        assertEquals(0, book.getCopiesSold());
        assertTrue(book.getGenres().isEmpty());
    }

    @Test
    void testSetAndGetAuthor() {
        Platform.runLater(() -> {
            // Arrange
            when(mockAuthorsDAO.getAll()).thenReturn(FXCollections.observableArrayList(mockAuthor));
            Book book = new Book("12345", "Test Book", "A great book", 19.99, mockAuthor, true, 10);

            // Act
            book.setAuthor("Nonexistent Author");

            // Assert
            assertNotEquals("Nonexistent Author", book.getAuthor().toString());
        });
    }



    @Test
    void testSetAuthorNotFound() {
        // Arrange
        when(mockAuthorsDAO.getAll()).thenReturn(FXCollections.observableArrayList(mockAuthor));
        Book book = new Book("12345", "Test Book", "A great book", 19.99, mockAuthor, true, 10);

        // Act
        Platform.runLater(() -> book.setAuthor("Nonexistent Author"));

        // Assert
        Platform.runLater(() -> {
            assertNotEquals("Nonexistent Author", book.getAuthor().toString());
        });
    }



    @Test
    void testSetAndGetQuantity() {
        // Arrange
        Book book = new Book("12345", "Test Book", "A great book", 19.99, mockAuthor, true, 10);

        // Act
        book.setQuantity(15);

        // Assert
        assertEquals(15, book.getQuantity());
    }

    @Test
    void testSetAndGetCopiesSold() {
        // Arrange
        Book book = new Book("12345", "Test Book", "A great book", 19.99, mockAuthor, true, 10);

        // Act
        book.setCopiesSold(5);

        // Assert
        assertEquals(5, book.getCopiesSold());

        // Act again
        book.setCopiesSold(3);

        // Assert
        assertEquals(8, book.getCopiesSold());
    }

    @Test
    void testSetAndGetGenres() {
        // Arrange
        Book book = new Book("12345", "Test Book", "A great book", 19.99, mockAuthor, true, 10);

        // Act
        book.addGenre(Genre.FANTASY);
        book.addGenres(Genre.ACTION, Genre.DYSTOPIAN);

        // Assert
        assertTrue(book.getGenres().contains(Genre.FANTASY));
        assertTrue(book.getGenres().contains(Genre.ACTION));
        assertTrue(book.getGenres().contains(Genre.DYSTOPIAN));
    }

    @Test
    void testToString() {
        // Arrange
        Book book = new Book("12345", "Test Book", "A great book", 19.99, mockAuthor, true, 10);

        // Act
        String result = book.toString();

        // Assert
        String expected = "Test Book by John Doe, 19.99 EUR";
        assertEquals(expected, result);
    }

    @Test
    void testInvalidPrice() {
        // Arrange
        Book book = new Book("12345", "Test Book", "A great book", -10.0, mockAuthor, true, 10);

        // Assert
        assertEquals(-10.0, book.getPrice());
    }
}

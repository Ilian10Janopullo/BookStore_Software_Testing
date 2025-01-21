package unitTesting.model;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import model.Author;
import model.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.JavaFXInitializer;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {

    private Author author;

    @BeforeEach
    void setup() {
        author = new Author("John", "Doe", Gender.MALE);
        new JavaFXInitializer().init();
    }

    @Test
    void testConstructorWithTwoNames() {
        // Arrange & Act
        Author author = new Author("Jane", "Doe", Gender.FEMALE);

        // Assert
        assertEquals("Jane Doe", author.getFullName());
        assertEquals(Gender.FEMALE, author.getGender());
        assertEquals(0, author.getNrOfBooks());
        assertEquals(0, author.getNrOfBooksSold());
    }

    @Test
    void testConstructorWithThreeNames() {
        // Arrange & Act
        Author author = new Author("Jane", "Middle", "Doe", Gender.FEMALE);

        // Assert
        assertEquals("Jane Middle Doe", author.getFullName());
        assertEquals(Gender.FEMALE, author.getGender());
    }

    @Test
    void testSetGenderWithValidString() {
        Platform.runLater(() -> {
            // Act
            author.setGender("FEMALE");

            // Assert
            assertEquals(Gender.FEMALE, author.getGender());
        });
    }

    @Test
    void testSetGenderWithInvalidString() {
        Platform.runLater(() -> {
            // Act
            author.setGender("INVALID");

            // Assert
            assertNull(author.getGender()); // Gender should not change
        });
    }

    @Test
    void testGetAndSetFirstName() {
        // Act
        author.setFirstName("Michael");

        // Assert
        assertEquals("Michael", author.getFirstName());
    }

    @Test
    void testGetAndSetLastName() {
        // Act
        author.setLastName("Smith");

        // Assert
        assertEquals("Smith", author.getLastName());
    }

    @Test
    void testGetAndSetMiddleName() {
        // Act
        author.setMiddleName("Allen");

        // Assert
        assertEquals("Allen", author.getMiddleName());
    }

    @Test
    void testSetFullName() {
        // Arrange
        author.setMiddleName("Allen");

        // Act
        author.setFullName();

        // Assert
        assertEquals("John Allen Doe", author.getFullName());
    }

    @Test
    void testSetFullNameWithoutMiddleName() {
        // Act
        author.setFullName();

        // Assert
        assertEquals("John Doe", author.getFullName());
    }

    @Test
    void testGetAndSetNrOfBooks() {
        // Act
        author.setNrOfBooks(5);

        // Assert
        assertEquals(5, author.getNrOfBooks());
    }

    @Test
    void testGetAndSetNrOfBooksSold() {
        // Act
        author.setNrOfBooksSold(10);

        // Assert
        assertEquals(10, author.getNrOfBooksSold());

        // Act
        author.setNrOfBooksSold(5);

        // Assert
        assertEquals(15, author.getNrOfBooksSold());
    }

    @Test
    void testToString() {
        // Act
        String result = author.toString();

        // Assert
        assertEquals("John Doe", result);
    }

    @Test
    void testEquals() {
        // Arrange
        Author sameAuthor = new Author("John", "Doe", Gender.MALE);
        Author differentAuthor = new Author("Jane", "Smith", Gender.FEMALE);

        // Assert
        assertTrue(author.equals(sameAuthor));
        assertFalse(author.equals(differentAuthor));
    }

    @Test
    void testEqualsWithDifferentClass() {
        // Arrange
        Object notAnAuthor = new Object();

        // Assert
        assertFalse(author.equals(notAnAuthor));
    }

    @Test
    void testEqualsWithNull() {
        // Assert
        assertFalse(author.equals(null));
    }

    @Test
    void testNegativeNrOfBooks() {
        // Act
        author.setNrOfBooks(-10);

        // Assert
        assertEquals(-10, author.getNrOfBooks());
    }

    @Test
    void testNegativeNrOfBooksSold() {
        // Act
        author.setNrOfBooksSold(-5);

        // Assert
        assertEquals(-5, author.getNrOfBooksSold());
    }
}

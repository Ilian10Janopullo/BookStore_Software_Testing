package unitTesting.model;

import javafx.application.Platform;
import model.BooksOrdered;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.JavaFXInitializer;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class BooksOrderedTest {

    private BooksOrdered booksOrdered;

    @BeforeAll
    static void set(){

        Platform.startup(() -> {
            new JavaFXInitializer().init();
        });
    }

    @BeforeEach
    void setup() {
        booksOrdered = new BooksOrdered("12345", "Test Book", 19.99);
    }

    @Test
    void testConstructor() {
        // Arrange & Act
        BooksOrdered booksOrdered = new BooksOrdered("54321", "Another Book", 25.50);

        // Assert
        assertEquals("54321", booksOrdered.getIsbn());
        assertEquals("Another Book", booksOrdered.getTitle());
        assertEquals(25.50, booksOrdered.getPrice());
        assertEquals(0, booksOrdered.getQuantityToOrderOfBookOrdered());
        assertNotNull(booksOrdered.getDate()); // Ensure date is initialized
    }

    @Test
    void testGetAndSetTitleOfBookOrdered() {
        // Act
        booksOrdered.setTitleOfBookOrdered("Updated Book");

        // Assert
        assertEquals("Updated Book", booksOrdered.getTitleOfBookOrdered());
    }

    @Test
    void testGetAndSetIsbnOfBookOrdered() {
        // Act
        booksOrdered.setIsbnOfBookOrdered("54321");

        // Assert
        assertEquals("54321", booksOrdered.getIsbnOfBookOrdered());
    }

    @Test
    void testGetAndSetPriceOfBookOrdered() {
        // Act
        booksOrdered.setPriceOfBookOrdered(29.99);

        // Assert
        assertEquals(29.99, booksOrdered.getPriceOfBookOrdered());
    }

    @Test
    void testGetAndSetQuantityToOrderOfBookOrdered() {
        // Act
        booksOrdered.setQuantityToOrder(5);

        // Assert
        assertEquals(5, booksOrdered.getQuantityToOrderOfBookOrdered());
    }


    @Test
    void testEqualsWithNonMatchingObjects() {
        // Arrange
        BooksOrdered differentBook = new BooksOrdered("54321", "Another Book", 25.50);

        // Assert
        assertFalse(booksOrdered.equals(differentBook));
    }

    @Test
    void testEqualsWithDifferentType() {
        // Arrange
        Object notABook = new Object();

        // Assert
        assertFalse(booksOrdered.equals(notABook));
    }

    @Test
    void testEqualsWithNull() {
        // Assert
        assertFalse(booksOrdered.equals(null));
    }

    @Test
    void testToString() {
        // Act
        String result = booksOrdered.toString();

        // Assert
        String expected = "Test Book\t Price : 19.99\t Quantity : 0\n";
        assertEquals(expected, result);
    }

    @Test
    void testDateInitialization() {
        // Arrange
        Calendar calendar = new GregorianCalendar();

        // Assert
        assertNotNull(booksOrdered.getDate());
        assertTrue(booksOrdered.getDate().before(calendar.getTime()) || booksOrdered.getDate().equals(calendar.getTime()));
    }

    @Test
    void testNegativeQuantityToOrder() {
        // Act
        booksOrdered.setQuantityToOrder(-5);

        // Assert
        assertEquals(-5, booksOrdered.getQuantityToOrderOfBookOrdered());
    }

    @Test
    void testNegativePrice() {
        // Act
        booksOrdered.setPriceOfBookOrdered(-19.99);

        // Assert
        assertEquals(-19.99, booksOrdered.getPriceOfBookOrdered());
    }
}

//Add getDate() method in the BooksOrdered() class in order to complete testing!
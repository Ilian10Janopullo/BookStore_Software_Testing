package unitTesting.model;

import model.Bill;
import model.BooksOrdered;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BillTest {

    @Test
    void testBillConstructor() {
        // Arrange
        ArrayList<BooksOrdered> books = new ArrayList<>();
        BooksOrdered book = new BooksOrdered("454545", "Test", 10.0);
        book.setQuantityToOrder(1);
        books.add(book);

        // Act
        Bill bill = new Bill(books, "User123");

        // Assert
        assertNotNull(bill.getBillID());
        assertEquals("User123", bill.getUserID());
        assertEquals(10.0, bill.getTotalAmount());
        assertNotNull(bill.getDate());
        assertEquals(books, bill.getBooks());
    }

    @Test
    void testSetBooks() {
        // Arrange
        ArrayList<BooksOrdered> books = new ArrayList<>();
        BooksOrdered book1 = new BooksOrdered("545454", "Test1", 20.0);
        book1.setQuantityToOrder(1);
        books.add(book1);

        Bill bill = new Bill(books, "User123");

        ArrayList<BooksOrdered> newBooks = new ArrayList<>();
        BooksOrdered book2 = new BooksOrdered("54545414", "Test2", 10.0);
        book2.setQuantityToOrder(2);
        newBooks.add(book2);

        // Act
        bill.setBooks(newBooks);

        // Assert
        assertEquals(newBooks, bill.getBooks());
    }

    @Test
    void testSetTotalAmount() {
        // Arrange
        ArrayList<BooksOrdered> books = new ArrayList<>();

        BooksOrdered book1 = new BooksOrdered("545454", "Test1", 20.0);
        book1.setQuantityToOrder(1);

        BooksOrdered book2 = new BooksOrdered("545454544", "Test2", 10.0);
        book2.setQuantityToOrder(2);

        books.add(book1);
        books.add(book2);

        // Act
        Bill bill = new Bill(books, "User123");

        // Assert
        assertEquals(40.0, bill.getTotalAmount()); // 20.0 * 1 + 10.0 * 2 = 40.0
    }

    @Test
    void testEquals() {
        // Arrange
        ArrayList<BooksOrdered> books = new ArrayList<>();
        BooksOrdered book = new BooksOrdered("545454", "Test1", 20.0);
        book.setQuantityToOrder(1);
        books.add(book);

        Bill bill1 = new Bill(books, "User123");
        Bill bill2 = new Bill(books, "User123");

        // Act & Assert
        assertNotEquals(bill1, bill2); // Different Bill IDs
        assertEquals(bill1, bill1);   // Same object
    }

    @Test
    void testEqualsWithNullAndDifferentClass() {
        // Arrange
        ArrayList<BooksOrdered> books = new ArrayList<>();
        BooksOrdered book = new BooksOrdered("545454", "Test1", 20.0);
        book.setQuantityToOrder(1);
        books.add(book);

        Bill bill = new Bill(books, "User123");

        // Act & Assert
        assertNotEquals(null, bill); // Not equal to null
        assertNotEquals(bill, new Object()); // Not equal to a different class
    }

    @Test
    void testToString() {
        // Arrange
        ArrayList<BooksOrdered> books = new ArrayList<>();
        BooksOrdered book = new BooksOrdered("545454", "Test1", 20.0);
        book.setQuantityToOrder(1);
        books.add(book);

        Bill bill = new Bill(books, "User123");

        // Act
        String expected = "Bill ID : " + bill.getBillID() + "\n" +
                "User ID : User123\n" +
                "Date/Time : " + bill.getDate().toString() + "\n" +
                "Book : " + books.get(0).toString() +
                "Total : " + bill.getTotalAmount() + "\n" +
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n";

        // Assert
        assertEquals(expected, bill.toString());
    }

    @Test
    void testEmptyBooksList() {
        // Arrange
        ArrayList<BooksOrdered> books = new ArrayList<>();

        // Act
        Bill bill = new Bill(books, "User123");

        // Assert
        assertEquals(0.0, bill.getTotalAmount()); // No books, total amount = 0
        assertEquals(books, bill.getBooks());
    }

    @Test
    void testInvalidBookData() {
        // Arrange
        ArrayList<BooksOrdered> books = new ArrayList<>();

        BooksOrdered book1 = new BooksOrdered("545454", "Test1", 20.0);
        book1.setQuantityToOrder(1);

        BooksOrdered book2 = new BooksOrdered("54545454", "Test2", -30.0); // Negative price
        book2.setQuantityToOrder(1);

        books.add(book1);
        books.add(book2);

        // Act
        Bill bill = new Bill(books, "User123");

        // Assert
        assertEquals(-10.0, bill.getTotalAmount()); // 20.0 * 1 + (-30.0 * 1) = -10.0
    }

    @Test
    void testLargeNumbers() {
        // Arrange
        ArrayList<BooksOrdered> books = new ArrayList<>();
        BooksOrdered book = new BooksOrdered("54545454", "Test1", 1e6); // $1 million
        book.setQuantityToOrder((int) 1e6); // 1 million units

        books.add(book);

        // Act
        Bill bill = new Bill(books, "User123");

        // Assert
        assertEquals(1e12, bill.getTotalAmount()); // $1 trillion
    }
}

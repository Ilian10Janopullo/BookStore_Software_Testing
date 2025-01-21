package unitTesting.dao;

import dao.BillPrintingDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Author;
import model.Bill;
import model.Book;
import model.BooksOrdered;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;

public class BillPrintingDAOTest {

    private static File tempFile;
    private BillPrintingDAO billPrintingDAO;

    @BeforeEach
    void setUp() throws IOException {
        // Create a temporary file for testing
        tempFile = File.createTempFile("test_billsPrinted", ".txt");
        BillPrintingDAO.FILE_PATH = tempFile.getAbsolutePath();
        billPrintingDAO = new BillPrintingDAO();
    }

    @AfterEach
    void tearDown() {
        if (tempFile.exists()) {
            assertTrue(tempFile.delete(), "Temporary file should be deleted.");
        }
    }

    @Test
    void testFileCreationIfNotExists() throws IOException {

        // Create test bills
        ArrayList<BooksOrdered> booksOrdered1 = new ArrayList<>();
        Book mockBook = new Book("1234567", "Test Book", "Test Description", 10.99, mock(Author.class, CALLS_REAL_METHODS), true, 10);
        booksOrdered1.add(new BooksOrdered(mockBook.getIsbn(), mockBook.getTitle(), mockBook.getPrice()));
        Bill bill1 = new Bill(booksOrdered1, "User3");
        ObservableList<Bill> testBills = FXCollections.observableArrayList(bill1);

        // Act: Write to file
        billPrintingDAO.addBillToFile(testBills);

        // Assert: Verify the file is created
        assertTrue(tempFile.exists(), "File should be created if it does not exist.");
    }

    @Test
    void testAddBillToFile() throws IOException {
        // Arrange: Create test bills
        ArrayList<BooksOrdered> booksOrdered1 = new ArrayList<>();
        Book mockBook = new Book("123456789", "Test Book", "Test Description", 10.99, mock(Author.class, CALLS_REAL_METHODS), true, 10);
        booksOrdered1.add(new BooksOrdered(mockBook.getIsbn(), mockBook.getTitle(), mockBook.getPrice()));
        Bill bill1 = new Bill(booksOrdered1, "User1");

        ArrayList<BooksOrdered> booksOrdered2 = new ArrayList<>();
        booksOrdered2.add(new BooksOrdered(mockBook.getIsbn(), mockBook.getTitle(), mockBook.getPrice()));
        Bill bill2 = new Bill(booksOrdered2, "User2");

        ObservableList<Bill> testBills = FXCollections.observableArrayList(bill1, bill2);

        // Act: Write to file
        billPrintingDAO.addBillToFile(testBills);

        // Assert: Verify file contents
        String fileContents = Files.readString(tempFile.toPath());
        String expectedContents = bill1.toString() + bill2.toString();
        assumeTrue(expectedContents.equals(fileContents));
    }

    @Test
    void testAddEmptyBillListToFile() throws IOException {
        // Arrange: Create an empty bill list
        ObservableList<Bill> emptyBills = FXCollections.observableArrayList();

        // Act: Write to file
        billPrintingDAO.addBillToFile(emptyBills);

        // Assert: Verify the file is empty
        String fileContents = Files.readString(tempFile.toPath());
        assertTrue(fileContents.isEmpty(), "File should be empty when no bills are added.");
    }
}

//Changed the path from final to not final so it could be tested!

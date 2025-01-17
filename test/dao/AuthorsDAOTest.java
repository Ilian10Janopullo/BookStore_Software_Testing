package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Author;
import model.Gender;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorsDAOTest {

    private Path tempFilePath;
    private AuthorsDAO authorsDAO;

    @BeforeEach
    void setUp() throws IOException {
        // Create a temporary file for each test
        tempFilePath = Files.createTempFile("test_authors", ".dat");
        AuthorsDAO.FILE_PATH = tempFilePath.toString();
        authorsDAO = new AuthorsDAO();
    }

    @AfterEach
    void tearDown() throws IOException {
        // Ensure the file is no longer in use
        authorsDAO = null;

        // Wait a short moment to ensure any pending file locks are released
        System.gc();

        // Delete the temporary file
        if (Files.exists(tempFilePath)) {
            Files.delete(tempFilePath);
        }
    }


    @Test
    void testLoadFromEmptyFile() {
        ObservableList<Author> authorsList = FXCollections.observableArrayList();

        authorsDAO.loadFromFileImplementation(authorsList, tempFilePath.toFile());

        // Assert that the list is empty
        assertTrue(authorsList.isEmpty(), "Authors list should be empty for a new file.");
    }

    @Test
    void testAddToFile() {
        Author author = new Author("John", "Doe", Gender.MALE);
        assertTrue(authorsDAO.addToFile(author), "Author should be added successfully.");

        ObservableList<Author> authors = authorsDAO.getAll();
        assertEquals(1, authors.size(), "Authors list should contain 1 author.");
        assertEquals("John", authors.get(0).getFirstName());
    }

    @Test
    void testAddDuplicateToFile() {
        Author author = new Author("John", "Doe", Gender.MALE);
        authorsDAO.addToFile(author);

        // Attempt to add the same author again
        assertTrue(authorsDAO.addToFile(author), "Duplicate author should be added successfully.");
        ObservableList<Author> authors = authorsDAO.getAll();
        assertEquals(2, authors.size(), "Authors list should allow duplicates.");
    }

    @Test
    void testDelete() {
        Author author = new Author("John", "Doe", Gender.MALE);
        authorsDAO.addToFile(author);

        assertTrue(authorsDAO.delete(author), "Author should be deleted successfully.");
        ObservableList<Author> authors = authorsDAO.getAll();
        assertTrue(authors.isEmpty(), "Authors list should be empty after deletion.");
    }

    @Test
    void testDeleteNonexistentAuthor() {
        Author author = new Author("Nonexistent", "Author", Gender.MALE);
        assertFalse(authorsDAO.delete(author), "Deleting a nonexistent author should return false.");
    }

    @Test
    void testDeleteAll() {
        List<Author> authorsToAdd = List.of(
                new Author("John", "Doe", Gender.MALE),
                new Author("Jane", "Smith", Gender.FEMALE)
        );
        authorsToAdd.forEach(authorsDAO::addToFile);

        assertTrue(authorsDAO.deleteAll(authorsToAdd), "All authors should be deleted successfully.");
        ObservableList<Author> authors = authorsDAO.getAll();
        assertTrue(authors.isEmpty(), "Authors list should be empty after deleting all authors.");
    }

    @Test
    void testUpdateAll() throws IOException {
        Author author = new Author("John", "Doe", Gender.MALE);
        authorsDAO.addToFile(author);

        // Modify the author's name directly in the observable list
        authorsDAO.getAll().get(0).setFirstName("UpdatedName");

        assertTrue(authorsDAO.updateAll(), "UpdateAll should return true.");
        AuthorsDAO freshDAO = new AuthorsDAO();
        ObservableList<Author> updatedAuthors = freshDAO.getAll();
        assertEquals("UpdatedName", updatedAuthors.get(0).getFirstName(), "Author's name should be updated in the file.");
    }

    @Test
    void testHandleCorruptedFile() throws IOException {
        // Write invalid data to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFilePath.toFile()))) {
            writer.write("INVALID DATA");
        }

        // Attempt to load authors from the corrupted file
        ObservableList<Author> authors = authorsDAO.getAll();
        assertNotNull(authors, "Authors list should not be null even if the file is corrupted.");
        assertTrue(authors.isEmpty(), "Authors list should be empty for corrupted file.");
    }

    @Test
    void testAddLargeNumberOfAuthors() {
        int largeNumber = 1000;
        for (int i = 0; i < largeNumber; i++) {
            authorsDAO.addToFile(new Author("Author" + i, "LastName" + i, Gender.MALE));
        }

        ObservableList<Author> authors = authorsDAO.getAll();
        assertEquals(largeNumber, authors.size(), "All authors should be added successfully.");
    }

    @Test
    void testAddAuthorWithSpecialCharacters() {
        Author author = new Author("J@hn", "D!oe", Gender.MALE);
        assertTrue(authorsDAO.addToFile(author), "Author with special characters should be added successfully.");

        ObservableList<Author> authors = authorsDAO.getAll();
        assertEquals(1, authors.size(), "Authors list should contain 1 author.");
        assertEquals("J@hn", authors.get(0).getFirstName());
    }
}

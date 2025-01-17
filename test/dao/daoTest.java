package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

class DaoImplementationTest {

    private static Path testFile;
    private static ObservableList<String> list;
    private static dao<String> dao;

    @BeforeAll
    static void setupFiles() throws IOException {
        testFile = Files.createTempFile("test_file", ".dat");
    }

    @BeforeEach
    void setup() {
        list = FXCollections.observableArrayList();
        dao = new dao() {}; // Anonymous implementation of the interface
    }

    @AfterEach
    void cleanup() throws IOException {
        try (FileWriter writer = new FileWriter(testFile.toFile(), false)) {
            writer.write(""); // Clear file content
        }
    }

    @AfterAll
    static void cleanupAll() throws IOException {
        if (Files.exists(testFile)) {
            Files.deleteIfExists(testFile);
        }
    }

    @Test
    @DisplayName("Test adding an item to the file")
    void testAddToFile() {
        Assertions.assertTrue(dao.addToFileImplementation("Item1", list, testFile.toFile()));
        Assertions.assertTrue(list.contains("Item1"));

        ObservableList<String> tempList = FXCollections.observableArrayList();
        dao.loadFromFileImplementation(tempList, testFile.toFile());
        Assertions.assertEquals(1, tempList.size());
        Assertions.assertTrue(tempList.contains("Item1"));
    }

    @Test
    @DisplayName("Test adding an item to a non-empty file")
    void testAddToNonEmptyFile() {
        dao.addToFileImplementation("Item1", list, testFile.toFile());
        Assertions.assertTrue(dao.addToFileImplementation("Item2", list, testFile.toFile()));
        Assertions.assertEquals(2, list.size());
        Assertions.assertTrue(list.containsAll(List.of("Item1", "Item2")));

        ObservableList<String> tempList = FXCollections.observableArrayList();
        dao.loadFromFileImplementation(tempList, testFile.toFile());
        Assertions.assertEquals(2, tempList.size());
        Assertions.assertTrue(tempList.containsAll(List.of("Item1", "Item2")));
    }

    @Test
    @DisplayName("Test deleting an item that does not exist")
    void testDeleteNonExistentItem() {
        dao.addToFileImplementation("Item1", list, testFile.toFile());
        Assertions.assertFalse(dao.deleteImplementation("NonExistent", list, testFile.toFile()));
    }

    @Test
    @DisplayName("Test deleting from an empty list")
    void testDeleteFromEmptyList() {
        Assertions.assertFalse(dao.deleteImplementation("NonExistent", list, testFile.toFile()));
    }

    @Test
    @DisplayName("Test updating an empty list to the file")
    void testUpdateEmptyList() {
        Assertions.assertTrue(dao.updateAllImplementation(list, testFile.toFile()));

        ObservableList<String> tempList = FXCollections.observableArrayList();
        dao.loadFromFileImplementation(tempList, testFile.toFile());
        Assertions.assertTrue(tempList.isEmpty());
    }

    @Test
    @DisplayName("Test loading from an empty file")
    void testLoadFromEmptyFile() {
        ObservableList<String> tempList = FXCollections.observableArrayList();
        dao.loadFromFileImplementation(tempList, testFile.toFile());
        Assertions.assertTrue(tempList.isEmpty());
    }

    @Test
    @DisplayName("Test loading from a corrupted file")
    void testLoadFromCorruptedFile() throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(testFile)) {
            writer.write("Corrupted data");
        }

        ObservableList<String> tempList = FXCollections.observableArrayList();
        dao.loadFromFileImplementation(tempList, testFile.toFile());
        Assertions.assertTrue(tempList.isEmpty());
    }

    @Test
    @DisplayName("Test adding null item to the file")
    void testAddNullItem() {
        Assertions.assertFalse(dao.addToFileImplementation(null, list, testFile.toFile()));
    }
}




//Changed dao interface at deleteImplementation() to return false immediately if list is empty!
    //Removed final from FILE_PATH only at AuthorsDAO so it can be used in testing
    //At others we cannot test them without making the field of Path not final
    //Even if we were to test them, it would be the same


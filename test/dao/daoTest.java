package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import java.io.*;
import java.util.List;

class LoadFromFileImplementationTest {

    private static final File VALID_DATA_FILE = new File("valid_data_file.dat");
    private static final File EMPTY_DATA_FILE = new File("empty_data_file.dat");
    private static final File MALFORMED_DATA_FILE = new File("malformed_data_file.dat");
    private static ObservableList<String> list;

    @BeforeAll
    static void setupFiles() throws IOException {

        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(VALID_DATA_FILE))) {
            outputStream.writeObject("Item1");
            outputStream.writeObject("Item2");
            outputStream.writeObject("Item3");
        }

        // Create empty data file
        new FileOutputStream(EMPTY_DATA_FILE).close();

        // Create malformed data file
        try (FileWriter writer = new FileWriter(MALFORMED_DATA_FILE)) {
            writer.write("Invalid Serialized Data");
        }
    }

    @BeforeEach
    void setup() {
        list = FXCollections.observableArrayList();
    }

    @AfterAll
    static void cleanup() {
        VALID_DATA_FILE.delete();
        EMPTY_DATA_FILE.delete();
        MALFORMED_DATA_FILE.delete();
    }

    @Test
    @DisplayName("Test loading from a valid data file")
    void testLoadFromValidFile() {
        loadFromFileImplementation(list, VALID_DATA_FILE);
        Assertions.assertEquals(3, list.size());
        Assertions.assertTrue(list.containsAll(List.of("Item1", "Item2", "Item3")));
    }

    @Test
    @DisplayName("Test loading from an empty data file")
    void testLoadFromEmptyFile() {
        loadFromFileImplementation(list, EMPTY_DATA_FILE);
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("Test loading from a malformed data file")
    void testLoadFromMalformedFile() {
        loadFromFileImplementation(list, MALFORMED_DATA_FILE);
        Assertions.assertTrue(list.isEmpty());
    }

    @Test
    @DisplayName("Test loading from a non-existent file")
    void testLoadFromNonExistentFile() {
        File nonExistentFile = new File("non_existent_file.dat");
        loadFromFileImplementation(list, nonExistentFile);
        Assertions.assertTrue(list.isEmpty());
    }

    // Method to test
    private <T> void loadFromFileImplementation(ObservableList<T> list, File DATA_FILE) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            while (true) {
                T t = (T) inputStream.readObject();
                list.add(t);
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
}

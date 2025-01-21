package unitTesting.dao;

import dao.HeaderlessObjectOutputStream;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HeaderlessObjectOutputStreamTest {

    private static final String TEST_FILE_PATH = "test_headerless_stream.dat";

    @Test
    void testWriteAndReadObjects() throws IOException, ClassNotFoundException {
        // Data to serialize
        List<String> objects = List.of("Object1", "Object2", "Object3");

        // Write objects to file
        try (FileOutputStream fos = new FileOutputStream(TEST_FILE_PATH);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(objects.get(0)); // Write first object with regular header
        }

        // Append objects without header
        try (FileOutputStream fos = new FileOutputStream(TEST_FILE_PATH, true);
             HeaderlessObjectOutputStream hoos = new HeaderlessObjectOutputStream(fos)) {

            hoos.writeObject(objects.get(1)); // Append second object
            hoos.writeObject(objects.get(2)); // Append third object
        }

        // Read objects back
        try (FileInputStream fis = new FileInputStream(TEST_FILE_PATH);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            // Read the first object
            String readObject1 = (String) ois.readObject();
            assertEquals("Object1", readObject1);

            // Read the second object
            String readObject2 = (String) ois.readObject();
            assertEquals("Object2", readObject2);

            // Read the third object
            String readObject3 = (String) ois.readObject();
            assertEquals("Object3", readObject3);
        } finally {
            // Cleanup test file
            new File(TEST_FILE_PATH).delete();
        }
    }
}

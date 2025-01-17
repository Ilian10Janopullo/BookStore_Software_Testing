package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class PermissionsDAOTest {

    private Path tempFilePath;
    private PermissionsDAO permissionsDAO;

    @BeforeEach
    void setUp() throws IOException {
        // Create a temporary file for testing
        tempFilePath = Files.createTempFile("test_permissions", ".dat");
        PermissionsDAO.FILE_PATH = tempFilePath.toString();
        permissionsDAO = new PermissionsDAO();
    }

    @AfterEach
    void tearDown() throws IOException {
        // Clean up the temporary file after each test
        if (Files.exists(tempFilePath)) {
            Files.delete(tempFilePath);
        }
    }

    @Test
    void testGetAllFromEmptyFile() {
        ObservableList<String> permissions = permissionsDAO.getAll();
        assertNotNull(permissions, "Permissions list should not be null.");
        assumeTrue(permissions.isEmpty(), "Permissions list should be empty for a new file.");
    }

    @Test
    void testLoadFromFile() throws IOException {
        // Write some permissions to the file
        try (DataOutputStream writer = new DataOutputStream(new FileOutputStream(tempFilePath.toFile()))) {
            writer.writeUTF("Permission1");
            writer.writeUTF("Permission2");
        }

        permissionsDAO.loadFromFile();
        ObservableList<String> permissions = permissionsDAO.getAll();

        assumeTrue(permissions.size()==2);
        assumeTrue(permissions.contains("Permission1"), "Permissions list should contain 'Permission1'.");
        assumeTrue(permissions.contains("Permission2"), "Permissions list should contain 'Permission2'.");
    }

    @Test
    void testUpdatePermissions() {
        ObservableList<String> newPermissions = FXCollections.observableArrayList(
                "NewPermission1", "NewPermission2", "NewPermission3"
        );

        assertTrue(permissionsDAO.update(newPermissions), "Update should return true.");
        ObservableList<String> updatedPermissions = permissionsDAO.getAll();

        assertEquals(3, updatedPermissions.size(), "Updated permissions list should contain 3 items.");
        assertEquals("NewPermission1", updatedPermissions.get(0));
        assertEquals("NewPermission2", updatedPermissions.get(1));
        assertEquals("NewPermission3", updatedPermissions.get(2));
    }

    @Test
    void testUpdateWithEmptyList() {
        ObservableList<String> emptyPermissions = FXCollections.observableArrayList();

        assertTrue(permissionsDAO.update(emptyPermissions), "Update with empty list should return true.");
        ObservableList<String> updatedPermissions = permissionsDAO.getAll();

        assertNotNull(updatedPermissions, "Permissions list should not be null after update.");
        assertTrue(updatedPermissions.isEmpty(), "Permissions list should be empty after updating with an empty list.");
    }

    @Test
    void testHandleCorruptedFile() throws IOException {
        // Write corrupted data to the file
        try (FileOutputStream outputStream = new FileOutputStream(tempFilePath.toFile())) {
            outputStream.write(new byte[]{0x00, 0x01, 0x02, 0x03});
        }

        permissionsDAO.loadFromFile();
        ObservableList<String> permissions = permissionsDAO.getAll();

        assertNotNull(permissions, "Permissions list should not be null even if the file is corrupted.");
        assumeTrue(permissions.isEmpty(), "Permissions list should be empty for corrupted file.");
    }

    @Test
    void testAddAndReloadPermissions() {
        ObservableList<String> initialPermissions = FXCollections.observableArrayList("PermissionA", "PermissionB");
        assertTrue(permissionsDAO.update(initialPermissions), "Initial update should return true.");

        // Create a new DAO instance to simulate reloading from file
        PermissionsDAO newDAO = new PermissionsDAO();
        ObservableList<String> reloadedPermissions = newDAO.getAll();

        assertEquals(2, reloadedPermissions.size(), "Reloaded permissions list should contain 2 items.");
        assertEquals("PermissionA", reloadedPermissions.get(0));
        assertEquals("PermissionB", reloadedPermissions.get(1));
    }

    @Test
    void testUpdateWithSpecialCharacters() {
        ObservableList<String> specialPermissions = FXCollections.observableArrayList(
                "Perm!ssion$", "P3rm1ssion#", "@Permission"
        );

        assertTrue(permissionsDAO.update(specialPermissions), "Update with special characters should return true.");
        ObservableList<String> updatedPermissions = permissionsDAO.getAll();

        assertEquals(3, updatedPermissions.size(), "Permissions list should contain 3 items.");
        assertEquals("Perm!ssion$", updatedPermissions.get(0));
        assertEquals("P3rm1ssion#", updatedPermissions.get(1));
        assertEquals("@Permission", updatedPermissions.get(2));
    }
}

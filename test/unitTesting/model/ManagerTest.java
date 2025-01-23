package unitTesting.model;

import javafx.application.Platform;
import model.Gender;
import model.Manager;
import model.Role;
import model.Status;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.JavaFXInitializer;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ManagerTest {

    private Manager manager;

    @BeforeAll
    static void set(){

        Platform.startup(() -> {
            new JavaFXInitializer().init();
        });
    }

    @BeforeEach
    void setup() {
        new JavaFXInitializer().init(); // Initialize JavaFX for Alert handling
        manager = new Manager("Bob", "Johnson", new Date(), Gender.MALE, "manager123", "password", Role.MANAGER, "manager@example.com", "9876543210", 60000.0);
    }

    @Test
    void testConstructorWithTwoNames() {
        // Assert
        assertEquals("Bob", manager.getFirstName());
        assertEquals("Johnson", manager.getLastName());
        assertEquals(Gender.MALE, manager.getGender());
        assertEquals(Role.MANAGER, manager.getRole());
        assertEquals("manager123", manager.getUsername());
        assertEquals("password", manager.getPassword());
        assertEquals("manager@example.com", manager.getEmail());
        assertEquals("9876543210", manager.getPhoneNo());
        assertEquals(60000.0, manager.getSalary());
        assertNotNull(manager.getUserID());
        assertNotNull(manager.getEnrollmentDate());
    }

    @Test
    void testConstructorWithThreeNames() {
        // Arrange
        Manager manager = new Manager("Bob", "Johnson", "Smith", new Date(), Gender.MALE, "manager123", "password", Role.MANAGER, "manager@example.com", "9876543210", 60000.0);

        // Assert
        assertEquals("Bob Smith Johnson", manager.getFullName());
    }

    @Test
    void testGetAndSetPassword() {
        // Act
        manager.setPassword("newPassword");

        // Assert
        assertEquals("newPassword", manager.getPassword());
    }

    @Test
    void testGetAndSetUsername() {
        // Act
        manager.setUsername("newUsername");

        // Assert
        assertEquals("newUsername", manager.getUsername());
    }

    @Test
    void testSetRoleWithValidString() {
        Platform.runLater(() -> {
            // Act
            manager.setRole("ADMIN");

            // Assert
            assertEquals(Role.ADMIN, manager.getRole());
        });
    }

    @Test
    void testSetRoleWithInvalidString() {
        Platform.runLater(() -> {
            // Act
            manager.setRole("INVALID");

            // Assert
            assertNull(manager.getRole());
        });
    }

    @Test
    void testGetAndSetEmail() {
        // Act
        manager.setEmail("newemail@example.com");

        // Assert
        assertEquals("newemail@example.com", manager.getEmail());
    }

    @Test
    void testGetAndSetPhoneNo() {
        // Act
        manager.setPhoneNo("1234567890");

        // Assert
        assertEquals("1234567890", manager.getPhoneNo());
    }

    @Test
    void testGetAndSetSalary() {
        // Act
        manager.setSalary(70000.0);

        // Assert
        assertEquals(70000.0, manager.getSalary());
    }

    @Test
    void testSetGenderWithValidString() {
        Platform.runLater(() -> {
            // Act
            manager.setGender("FEMALE");

            // Assert
            assertEquals(Gender.FEMALE, manager.getGender());
        });
    }

    @Test
    void testSetGenderWithInvalidString() {
        Platform.runLater(() -> {
            // Act
            manager.setGender("UNKNOWN");

            // Assert
            assertNull(manager.getGender());
        });
    }

    @Test
    void testGetAndSetRevenueMade() {
        // Act
        manager.setRevenueMade(20000.0);

        // Assert
        assertEquals(20000.0, manager.getRevenueMade());

        // Act again
        manager.setRevenueMade(10000.0);

        // Assert
        assertEquals(30000.0, manager.getRevenueMade());
    }

    @Test
    void testGetAndSetNrOfBooksSold() {
        // Act
        manager.setNrOfBooksSold(15);

        // Assert
        assertEquals(15, manager.getNrOfBooksSold());

        // Act again
        manager.setNrOfBooksSold(10);

        // Assert
        assertEquals(25, manager.getNrOfBooksSold());
    }

    @Test
    void testSetFullName() {
        // Act
        manager.setMiddleName("Smith");
        manager.setFullName();

        // Assert
        assertEquals("Bob Smith Johnson", manager.getFullName());
    }

    @Test
    void testSetStatusWithValidString() {
        Platform.runLater(() -> {
            // Act
            manager.setStatus("PASIVE");

            // Assert
            assertEquals(Status.PASIVE, manager.getStatus());
        });
    }

    @Test
    void testSetStatusWithInvalidString() {
        Platform.runLater(() -> {
            // Act
            manager.setStatus("UNKNOWN");

            // Assert
            assertEquals(Status.ACTIVE, manager.getStatus()); // Default status remains unchanged
        });
    }

    @Test
    void testToString() {
        // Act
        String result = manager.toString();

        // Assert
        assertEquals("Bob Johnson", result);
    }

    @Test
    void testGetEnrollmentDate() {
        // Assert
        assertNotNull(manager.getEnrollmentDate());
    }

    @Test
    void testGetUserID() {
        // Assert
        assertNotNull(manager.getUserID());
    }
}

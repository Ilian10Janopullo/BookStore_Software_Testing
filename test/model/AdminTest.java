package model;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.JavaFXInitializer;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminTest {

    private Admin admin;

    @BeforeEach
    void setup() {
        new JavaFXInitializer().init(); // Initialize JavaFX for Alert handling
        admin = new Admin("John", "Doe", new Date(), Gender.MALE, "admin123", "password", Role.ADMIN, "admin@example.com", "1234567890", 50000.0);
    }

    @Test
    void testConstructorWithTwoNames() {
        // Assert
        assertEquals("John", admin.getFirstName());
        assertEquals("Doe", admin.getLastName());
        assertEquals(Gender.MALE, admin.getGender());
        assertEquals(Role.ADMIN, admin.getRole());
        assertEquals("admin123", admin.getUsername());
        assertEquals("password", admin.getPassword());
        assertEquals("admin@example.com", admin.getEmail());
        assertEquals("1234567890", admin.getPhoneNo());
        assertEquals(50000.0, admin.getSalary());
        assertNotNull(admin.getUserID());
        assertNotNull(admin.getEnrollmentDate());
    }

    @Test
    void testConstructorWithThreeNames() {
        // Arrange
        Admin admin = new Admin("John", "Doe", "Smith", new Date(), Gender.MALE, "admin123", "password", Role.ADMIN, "admin@example.com", "1234567890", 50000.0);

        // Assert
        assertEquals("John Smith Doe", admin.getFullName());
    }

    @Test
    void testGetAndSetPassword() {
        // Act
        admin.setPassword("newPassword");

        // Assert
        assertEquals("newPassword", admin.getPassword());
    }

    @Test
    void testGetAndSetUsername() {
        // Act
        admin.setUsername("newUsername");

        // Assert
        assertEquals("newUsername", admin.getUsername());
    }

    @Test
    void testSetRoleWithValidString() {
        Platform.runLater(() -> {
            // Act
            admin.setRole("LIBRARIAN");

            // Assert
            assertEquals(Role.LIBRARIAN, admin.getRole());
        });
    }

    @Test
    void testSetRoleWithInvalidString() {
        Platform.runLater(() -> {
            // Act
            admin.setRole("INVALID");

            // Assert
            assertNull(admin.getRole());
        });
    }

    @Test
    void testGetAndSetEmail() {
        // Act
        admin.setEmail("newemail@example.com");

        // Assert
        assertEquals("newemail@example.com", admin.getEmail());
    }

    @Test
    void testGetAndSetPhoneNo() {
        // Act
        admin.setPhoneNo("0987654321");

        // Assert
        assertEquals("0987654321", admin.getPhoneNo());
    }

    @Test
    void testGetAndSetSalary() {
        // Act
        admin.setSalary(60000.0);

        // Assert
        assertEquals(60000.0, admin.getSalary());
    }

    @Test
    void testSetGenderWithValidString() {
        Platform.runLater(() -> {
            // Act
            admin.setGender("FEMALE");

            // Assert
            assertEquals(Gender.FEMALE, admin.getGender());
        });
    }

    @Test
    void testSetGenderWithInvalidString() {
        Platform.runLater(() -> {
            // Act
            admin.setGender("UNKNOWN");

            // Assert
            assertNull(admin.getGender());
        });
    }

    @Test
    void testGetAndSetRevenueMade() {
        // Act
        admin.setRevenueMade(10000.0);

        // Assert
        assertEquals(10000.0, admin.getRevenueMade());

        // Act again
        admin.setRevenueMade(5000.0);

        // Assert
        assertEquals(15000.0, admin.getRevenueMade());
    }

    @Test
    void testGetAndSetNrOfBooksSold() {
        // Act
        admin.setNrOfBooksSold(10);

        // Assert
        assertEquals(10, admin.getNrOfBooksSold());

        // Act again
        admin.setNrOfBooksSold(5);

        // Assert
        assertEquals(15, admin.getNrOfBooksSold());
    }

    @Test
    void testSetFullName() {
        // Act
        admin.setMiddleName("Smith");
        admin.setFullName();

        // Assert
        assertEquals("John Smith Doe", admin.getFullName());
    }

    @Test
    void testSetStatusWithValidString() {
        Platform.runLater(() -> {
            // Act
            admin.setStatus("PASIVE");

            // Assert
            assertEquals(Status.PASIVE, admin.getStatus());
        });
    }

    @Test
    void testSetStatusWithInvalidString() {
        Platform.runLater(() -> {
            // Act
            admin.setStatus("UNKNOWN");

            // Assert
            assertEquals(Status.ACTIVE, admin.getStatus()); // Default status remains unchanged
        });
    }

    @Test
    void testToString() {
        // Act
        String result = admin.toString();

        // Assert
        assertEquals("John Doe", result);
    }

    @Test
    void testGetEnrollmentDate() {
        // Assert
        assertNotNull(admin.getEnrollmentDate());
    }

    @Test
    void testGetUserID() {
        // Assert
        assertNotNull(admin.getUserID());
    }
}

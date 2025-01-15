package model;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.JavaFXInitializer;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LibrarianTest {

    private Librarian librarian;

    @BeforeEach
    void setup() {
        new JavaFXInitializer().init(); // Initialize JavaFX for Alert handling
        librarian = new Librarian("Alice", "Smith", new Date(), Gender.FEMALE, "librarian123", "password", Role.LIBRARIAN, "librarian@example.com", "1234567890", 40000.0);
    }

    @Test
    void testConstructorWithTwoNames() {
        // Assert
        assertEquals("Alice", librarian.getFirstName());
        assertEquals("Smith", librarian.getLastName());
        assertEquals(Gender.FEMALE, librarian.getGender());
        assertEquals(Role.LIBRARIAN, librarian.getRole());
        assertEquals("librarian123", librarian.getUsername());
        assertEquals("password", librarian.getPassword());
        assertEquals("librarian@example.com", librarian.getEmail());
        assertEquals("1234567890", librarian.getPhoneNo());
        assertEquals(40000.0, librarian.getSalary());
        assertNotNull(librarian.getUserID());
        assertNotNull(librarian.getEnrollmentDate());
    }

    @Test
    void testConstructorWithThreeNames() {
        // Arrange
        Librarian librarian = new Librarian("Alice", "Smith", "Johnson", new Date(), Gender.FEMALE, "librarian123", "password", Role.LIBRARIAN, "librarian@example.com", "1234567890", 40000.0);

        // Assert
        assertEquals("Alice Johnson Smith", librarian.getFullName());
    }

    @Test
    void testGetAndSetPassword() {
        // Act
        librarian.setPassword("newPassword");

        // Assert
        assertEquals("newPassword", librarian.getPassword());
    }

    @Test
    void testGetAndSetUsername() {
        // Act
        librarian.setUsername("newUsername");

        // Assert
        assertEquals("newUsername", librarian.getUsername());
    }

    @Test
    void testSetRoleWithValidString() {
        Platform.runLater(() -> {
            // Act
            librarian.setRole("ADMIN");

            // Assert
            assertEquals(Role.ADMIN, librarian.getRole());
        });
    }

    @Test
    void testSetRoleWithInvalidString() {
        Platform.runLater(() -> {
            // Act
            librarian.setRole("INVALID");

            // Assert
            assertNull(librarian.getRole());
        });
    }

    @Test
    void testGetAndSetEmail() {
        // Act
        librarian.setEmail("newemail@example.com");

        // Assert
        assertEquals("newemail@example.com", librarian.getEmail());
    }

    @Test
    void testGetAndSetPhoneNo() {
        // Act
        librarian.setPhoneNo("0987654321");

        // Assert
        assertEquals("0987654321", librarian.getPhoneNo());
    }

    @Test
    void testGetAndSetSalary() {
        // Act
        librarian.setSalary(50000.0);

        // Assert
        assertEquals(50000.0, librarian.getSalary());
    }

    @Test
    void testSetGenderWithValidString() {
        Platform.runLater(() -> {
            // Act
            librarian.setGender("MALE");

            // Assert
            assertEquals(Gender.MALE, librarian.getGender());
        });
    }

    @Test
    void testSetGenderWithInvalidString() {
        Platform.runLater(() -> {
            // Act
            librarian.setGender("UNKNOWN");

            // Assert
            assertNull(librarian.getGender());
        });
    }

    @Test
    void testGetAndSetRevenueMade() {
        // Act
        librarian.setRevenueMade(15000.0);

        // Assert
        assertEquals(15000.0, librarian.getRevenueMade());

        // Act again
        librarian.setRevenueMade(5000.0);

        // Assert
        assertEquals(20000.0, librarian.getRevenueMade());
    }

    @Test
    void testGetAndSetNrOfBooksSold() {
        // Act
        librarian.setNrOfBooksSold(20);

        // Assert
        assertEquals(20, librarian.getNrOfBooksSold());

        // Act again
        librarian.setNrOfBooksSold(15);

        // Assert
        assertEquals(35, librarian.getNrOfBooksSold());
    }

    @Test
    void testSetFullName() {
        // Act
        librarian.setMiddleName("Johnson");
        librarian.setFullName();

        // Assert
        assertEquals("Alice Johnson Smith", librarian.getFullName());
    }

    @Test
    void testSetStatusWithValidString() {
        Platform.runLater(() -> {
            // Act
            librarian.setStatus("PASIVE");

            // Assert
            assertEquals(Status.PASIVE, librarian.getStatus());
        });
    }

    @Test
    void testSetStatusWithInvalidString() {
        Platform.runLater(() -> {
            // Act
            librarian.setStatus("UNKNOWN");

            // Assert
            assertEquals(Status.ACTIVE, librarian.getStatus()); // Default status remains unchanged
        });
    }

    @Test
    void testToString() {
        // Act
        String result = librarian.toString();

        // Assert
        assertEquals("Alice Smith", result);
    }

    @Test
    void testGetEnrollmentDate() {
        // Assert
        assertNotNull(librarian.getEnrollmentDate());
    }

    @Test
    void testGetUserID() {
        // Assert
        assertNotNull(librarian.getUserID());
    }
}

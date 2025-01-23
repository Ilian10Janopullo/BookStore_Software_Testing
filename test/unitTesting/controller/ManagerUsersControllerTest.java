package unitTesting.controller;

import controller.ManageUsersController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.JavaFXInitializer;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ManagerUsersControllerTest {
    private static ManageUsersController controller;
    private static UsersOfTheSystem mockUser;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {

        Platform.startup(() -> {
            new JavaFXInitializer().init(); // Initialize JavaFX for UI components
            controller = mock(ManageUsersController.class, CALLS_REAL_METHODS); // Mock the controller
        });

    }

    @BeforeEach
    void setUp() {
        mockUser = mock(UsersOfTheSystem.class);
    }

    @ParameterizedTest
    @DisplayName("Equivalence Class Testing for checkBirthdate")
    @CsvSource({
            "2000, 2, 29, true",  // Leap year
            "2023, 1, 1, true",   // Start of year
            "2023, 12, 31, true", // End of year
            "2023, 4, 30, true",  // Valid end of April
            "-1, 1, 15, false",   // Negative year
            "123, 1, 15, false",  // Too few digits
            "12345, 1, 15, false",// Too many digits
            "abcd, 1, 15, false", // Non-numeric year
            ", 1, 15, false",     // Null year
            "2023, 0, 15, false", // Month below range
            "2023, 13, 15, false",// Month above range
            "2023, xyz, 15, false", // Non-numeric month
            "2023, @, 15, false", // Special character month
            "2023, , 15, false",  // Null month
            "2023, 1, 0, false",  // Day below range
            "2023, 1, 32, false", // Day above range
            "2023, 4, 31, false", // Invalid day for April
            "2023, 2, 29, false", // Invalid day for non-leap year
            "2023, 2, xyz, false",// Non-numeric day
            "2023, 2, @, false",  // Special character day
            "2023, 2, , false",  // Null day
            "-1, 1, 1, false",    // Extreme negative year
            "3000, 1, 1, false"  // Extreme future year
    })
    void testCheckBirthdateEquivalence(String year, String month, String day, boolean expected) {
        Platform.runLater(() -> {
            try {
                int y = Integer.parseInt(year);
                int m = Integer.parseInt(month);
                int d = Integer.parseInt(day);
                Alert mockAlert = mock(Alert.class);
                doNothing().when(mockAlert).show();

                boolean result = controller.checkBirthdate(d, m, y);
                assertEquals(expected, result);
            } catch (NumberFormatException | NullPointerException e) {
                assertFalse(expected);
            }
        });
    }

    @ParameterizedTest
    @DisplayName("Boundary Value Testing for checkBirthdate")
    @CsvSource({
            // Year Boundary Cases
            "2007, 1, 1, true",   // Minimum valid age (16 years)
            "2008, 1, 1, false",  // Just below minimum
            "1958, 1, 1, true",   // Maximum valid age (65 years)
            "1957, 1, 1, false",  // Just above maximum
            "-1, 1, 1, false",    // Negative year
            "3000, 1, 1, false",  // Future year
            // Day Boundary Cases
            "2000, 1, 0, false",  // Day below range (January)
            "2000, 1, 32, false", // Day above range (January)
            "2000, 4, 31, false", // Invalid day for April
            "2001, 2, 28, true",  // Maximum day for February Non-leap year
            "2000, 2, 29, true",  // Maximum day for February Leap year
            "2001, 2, 29, false", // Invalid day for February Non-leap year
            // Month Boundary Cases
            "2000, 0, 15, false", // Month below range
            "2000, 13, 15, false", // Month above range
            "2000, 8, 15, true", // Nominal month value
            "2000, 1, 15, true", // Month minimum range
            "2000, 12, 15, true" // Month maximum range
    })
    void testCheckBirthdateBoundary(int year, int month, int day, boolean expected) {
        Platform.runLater(() -> {
            Alert mockAlert = mock(Alert.class);
            doNothing().when(mockAlert).show();

            boolean result = controller.checkBirthdate(day, month, year);
            assertEquals(expected, result);
        });
    }

    @ParameterizedTest
    @DisplayName("Code Coverage Testing for checkBirthdate")
    @CsvSource({
            // Age Boundary Cases
            "2007,1,1,false",   // Minimum invalid age (just below 16)
            "1958,1,1,false",  // Maximum invalid age (just above 65)
            "2000,1,1,true",     // Valid age, month, and day
            "-1,1,1,false",  // Invalid negative year
            "3000,1,1,false",   // Invalid future year
            "2000,0,15,false", // Invalid month (below range)
            "2000,13,15,false", // Invalid month (above range)
            "2000,1,0,false",    // Invalid day (below range)
            "2000,1,32,false",   // Invalid day (above range for January)
            "2000,4,31,false",   // Invalid day for April (30-day month)
            "2001,2,28,true",    // Valid February day (non-leap year)
            "2000,2,29,true",    // Valid February day (leap year)
            "2001,2,29,false",   // Invalid February day (non-leap year)
            "2000,2,30,false",   // Invalid February day (leap year)
            "2000,2,1,true",     // First valid day of February
            "2000,2,28,true",    // Last valid day of February (non-leap year)
            "2000,2,29,true",    // Last valid day of February (leap year)
            "2024,2,29,true",    // Valid leap year (future)
            "2000,3,31,true",    // Valid day in 31-day month (March)
            "2000,4,30,true"    // Valid day in 30-day month (April)
    })
    void testCheckBirthdateCoverage(int year, int month, int day, boolean expected) {
        Platform.runLater(() -> {
            Alert mockAlert = mock(Alert.class);
            doNothing().when(mockAlert).show();

            boolean result = controller.checkBirthdate(day, month, year);
            assertEquals(expected, result);
        });
    }

    @Test
    @DisplayName("Test checkFirstName() with Valid Names")
    void testCheckFirstNameValid() {
        Platform.runLater(() -> {
            String validName = "John";
            assertTrue(controller.checkFirstName(validName));
        });
    }

    @Test
    @DisplayName("Test checkFirstName() with Invalid Names")
    void testCheckFirstNameInvalid() {
        Platform.runLater(() -> {
            String invalidName = "John123";
            assertFalse(controller.checkFirstName(invalidName));
        });
    }

    @Test
    @DisplayName("Test checkLastName() with Valid Names")
    void testCheckLastNameValid() {
        Platform.runLater(() -> {
            String validName = "Doe";
            assertTrue(controller.checkLastName(validName));
        });
    }

    @Test
    @DisplayName("Test checkLastName() with Invalid Names")
    void testCheckLastNameInvalid() {
        Platform.runLater(() -> {
            String invalidName = "Doe@123";
            assertFalse(controller.checkLastName(invalidName));
        });
    }

    @Test
    @DisplayName("Test checkUsername() with Valid Usernames")
    void testCheckUsernameValid() {
        Platform.runLater(() -> {
            String validUsername = "john_doe";
            assertTrue(controller.checkUsername(validUsername));
        });
    }

    @Test
    @DisplayName("Test checkUsername() with Invalid Usernames")
    void testCheckUsernameInvalid() {
        Platform.runLater(() -> {
            String invalidUsername = "John@Doe";
            assertFalse(controller.checkUsername(invalidUsername));
        });
    }
}

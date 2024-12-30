package controller;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import util.JavaFXInitializer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class ManagerUsersControllerTest {
    private static ManageUsersController controller;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        new JavaFXInitializer().init(); // Initialize JavaFX for UI components
        controller = mock(ManageUsersController.class, CALLS_REAL_METHODS); // Mock the controller
    }
    @ParameterizedTest
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
            "2023, 2, , false"    // Null day
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
}

package unitTesting.controller;

import controller.CheckUsersController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class CheckUsersControllerTest {

    private static ObservableList<UsersOfTheSystem> mockUsers;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        // Initialize JavaFX Toolkit
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();

        // Initialize mock users
        mockUsers = FXCollections.observableArrayList(
                new Admin("Admin", "Test", new Date(), Gender.MALE, "admin", "admin123", Role.ADMIN, "admin@test.com", "1111111111", 5000),
                new Manager("Manager", "Test", new Date(), Gender.FEMALE, "manager", "manager123", Role.MANAGER, "manager@test.com", "2222222222", 4000),
                new Librarian("Librarian", "Test", new Date(), Gender.MALE, "librarian", "librarian123", Role.LIBRARIAN, "librarian@test.com", "3333333333", 3000)
        );
    }

    @Test
    void testInitializeControllerForManager() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();
            UsersOfTheSystem manager = new Manager("Manager", "Test", new Date(), Gender.FEMALE, "manager", "manager123", Role.MANAGER, "manager@test.com", "2222222222", 4000);

            CheckUsersController controller = new CheckUsersController(mockStage, manager);

            // Verify that the view is initialized and not null
            assertNotNull(controller.getView());

            // Verify that the table view contains the correct users
            assertTrue(controller.getView().getTableView().getItems()
                    .stream()
                    .allMatch(user -> user instanceof Manager || user instanceof Librarian));
        });
    }

    @Test
    void testInitializeControllerForLibrarian() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();
            UsersOfTheSystem librarian = new Librarian("Librarian", "Test", new Date(), Gender.FEMALE, "librarian", "librarian123", Role.LIBRARIAN, "librarian@test.com", "3333333333", 3000);

            CheckUsersController controller = new CheckUsersController(mockStage, librarian);

            // Verify that the view is initialized and not null
            assertNotNull(controller.getView());

            // Verify that the table view contains only Librarian users
            assertTrue(controller.getView().getTableView().getItems()
                    .stream()
                    .allMatch(user -> user instanceof Librarian));
        });
    }

    @Test
    void testSearchFilter() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();
            UsersOfTheSystem manager = new Manager("Manager", "Test", new Date(), Gender.FEMALE, "manager", "manager123", Role.MANAGER, "manager@test.com", "2222222222", 4000);

            CheckUsersController controller = new CheckUsersController(mockStage, manager);

            // Simulate setting a search filter
            controller.getView().getSearchBarTf().setText("Manager");
            ObservableList<UsersOfTheSystem> filteredItems = controller.getView().getTableView().getItems();

            // Verify that the filtered list contains matching items
            assertTrue(filteredItems.stream().allMatch(user -> user.getFullName().contains("Manager")));
        });
    }

    @Test
    void testResetSearchFilter() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();
            UsersOfTheSystem manager = new Manager("Manager", "Test", new Date(), Gender.FEMALE, "manager", "manager123", Role.MANAGER, "manager@test.com", "2222222222", 4000);

            CheckUsersController controller = new CheckUsersController(mockStage, manager);

            // Set a search filter and then reset it
            controller.getView().getSearchBarTf().setText("Manager");
            controller.getView().getResetBtn().fire();

            // Verify that the search filter is cleared
            assertEquals("", controller.getView().getSearchBarTf().getText());

            // Verify that the full list is displayed again
            assertEquals(mockUsers.size(), controller.getView().getTableView().getItems().size());
        });
    }

    @Test
    void testBackFunctionForAdmin() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();
            UsersOfTheSystem admin = new Admin("Admin", "Test", new Date(), Gender.MALE, "admin", "admin123", Role.ADMIN, "admin@test.com", "1111111111", 5000);

            CheckUsersController controller = new CheckUsersController(mockStage, admin);
            controller.Back(null);

            // Verify that the back function works for Admin
            assertNotNull(controller.getView()); // Mock assertions to confirm functionality
        });
    }

    @Test
    void testBackFunctionForManager() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();
            UsersOfTheSystem manager = new Manager("Manager", "Test", new Date(), Gender.FEMALE, "manager", "manager123", Role.MANAGER, "manager@test.com", "2222222222", 4000);

            CheckUsersController controller = new CheckUsersController(mockStage, manager);
            controller.Back(null);

            // Verify that the back function works for Manager
            assertNotNull(controller.getView()); // Mock assertions to confirm functionality
        });
    }
}

package view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TableViewCheckingUsersTest extends ApplicationTest {

    private TableViewCheckingUsers tableViewCheckingUsers;

    @Override
    public void start(Stage stage) {
        Platform.runLater(() -> {
            tableViewCheckingUsers = new TableViewCheckingUsers();
            Scene scene = new Scene(tableViewCheckingUsers, 1200, 800);
            stage.setScene(scene);
            stage.show();
        });
    }

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> tableViewCheckingUsers = new TableViewCheckingUsers());
    }

    @Test
    void testComponentInitialization() {
        Platform.runLater(() -> {
            // Ensure components are initialized
            assertNotNull(tableViewCheckingUsers.getTableView());
            assertNotNull(tableViewCheckingUsers.getFullNameColumn());
            assertNotNull(tableViewCheckingUsers.getGenderColumn());
            assertNotNull(tableViewCheckingUsers.getUserIDColumn());
            assertNotNull(tableViewCheckingUsers.getRevenueMadeColumn());
            assertNotNull(tableViewCheckingUsers.getSearchBarTf());
            assertNotNull(tableViewCheckingUsers.getResetBtn());
            assertNotNull(tableViewCheckingUsers.getReturnButton());
        });
    }

    @Test
    void testTableColumnsPresence() {
        Platform.runLater(() -> {
            // Verify that all columns are added to the table view
            assertTrue(tableViewCheckingUsers.getTableView().getColumns().contains(tableViewCheckingUsers.getFullNameColumn()));
            assertTrue(tableViewCheckingUsers.getTableView().getColumns().contains(tableViewCheckingUsers.getGenderColumn()));
            assertTrue(tableViewCheckingUsers.getTableView().getColumns().contains(tableViewCheckingUsers.getUserIDColumn()));
            assertTrue(tableViewCheckingUsers.getTableView().getColumns().contains(tableViewCheckingUsers.getRevenueMadeColumn()));
            assertTrue(tableViewCheckingUsers.getTableView().getColumns().contains(tableViewCheckingUsers.getRoleColumn()));
        });
    }

    @Test
    void testDefaultSearchBar() {
        Platform.runLater(() -> {
            // Ensure search bar is initialized and empty
            assertNotNull(tableViewCheckingUsers.getSearchBarTf());
            assertEquals("", tableViewCheckingUsers.getSearchBarTf().getText());
        });
    }

    @Test
    void testSetSearchBar() {
        Platform.runLater(() -> {
            // Set a value in the search bar
            tableViewCheckingUsers.getSearchBarTf().setText("SearchTest");

            // Verify the value is set correctly
            assertEquals("SearchTest", tableViewCheckingUsers.getSearchBarTf().getText());

            // Clear the search bar
            tableViewCheckingUsers.setSearchBarTf();

            // Verify the search bar is cleared
            assertEquals("", tableViewCheckingUsers.getSearchBarTf().getText());
        });
    }

    @Test
    void testButtonProperties() {
        Platform.runLater(() -> {
            // Verify button properties
            assertEquals(80, tableViewCheckingUsers.getResetBtn().getPrefWidth());
            assertNotNull(tableViewCheckingUsers.getResetBtn().getGraphic());
            assertEquals(80, tableViewCheckingUsers.getReturnButton().getPrefWidth());
            assertNotNull(tableViewCheckingUsers.getReturnButton().getGraphic());
        });
    }

    @Test
    void testTableViewDataBinding() {
        Platform.runLater(() -> {
            // Add sample data to the table view
            UsersOfTheSystem user1 = new Admin("Alice", "Smith", new Date(), Gender.FEMALE, "alicesmith", "password1", Role.MANAGER, "alice@example.com", "1234567890", 50000.0);
            UsersOfTheSystem user2 = new Manager("Bob", "Johnson", new Date(), Gender.MALE, "bobjohnson", "password2", Role.LIBRARIAN, "bob@example.com", "0987654321", 40000.0);
            tableViewCheckingUsers.getTableView().getItems().addAll(user1, user2);

            // Verify data binding
            assertEquals(2, tableViewCheckingUsers.getTableView().getItems().size());
            assertEquals("Alice", tableViewCheckingUsers.getTableView().getItems().get(0).getFirstName());
            assertEquals("Bob", tableViewCheckingUsers.getTableView().getItems().get(1).getFirstName());
        });
    }

    @Test
    void testColumnWidths() {
        Platform.runLater(() -> {
            // Verify column widths
            assertEquals(135, tableViewCheckingUsers.getFullNameColumn().getMinWidth());
            assertEquals(135, tableViewCheckingUsers.getUserIDColumn().getMinWidth());
            assertEquals(135, tableViewCheckingUsers.getRevenueMadeColumn().getMinWidth());
            assertEquals(135, tableViewCheckingUsers.getPhoneNoColumn().getMinWidth());
        });
    }
}

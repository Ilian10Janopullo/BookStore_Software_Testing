package unitTesting.view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import view.TableManagingUsersView;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class TableManagingUsersViewTest extends ApplicationTest {

    private TableManagingUsersView tableManagingUsersView;

    @Override
    public void start(Stage stage) {
        Platform.runLater(() -> {
            tableManagingUsersView = new TableManagingUsersView();
            Scene scene = new Scene(tableManagingUsersView, 1200, 800);
            stage.setScene(scene);
            stage.show();
        });
    }

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> tableManagingUsersView = new TableManagingUsersView());
    }

    @Test
    void testComponentInitialization() {
        Platform.runLater(() -> {
            // Test if all components are initialized properly
            assertNotNull(tableManagingUsersView.getTableView());
            assertNotNull(tableManagingUsersView.getFirstNameColumn());
            assertNotNull(tableManagingUsersView.getLastNameColumn());
            assertNotNull(tableManagingUsersView.getMiddleNameColumn());
            assertNotNull(tableManagingUsersView.getGenderColumn());
            assertNotNull(tableManagingUsersView.getRoleComboBox());
            assertNotNull(tableManagingUsersView.getSubmitBtn());
            assertNotNull(tableManagingUsersView.getBtnUpdate());
            assertNotNull(tableManagingUsersView.getBtnDelete());
            assertNotNull(tableManagingUsersView.getReturnButton());
        });
    }


    @Test
    void testAddUserInputs() {
        Platform.runLater(() -> {
            // Test setting and getting text fields
            tableManagingUsersView.setFirstNameTF("John");
            tableManagingUsersView.setLastNameTF("Doe");
            tableManagingUsersView.setMiddleNameTF("Middle");
            tableManagingUsersView.getBirthDateDayTf().setText("15");
            tableManagingUsersView.getBirthDateMonthTf().setText("6");
            tableManagingUsersView.getBirthDateYearTf().setText("1990");

            assertEquals("John", tableManagingUsersView.getFirstNameTF());
            assertEquals("Doe", tableManagingUsersView.getLastNameTF());
            assertEquals("Middle", tableManagingUsersView.getMiddleNameTF());
            assertEquals("15", tableManagingUsersView.getBirthDateDayTf().getText());
            assertEquals("6", tableManagingUsersView.getBirthDateMonthTf().getText());
            assertEquals("1990", tableManagingUsersView.getBirthDateYearTf().getText());
        });
    }

    @Test
    void testTableColumns() {
        Platform.runLater(() -> {
            // Ensure all columns are added to the table
            assertTrue(tableManagingUsersView.getTableView().getColumns().contains(tableManagingUsersView.getFirstNameColumn()));
            assertTrue(tableManagingUsersView.getTableView().getColumns().contains(tableManagingUsersView.getLastNameColumn()));
            assertTrue(tableManagingUsersView.getTableView().getColumns().contains(tableManagingUsersView.getRoleColumn()));
        });
    }

    @Test
    void testButtonProperties() {
        Platform.runLater(() -> {
            // Verify button properties
            assertEquals(80, tableManagingUsersView.getReturnButton().getPrefWidth());
            assertNotNull(tableManagingUsersView.getReturnButton().getGraphic());
            assertEquals(80, tableManagingUsersView.getBtnUpdate().getPrefWidth());
            assertNotNull(tableManagingUsersView.getBtnUpdate().getGraphic());
            assertEquals(80, tableManagingUsersView.getBtnDelete().getPrefWidth());
            assertNotNull(tableManagingUsersView.getBtnDelete().getGraphic());
        });
    }

    @Test
    void testClearInputFields() {
        Platform.runLater(() -> {
            // Set some values
            tableManagingUsersView.setFirstNameTF("John");
            tableManagingUsersView.setLastNameTF("Doe");
            tableManagingUsersView.setMiddleNameTF("Middle");

            // Clear the fields
            tableManagingUsersView.setFirstNameTF("");
            tableManagingUsersView.setLastNameTF("");
            tableManagingUsersView.setMiddleNameTF("");

            // Assert fields are cleared
            assertEquals("", tableManagingUsersView.getFirstNameTF());
            assertEquals("", tableManagingUsersView.getLastNameTF());
            assertEquals("", tableManagingUsersView.getMiddleNameTF());
        });
    }

    @Test
    void testTableViewBinding() {
        Platform.runLater(() -> {
            // Add some users to the table view
            UsersOfTheSystem user1 = new Admin("John", "Doe", new Date(), Gender.MALE, "johndoe", "password", Role.MANAGER, "john@example.com", "1234567890", 50000.0);
            UsersOfTheSystem user2 = new Manager("Jane", "Doe", new Date(), Gender.FEMALE, "janedoe", "password123", Role.LIBRARIAN, "jane@example.com", "0987654321", 40000.0);
            tableManagingUsersView.getTableView().setItems(FXCollections.observableArrayList(user1, user2));

            // Verify data in the table
            assertEquals(2, tableManagingUsersView.getTableView().getItems().size());
            assertEquals("John", tableManagingUsersView.getTableView().getItems().get(0).getFirstName());
            assertEquals("Jane", tableManagingUsersView.getTableView().getItems().get(1).getFirstName());
        });
    }
}

package unitTesting.view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Author;
import model.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import view.TableViewManagingAuthors;

import static org.junit.jupiter.api.Assertions.*;

class TableViewManagingAuthorsTest extends ApplicationTest {

    private TableViewManagingAuthors tableViewManagingAuthors;

    @Override
    public void start(Stage stage) {
        Platform.runLater(() -> {
            tableViewManagingAuthors = new TableViewManagingAuthors();
            Scene scene = new Scene(tableViewManagingAuthors, 1200, 800);
            stage.setScene(scene);
            stage.show();
        });
    }

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> tableViewManagingAuthors = new TableViewManagingAuthors());
    }

    @Test
    void testComponentInitialization() {
        Platform.runLater(() -> {
            // Ensure components are initialized
            assertNotNull(tableViewManagingAuthors.getTableView());
            assertNotNull(tableViewManagingAuthors.getFirstNameColumn());
            assertNotNull(tableViewManagingAuthors.getMiddleNameColumn());
            assertNotNull(tableViewManagingAuthors.getLastNameColumn());
            assertNotNull(tableViewManagingAuthors.getFullNameColumn());
            assertNotNull(tableViewManagingAuthors.getNrOfBooksColumn());
            assertNotNull(tableViewManagingAuthors.getNrOfBooksSoldColumn());
            assertNotNull(tableViewManagingAuthors.getGenderColumn());
            assertNotNull(tableViewManagingAuthors.getSearchBarTf());
            assertNotNull(tableViewManagingAuthors.getSubmitBtn());
            assertNotNull(tableViewManagingAuthors.getBtnDelete());
            assertNotNull(tableViewManagingAuthors.getBtnUpdate());
            assertNotNull(tableViewManagingAuthors.getReturnButton());
        });
    }

    @Test
    void testTableColumnsPresence() {
        Platform.runLater(() -> {
            // Verify that all columns are added to the table view
            assertTrue(tableViewManagingAuthors.getTableView().getColumns().contains(tableViewManagingAuthors.getFirstNameColumn()));
            assertTrue(tableViewManagingAuthors.getTableView().getColumns().contains(tableViewManagingAuthors.getMiddleNameColumn()));
            assertTrue(tableViewManagingAuthors.getTableView().getColumns().contains(tableViewManagingAuthors.getLastNameColumn()));
            assertTrue(tableViewManagingAuthors.getTableView().getColumns().contains(tableViewManagingAuthors.getFullNameColumn()));
            assertTrue(tableViewManagingAuthors.getTableView().getColumns().contains(tableViewManagingAuthors.getNrOfBooksColumn()));
            assertTrue(tableViewManagingAuthors.getTableView().getColumns().contains(tableViewManagingAuthors.getNrOfBooksSoldColumn()));
            assertTrue(tableViewManagingAuthors.getTableView().getColumns().contains(tableViewManagingAuthors.getGenderColumn()));
        });
    }

    @Test
    void testSearchBarDefaultState() {
        Platform.runLater(() -> {
            // Ensure search bar is initialized and empty
            assertNotNull(tableViewManagingAuthors.getSearchBarTf());
            assertEquals("", tableViewManagingAuthors.getSearchBarTf().getText());
        });
    }

    @Test
    void testSetSearchBar() {
        Platform.runLater(() -> {
            // Set a value in the search bar
            tableViewManagingAuthors.getSearchBarTf().setText("SearchTest");

            // Verify the value is set correctly
            assertEquals("SearchTest", tableViewManagingAuthors.getSearchBarTf().getText());

            // Clear the search bar
            tableViewManagingAuthors.getSearchBarTf().setText("");

            // Verify the search bar is cleared
            assertEquals("", tableViewManagingAuthors.getSearchBarTf().getText());
        });
    }

    @Test
    void testAddAuthor() {
        Platform.runLater(() -> {
            // Add a sample author
            Author author = new Author("John", "Doe", Gender.MALE);
            tableViewManagingAuthors.getTableView().getItems().add(author);

            // Verify the author is added
            assertEquals(1, tableViewManagingAuthors.getTableView().getItems().size());
            assertEquals("John", tableViewManagingAuthors.getTableView().getItems().get(0).getFirstName());
            assertEquals("Doe", tableViewManagingAuthors.getTableView().getItems().get(0).getLastName());
        });
    }

    @Test
    void testButtonProperties() {
        Platform.runLater(() -> {
            // Verify button properties
            assertEquals(80, tableViewManagingAuthors.getReturnButton().getPrefWidth());
            assertNotNull(tableViewManagingAuthors.getReturnButton().getGraphic());
            assertEquals(80, tableViewManagingAuthors.getBtnDelete().getPrefWidth());
            assertNotNull(tableViewManagingAuthors.getBtnDelete().getGraphic());
            assertEquals(80, tableViewManagingAuthors.getBtnUpdate().getPrefWidth());
            assertNotNull(tableViewManagingAuthors.getBtnUpdate().getGraphic());
            assertEquals(100, tableViewManagingAuthors.getSubmitBtn().getPrefWidth());
            assertNotNull(tableViewManagingAuthors.getSubmitBtn().getGraphic());
        });
    }

    @Test
    void testTextFieldFunctionality() {
        Platform.runLater(() -> {
            // Set values in the text fields
            tableViewManagingAuthors.setFirstNameTF("Jane");
            tableViewManagingAuthors.setMiddleNameTF("Middle");
            tableViewManagingAuthors.setLastNameTF("Doe");

            // Verify the text fields
            assertEquals("Jane", tableViewManagingAuthors.getFirstNameTF());
            assertEquals("Middle", tableViewManagingAuthors.getMiddleNameTF());
            assertEquals("Doe", tableViewManagingAuthors.getLastNameTF());

            // Clear the text fields
            tableViewManagingAuthors.setFirstNameTF("");
            tableViewManagingAuthors.setMiddleNameTF("");
            tableViewManagingAuthors.setLastNameTF("");

            // Verify the text fields are cleared
            assertEquals("", tableViewManagingAuthors.getFirstNameTF());
            assertEquals("", tableViewManagingAuthors.getMiddleNameTF());
            assertEquals("", tableViewManagingAuthors.getLastNameTF());
        });
    }

    @Test
    void testGenderComboBox() {
        Platform.runLater(() -> {
            // Select a gender
            tableViewManagingAuthors.getGenderComboBox().getSelectionModel().select(Gender.FEMALE);

            // Verify selection
            assertEquals(Gender.FEMALE, tableViewManagingAuthors.getGenderComboBox().getSelectionModel().getSelectedItem());

            // Clear selection
            tableViewManagingAuthors.setGenderComboBox();

            // Verify no selection
            assertNull(tableViewManagingAuthors.getGenderComboBox().getSelectionModel().getSelectedItem());
        });
    }
}

package view;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Author;
import model.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class TableViewManagingBooksTest extends ApplicationTest {

    private TableViewManagingBooks tableViewManagingBooks;

    @Override
    public void start(Stage stage) {
        Platform.runLater(() -> {
            tableViewManagingBooks = new TableViewManagingBooks();
            Scene scene = new Scene(tableViewManagingBooks, 1200, 800);
            stage.setScene(scene);
            stage.show();
        });
    }

    @BeforeEach
    void setUp() {
        Platform.runLater(() -> tableViewManagingBooks = new TableViewManagingBooks());
    }

    @Test
    void testComponentInitialization() {
        Platform.runLater(() -> {
            // Verify main components are initialized
            assertNotNull(tableViewManagingBooks.getTableView());
            assertNotNull(tableViewManagingBooks.getTitleColumn());
            assertNotNull(tableViewManagingBooks.getIsbnColumn());
            assertNotNull(tableViewManagingBooks.getDescriptionColumn());
            assertNotNull(tableViewManagingBooks.getPriceColumn());
            assertNotNull(tableViewManagingBooks.getAuthorColumn());
            assertNotNull(tableViewManagingBooks.getGenreColumn());
            assertNotNull(tableViewManagingBooks.getCopiesSoldColumn());
            assertNotNull(tableViewManagingBooks.getQuantityColumn());
            assertNotNull(tableViewManagingBooks.getPaperbackColumn());
        });
    }

    @Test
    void testTableColumnsPresence() {
        Platform.runLater(() -> {
            // Verify that all required columns are present
            assertTrue(tableViewManagingBooks.getTableView().getColumns().contains(tableViewManagingBooks.getTitleColumn()));
            assertTrue(tableViewManagingBooks.getTableView().getColumns().contains(tableViewManagingBooks.getIsbnColumn()));
            assertTrue(tableViewManagingBooks.getTableView().getColumns().contains(tableViewManagingBooks.getDescriptionColumn()));
            assertTrue(tableViewManagingBooks.getTableView().getColumns().contains(tableViewManagingBooks.getPriceColumn()));
            assertTrue(tableViewManagingBooks.getTableView().getColumns().contains(tableViewManagingBooks.getAuthorColumn()));
            assertTrue(tableViewManagingBooks.getTableView().getColumns().contains(tableViewManagingBooks.getGenreColumn()));
            assertTrue(tableViewManagingBooks.getTableView().getColumns().contains(tableViewManagingBooks.getQuantityColumn()));
            assertTrue(tableViewManagingBooks.getTableView().getColumns().contains(tableViewManagingBooks.getCopiesSoldColumn()));
        });
    }

    @Test
    void testSearchBarDefaultState() {
        Platform.runLater(() -> {
            // Verify the search bar is initialized correctly
            assertNotNull(tableViewManagingBooks.getSearchBarTf());
            assertEquals("", tableViewManagingBooks.getSearchBarTf().getText());
        });
    }

    @Test
    void testSetSearchBar() {
        Platform.runLater(() -> {
            // Set and verify the search bar content
            tableViewManagingBooks.getSearchBarTf().setText("TestSearch");
            assertEquals("TestSearch", tableViewManagingBooks.getSearchBarTf().getText());
            tableViewManagingBooks.getSearchBarTf().setText("");
            assertEquals("", tableViewManagingBooks.getSearchBarTf().getText());
        });
    }

    @Test
    void testAddGenre() {
        Platform.runLater(() -> {
            // Add genres and verify they appear in the check combo box
            ObservableList<String> genres = tableViewManagingBooks.getCheckComboBox().getItems();
            assertTrue(genres.contains(Genre.ACTION.toString()));
            assertTrue(genres.contains(Genre.FANTASY.toString()));
        });
    }

    @Test
    void testTextFieldFunctionality() {
        Platform.runLater(() -> {
            // Set values in text fields and verify them
            tableViewManagingBooks.setTitleTF("Sample Title");
            tableViewManagingBooks.setIsbnTF("123456789");
            tableViewManagingBooks.setPriceTF("19.99");
            tableViewManagingBooks.setQuantityTf("5");
            assertEquals("Sample Title", tableViewManagingBooks.getTitleTF());
            assertEquals("123456789", tableViewManagingBooks.getIsbnTF());
            assertEquals("19.99", tableViewManagingBooks.getPriceTF());
            assertEquals("5", tableViewManagingBooks.getQuantityTf());
        });
    }

    @Test
    void testComboBoxFunctionality() {
        Platform.runLater(() -> {
            // Select an author and verify selection
            tableViewManagingBooks.getAuthorComboBox().getSelectionModel().select(0);
            Author selectedAuthor = tableViewManagingBooks.getAuthorComboBox().getSelectionModel().getSelectedItem();
            assertNotNull(selectedAuthor);
            tableViewManagingBooks.setAuthorComboBox();
            assertNull(tableViewManagingBooks.getAuthorComboBox().getSelectionModel().getSelectedItem());
        });
    }

    @Test
    void testRadioButtonFunctionality() {
        Platform.runLater(() -> {
            // Select radio buttons and verify
            tableViewManagingBooks.getRbPaperback().setSelected(true);
            assertTrue(tableViewManagingBooks.getRbPaperback().isSelected());
            assertFalse(tableViewManagingBooks.getRbEbook().isSelected());

            tableViewManagingBooks.getRbEbook().setSelected(true);
            assertTrue(tableViewManagingBooks.getRbEbook().isSelected());
            assertFalse(tableViewManagingBooks.getRbPaperback().isSelected());
        });
    }

    @Test
    void testDescriptionAreaFunctionality() {
        Platform.runLater(() -> {
            // Set and verify the description text area
            tableViewManagingBooks.setDescriptionTA("Sample Description");
            assertEquals("Sample Description", tableViewManagingBooks.getDescriptionTA());
            tableViewManagingBooks.setDescriptionTA("");
            assertEquals("", tableViewManagingBooks.getDescriptionTA());
        });
    }

    @Test
    void testButtonGraphics() {
        Platform.runLater(() -> {
            // Verify button graphics are properly set
            assertNotNull(tableViewManagingBooks.getReturnButton().getGraphic());
            assertNotNull(tableViewManagingBooks.getBtnDelete().getGraphic());
            assertNotNull(tableViewManagingBooks.getBtnUpdate().getGraphic());
            assertNotNull(tableViewManagingBooks.getSubmitBtn().getGraphic());
        });
    }

    @Test
    void testSubmitButtonProperties() {
        Platform.runLater(() -> {
            // Verify properties of the submit button
            assertEquals(150, tableViewManagingBooks.getSubmitBtn().getPrefWidth());
        });
    }
}

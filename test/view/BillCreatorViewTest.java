package view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Author;
import model.Book;
import model.BooksOrdered;
import model.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import static org.junit.jupiter.api.Assertions.*;

class BillCreatorViewTest extends ApplicationTest {

    private BillCreatorView billCreatorView;

    @Override
    public void start(Stage stage) {
        Platform.runLater(() -> {
            billCreatorView = new BillCreatorView();
            Scene scene = new Scene(billCreatorView, 1000, 800);
            stage.setScene(scene);
            stage.show();
        });
    }

    @BeforeEach
    void setup() {
        Platform.runLater(() -> billCreatorView = new BillCreatorView());
    }

    @Test
    void testTableViewInitialization() {
        // Verify the stock table view
        assertNotNull(billCreatorView.getTableViewOgBooksInStock());
        assertEquals(8, billCreatorView.getTableViewOgBooksInStock().getColumns().size());

        // Verify the order table view
        assertNotNull(billCreatorView.getTableViewOfBooksToOrder());
        assertEquals(4, billCreatorView.getTableViewOfBooksToOrder().getColumns().size());
    }

    @Test
    void testButtonInitialization() {
        // Verify buttons
        assertNotNull(billCreatorView.getSubmitBtn());
        assertNotNull(billCreatorView.getBtnRemove());
        assertNotNull(billCreatorView.getReturnButton());
        assertNotNull(billCreatorView.getResetBtn());

        // Check button widths
        assertEquals(80, billCreatorView.getSubmitBtn().getPrefWidth());
        assertEquals(80, billCreatorView.getBtnRemove().getPrefWidth());
    }

    @Test
    void testLabelInitialization() {
        // Verify labels
        assertNotNull(billCreatorView.getTotalAmountFieldLb());
        assertEquals("", billCreatorView.getTotalAmountFieldLb().getText());
    }

    @Test
    void testSearchBarInitialization() {
        // Verify search bar
        assertNotNull(billCreatorView.getSearchBarTf());
        assertEquals("", billCreatorView.getSearchBarTf().getText());
    }

    @Test
    void testSetTotalAmountFieldLb() {
        // Act
        Platform.runLater(() -> billCreatorView.setTotalAmountFieldLb(99.99));

        // Assert
        Platform.runLater(() -> assertEquals("99.99", billCreatorView.getTotalAmountFieldLb().getText()));
    }

    @Test
    void testTableViewColumns() {
        // Verify columns in the stock table view
        assertEquals("Title", billCreatorView.getTitleColumn().getText());
        assertEquals("ISBN", billCreatorView.getIsbnColumn().getText());
        assertEquals("Description", billCreatorView.getDescriptionColumn().getText());
        assertEquals("Price", billCreatorView.getPriceColumn().getText());
        assertEquals("Genres", billCreatorView.getGenreColumn().getText());
        assertEquals("Author", billCreatorView.getAuthorColumn().getText());
        assertEquals("Quantity In Stock", billCreatorView.getQuantityColumn().getText());
        assertEquals("Is paperback?", billCreatorView.getPaperbackColumn().getText());

        // Verify columns in the order table view
        assertEquals("Title", billCreatorView.getTitleOfOrderedBooksColumn().getText());
        assertEquals("ISBN", billCreatorView.getIsbnOfOrderedBooksColumn().getText());
        assertEquals("Price", billCreatorView.getPriceOfOrderedBooksColumn().getText());
        assertEquals("Quantity Ordered", billCreatorView.getQuantityToOrderColumn().getText());
    }

    @Test
    void testButtonGraphics() {
        // Verify buttons have graphics (icons)
        assertNotNull(billCreatorView.getSubmitBtn().getGraphic());
        assertNotNull(billCreatorView.getBtnRemove().getGraphic());
        assertNotNull(billCreatorView.getResetBtn().getGraphic());
        assertNotNull(billCreatorView.getReturnButton().getGraphic());
    }

    @Test
    void testLayoutInitialization() {
        // Verify layout components
        assertNotNull(billCreatorView.getBottom());
        assertNotNull(billCreatorView.getCenter());
        assertNotNull(billCreatorView.getTop());
    }
}

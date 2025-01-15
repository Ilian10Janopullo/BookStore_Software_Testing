package view;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class ManageBillsViewTest extends ApplicationTest {

    private ManageBillsView manageBillsView;

    @Override
    public void start(Stage stage) {
        Platform.runLater(() -> {
            manageBillsView = new ManageBillsView();
            Scene scene = new Scene(manageBillsView, 800, 600);
            stage.setScene(scene);
            stage.show();
        });
    }

    @BeforeEach
    void setup() {
        Platform.runLater(() -> manageBillsView = new ManageBillsView());
    }

    @Test
    void testComponentInitialization() {
        assertNotNull(manageBillsView.getTableViewOfBills(), "TableView of Bills should not be null");
        assertNotNull(manageBillsView.getTableViewOfBooksOrdered(), "TableView of Books Ordered should not be null");
        assertNotNull(manageBillsView.getBtnUpdate(), "Update Button should not be null");
        assertNotNull(manageBillsView.getBtnRemove(), "Remove Button should not be null");
        assertNotNull(manageBillsView.getReturnButton(), "Return Button should not be null");
        assertNotNull(manageBillsView.getShowAllButton(), "Show All Button should not be null");
        assertNotNull(manageBillsView.getSearchButton2(), "Search Button should not be null");
        assertNotNull(manageBillsView.getSearchBarTf(), "Search Bar should not be null");
        assertNotNull(manageBillsView.getFromDateLb(), "From Date Label should not be null");
    }

    @Test
    void testTableViewColumns() {
        // Verify columns in the TableView of Bills
        assertEquals("Bill ID", manageBillsView.getIdBillColumn().getText(), "ID Bill Column header text should match");
        assertEquals("Seller ID", manageBillsView.getIdUserColumn().getText(), "User ID Column header text should match");
        assertEquals("Date", manageBillsView.getDateBillColumn().getText(), "Date Column header text should match");
        assertEquals("Total", manageBillsView.getTotalAmountColumn().getText(), "Total Amount Column header text should match");

        // Verify columns in the TableView of Books Ordered
        assertEquals("Title", manageBillsView.getTitleOfOrderedBooksColumn().getText(), "Title Column header text should match");
        assertEquals("ISBN", manageBillsView.getIsbnOfOrderedBooksColumn().getText(), "ISBN Column header text should match");
        assertEquals("Price", manageBillsView.getPriceOfOrderedBooksColumn().getText(), "Price Column header text should match");
        assertEquals("Quantity Ordered", manageBillsView.getQuantityToOrderColumn().getText(), "Quantity Ordered Column header text should match");
    }

    @Test
    void testButtonProperties() {
        assertEquals(100, manageBillsView.getBtnUpdate().getPrefWidth(), "Update Button width should be 100");
        assertEquals(100, manageBillsView.getBtnRemove().getPrefWidth(), "Remove Button width should be 100");
        assertEquals(100, manageBillsView.getReturnButton().getPrefWidth(), "Return Button width should be 100");
        assertEquals(100, manageBillsView.getShowAllButton().getPrefWidth(), "Show All Button width should be 100");
        assertEquals(100, manageBillsView.getSearchButton2().getPrefWidth(), "Search Button width should be 100");
    }


    @Test
    void testClearFunctions() {
        Platform.runLater(() -> {
            // Act
            manageBillsView.setToDate();
            manageBillsView.setFromDate();
            manageBillsView.setSearchBarTf();

            // Assert
            assertNull(manageBillsView.getFromDateLb().getText(), "From Date Label should be null after clearing");
            assertEquals("", manageBillsView.getSearchBarTf().getText(), "Search Bar should be cleared");
        });
    }

    @Test
    void testStyles() {
        assertEquals("-fx-font-family: 'Times New Roman' ; -fx-font-size: 14;", manageBillsView.getStyle(), "Style should match expected CSS");
    }
}

//Date picker cannot be tested because appropriated setters are missing!
package systemTesting;

import controller.ManageBillsController;
import dao.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.*;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ManageBillsSystemTest extends ApplicationTest {
    private ManageBillsController manageBillsController;
    @Mock
    private BillsDAO mockBillsDAO;
    @Mock
    private BooksDAO mockBooksDAO;
    @Mock
    private AuthorsDAO mockAuthorsDAO;
    @Mock
    private UsersDAO mockUsersDAO;
    @Mock
    private PermissionsDAO mockPermissionsDAO;
    @Mock
    private BillPrintingDAO mockBillPrintingDAO;
    @Mock
    private Stage mockStage;
    @Mock
    private UsersOfTheSystem mockUser;

    @Override
    public void start(Stage stage) {
        MockitoAnnotations.openMocks(this);
        mockUser = mock(UsersOfTheSystem.class);
        when(mockUser.getUserID()).thenReturn("testUser");
        when(mockUser.getRole()).thenReturn(Role.ADMIN);
        manageBillsController = new ManageBillsController(stage, mockUser);
        stage.setScene(new javafx.scene.Scene(manageBillsController.getView(), 1525, 600));
        stage.show();
    }
    @BeforeEach
    public void setup() {
        reset(mockBillsDAO, mockBooksDAO, mockAuthorsDAO, mockUsersDAO, mockPermissionsDAO, mockBillPrintingDAO);
    }

    @Test
    @Order(1)
    public void testViewInitialization() {
        Platform.runLater(() -> {
            Assertions.assertNotNull(manageBillsController.getView(), "ManageBillsView should be initialized");
            Assertions.assertFalse(manageBillsController.getView().getTableViewOfBills().getItems().isEmpty(),
                    "Bills TableView should be populated");
        });
    }

    @Test
    @Order(2)
    public void testRevenueCalculation() {
        Platform.runLater(() -> {
            double totalRevenue = 0;
            for (Bill bill : manageBillsController.getView().getTableViewOfBills().getItems()) {
                totalRevenue += bill.getTotalAmount();
            }
            String actualRevenue = manageBillsController.getView().getRevenueTF().getText(); // Adjust based on your implementation
            Assertions.assertEquals(String.valueOf(totalRevenue), actualRevenue,
                    "Total revenue should match the calculated value");
        });
    }

    @Test
    @Order(3)
    public void testAddOrder() {
        Platform.runLater(() -> {
            Book mockBook = new Book("123", "Test Book", "Description", 10.0, new Author("Test", "Author", Gender.MALE), true, 5);
            BooksOrdered order = new BooksOrdered(mockBook.getIsbn(), mockBook.getTitle(), mockBook.getPrice());
            order.setQuantityToOrder(2);

            manageBillsController.getView().getTableViewOfBooksOrdered().getItems().add(order);

            Assertions.assertFalse(manageBillsController.getView().getTableViewOfBooksOrdered().getItems().isEmpty(),
                    "Order should be added to the TableView");
            Assertions.assertEquals(2, manageBillsController.getView().getTableViewOfBooksOrdered().getItems().get(0).getQuantityToOrderOfBookOrdered(),
                    "Order quantity should match the added value");
        });
    }

    @Test
    @Order(4)
    public void testRemoveOrder() {
        Platform.runLater(() -> {
            BooksOrdered order1 = new BooksOrdered("12345", "Mock Book 1", 25.0);
            order1.setQuantityToOrder(2);
            BooksOrdered order2 = new BooksOrdered("67890", "Mock Book 2", 30.0);
            order2.setQuantityToOrder(1);
            // Add mock orders to the orders list
            ObservableList<BooksOrdered> mockOrders = FXCollections.observableArrayList(order1, order2);
            manageBillsController.getView().getTableViewOfBooksOrdered().setItems(mockOrders);
            System.out.println("Initial orders: " + mockOrders);
            // Verify initial state
            Assertions.assertEquals(2, mockOrders.size(), "Orders list should contain 2 items initially");
            manageBillsController.getView().getTableViewOfBooksOrdered().getSelectionModel().select(order1);
            // Log selected item
            System.out.println("Selected order before removal: " + manageBillsController.getView().getTableViewOfBooksOrdered().getSelectionModel().getSelectedItem());
            manageBillsController.getView().getBtnRemove().fire();
            // Wait for the UI to update
            WaitForAsyncUtils.waitForFxEvents();
            Assertions.assertFalse(mockOrders.contains(order1), "Removed order should no longer be in the list");
        });
    }

    @Test
    @Order(5)
    public void testSearchBills() {
        Platform.runLater(() -> {
            manageBillsController.getView().getSearchBarTf().setText("12345"); // Assume 12345 is a bill ID
            Assertions.assertTrue(manageBillsController.getView().getTableViewOfBills().getItems().stream()
                            .allMatch(bill -> bill.getBillID().contains("12345")),
                    "Filtered bills should only include those matching the search criteria");
        });
    }

    @Test
    @Order(6)
    public void testNavigationBasedOnRole() {
        Platform.runLater(() -> {
            manageBillsController.Back(null);
            verify(mockUser, atLeastOnce()).getRole();
        });
    }
}

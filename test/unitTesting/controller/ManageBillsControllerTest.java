package unitTesting.controller;

import controller.ManageBillsController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ManageBillsControllerTest {

    private static Stage mockStage;
    private static UsersOfTheSystem mockUser;
    private static ObservableList<Bill> mockBills;
    private static ObservableList<Book> mockBooks;
    private static ObservableList<Author> mockAuthors;
    private static ObservableList<UsersOfTheSystem> mockUsers;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        // Initialize JavaFX Toolkit
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();

        // Mock dependencies
        mockStage = mock(Stage.class);
        mockUser = mock(UsersOfTheSystem.class);

        // Mock data
        Author mockAuthor = new Author("John", "Doe", Gender.MALE);
        mockAuthors = FXCollections.observableArrayList(mockAuthor);

        Book mockBook = new Book("123456789", "Test Book", "Test Description", 10.99, mockAuthor, true, 10);
        mockBooks = FXCollections.observableArrayList(mockBook);

        ArrayList<BooksOrdered> mockOrders = new ArrayList<>();
        mockOrders.add(new BooksOrdered(mockBook.getIsbn(), mockBook.getTitle(), mockBook.getPrice()));

        Bill mockBill = new Bill(mockOrders, "user1");
        mockBills = FXCollections.observableArrayList(mockBill);

        mockUsers = FXCollections.observableArrayList(mockUser);
    }

    @Test
    void testControllerInitialization() {
        Platform.runLater(() -> {
            ManageBillsController controller = new ManageBillsController(mockStage, mockUser);

            // Verify view and data are initialized
            assertNotNull(controller.getView());
            assertNotNull(controller.getView().getTableViewOfBills());
            assertNotNull(controller.getView().getTableViewOfBooksOrdered());
        });
    }

    @Test
    void testGetItem() {
        Platform.runLater(() -> {
            ManageBillsController controller = new ManageBillsController(mockStage, mockUser);

            controller.getView().getTableViewOfBills().setItems(mockBills);
            controller.getView().getTableViewOfBills().getSelectionModel().selectFirst();

            // Trigger the method to populate orders
            controller.getItem(null);

            // Verify orders are populated
            assertEquals(1, controller.getView().getTableViewOfBooksOrdered().getItems().size());
            assertEquals(mockBills.get(0).getBooks().get(0).getTitle(), controller.getView().getTableViewOfBooksOrdered().getItems().get(0).getTitle());
        });
    }

    @Test
    void testOnOrderRemove() {
        Platform.runLater(() -> {
            ManageBillsController controller = new ManageBillsController(mockStage, mockUser);

            controller.getView().getTableViewOfBills().setItems(mockBills);
            controller.getView().getTableViewOfBills().getSelectionModel().selectFirst();
            controller.getItem(null); // Populate orders

            // Mock a book selection
            controller.getView().getTableViewOfBooksOrdered().getSelectionModel().selectFirst();

            // Trigger remove
            controller.onOrderRemove(null);

            // Verify order is removed
            assertTrue(controller.getView().getTableViewOfBooksOrdered().getItems().isEmpty());
        });
    }

    @Test
    void testBack() {
        Platform.runLater(() -> {
            ManageBillsController controller = new ManageBillsController(mockStage, mockUser);

            when(mockUser.getRole()).thenReturn(Role.ADMIN);

            // Trigger the back action
            controller.Back(null);

            // Verify correct navigation (mockStage.setScene() called)
            verify(mockStage, times(1)).setScene(any());
        });
    }

    @Test
    void testSearchFilter() {
        Platform.runLater(() -> {
            ManageBillsController controller = new ManageBillsController(mockStage, mockUser);

            controller.getView().getTableViewOfBills().setItems(mockBills);

            // Apply search filter
            controller.getView().getSearchBarTf().setText("123");

            // Verify filtered list
            assertEquals(1, controller.getView().getTableViewOfBills().getItems().size());
            assertEquals("123456789", controller.getView().getTableViewOfBills().getItems().get(0).getBooks().get(0).getIsbn());
        });
    }

    //We cannot check updates on bills on this class because the Revenue display is a label, and does not have a getter, just a setter.
}

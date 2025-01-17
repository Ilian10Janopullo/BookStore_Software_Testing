package Integration;

import controller.BillCreatorController;
import dao.BillsDAO;
import dao.BooksDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.JavaFXInitializer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

class BillCreatorControllerTest {
    private BillCreatorController controller;
    private BillsDAO billsDAO;
    private BooksDAO booksDAO;
    private ObservableList<Book> books;

    @BeforeEach
    void setup() throws Exception {
        // Initialize JavaFX runtime
        JavaFXInitializer initializer = new JavaFXInitializer();
        initializer.init();

        // Mock DAO objects
        billsDAO = mock(BillsDAO.class);
        booksDAO = mock(BooksDAO.class);

        // Create an observable list of books
        books = FXCollections.observableArrayList(
                new Book("123", "Test Book", "Description", 10.0, new Author("Test", "Test", Gender.MALE), true, 5)
        );

        when(booksDAO.getAll()).thenReturn(books);

        // Mock Stage and user
        Stage stage = mock(Stage.class);
        UsersOfTheSystem user = mock(UsersOfTheSystem.class);

        // Initialize the controller on JavaFX Application Thread
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller = new BillCreatorController(stage, user);
            latch.countDown();
        });
        latch.await(); // Wait for initialization to complete
    }

    @Test
    void testAddBookToOrder() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Select the first book in the stock table and add it to the order
            controller.getView().getTableViewOgBooksInStock().getSelectionModel().select(0);
            controller.getItem(null);

            // Verify the book is added to the order table
            assertEquals(1, controller.getView().getTableViewOfBooksToOrder().getItems().size());
            latch.countDown();
        });
        latch.await(); // Wait for JavaFX operations to complete
    }

    @Test
    void testRemoveBookFromOrder() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Add a book to the order and then select it for removal
            BooksOrdered order = new BooksOrdered("123", "Test Book", 10.0);
            controller.getView().getTableViewOfBooksToOrder().getItems().add(order);
            controller.getView().getTableViewOfBooksToOrder().getSelectionModel().select(0);

            // Remove the book from the order
            controller.onBookRemove(null);

            // Verify the book is removed from the order table
            assertEquals(0, controller.getView().getTableViewOfBooksToOrder().getItems().size());
            latch.countDown();
        });
        latch.await(); // Wait for JavaFX operations to complete
    }

    @Test
    void testSubmitBillUpdatesQuantities() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Add a book to the order
            BooksOrdered order = new BooksOrdered("123", "Test Book", 10.0);
            order.setQuantityToOrder(2);
            controller.getView().getTableViewOfBooksToOrder().getItems().add(order);

            // Mock the billsDAO response
            when(billsDAO.addToFile(any())).thenReturn(true);

            // Submit the bill
            controller.Submit(null);

            // Verify the book quantity is updated correctly
            assertEquals(3, books.get(0).getQuantity()); // Initial 5 - Ordered 2 = 3
            latch.countDown();
        });
        if (!latch.await(5, TimeUnit.SECONDS)) {
            fail("Test timed out");
        } // Wait for JavaFX operations to complete
    }
}

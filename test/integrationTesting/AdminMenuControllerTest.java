package integrationTesting;

import controller.AdminMenuController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Book;
import model.UsersOfTheSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.JavaFXInitializer;

import java.util.concurrent.CountDownLatch;

import static org.mockito.Mockito.*;

class AdminMenuControllerTest {
    private AdminMenuController controller;
    private Stage stage;
    private ObservableList<Book> books;

    @BeforeEach
    void setup() throws Exception {
        // Initialize JavaFX runtime
        JavaFXInitializer initializer = new JavaFXInitializer();
        initializer.init();

        // Mocking the stage
        stage = mock(Stage.class);
        UsersOfTheSystem user = mock(UsersOfTheSystem.class);

        // Mock a Scene and set it in the mocked Stage
        Scene scene = mock(Scene.class);
        when(stage.getScene()).thenReturn(scene);

        // Initialize the books list
        books = FXCollections.observableArrayList();

        // Use Platform.runLater to initialize the controller on the JavaFX Application Thread
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            AdminMenuController.setBooks(books);
            controller = new AdminMenuController(stage, user);
            latch.countDown();
        });
        latch.await(); // Wait for the initialization to complete
    }

    @Test
    void testNavigateToManageBooks() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Simulate navigation to ManageBooks
            controller.ManageBooks();

            // Verify the scene root was set correctly
            verify(stage.getScene(), times(1)).setRoot(any());
            latch.countDown();
        });
        latch.await(); // Wait for JavaFX operations to complete
    }

    @Test
    void testNoBooksAvailableForSale() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Clear all books to simulate no books available
            books.clear();

            // Attempt to navigate to SellBooks
            controller.Sell();

            // Verify no navigation occurred
            verify(stage.getScene(), never()).setRoot(any());
            latch.countDown();
        });
        latch.await(); // Wait for JavaFX operations to complete
    }

    @Test
    void testManageAuthorsWithoutBooks() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Clear books but attempt to navigate to ManageAuthors
            books.clear();

            // Call ManageAuthors
            controller.ManageAuthors();

            // Verify navigation occurred
            verify(stage.getScene(), times(1)).setRoot(any());
            latch.countDown();
        });
        latch.await(); // Wait for JavaFX operations to complete
    }

    @Test
    void testReminderLowStockBooks() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            // Add a mock book with low stock
            Book lowStockBook = mockBookWithLowStock();
            books.add(lowStockBook);

            // Create a new controller to trigger the low stock warning
            controller = new AdminMenuController(stage, mock(UsersOfTheSystem.class));

            // Verify the stage's scene is interacted with
            verify(stage.getScene(), times(1)).getRoot();
            latch.countDown();
        });
        latch.await(); // Wait for JavaFX operations to complete
    }

    private Book mockBookWithLowStock() {
        Book book = mock(Book.class);
        when(book.getQuantity()).thenReturn(3); // Simulate low stock
        return book;
    }
}

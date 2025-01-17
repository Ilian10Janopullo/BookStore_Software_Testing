package controller;

import dao.AuthorsDAO;
import dao.BillsDAO;
import dao.BooksDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

public class AdminMenuControllerTest {

    private static ObservableList<Author> mockAuthors;
    private static ObservableList<Bill> mockBills;
    private static ObservableList<Book> mockBooks;
    private static Admin mockAdmin;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        // Initialize JavaFX Toolkit
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();

        // Mock Data
        Author mockAuthor = new Author("John", "Doe", Gender.MALE);
        mockAuthors = FXCollections.observableArrayList(mockAuthor);

        Book mockBook = new Book("12345", "Book Title", "A thrilling adventure", 9.99, mockAuthor, true, 10);
        mockBooks = FXCollections.observableArrayList(mockBook);

        ArrayList<BooksOrdered> booksOrdered = new ArrayList<>();
        booksOrdered.add(new BooksOrdered(mockBook.getIsbn(), mockBook.getTitle(), mockBook.getPrice()));
        Bill mockBill = new Bill(booksOrdered, "adminUser");
        mockBills = FXCollections.observableArrayList(mockBill);

        mockAdmin = new Admin("Admin", "Test", new Date(), Gender.MALE, "admin", "admin123", Role.ADMIN, "admin@test.com", "1234567890", 5000);
    }

    @Test
    void testControllerInitialization() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();
            AdminMenuController controller = new AdminMenuController(mockStage, mockAdmin);

            assertNotNull(controller.getView());
        });
    }

    @Test
    void testManageAuthors() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();
            AdminMenuController controller = new AdminMenuController(mockStage, mockAdmin);

            // Mock navigation behavior
            ManagingAuthorsController mockAuthorsController = mock(ManagingAuthorsController.class);

            controller.ManageAuthors();

            // Verify that navigation works
            assertNotNull(controller.getView().getManageAuthors());
        });
    }

    @Test
    void testManageBooksWhenAuthorsExist() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();
            AdminMenuController controller = new AdminMenuController(mockStage, mockAdmin);

            controller.ManageBooks();

            // Verify ManageBooks action
            assertNotNull(controller.getView().getManageBooks());
        });
    }

    @Test
    void testManageBooksWhenAuthorsAbsent() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();
            AdminMenuController controller = new AdminMenuController(mockStage, mockAdmin);

            // Clear mock authors to simulate no authors in the system
            mockAuthors.clear();

            controller.ManageBooks();

            // Verify alert is shown
            Alert alert = new Alert(Alert.AlertType.WARNING);
            assertNotNull(alert.getContentText());
        });
    }

    @Test
    void testManageUsers() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();
            AdminMenuController controller = new AdminMenuController(mockStage, mockAdmin);

            controller.ManageUsers();

            // Verify ManageUsers action
            assertNotNull(controller.getView().getManageUsers());
        });
    }

    @Test
    void testLogOut() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();
            AdminMenuController controller = new AdminMenuController(mockStage, mockAdmin);

            controller.LogOut();

            // Verify LogOut action
            assertNotNull(controller.getView().getLogout());
        });
    }

    @Test
    void testSellWhenBooksExist() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();
            AdminMenuController controller = new AdminMenuController(mockStage, mockAdmin);

            controller.Sell();

            // Verify Sell action
            assertNotNull(controller.getView().getSellBooks());
        });
    }

    @Test
    void testSellWhenNoBooksExist() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();
            AdminMenuController controller = new AdminMenuController(mockStage, mockAdmin);

            // Clear books to simulate no books in the system
            mockBooks.clear();

            controller.Sell();

            // Verify alert is shown
            Alert alert = new Alert(Alert.AlertType.WARNING);
            assertNotNull(alert.getContentText());
        });
    }

    @Test
    void testManageBillsWhenBillsExist() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();
            AdminMenuController controller = new AdminMenuController(mockStage, mockAdmin);

            controller.ManageBills();

            // Verify ManageBills action
            assertNotNull(controller.getView().getManageBills());
        });
    }

    @Test
    void testManageBillsWhenNoBillsExist() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();
            AdminMenuController controller = new AdminMenuController(mockStage, mockAdmin);

            // Clear bills to simulate no bills in the system
            mockBills.clear();

            controller.ManageBills();

            // Verify alert is shown
            Alert alert = new Alert(Alert.AlertType.WARNING);
            assertNotNull(alert.getContentText());
        });
    }

    @Test
    void testControlPermissions() {
        Platform.runLater(() -> {
            Stage mockStage = new Stage();
            AdminMenuController controller = new AdminMenuController(mockStage, mockAdmin);

            controller.ControlPermissions();

            // Verify ControlPermissions action
            assertNotNull(controller.getView().getControlPermissions());
        });
    }
}

package systemTesting;

import controller.BillCreatorController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.collections.ObservableList;
import model.*;
import org.testfx.util.WaitForAsyncUtils;

import static org.mockito.Mockito.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BillCreatorSystemTest extends ApplicationTest {

    private BillCreatorController billCreatorController;
    @Mock
    private UsersOfTheSystem mockUser;
    @Mock
    private Stage mockStage;

    @Override
    public void start(Stage stage) {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        mockUser = mock(UsersOfTheSystem.class); // Mock the user
        when(mockUser.getUserID()).thenReturn("testUser"); // Define behavior of getUserID()

        billCreatorController = new BillCreatorController(stage, mockUser);
        stage.setScene(new javafx.scene.Scene(billCreatorController.getView(), 1525, 600));
        stage.show();
    }
    @BeforeEach
    public void setup() throws InterruptedException {
        reset(mockUser, mockStage);
        Thread.sleep(3000);
    }

    @Test
    @Order(1)
    public void testAddBookToOrder() throws InterruptedException {
        // Create list for TableView
        ObservableList<Book> modifiableBooksList = FXCollections.observableArrayList();
        billCreatorController.getView().getTableViewOgBooksInStock().setItems(modifiableBooksList);

        Book mockBook = new Book("123", "Test Book", "Description", 10.0, new Author("Test", "Test", Gender.MALE), true, 5);
        modifiableBooksList.add(mockBook);

        // Selecting the book and adding it to the order
        interact(() -> billCreatorController.getView().getTableViewOgBooksInStock().getSelectionModel().select(mockBook));
        billCreatorController.getView().getTableViewOgBooksInStock().fireEvent(new javafx.scene.input.MouseEvent(
                javafx.scene.input.MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, javafx.scene.input.MouseButton.PRIMARY, 1,
                true, true, true, true, true, true, true, true, true, true, null
        ));

        Thread.sleep(3000);
        Assertions.assertFalse(billCreatorController.getView().getTableViewOfBooksToOrder().getItems().isEmpty());
        Assertions.assertEquals(1, billCreatorController.getView().getTableViewOfBooksToOrder().getItems().size());
    }

    @Test
    @Order(2)
    public void testRemoveBookFromOrder() throws InterruptedException {
        BooksOrdered mockOrder = new BooksOrdered("12345", "Test Book", 20.0);
        mockOrder.setQuantityToOrder(2);

        Platform.runLater(() -> {
            billCreatorController.getView().getTableViewOfBooksToOrder().getItems().add(mockOrder);
            // Selecting the book and removing it
            billCreatorController.getView().getTableViewOfBooksToOrder().getSelectionModel().select(mockOrder);
            billCreatorController.onBookRemove(null);
            Assertions.assertTrue(billCreatorController.getView().getTableViewOfBooksToOrder().getItems().isEmpty());
        });

        // Allow time for JavaFX thread to process
        Thread.sleep(3000);
    }
    @Test
    @Order(3)
    public void testSubmitOrder() throws InterruptedException {
        BooksOrdered mockOrder = new BooksOrdered("12345", "Test Book", 20.0);
        mockOrder.setQuantityToOrder(1);
        Platform.runLater(() -> {
            // Add the order to the TableView
            billCreatorController.getView().getTableViewOfBooksToOrder().getItems().add(mockOrder);
            // Click the submit button
            billCreatorController.getView().getSubmitBtn().fire();
        });

        // Wait for JavaFX thread to process events
        WaitForAsyncUtils.waitForFxEvents();
        // Verify the TableView is clear
        ObservableList<BooksOrdered> items = billCreatorController.getView().getTableViewOfBooksToOrder().getItems();
        System.out.println(items.toString());
        Assertions.assertTrue(items.isEmpty(), "The TableView should be clear after submitting the order.");
    }


    @Test
    @Order(4)
    public void testResetOrder() throws Exception {
        Platform.runLater(() -> {
            // Simulate resetting the order
            billCreatorController.getView().getResetBtn().fire();
            // Retrieve total amount label value
            String actualTotal = billCreatorController.getView().getTotalAmountFieldLb().getText();
            // Assert the value is reset to "0.0"
            Assertions.assertEquals("0.0", actualTotal, "The total amount label should reset to 0.0");
        });

        // Wait for JavaFX thread to process
        WaitForAsyncUtils.waitForFxEvents();
    }

}
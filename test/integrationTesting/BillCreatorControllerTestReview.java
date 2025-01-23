package integrationTesting;

import controller.BillCreatorController;
import dao.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.JavaFXInitializer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.CountDownLatch;
import static org.mockito.Mockito.*;


public class BillCreatorControllerTestReview {

    private static ArrayList<ObservableList<BooksOrdered>> ordersOfBooks;
    private static Stage stage;
    private static UsersOfTheSystem user;
    private static Author author;
    private static Book book1;
    private static Book book2;

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {

        CountDownLatch latch = new CountDownLatch(1);

        Platform.startup(() -> {
            new JavaFXInitializer().init();

            stage = new Stage();
            Calendar calendar= new GregorianCalendar();
            user =new Admin("Ilian", "Janopullo", calendar.getTime(), Gender.MALE, "ijanopullo22", "1234", Role.ADMIN, "ijanopullo22@gmail.com", "0695383073", 700);


            author = new Author("TestFirstName", "TestLastName", Gender.MALE);

            book1 = new Book("9928-186-59-1", "Book 1", "A description for Book 1", 20.00, author, true, 2);
            book2 = new Book("9789992755067", "Book 2", "A description for Book 2", 15.00, author, true, 10);

            latch.countDown();
        });
        latch.await();
    }

    @Test
    public void submitInvalidBill(){

        Platform.runLater(() -> {

            BooksOrdered booksOrdered = null;

            booksOrdered = new BooksOrdered(book1.getIsbn(), book1.getTitle(), book1.getPrice());
            booksOrdered.setQuantityToOrder(3);

            ObservableList<BooksOrdered> invalidOrderOfBooks = FXCollections.observableArrayList();
            invalidOrderOfBooks.add(booksOrdered);

            BillCreatorController controller = new BillCreatorController(stage, user);
            controller.setOrders(invalidOrderOfBooks);

            Alert mockAlert = mock(Alert.class);
            controller.setAlertFactory(type -> mockAlert);

            controller.Submit(new ActionEvent());

            verify(mockAlert, times(1)).show();
        });
    }

    @Test
    public void submitDuplicateBill(){

        Platform.runLater(() -> {

            BooksOrdered booksOrdered = null;

            booksOrdered = new BooksOrdered(book1.getIsbn(), book1.getTitle(), book1.getPrice());
            booksOrdered.setQuantityToOrder(1);

            ObservableList<BooksOrdered> duplicateOrderOfBooks = FXCollections.observableArrayList();
            duplicateOrderOfBooks.add(booksOrdered);
            duplicateOrderOfBooks.add(booksOrdered);

            BillCreatorController controller = new BillCreatorController(stage, user);
            controller.setOrders(duplicateOrderOfBooks);

            Alert mockAlert = mock(Alert.class);
            controller.setAlertFactory(type -> mockAlert);

            controller.Submit(new ActionEvent());

            verify(mockAlert, times(1)).show();
        });
    }

    @Test
    public void submitValidBill(){

        Platform.runLater(() -> {

            Path tempFilePath = null;

            try {

                tempFilePath = Files.createTempFile("test_authors", ".dat");
                AuthorsDAO.FILE_PATH = tempFilePath.toString();

                tempFilePath = Files.createTempFile("test_books", ".dat");
                BooksDAO.FILE_PATH = tempFilePath.toString();

                tempFilePath = Files.createTempFile("test_bills", ".dat");
                BillsDAO.FILE_PATH = tempFilePath.toString();

                tempFilePath = Files.createTempFile("test_users", ".dat");
                UsersDAO.FILE_PATH = tempFilePath.toString();

                tempFilePath = Files.createTempFile("test_bills_printing", ".txt");
                BillPrintingDAO.FILE_PATH = tempFilePath.toString();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            BooksDAO booksDAO = new BooksDAO();
            AuthorsDAO authorsDAO = new AuthorsDAO();
            UsersDAO usersDAO = new UsersDAO();
            BillsDAO billsDAO = new BillsDAO();
            BillPrintingDAO billPrintingDAO = new BillPrintingDAO();

            booksDAO.addToFile(book1);
            booksDAO.addToFile(book2);
            authorsDAO.addToFile(author);
            usersDAO.addToFile(user);

            BooksOrdered booksOrdered = null;

            ObservableList<BooksOrdered> validOrderOfBooks = FXCollections.observableArrayList();

            booksOrdered = new BooksOrdered(book1.getIsbn(), book1.getTitle(), book1.getPrice());
            booksOrdered.setQuantityToOrder(1);
            validOrderOfBooks.add(booksOrdered);
            booksOrdered = new BooksOrdered(book2.getIsbn(), book2.getTitle(), book2.getPrice());
            booksOrdered.setQuantityToOrder(1);
            validOrderOfBooks.add(booksOrdered);

            BillCreatorController controller = new BillCreatorController(stage, user);
            controller.setOrders(validOrderOfBooks);


            Assertions.assertAll(
                    () -> Assertions.assertEquals(1, billsDAO.getAll().size()),
                    () -> Assertions.assertEquals(2, authorsDAO.getAll().getFirst().getNrOfBooksSold()),
                    () -> Assertions.assertEquals(1, booksDAO.getAll().getFirst().getQuantity()),
                    () -> Assertions.assertEquals(1, booksDAO.getAll().getFirst().getCopiesSold()),
                    () -> Assertions.assertEquals(9, booksDAO.getAll().getLast().getQuantity()),
                    () -> Assertions.assertEquals(1, booksDAO.getAll().getLast().getCopiesSold()),
                    () -> Assertions.assertEquals(2, usersDAO.getAll().getFirst().getNrOfBooksSold()),
                    () -> Assertions.assertEquals(35.0, usersDAO.getAll().getFirst().getRevenueMade())
            );

            try {
                Files.deleteIfExists(Path.of(AuthorsDAO.FILE_PATH));
                Files.deleteIfExists(Path.of(BillsDAO.FILE_PATH));
                Files.createFile(Path.of(BooksDAO.FILE_PATH));
                Files.createFile(Path.of(AuthorsDAO.FILE_PATH));
                Files.createFile(Path.of(BillsDAO.FILE_PATH));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }

}

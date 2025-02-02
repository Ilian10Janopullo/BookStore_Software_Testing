package integrationTesting;

import controller.ManagingBooksController;
import dao.AuthorsDAO;
import dao.BooksDAO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import util.JavaFXInitializer;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.concurrent.CountDownLatch;
import static org.mockito.Mockito.*;

public class ManagingBooksControllerTest {

    private static Stage stage;
    private static UsersOfTheSystem user;
    private static Author author;
    private static Book testBook;
    private static BooksDAO booksDAO;
    private static AuthorsDAO authorsDAO;

    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        Platform.startup(() -> {
            new JavaFXInitializer().init();

            stage = new Stage();
            Calendar calendar = new GregorianCalendar();
            user = new Admin("Integration", "Test", calendar.getTime(), Gender.MALE,
                    "integrationTest25", "@Integration2025", Role.ADMIN, "itest25@gmail.com", "0695383074", 1000);

            author = new Author("Integration", "Test", Gender.MALE);
            testBook = new Book("978-3-16-148410-0", "Test Book", "A test description", 25.00, author, true, 5);

            booksDAO = new BooksDAO();
            authorsDAO = new AuthorsDAO();
            authorsDAO.addToFile(author);

            latch.countDown();
        });
        latch.await();
    }

    @Test
    public void addValidBook() {
        Platform.runLater(() -> {
            int initialSize = booksDAO.getAll().size();

            ManagingBooksController controller = new ManagingBooksController(stage, user);
            controller.getView().setIsbnTF(testBook.getIsbn());
            controller.getView().setTitleTF(testBook.getTitle());
            controller.getView().setPriceTF(String.valueOf(testBook.getPrice()));
            controller.getView().setDescriptionTA(testBook.getDescription());
            controller.getView().setRbPaperback();
            controller.getView().setAuthorComboBox();
            controller.getView().setQuantityTf(String.valueOf(testBook.getQuantity()));
            controller.Submit(new ActionEvent());

            Assertions.assertEquals(initialSize + 1, booksDAO.getAll().size(), "Book size increment");
            Assertions.assertTrue(booksDAO.getAll().contains(testBook), "Added Book in found in database");
        });
    }

    @Test
    public void addDuplicateBook() {
        Platform.runLater(() -> {
            booksDAO.addToFile(testBook);
            int initialSize = booksDAO.getAll().size();

            ManagingBooksController controller = spy(new ManagingBooksController(stage, user));
            controller.getView().setIsbnTF(testBook.getIsbn());
            controller.getView().setTitleTF(testBook.getTitle());
            controller.getView().setPriceTF(String.valueOf(testBook.getPrice()));
            controller.getView().setDescriptionTA(testBook.getDescription());
            controller.getView().setRbPaperback();
            controller.getView().setAuthorComboBox();
            controller.getView().setQuantityTf(String.valueOf(testBook.getQuantity()));
            Alert alert = new Alert(Alert.AlertType.ERROR);
            controller.setAlertFactory(type -> alert);
            controller.Submit(new ActionEvent());

            verify(alert, times(1)).show();
            Assertions.assertEquals(initialSize, booksDAO.getAll().size());
        });
    }
}

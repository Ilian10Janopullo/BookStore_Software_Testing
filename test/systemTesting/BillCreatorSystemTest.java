package systemTesting;

import controller.BillCreatorController;
import dao.BooksDAO;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import model.*;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.service.query.PointQuery;
import view.BillCreatorView;

import java.util.Calendar;
import java.util.GregorianCalendar;



@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BillCreatorSystemTest extends ApplicationTest {

    private BillCreatorController billCreatorController;
    private BillCreatorView billCreatorView;
    private UsersOfTheSystem user;

    @Override
    public void start(Stage stage) {
        Calendar calendar1 = new GregorianCalendar();
        user = new Librarian("Ilian", "Janopullo", calendar1.getTime(), Gender.MALE, "ijanopullo22", "1234", Role.ADMIN, "ijanopullo22@gmail.com", "0695383073", 700);
        billCreatorController = new BillCreatorController(stage, user);
        billCreatorView = billCreatorController.getView();
        stage.setScene(new javafx.scene.Scene(billCreatorView, 1525, 600));
        stage.show();
    }

    @BeforeEach
    public void setup() throws InterruptedException {
        billCreatorController.getView().getTableViewOgBooksInStock().setItems(new BooksDAO().getAll());
        billCreatorController.getView().getTableViewOfBooksToOrder().setItems(FXCollections.observableArrayList());
        Thread.sleep(3000);
    }

    @Test
    @Order(1)
    public void testAddBookToOrder() throws InterruptedException {
        clickOn(".table-row-cell");
        clickOn(".quantity-cell");
        write("2");
        clickOn("#submitButtonId");

        Thread.sleep(3000);
    }


    @Test
    @Order(2)
    public void testRemoveBookFromOrder() throws InterruptedException {

        Book book = billCreatorController.getView().getTableViewOgBooksInStock().getItems().getFirst();
        billCreatorController.getItem(null);
        billCreatorController.onBookRemove(null);
        Thread.sleep(3000);
    }



    @Test
    @Order(3)
    public void testResetAction() throws InterruptedException {

        Book book = billCreatorController.getView().getTableViewOgBooksInStock().getItems().getFirst();
        billCreatorController.getItem(null);

        billCreatorController.getView().getResetBtn().fire();

        Thread.sleep(3000);
    }
}

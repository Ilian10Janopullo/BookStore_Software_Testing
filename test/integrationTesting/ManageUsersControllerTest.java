package integrationTesting;

import controller.ManageUsersController;
import dao.UsersDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.stage.Stage;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.JavaFXInitializer;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ManageUsersControllerTest {
    private ManageUsersController controller;
    private UsersDAO usersDAO;
    private ObservableList<UsersOfTheSystem> users;

    @BeforeEach
    void setup() throws Exception {
        // Initialize JavaFX runtime
        JavaFXInitializer initializer = new JavaFXInitializer();
        initializer.init();
        // Mock DAO and user list
        usersDAO = mock(UsersDAO.class);
        users = FXCollections.observableArrayList();

        when(usersDAO.getAll()).thenReturn(users);

        // Mock Stage and initialize controller
        Stage stage = mock(Stage.class);
        UsersOfTheSystem user = mock(UsersOfTheSystem.class);

        // Initialize the controller on the JavaFX thread
        runOnJavaFXThread(() -> controller = new ManageUsersController(stage, user));
    }

    @Test
    void testRegisterNewLibrarian() throws Exception {
        runOnJavaFXThread(() -> {
            // Simulate UI input
            controller.getView().getEmailTf().setText("john.doe@example.com");
            controller.getView().getPhoneNoTf().setText("0681234567");
            controller.getView().setFirstNameTF("John");
            controller.getView().setLastNameTF("Doe");
            controller.getView().getUserNameTf().setText("johndoe");
            controller.getView().getPasswordTf().setText("Password123@");
            controller.getView().getSalaryTf().setText("500");
            controller.getView().getBirthDateDayTf().setText("15");
            controller.getView().getBirthDateMonthTf().setText("5");
            controller.getView().getBirthDateYearTf().setText("1995");

            controller.getView().getGenderComboBox().setValue(Gender.MALE);
            controller.getView().getRoleComboBox().setValue(Role.LIBRARIAN);

            // Simulate form submission
            controller.Submit(null);
        });

        // Allow time for JavaFX thread to process
        Thread.sleep(500);

        // Verify the user was added to the DAO and list
        verify(usersDAO, times(1)).addToFile(any());
        assertEquals(1, users.size());
        assertEquals("John", users.get(0).getFirstName());
    }

    @Test
    void testEditExistingUserDetails() throws Exception {
        // Add an existing user
        Librarian librarian = new Librarian(
                "Emily", "Brown", new Date(), Gender.FEMALE,
                "emilybrown", "pass456", Role.LIBRARIAN, "emily.brown@example.com", "0681234569", 700.0);
        users.add(librarian);

        runOnJavaFXThread(() -> {
            // Simulate selecting and editing a user
            controller.getView().getTableView().getSelectionModel().select(librarian);

            TableColumn.CellEditEvent<UsersOfTheSystem, Double> event = new TableColumn.CellEditEvent<>(
                    controller.getView().getTableView(),
                    new TablePosition<>(controller.getView().getTableView(), 0, controller.getView().getSalaryColumn()),
                    TableColumn.editCommitEvent(),
                    750.0
            );

            // Fire the event manually
            controller.getView().getSalaryColumn().getOnEditCommit().handle(event);
        });

        // Verify the salary was updated
        assertEquals(750.0, librarian.getSalary(), 0.01);
        verify(usersDAO, times(1)).updateAll();
    }

    @Test
    void testDeleteUser() throws Exception {
        // Add a user to the system
        Manager manager = new Manager(
                "Mike", "Johnson", new Date(), Gender.MALE,
                "mikejohnson", "managerpass", Role.MANAGER, "mike.johnson@example.com", "0688765432", 1200.0);
        users.add(manager);

        runOnJavaFXThread(() -> {
            // Simulate deleting a user
            controller.getView().getTableView().getSelectionModel().select(manager);
            controller.onUserDelete(null);
        });

        // Verify user removal
        assertEquals(0, users.size());
        verify(usersDAO, times(1)).deleteAll(any());
    }

    private void runOnJavaFXThread(Runnable task) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                task.run();
            } finally {
                latch.countDown();
            }
        });
        if (!latch.await(5, TimeUnit.SECONDS)) {
            throw new Exception("Timeout while waiting for JavaFX task to complete");
        }
    }
}

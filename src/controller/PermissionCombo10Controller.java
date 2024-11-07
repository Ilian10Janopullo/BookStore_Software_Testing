package controller;

import dao.BillsDAO;
import dao.UsersDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Bill;
import model.UsersOfTheSystem;
import view.PermissionCombo10;

public class PermissionCombo10Controller {
    private static ObservableList<UsersOfTheSystem> users;
    private static ObservableList<Bill> bills;
    private final PermissionCombo10 view;
    private final Stage stage;
    private final UsersOfTheSystem user;

    public PermissionCombo10Controller(Stage stage, UsersOfTheSystem user) {
        this.user = user;
        this.stage = stage;
        this.view = new PermissionCombo10(stage);
        BillsDAO billsDAO = new BillsDAO();
        UsersDAO usersDAO = new UsersDAO();
        users = FXCollections.observableArrayList(usersDAO.getAll());
        bills = FXCollections.observableArrayList(billsDAO.getAll());


        this.view.getLogout().setOnAction(e -> LogOut());
        this.view.getManageBills().setOnAction(e -> ManageBills());
        this.view.getCheckUsers().setOnAction(e -> CheckUsers());
    }

    public PermissionCombo10 getView() {
        return view;
    }

    public void LogOut() {
        LoginController controller = new LoginController(stage);
        stage.getScene().setRoot(controller.getView());
    }


    public void ManageBills() {
        if (!bills.isEmpty()) {
            ManageBillsController controller = new ManageBillsController(stage, user);
            stage.getScene().setRoot(controller.getView());
        } else {
            Alert alert;
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("You cannot manage any bill, because there is no bill in the system!");
            alert.setTitle("Warning");
            alert.show();
        }
    }

    public void CheckUsers() {
        if (!users.isEmpty()) {
            CheckUsersController controller = new CheckUsersController(stage, user);
            stage.getScene().setRoot(controller.getView());
        } else {
            Alert alert;
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No users in the system!!");
            alert.setTitle("Warning");
            alert.show();
        }
    }
}

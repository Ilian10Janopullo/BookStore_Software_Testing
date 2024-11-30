package org.example.controller;


import org.example.dao.BillsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.model.Bill;
import org.example.model.UsersOfTheSystem;
import org.example.view.PermissionCombo3;

public class PermissionCombo3Controller {
    private static ObservableList<Bill> bills;
    private final PermissionCombo3 view;
    private final Stage stage;
    private final UsersOfTheSystem user;

    public PermissionCombo3Controller(Stage stage, UsersOfTheSystem user) {
        this.user = user;
        this.stage = stage;
        this.view = new PermissionCombo3(stage);
        BillsDAO billsDAO = new BillsDAO();
        bills = FXCollections.observableArrayList(billsDAO.getAll());

        this.view.getLogout().setOnAction(e -> LogOut());
        this.view.getManageBills().setOnAction(e -> ManageBills());
    }

    public PermissionCombo3 getView() {
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
}

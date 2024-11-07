package controller;

import dao.UsersDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.UsersOfTheSystem;
import view.PermissionCombo4;

public class PermissionCombo4Controller {
    private static ObservableList<UsersOfTheSystem> users;
    private final PermissionCombo4 view;
    private final Stage stage;
    private final UsersOfTheSystem user;

    public PermissionCombo4Controller(Stage stage, UsersOfTheSystem user) {
        this.user = user;
        this.stage = stage;
        this.view = new PermissionCombo4(stage);
        UsersDAO usersDAO = new UsersDAO();
        users = FXCollections.observableArrayList(usersDAO.getAll());

        this.view.getLogout().setOnAction(e -> LogOut());
        this.view.getCheckUsers().setOnAction(e -> CheckUsers());
    }

    public PermissionCombo4 getView() {
        return view;
    }



    public void LogOut() {
        LoginController controller = new LoginController(stage);
        stage.getScene().setRoot(controller.getView());
    }



    public void CheckUsers() {
        if (!users.isEmpty()) {
            CheckUsersController controller = new CheckUsersController(stage, user);
            stage.getScene().setRoot(controller.getView());
        } else {
            Alert alert;
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("No users in the system!");
            alert.setTitle("Warning");
            alert.show();
        }
    }
}

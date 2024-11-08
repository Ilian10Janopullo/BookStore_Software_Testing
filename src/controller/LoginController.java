package controller;


import dao.PermissionsDAO;
import dao.UsersDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.Role;
import model.Status;
import model.UsersOfTheSystem;
import view.LoginView;

public class LoginController {
    private static ObservableList<UsersOfTheSystem> users;
    private static ObservableList<String> permissionsCombo;
    private final LoginView view;
    private final Stage stage;

    public LoginController(Stage stage) {
        this.stage = stage;
        this.view = new LoginView();
        UsersDAO usersDAO = new UsersDAO();
        PermissionsDAO permissionsDAO = new PermissionsDAO();
        permissionsCombo = FXCollections.observableArrayList(permissionsDAO.getAll());
        users = FXCollections.observableArrayList(usersDAO.getAll());
        this.view.getEnterBtn().setOnAction(e -> Enter());
    }

    public LoginView getView() {
        return view;
    }

    public void Enter() {
        String username = view.getEnterUserName().trim();
        String password = view.getEnterPassword();

        boolean authenticationStatus = false;

        for (UsersOfTheSystem user : users) {
            if (username.compareTo(user.getUsername()) == 0 && password.compareTo(user.getPassword()) == 0) {
                authenticationStatus = true;
                if (user.getRole() == Role.ADMIN && user.getStatus() == Status.ACTIVE) {
                    AdminMenuController controller = new AdminMenuController(stage, user);
                    stage.getScene().setRoot(controller.getView());
                    break;
                } else if (user.getRole() == Role.MANAGER && user.getStatus() == Status.ACTIVE) {
                    if(permissionsCombo.getFirst().equals("PermissionCombo1Controller")){
                        PermissionCombo1Controller controller = new PermissionCombo1Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    }
                    else if(permissionsCombo.getFirst().equals("PermissionCombo2Controller")){
                        PermissionCombo2Controller controller = new PermissionCombo2Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    }
                    else if(permissionsCombo.getFirst().equals("PermissionCombo3Controller")){
                        PermissionCombo3Controller controller = new PermissionCombo3Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    }
                    else if(permissionsCombo.getFirst().equals("PermissionCombo4Controller")){
                        PermissionCombo4Controller controller = new PermissionCombo4Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    }
                    else if(permissionsCombo.getFirst().equals("PermissionCombo5Controller")){
                        PermissionCombo5Controller controller = new PermissionCombo5Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    }
                    else if(permissionsCombo.getFirst().equals("PermissionCombo6Controller")){
                        PermissionCombo6Controller controller = new PermissionCombo6Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    }
                    else if(permissionsCombo.getFirst().equals("PermissionCombo7Controller")){
                        PermissionCombo7Controller controller = new PermissionCombo7Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    }
                    else if(permissionsCombo.getFirst().equals("PermissionCombo8Controller")){
                        PermissionCombo8Controller controller = new PermissionCombo8Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    }
                    else if(permissionsCombo.getFirst().equals("PermissionCombo9Controller")){
                        PermissionCombo9Controller controller = new PermissionCombo9Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    }
                    else if(permissionsCombo.getFirst().equals("PermissionCombo10Controller")){
                        PermissionCombo10Controller controller = new PermissionCombo10Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    }
                    else if(permissionsCombo.getFirst().equals("PermissionCombo11Controller")){
                        PermissionCombo11Controller controller = new PermissionCombo11Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    }
                    else if(permissionsCombo.getFirst().equals("PermissionCombo12Controller")){
                        PermissionCombo12Controller controller = new PermissionCombo12Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    }
                    else if(permissionsCombo.getFirst().equals("PermissionCombo13Controller")){
                        PermissionCombo13Controller controller = new PermissionCombo13Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    }
                    else if(permissionsCombo.getFirst().equals("PermissionCombo14Controller")){
                        PermissionCombo14Controller controller = new PermissionCombo14Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    }
                    else if(permissionsCombo.getFirst().equals("PermissionCombo15Controller")){
                        PermissionCombo15Controller controller = new PermissionCombo15Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    }
                } else if (user.getRole() == Role.LIBRARIAN && user.getStatus() == Status.ACTIVE) {
                    if (permissionsCombo.get(1).equals("PermissionCombo1Controller")) {
                        PermissionCombo1Controller controller = new PermissionCombo1Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    } else if (permissionsCombo.get(1).equals("PermissionCombo2Controller")) {
                        PermissionCombo2Controller controller = new PermissionCombo2Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    } else if (permissionsCombo.get(1).equals("PermissionCombo3Controller")) {
                        PermissionCombo3Controller controller = new PermissionCombo3Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    } else if (permissionsCombo.get(1).equals("PermissionCombo4Controller")) {
                        PermissionCombo4Controller controller = new PermissionCombo4Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    } else if (permissionsCombo.get(1).equals("PermissionCombo5Controller")) {
                        PermissionCombo5Controller controller = new PermissionCombo5Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    } else if (permissionsCombo.get(1).equals("PermissionCombo6Controller")) {
                        PermissionCombo6Controller controller = new PermissionCombo6Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    } else if (permissionsCombo.get(1).equals("PermissionCombo7Controller")) {
                        PermissionCombo7Controller controller = new PermissionCombo7Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    } else if (permissionsCombo.get(1).equals("PermissionCombo8Controller")) {
                        PermissionCombo8Controller controller = new PermissionCombo8Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    } else if (permissionsCombo.get(1).equals("PermissionCombo9Controller")) {
                        PermissionCombo9Controller controller = new PermissionCombo9Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    } else if (permissionsCombo.get(1).equals("PermissionCombo10Controller")) {
                        PermissionCombo10Controller controller = new PermissionCombo10Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    } else if (permissionsCombo.get(1).equals("PermissionCombo11Controller")) {
                        PermissionCombo11Controller controller = new PermissionCombo11Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    } else if (permissionsCombo.get(1).equals("PermissionCombo12Controller")) {
                        PermissionCombo12Controller controller = new PermissionCombo12Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    } else if (permissionsCombo.get(1).equals("PermissionCombo13Controller")) {
                        PermissionCombo13Controller controller = new PermissionCombo13Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    } else if (permissionsCombo.get(1).equals("PermissionCombo14Controller")) {
                        PermissionCombo14Controller controller = new PermissionCombo14Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    } else if (permissionsCombo.get(1).equals("PermissionCombo15Controller")) {
                        PermissionCombo15Controller controller = new PermissionCombo15Controller(stage, user);
                        stage.getScene().setRoot(controller.getView());
                        break;
                    }
                } else {
                    Alert alert;
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("You are not an active user of the system!");
                    alert.setTitle("Authentication result");
                    this.view.setEnterPassword();
                    this.view.setEnterUserName();
                    alert.show();
                    break;
                }
            }
        }
        if (!authenticationStatus) {
            Alert alert;
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Authentication failed");
            alert.setTitle("Authentication result");
            this.view.setEnterPassword();
            this.view.setEnterUserName();
            alert.show();
        }
    }
}
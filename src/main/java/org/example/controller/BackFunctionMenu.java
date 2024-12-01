package org.example.controller;

import javafx.collections.ObservableList;
import javafx.stage.Stage;
import org.example.model.UsersOfTheSystem;

public class BackFunctionMenu {
    static void mainMenuBack(int i, ObservableList<String> permissionsCombo, Stage stage, UsersOfTheSystem user) {
        switch (permissionsCombo.get(i)) {
            case "PermissionCombo1Controller" -> {
                PermissionCombo1Controller controller = new PermissionCombo1Controller(stage, user, true);
                stage.getScene().setRoot(controller.getView());
            }
            case "PermissionCombo2Controller" -> {
                PermissionCombo2Controller controller = new PermissionCombo2Controller(stage, user);
                stage.getScene().setRoot(controller.getView());
            }
            case "PermissionCombo3Controller" -> {
                PermissionCombo3Controller controller = new PermissionCombo3Controller(stage, user);
                stage.getScene().setRoot(controller.getView());
            }
            case "PermissionCombo4Controller" -> {
                PermissionCombo4Controller controller = new PermissionCombo4Controller(stage, user);
                stage.getScene().setRoot(controller.getView());
            }
            case "PermissionCombo5Controller" -> {
                PermissionCombo5Controller controller = new PermissionCombo5Controller(stage, user, true);
                stage.getScene().setRoot(controller.getView());
            }
            case "PermissionCombo6Controller" -> {
                PermissionCombo6Controller controller = new PermissionCombo6Controller(stage, user, true);
                stage.getScene().setRoot(controller.getView());
            }
            case "PermissionCombo7Controller" -> {
                PermissionCombo7Controller controller = new PermissionCombo7Controller(stage, user, true);
                stage.getScene().setRoot(controller.getView());
            }
            case "PermissionCombo8Controller" -> {
                PermissionCombo8Controller controller = new PermissionCombo8Controller(stage, user);
                stage.getScene().setRoot(controller.getView());
            }
            case "PermissionCombo9Controller" -> {
                PermissionCombo9Controller controller = new PermissionCombo9Controller(stage, user);
                stage.getScene().setRoot(controller.getView());
            }
            case "PermissionCombo10Controller" -> {
                PermissionCombo10Controller controller = new PermissionCombo10Controller(stage, user);
                stage.getScene().setRoot(controller.getView());
            }
            case "PermissionCombo11Controller" -> {
                PermissionCombo11Controller controller = new PermissionCombo11Controller(stage, user, true);
                stage.getScene().setRoot(controller.getView());
            }
            case "PermissionCombo12Controller" -> {
                PermissionCombo12Controller controller = new PermissionCombo12Controller(stage, user, true);
                stage.getScene().setRoot(controller.getView());
            }
            case "PermissionCombo13Controller" -> {
                PermissionCombo13Controller controller = new PermissionCombo13Controller(stage, user, true);
                stage.getScene().setRoot(controller.getView());
            }
            case "PermissionCombo14Controller" -> {
                PermissionCombo14Controller controller = new PermissionCombo14Controller(stage, user);
                stage.getScene().setRoot(controller.getView());
            }
            case "PermissionCombo15Controller" -> {
                PermissionCombo15Controller controller = new PermissionCombo15Controller(stage, user, true);
                stage.getScene().setRoot(controller.getView());
            }
            default -> throw new IllegalStateException("Unexpected value: " + permissionsCombo.get(i));
        }
    }
}

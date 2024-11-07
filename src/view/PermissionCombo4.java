package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class PermissionCombo4 extends BorderPane {
    private final Button logout = new Button("");
    private final Button checkUsers = new Button("");

    public PermissionCombo4(Stage stage) {

        logout.setPrefWidth(100);
        logout.setPrefHeight(100);
        checkUsers.setPrefWidth(200);
        checkUsers.setPrefHeight(200);

        FileInputStream input = null;

        try {
            input = new FileInputStream("images/logout.png");
        }catch(java.io.FileNotFoundException e) {
            System.out.println("Check images for bookshop");
        }
        assert input != null;
        ImageView logoutIcon = new ImageView(new Image(input));
        logoutIcon.setFitHeight(90);
        logoutIcon.setFitWidth(90);
        logout.setGraphic(logoutIcon);
        logout.setContentDisplay(ContentDisplay.RIGHT);


        try {
            input = new FileInputStream("images/best-employee.png");
        }catch(java.io.FileNotFoundException e) {
            System.out.println("Check images for bookshop");
        }
        ImageView incorporationIcon = new ImageView(new Image(input));
        incorporationIcon.setFitHeight(180);
        incorporationIcon.setFitWidth(180);
        checkUsers.setGraphic(incorporationIcon);
        checkUsers.setContentDisplay(ContentDisplay.RIGHT);


        VBox vBox4 = new VBox();
        Label checkU = new Label("Check Users");
        vBox4.getChildren().addAll(checkU, checkUsers);
        vBox4.setAlignment(Pos.CENTER);
        vBox4.setSpacing(30);


        HBox hBox = new HBox();
        hBox.getChildren().addAll(vBox4);
        hBox.setSpacing(100);
        this.setCenter(hBox);
        this.setBottom(logout);
        setMargin(hBox, new Insets(0, 20, 0, 660));
        setMargin(logout, new Insets(0, 0, 20, 705));
        this.setStyle("-fx-font-family: 'Times New Roman' ; -fx-font-size: 20; -fx-font-weight: BOLD ;-fx-border-width: 2 ; -fx-border-color: BLACK;");
    }

    public Button getLogout() {
        return logout;
    }

    public Button getCheckUsers() {
        return checkUsers;
    }
}

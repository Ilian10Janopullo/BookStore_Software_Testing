package org.example.view;

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

public class PermissionCombo3 extends BorderPane {
    private final Button logout = new Button("");

    private final Button manageBills = new Button("");

    @SuppressWarnings("unused")
    public PermissionCombo3(Stage stage) {

        logout.setPrefWidth(100);
        logout.setPrefHeight(100);
        manageBills.setPrefWidth(200);
        manageBills.setPrefHeight(200);

        FileInputStream input = null;

        try {
            input = new FileInputStream("src/main/images/logout.png");
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
            input = new FileInputStream("src/main/images/receipt.png");
        }catch(java.io.FileNotFoundException e) {
            System.out.println("Check images for bookshop");
        }

        ImageView receiptIcon = new ImageView(new Image(input));
        receiptIcon.setFitHeight(180);
        receiptIcon.setFitWidth(180);
        manageBills.setGraphic(receiptIcon);
        manageBills.setContentDisplay(ContentDisplay.RIGHT);


        VBox vBox5 = new VBox();
        Label manageBi = new Label("Manage Bills");
        vBox5.getChildren().addAll(manageBi, manageBills);
        vBox5.setAlignment(Pos.CENTER);
        vBox5.setSpacing(30);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(vBox5);
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

    public Button getManageBills() {
        return manageBills;
    }

}

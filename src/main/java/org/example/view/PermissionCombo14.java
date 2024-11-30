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

public class PermissionCombo14  extends BorderPane {
    private final Button logout = new Button("");
    private final Button sellBooks = new Button("");
    private final Button manageBills = new Button("");
    private final Button checkUsers = new Button("");

    public PermissionCombo14(Stage stage) {
        sellBooks.setPrefWidth(200);
        sellBooks.setPrefHeight(200);
        logout.setPrefWidth(100);
        logout.setPrefHeight(100);
        checkUsers.setPrefWidth(200);
        checkUsers.setPrefHeight(200);
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

        try {
            input = new FileInputStream("src/main/images/buy.png");
        }catch(java.io.FileNotFoundException e) {
            System.out.println("Check images for bookshop");
        }
        ImageView sellIcon = new ImageView(new Image(input));
        sellIcon.setFitHeight(180);
        sellIcon.setFitWidth(180);
        sellBooks.setGraphic(sellIcon);
        sellBooks.setContentDisplay(ContentDisplay.RIGHT);

        try {
            input = new FileInputStream("src/main/images/best-employee.png");
        }catch(java.io.FileNotFoundException e) {
            System.out.println("Check images for bookshop");
        }
        ImageView incorporationIcon = new ImageView(new Image(input));
        incorporationIcon.setFitHeight(180);
        incorporationIcon.setFitWidth(180);
        checkUsers.setGraphic(incorporationIcon);
        checkUsers.setContentDisplay(ContentDisplay.RIGHT);

        VBox vBox1 = new VBox();
        Label checkU = new Label("Check Users");
        vBox1.getChildren().addAll(checkU, checkUsers);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setSpacing(30);

        VBox vBox4 = new VBox();
        Label sellB = new Label("Sell Books");
        vBox4.getChildren().addAll(sellB, sellBooks);
        vBox4.setAlignment(Pos.CENTER);
        vBox4.setSpacing(30);

        VBox vBox5 = new VBox();
        Label manageBi = new Label("Manage Bills");
        vBox5.getChildren().addAll(manageBi, manageBills);
        vBox5.setAlignment(Pos.CENTER);
        vBox5.setSpacing(30);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(vBox1, vBox4, vBox5);
        hBox.setSpacing(80);
        this.setCenter(hBox);
        this.setBottom(logout);
        setMargin(hBox, new Insets(0, 20, 0, 370));
        setMargin(logout, new Insets(0, 0, 20, 705));
        this.setStyle("-fx-font-family: 'Times New Roman' ; -fx-font-size: 20; -fx-font-weight: BOLD ;-fx-border-width: 2 ; -fx-border-color: BLACK;");
    }

    public Button getLogout() {
        return logout;
    }

    public Button getCheckUsers() {
        return checkUsers;
    }

    public Button getSellBooks() {
        return sellBooks;
    }

    public Button getManageBills() {
        return manageBills;
    }
}

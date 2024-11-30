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

public class PermissionCombo2 extends BorderPane {
    private final Button logout = new Button("");
    private final Button sellBooks = new Button("");
    Label sellB = new Label("Sell Books");


    public PermissionCombo2(Stage stage) {
        sellBooks.setPrefWidth(200);
        sellBooks.setPrefHeight(200);
        logout.setPrefWidth(100);
        logout.setPrefHeight(100);

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
            input = new FileInputStream("src/main/images/buy.png");
        }catch(java.io.FileNotFoundException e) {
            System.out.println("Check images for bookshop");
        }
        ImageView sellIcon = new ImageView(new Image(input));
        sellIcon.setFitHeight(180);
        sellIcon.setFitWidth(180);
        sellBooks.setGraphic(sellIcon);
        sellBooks.setContentDisplay(ContentDisplay.RIGHT);

        VBox vBox4 = new VBox();
        vBox4.getChildren().addAll(sellB, sellBooks);
        vBox4.setAlignment(Pos.CENTER);
        vBox4.setSpacing(30);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(vBox4);
        hBox.setSpacing(80);
        this.setCenter(hBox);
        this.setBottom(logout);
        setMargin(hBox, new Insets(0, 20, 0, 660));
        setMargin(logout, new Insets(0, 0, 20, 705));
        this.setStyle("-fx-font-family: 'Times New Roman' ; -fx-font-size: 20; -fx-font-weight: BOLD ;-fx-border-width: 2 ; -fx-border-color: BLACK;");
    }

    public Button getLogout() {
        return logout;
    }

    public Button getSellBooks() {
        return sellBooks;
    }

}

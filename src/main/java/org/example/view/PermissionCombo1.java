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

public class PermissionCombo1 extends BorderPane {
    private final Button logout = new Button("");
    private final Button manageBooks = new Button("");
    private final Button manageAuthors = new Button("");

    public PermissionCombo1(Stage stage) {

        logout.setPrefWidth(100);
        logout.setPrefHeight(100);
        manageAuthors.setPrefWidth(200);
        manageAuthors.setPrefHeight(200);
        manageBooks.setPrefWidth(200);
        manageBooks.setPrefHeight(200);

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
            input = new FileInputStream("src/main/images/pencil.png");
        }catch(java.io.FileNotFoundException e) {
            System.out.println("Check images for bookshop");
        }
        ImageView writerIcon = new ImageView(new Image(input));
        writerIcon.setFitHeight(180);
        writerIcon.setFitWidth(180);
        manageAuthors.setGraphic(writerIcon);
        manageAuthors.setContentDisplay(ContentDisplay.RIGHT);

        try {
            input = new FileInputStream("src/main/images/open-book.png");
        }catch(java.io.FileNotFoundException e) {
            System.out.println("Check images for bookshop");
        }
        ImageView bookIcon = new ImageView(new Image(input));
        bookIcon.setFitHeight(180);
        bookIcon.setFitWidth(180);
        manageBooks.setGraphic(bookIcon);
        manageBooks.setContentDisplay(ContentDisplay.RIGHT);

        VBox vBox1 = new VBox();
        Label manageA = new Label("Manage Authors");
        vBox1.getChildren().addAll(manageA, manageAuthors);
        vBox1.setAlignment(Pos.CENTER);
        vBox1.setSpacing(30);

        VBox vBox2 = new VBox();
        Label manageB = new Label("Manage Books");
        vBox2.getChildren().addAll(manageB, manageBooks);
        vBox2.setAlignment(Pos.CENTER);
        vBox2.setSpacing(30);



        HBox hBox = new HBox();
        hBox.getChildren().addAll(vBox1, vBox2);
        hBox.setSpacing(100);
        this.setCenter(hBox);
        this.setBottom(logout);
        setMargin(hBox, new Insets(0, 20, 0, 510));
        setMargin(logout, new Insets(0, 0, 20, 705));
        this.setStyle("-fx-font-family: 'Times New Roman' ; -fx-font-size: 20; -fx-font-weight: BOLD ;-fx-border-width: 2 ; -fx-border-color: BLACK;");
    }

    public Button getLogout() {
        return logout;
    }

    public Button getManageBooks() {
        return manageBooks;
    }

    public Button getManageAuthors() {
        return manageAuthors;
    }

}

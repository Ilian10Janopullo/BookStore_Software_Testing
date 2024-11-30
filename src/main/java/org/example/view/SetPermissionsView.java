package org.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;

public class SetPermissionsView extends BorderPane {
    private final CheckBox manageStockManager = new CheckBox();
    private final CheckBox manageStockLibrarian = new CheckBox();
    private final CheckBox manageSellsManager = new CheckBox();
    private final CheckBox manageSellsLibrarian = new CheckBox();
    private final CheckBox manageBillsManager = new CheckBox();
    private final CheckBox manageBillsLibrarian = new CheckBox();
    private final CheckBox checkUsersManager = new CheckBox();
    private final CheckBox checkUsersLibrarian = new CheckBox();
    private final Button updateBtn = new Button("");
    private final Button backBtn = new Button("");

    public SetPermissionsView(){

        updateBtn.setPrefWidth(90);
        backBtn.setPrefWidth(90);

        VBox vBox1 = new VBox();
        Label manager = new Label("Manager");
        Label librarian = new Label("Librarian");
        Label empty = new Label("");
        vBox1.getChildren().addAll(empty, manager, librarian);
        vBox1.setSpacing(50);

        VBox vBox2 = new VBox();
        Label manageStock = new Label("Manage Stock");
        vBox2.getChildren().addAll(manageStock, manageStockManager, manageStockLibrarian);
        VBox.setMargin(manageStockManager, new Insets(0, 0, 0, 38));
        VBox.setMargin(manageStockLibrarian, new Insets(0, 0, 0, 38));
        vBox2.setSpacing(50);

        VBox vBox3 = new VBox();
        Label manageSells = new Label("Bill Creator");
        vBox3.getChildren().addAll(manageSells, manageSellsManager, manageSellsLibrarian);
        VBox.setMargin(manageSellsManager, new Insets(0, 0, 0, 27));
        VBox.setMargin(manageSellsLibrarian, new Insets(0, 0, 0, 27));
        vBox3.setSpacing(50);

        VBox vBox4 = new VBox();
        Label manageBills = new Label("Manage Bills");
        vBox4.getChildren().addAll(manageBills, manageBillsManager, manageBillsLibrarian);
        VBox.setMargin(manageBillsManager, new Insets(0, 0, 0, 36));
        VBox.setMargin(manageBillsLibrarian, new Insets(0, 0, 0, 36));
        vBox4.setSpacing(50);

        VBox vBox5 = new VBox();
        Label checkUsers = new Label("Check Users");
        vBox5.getChildren().addAll(checkUsers, checkUsersManager, checkUsersLibrarian);
        VBox.setMargin(checkUsersManager, new Insets(0, 0, 0, 36));
        VBox.setMargin(checkUsersLibrarian, new Insets(0, 0, 0, 36));
        vBox5.setSpacing(50);

        FileInputStream input = null;

        try {
            input = new FileInputStream("src/main/images/back.png");
        }catch(java.io.FileNotFoundException e) {
            System.out.println("Check images for bookshop");
        }
        assert input != null;
        ImageView backIcon = new ImageView(new Image(input));
        backIcon.setFitHeight(25);
        backIcon.setFitWidth(25);
        backBtn.setGraphic(backIcon);
        backBtn.setContentDisplay(ContentDisplay.RIGHT);

        try {
            input = new FileInputStream("src/main/images/update.png");
        }catch(java.io.FileNotFoundException e) {
            System.out.println("Check images for bookshop");
        }
        ImageView updateIcon = new ImageView(new Image(input));
        updateIcon.setFitHeight(25);
        updateIcon.setFitWidth(25);
        updateBtn.setGraphic(updateIcon);
        updateBtn.setContentDisplay(ContentDisplay.RIGHT);

        HBox hBox1 = new HBox();
        hBox1.getChildren().addAll(backBtn, updateBtn);
        hBox1.setSpacing(100);

        HBox hBox2 = new HBox();
        hBox2.getChildren().addAll(vBox1, vBox2, vBox3, vBox4, vBox5);
        hBox2.setSpacing(100);


        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox2, hBox1);
        vBox.setSpacing(70);
        VBox.setMargin(hBox1, new Insets(0, 0, 0, 330));
        vBox.setAlignment(Pos.CENTER);

        this.setCenter(vBox);
        setMargin(vBox, new Insets(-45, 0,0 , 325));

        Label header = new Label("Manage Permissions");
        this.setTop(header);
        setMargin(header, new Insets(30, 0, 0, 650));
        header.setStyle("-fx-font-family: 'Times New Roman' ; -fx-font-size: 35; -fx-font-weight: BOLD; ");


        this.setStyle("-fx-font-family: 'Times New Roman' ; -fx-font-size: 16; -fx-font-weight: BOLD;");
    }

    public Button getBackBtn() {
        return backBtn;
    }

    public Button getUpdateBtn() {
        return updateBtn;
    }

    public CheckBox getCheckUsersLibrarian() {
        return checkUsersLibrarian;
    }

    public void setCheckUsersLibrarian(boolean bool) {
        checkUsersLibrarian.setSelected(bool);
    }

    public CheckBox getCheckUsersManager() {
        return checkUsersManager;
    }
    public void setCheckUsersManager(boolean bool) {
        checkUsersManager.setSelected(bool);
    }

    public CheckBox getManageBillsLibrarian() {
        return manageBillsLibrarian;
    }

    public void setManageBillsLibrarian(boolean bool) {
        manageBillsLibrarian.setSelected(bool);
    }

    public CheckBox getManageBillsManager() {
        return manageBillsManager;
    }

    public void setManageBillsManager(boolean bool) {
        manageBillsManager.setSelected(bool);
    }

    public CheckBox getManageSellsLibrarian() {
        return manageSellsLibrarian;
    }

    public void setManageSellsLibrarian(boolean bool) {
        manageSellsLibrarian.setSelected(bool);
    }

    public CheckBox getManageSellsManager() {
        return manageSellsManager;
    }

    public void setManageSellsManager(boolean bool) {
        manageSellsManager.setSelected(bool);
    }

    public CheckBox getManageStockLibrarian() {
        return manageStockLibrarian;
    }

    public void setManageStockLibrarian(boolean bool) {
        manageStockLibrarian.setSelected(bool);
    }

    public CheckBox getManageStockManager() {
        return manageStockManager;
    }

    public void setManageStockManager(boolean bool) {
        manageStockManager.setSelected(bool);
    }
}

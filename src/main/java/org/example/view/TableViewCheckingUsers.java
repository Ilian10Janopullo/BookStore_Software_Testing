package org.example.view;


import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import org.example.model.UsersOfTheSystem;

import java.io.FileInputStream;


public class TableViewCheckingUsers extends BorderPane {
    private final TableView<UsersOfTheSystem> tableView = new TableView<>();
    private final TableColumn<UsersOfTheSystem, String> fullNameColumn;
    private final TableColumn<UsersOfTheSystem, String> userIDColumn;
    private final TableColumn<UsersOfTheSystem, String> genderColumn;
    private final TableColumn<UsersOfTheSystem, Integer> nrOfBooksSoldColumn;
    private final TableColumn<UsersOfTheSystem, Double> revenueMadeColumn;
    private final TableColumn<UsersOfTheSystem, String> roleColumn;
    private final TableColumn<UsersOfTheSystem, String> statusColumn;
    private final TableColumn<UsersOfTheSystem, String> emailColumn;
    private final TableColumn<UsersOfTheSystem, String> phoneNoColumn;


    private final TextField searchBarTf = new TextField();
    private final Button resetBtn = new Button("");
    private final Button returnButton = new Button("");

    public TableViewCheckingUsers() {

        tableView.setEditable(false);

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);


        fullNameColumn = new TableColumn<>("Full Name");
        fullNameColumn.setMinWidth(135);
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));


        genderColumn = new TableColumn<>("Gender");
        genderColumn.setMinWidth(135);
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));


        revenueMadeColumn = new TableColumn<>("Revenue");
        revenueMadeColumn.setMinWidth(135);
        revenueMadeColumn.setCellValueFactory(new PropertyValueFactory<>("revenueMade"));


        nrOfBooksSoldColumn = new TableColumn<>("Sold Books Number");
        nrOfBooksSoldColumn.setMinWidth(135);
        nrOfBooksSoldColumn.setCellValueFactory(new PropertyValueFactory<>("nrOfBooksSold"));


        roleColumn = new TableColumn<>("Role");
        roleColumn.setMinWidth(135);
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));


        statusColumn = new TableColumn<>("Status");
        statusColumn.setMinWidth(135);
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


        userIDColumn = new TableColumn<>("User ID");
        userIDColumn.setMinWidth(135);
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        phoneNoColumn = new TableColumn<>("Phone Number");
        phoneNoColumn.setMinWidth(135);
        phoneNoColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));

        emailColumn = new TableColumn<>("e-Mail");
        emailColumn.setMinWidth(135);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));


        tableView.getColumns().addAll(fullNameColumn, userIDColumn, roleColumn, genderColumn, emailColumn, phoneNoColumn, statusColumn, nrOfBooksSoldColumn, revenueMadeColumn);

        returnButton.setPrefWidth(80);
        resetBtn.setPrefWidth(80);

        FileInputStream input = null;

        try {
            input = new FileInputStream("src/main/images/back.png");
        }catch(java.io.FileNotFoundException e) {
            System.out.println("Check images for bookshop");
        }
        assert input != null;
        ImageView backIcon = new ImageView(new Image(input));
        backIcon.setFitHeight(20);
        backIcon.setFitWidth(25);
        returnButton.setGraphic(backIcon);
        returnButton.setContentDisplay(ContentDisplay.RIGHT);

        try {
            input = new FileInputStream("src/main/images/refresh-button.png");
        }catch(java.io.FileNotFoundException e) {
            System.out.println("Check images for bookshop");
        }
        ImageView refreshIcon = new ImageView(new Image(input));
        refreshIcon.setFitHeight(20);
        refreshIcon.setFitWidth(25);
        resetBtn.setGraphic(refreshIcon);
        resetBtn.setContentDisplay(ContentDisplay.RIGHT);

        FlowPane flowPane = new FlowPane(3, 3);
        HBox hBox = new HBox();
        hBox.setSpacing(20);
        Label searchBarLb = new Label("Search Bar : ");
        hBox.getChildren().addAll(searchBarLb, searchBarTf, resetBtn);
        flowPane.getChildren().addAll(returnButton, hBox);
        flowPane.setHgap(425);

        this.setCenter(tableView);
        setMargin(tableView, new Insets(50, 150, 50, 150));
        this.setTop(flowPane);


        this.setStyle("-fx-font-family: 'Times New Roman' ; -fx-font-size: 15; ");
        setAlignment(flowPane, Pos.CENTER);
        setMargin(flowPane, new Insets(10, 0, 0, 150));

    }


    public TableColumn<UsersOfTheSystem, String> getFullNameColumn() {
        return fullNameColumn;
    }

    public TableColumn<UsersOfTheSystem, String> getGenderColumn() {
        return genderColumn;
    }

    public TableColumn<UsersOfTheSystem, Integer> getNrOfBooksSoldColumn() {
        return nrOfBooksSoldColumn;
    }

    public TableView<UsersOfTheSystem> getTableView() {
        return tableView;
    }


    public TableColumn<UsersOfTheSystem, String> getUserIDColumn() {
        return userIDColumn;
    }

    public TableColumn<UsersOfTheSystem, Double> getRevenueMadeColumn() {
        return revenueMadeColumn;
    }

    public TableColumn<UsersOfTheSystem, String> getRoleColumn() {
        return roleColumn;
    }

    public TableColumn<UsersOfTheSystem, String> getStatusColumn() {
        return statusColumn;
    }

    public TableColumn<UsersOfTheSystem, String> getEmailColumn() {
        return emailColumn;
    }

    public TableColumn<UsersOfTheSystem, String> getPhoneNoColumn() {
        return phoneNoColumn;
    }

    public TextField getSearchBarTf() {
        return searchBarTf;
    }

    public void setSearchBarTf() {
        searchBarTf.setText("");
    }

    public Button getResetBtn() {
        return resetBtn;
    }

    public Button getReturnButton() {
        return returnButton;
    }


}

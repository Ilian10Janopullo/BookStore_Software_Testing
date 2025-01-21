package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.converter.IntegerStringConverter;
import model.Author;
import model.Gender;

import java.io.FileInputStream;

public class TableViewManagingAuthors extends BorderPane {
    private final TableView<Author> tableView = new TableView<>();
    private final TableColumn<Author, String> firstNameColumn;
    private final TableColumn<Author, String> middleNameColumn;
    private final TableColumn<Author, String> lastNameColumn;
    private final TableColumn<Author, String> fullNameColumn;
    private final TableColumn<Author, Integer> nrOfBooksColumn;
    private final TableColumn<Author, String> genderColumn;
    private final TableColumn<Author, Integer> nrOfBooksSoldColumn;
    private final TextField firstNameTF = new TextField();
    private final Label firstNameLB = new Label("First Name : ");
    private final TextField lastNameTF = new TextField();
    private final Label lastNameLB = new Label("Last Name : ");
    private final TextField middleNameTF = new TextField();
    private final Label middleNameLB = new Label("Middle Name : ");
    private final Label genderLB = new Label("Gender : ");
    private final ComboBox<Gender> gender = new ComboBox<>();
    private final Label searchBarLb = new Label("Search Bar : ");
    private final TextField searchBarTf = new TextField();
    private final Button submitBtn = new Button("");
    private final Button btnDelete = new Button("");
    private final Button btnUpdate = new Button("");
    private final Button returnButton = new Button("");

    public TableViewManagingAuthors() {

        firstNameTF.setId("firstNameTextField");
        lastNameTF.setId("lastNameTextField");
        middleNameTF.setId("middleNameTextField");
        gender.setId("genderComboBox");
        searchBarTf.setId("searchBarTextField");
        submitBtn.setId("submitButton");
        btnDelete.setId("deleteButton");
        btnUpdate.setId("updateButton");
        returnButton.setId("returnButton");

        tableView.setEditable(true);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setMinWidth(130);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setMinWidth(130);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        middleNameColumn = new TableColumn<>("Middle Name");
        middleNameColumn.setMinWidth(130);
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        middleNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        fullNameColumn = new TableColumn<>("Full Name");
        fullNameColumn.setMinWidth(130);
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        fullNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        genderColumn = new TableColumn<>("Gender");
        genderColumn.setMinWidth(130);
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        ObservableList<String> genders = FXCollections.observableArrayList();
        for (Gender gender1 : Gender.values()) {
            genders.add(gender1.toString());
        }
        genderColumn.setCellFactory(ComboBoxTableCell.forTableColumn(genders));

        nrOfBooksColumn = new TableColumn<>("Book's Number");
        nrOfBooksColumn.setMinWidth(130);
        nrOfBooksColumn.setCellValueFactory(new PropertyValueFactory<>("nrOfBooks"));
        nrOfBooksColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        nrOfBooksSoldColumn = new TableColumn<>("Sold Books Number");
        nrOfBooksSoldColumn.setMinWidth(130);
        nrOfBooksSoldColumn.setCellValueFactory(new PropertyValueFactory<>("nrOfBooksSold"));
        nrOfBooksSoldColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        tableView.getColumns().addAll(firstNameColumn, middleNameColumn, lastNameColumn, fullNameColumn, genderColumn, nrOfBooksColumn, nrOfBooksSoldColumn);

        returnButton.setPrefWidth(80);
        btnUpdate.setPrefWidth(80);
        btnDelete.setPrefWidth(80);
        submitBtn.setPrefWidth(100);

        FileInputStream input = null;

        try {
            input = new FileInputStream("images/back.png");
        }catch(java.io.FileNotFoundException e) {
            System.out.println("Check images for bookshop");
        }
        assert input != null;
        ImageView backIcon = new ImageView(new Image(input));
        backIcon.setFitHeight(25);
        backIcon.setFitWidth(25);
        returnButton.setGraphic(backIcon);
        returnButton.setContentDisplay(ContentDisplay.RIGHT);

        try {
            input = new FileInputStream("images/update.png");
        }catch(java.io.FileNotFoundException e) {
            System.out.println("Check images for bookshop");
        }
        ImageView updateIcon = new ImageView(new Image(input));
        updateIcon.setFitHeight(25);
        updateIcon.setFitWidth(25);
        btnUpdate.setGraphic(updateIcon);
        btnUpdate.setContentDisplay(ContentDisplay.RIGHT);

        try {
            input = new FileInputStream("images/delete.png");
        }catch(java.io.FileNotFoundException e) {
            System.out.println("Check images for bookshop");
        }
        ImageView deleteIcon = new ImageView(new Image(input));
        deleteIcon.setFitHeight(25);
        deleteIcon.setFitWidth(25);
        btnDelete.setGraphic(deleteIcon);
        btnDelete.setContentDisplay(ContentDisplay.RIGHT);

        try {
            input = new FileInputStream("images/submit.png");
        }catch(java.io.FileNotFoundException e) {
            System.out.println("Check images for bookshop");
        }
        ImageView submitIcon = new ImageView(new Image(input));
        submitIcon.setFitHeight(25);
        submitIcon.setFitWidth(25);
        submitBtn.setGraphic(submitIcon);
        submitBtn.setContentDisplay(ContentDisplay.RIGHT);

        FlowPane flowPane = new FlowPane(3, 3);
        HBox hBox = new HBox();
        hBox.setSpacing(40);
        hBox.getChildren().addAll(returnButton, btnDelete, btnUpdate);
        flowPane.getChildren().add(hBox);

        this.setCenter(tableView);
        setMargin(tableView, new Insets(0, 180, 0, 0));
        this.setBottom(flowPane);

        GridPane addAuthorPane = setUpViewForAuthorAdding();
        VBox vBox = new VBox();
        vBox.getChildren().add(addAuthorPane);
        vBox.getChildren().add(submitBtn);
        vBox.setSpacing(60);
        vBox.setAlignment(Pos.CENTER);
        this.setLeft(vBox);
        this.setStyle("-fx-font-family: 'Times New Roman' ; -fx-font-size: 13; ");
        setAlignment(flowPane, Pos.CENTER);
        setMargin(flowPane, new Insets(0, 2, 8, 50));

    }

    public Button getReturnButton() {
        return returnButton;
    }

    public TableColumn<Author, String> getFirstNameColumn() {
        return firstNameColumn;
    }

    public TableColumn<Author, String> getMiddleNameColumn() {
        return middleNameColumn;
    }

    public TableColumn<Author, String> getLastNameColumn() {
        return lastNameColumn;
    }

    public TableColumn<Author, Integer> getNrOfBooksColumn() {
        return nrOfBooksColumn;
    }

    public TableColumn<Author, String> getFullNameColumn() {
        return fullNameColumn;
    }

    public TableColumn<Author, String> getGenderColumn() {
        return genderColumn;
    }

    public TableColumn<Author, Integer> getNrOfBooksSoldColumn() {
        return nrOfBooksSoldColumn;
    }

    public TableView<Author> getTableView() {
        return tableView;
    }

    public String getFirstNameTF() {
        return firstNameTF.getText();
    }

    public void setFirstNameTF(String string) {
        firstNameTF.setText(string);
    }

    public String getLastNameTF() {
        return lastNameTF.getText();
    }

    public void setLastNameTF(String string) {
        lastNameTF.setText(string);
    }

    public String getMiddleNameTF() {
        return middleNameTF.getText();
    }

    public void setMiddleNameTF(String string) {
        middleNameTF.setText(string);
    }

    public int getSelectedIndexOfGender() {
        return gender.getSelectionModel().getSelectedIndex();
    }

    public ComboBox<Gender> getGenderComboBox() {
        return gender;
    }

    public void setGenderComboBox() {
        this.gender.getSelectionModel().clearSelection();
    }

    public TextField getSearchBarTf() {
        return searchBarTf;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public Button getBtnUpdate() {
        return btnUpdate;
    }

    public Button getSubmitBtn() {
        return submitBtn;
    }

    private GridPane setUpViewForAuthorAdding() {
        GridPane addAuthorPane = new GridPane();
        addAuthorPane.setAlignment(Pos.CENTER);
        addAuthorPane.setPadding(new Insets(0, 65, 0, 110));
        addAuthorPane.setVgap(10);
        addAuthorPane.setHgap(10);
        //this.setStyle("-fx-border-width: 2 ; -fx-border-color: BLACK");

        gender.getItems().addAll(Gender.values());

        addAuthorPane.add(searchBarLb, 0, 0);
        addAuthorPane.add(searchBarTf, 1, 0);
        addAuthorPane.add(firstNameLB, 0, 10);
        addAuthorPane.add(firstNameTF, 1, 10);
        addAuthorPane.add(middleNameLB, 0, 11);
        addAuthorPane.add(middleNameTF, 1, 11);
        addAuthorPane.add(lastNameLB, 0, 12);
        addAuthorPane.add(lastNameTF, 1, 12);
        addAuthorPane.add(genderLB, 0, 13);
        addAuthorPane.add(gender, 1, 13);

        return addAuthorPane;
    }
}

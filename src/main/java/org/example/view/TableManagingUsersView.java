package org.example.view;

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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.example.model.Gender;
import org.example.model.Role;
import org.example.model.Status;
import org.example.model.UsersOfTheSystem;

import java.io.FileInputStream;
import java.util.Date;

public class TableManagingUsersView extends BorderPane {
    private final TableView<UsersOfTheSystem> tableView = new TableView<>();
    private final TableColumn<UsersOfTheSystem, String> firstNameColumn;
    private final TableColumn<UsersOfTheSystem, String> middleNameColumn;
    private final TableColumn<UsersOfTheSystem, String> lastNameColumn;
    private final TableColumn<UsersOfTheSystem, String> fullNameColumn;
    private final TableColumn<UsersOfTheSystem, String> userIDColumn;
    private final TableColumn<UsersOfTheSystem, String> genderColumn;
    private final TableColumn<UsersOfTheSystem, Integer> nrOfBooksSoldColumn;
    private final TableColumn<UsersOfTheSystem, Double> revenueMadeColumn;
    private final TableColumn<UsersOfTheSystem, String> roleColumn;
    private final TableColumn<UsersOfTheSystem, String> statusColumn;
    private final TableColumn<UsersOfTheSystem, Date> enrollmentDateColumn;
    private final TableColumn<UsersOfTheSystem, Date> birthDateColumn;
    private final TableColumn<UsersOfTheSystem, String> userNameColumn;
    private final TableColumn<UsersOfTheSystem, String> passwordColumn;
    private final TableColumn<UsersOfTheSystem, String> emailColumn;
    private final TableColumn<UsersOfTheSystem, String> phoneNoColumn;
    private final TableColumn<UsersOfTheSystem, Double> salaryColumn;


    private final TextField firstNameTF = new TextField();
    private final Label firstNameLB = new Label("First Name : ");
    private final TextField lastNameTF = new TextField();
    private final Label lastNameLB = new Label("Last Name : ");
    private final TextField middleNameTF = new TextField();
    private final Label middleNameLB = new Label("Middle Name : ");
    private final Label genderLB = new Label("Gender : ");
    private final ComboBox<Gender> gender = new ComboBox<>();
    private final Label roleLB = new Label("Role : ");
    private final ComboBox<Role> role = new ComboBox<>();
    private final TextField birthDateDayTf = new TextField();
    private final Label birthDateDayLb = new Label("Birth Day : ");
    private final TextField birthDateMonthTf = new TextField();
    private final Label birthDateMonthLb = new Label("Birth Month : ");
    private final TextField birthDateYearTf = new TextField();
    private final Label birthDateYearLb = new Label("Birth Year : ");
    private final TextField userNameTf = new TextField();
    private final Label userNameLb = new Label("Username : ");
    private final TextField passwordTf = new TextField();
    private final Label passwordLb = new Label("Password : ");
    private final Label emailLb = new Label("e-Mail : ");
    private final TextField emailTf = new TextField();
    private final Label phoneNoLb = new Label("Phone Number : ");
    private final TextField phoneNoTf = new TextField();
    private final Label salaryLb = new Label("Salary : ");
    private final TextField salaryTf = new TextField();
    private final Label searchBarLb = new Label("Search Bar : ");
    private final TextField searchBarTf = new TextField();
    private final Button submitBtn = new Button("");
    private final Button btnDelete = new Button("");
    private final Button btnUpdate = new Button("");
    private final Button returnButton = new Button("");

    public TableManagingUsersView() {
        tableView.setEditable(true);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        firstNameColumn = new TableColumn<>("First Name");
        firstNameColumn.setMinWidth(85);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        firstNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        lastNameColumn = new TableColumn<>("Last Name");
        lastNameColumn.setMinWidth(85);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        lastNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        middleNameColumn = new TableColumn<>("Middle Name");
        middleNameColumn.setMinWidth(85);
        middleNameColumn.setCellValueFactory(new PropertyValueFactory<>("middleName"));
        middleNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        fullNameColumn = new TableColumn<>("Full Name");
        fullNameColumn.setMinWidth(105);
        fullNameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        fullNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        genderColumn = new TableColumn<>("Gender");
        genderColumn.setMinWidth(85);
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        ObservableList<String> genders = FXCollections.observableArrayList();
        for (Gender gender1 : Gender.values()) {
            genders.add(gender1.toString());
        }
        genderColumn.setCellFactory(ComboBoxTableCell.forTableColumn(genders));

        revenueMadeColumn = new TableColumn<>("Revenue");
        revenueMadeColumn.setMinWidth(85);
        revenueMadeColumn.setCellValueFactory(new PropertyValueFactory<>("revenueMade"));
        revenueMadeColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        nrOfBooksSoldColumn = new TableColumn<>("Sold Books Number");
        nrOfBooksSoldColumn.setMinWidth(85);
        nrOfBooksSoldColumn.setCellValueFactory(new PropertyValueFactory<>("nrOfBooksSold"));
        nrOfBooksSoldColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        roleColumn = new TableColumn<>("Role");
        roleColumn.setMinWidth(85);
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        ObservableList<String> roles = FXCollections.observableArrayList();
        for (Role role1 : Role.values()) {
            roles.add(role1.toString());
        }
        roleColumn.setCellFactory(ComboBoxTableCell.forTableColumn(roles));

        statusColumn = new TableColumn<>("Status");
        statusColumn.setMinWidth(85);
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        ObservableList<String> status = FXCollections.observableArrayList();
        for (Status status1 : Status.values()) {
            status.add(status1.toString());
        }
        statusColumn.setCellFactory(ComboBoxTableCell.forTableColumn(status));

        userIDColumn = new TableColumn<>("User ID");
        userIDColumn.setMinWidth(255);
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        userIDColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        userNameColumn = new TableColumn<>("Username");
        userNameColumn.setMinWidth(85);
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        userNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        passwordColumn = new TableColumn<>("Password");
        passwordColumn.setMinWidth(85);
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        enrollmentDateColumn = new TableColumn<>("Enrollment Date");
        enrollmentDateColumn.setMinWidth(85);
        enrollmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("enrollmentDate"));
        enrollmentDateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));

        birthDateColumn = new TableColumn<>("BirthDate");
        birthDateColumn.setMinWidth(85);
        birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        birthDateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));

        phoneNoColumn = new TableColumn<>("Phone Number");
        phoneNoColumn.setMinWidth(85);
        phoneNoColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));
        phoneNoColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        emailColumn = new TableColumn<>("e-Mail");
        emailColumn.setMinWidth(85);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        salaryColumn = new TableColumn<>("Salary");
        salaryColumn.setMinWidth(85);
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        salaryColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));


        tableView.getColumns().addAll(firstNameColumn, middleNameColumn, lastNameColumn, fullNameColumn, userNameColumn, phoneNoColumn, emailColumn, passwordColumn, userIDColumn, roleColumn, genderColumn, salaryColumn, statusColumn, birthDateColumn, enrollmentDateColumn, nrOfBooksSoldColumn, revenueMadeColumn);

        returnButton.setPrefWidth(80);
        btnDelete.setPrefWidth(80);
        btnUpdate.setPrefWidth(80);
        submitBtn.setPrefWidth(100);

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
        returnButton.setGraphic(backIcon);
        returnButton.setContentDisplay(ContentDisplay.RIGHT);

        try {
            input = new FileInputStream("src/main/images/update.png");
        }catch(java.io.FileNotFoundException e) {
            System.out.println("Check images for bookshop");
        }
        ImageView updateIcon = new ImageView(new Image(input));
        updateIcon.setFitHeight(25);
        updateIcon.setFitWidth(25);
        btnUpdate.setGraphic(updateIcon);
        btnUpdate.setContentDisplay(ContentDisplay.RIGHT);

        try {
            input = new FileInputStream("src/main/images/delete.png");
        }catch(java.io.FileNotFoundException e) {
            System.out.println("Check images for bookshop");
        }
        ImageView deleteIcon = new ImageView(new Image(input));
        deleteIcon.setFitHeight(25);
        deleteIcon.setFitWidth(25);
        btnDelete.setGraphic(deleteIcon);
        btnDelete.setContentDisplay(ContentDisplay.RIGHT);

        try {
            input = new FileInputStream("src/main/images/submit.png");
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
        hBox.setSpacing(27);
        hBox.getChildren().addAll(returnButton, btnDelete, btnUpdate);
        flowPane.getChildren().add(hBox);

        this.setCenter(tableView);
        setMargin(tableView, new Insets(0, 30, 0, 0));
        this.setBottom(flowPane);

        GridPane addUserPane = setUpViewForUserAdding();
        this.setLeft(addUserPane);
        this.setStyle("-fx-font-family: 'Times New Roman' ; -fx-font-size: 13; ");
        setAlignment(flowPane, Pos.CENTER);
        setMargin(flowPane, new Insets(0, 2, 8, 51));

    }

    public TableColumn<UsersOfTheSystem, String> getFirstNameColumn() {
        return firstNameColumn;
    }

    public TableColumn<UsersOfTheSystem, String> getMiddleNameColumn() {
        return middleNameColumn;
    }

    public TableColumn<UsersOfTheSystem, String> getLastNameColumn() {
        return lastNameColumn;
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

    public int getSelectedIndexOfRole() {
        return role.getSelectionModel().getSelectedIndex();
    }

    public ComboBox<Gender> getGenderComboBox() {
        return gender;
    }

    public void setGenderComboBox() {
        this.gender.getSelectionModel().clearSelection();
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

    public TableColumn<UsersOfTheSystem, Date> getEnrollmentDateColumn() {
        return enrollmentDateColumn;
    }

    public TableColumn<UsersOfTheSystem, Date> getBirthDateColumn() {
        return birthDateColumn;
    }

    public TableColumn<UsersOfTheSystem, String> getUserNameColumn() {
        return userNameColumn;
    }

    public TableColumn<UsersOfTheSystem, String> getPasswordColumn() {
        return passwordColumn;
    }

    public TableColumn<UsersOfTheSystem, String> getEmailColumn() {
        return emailColumn;
    }

    public TableColumn<UsersOfTheSystem, String> getPhoneNoColumn() {
        return phoneNoColumn;
    }

    public TableColumn<UsersOfTheSystem, Double> getSalaryColumn() {
        return salaryColumn;
    }


    public ComboBox<Role> getRoleComboBox() {
        return role;
    }

    public void setRoleComboBox() {
        this.role.getSelectionModel().clearSelection();
    }

    public TextField getBirthDateDayTf() {
        return birthDateDayTf;
    }

    public void setBirthDateDayTf() {
        birthDateDayTf.setText("");
    }

    public TextField getBirthDateMonthTf() {
        return birthDateMonthTf;
    }

    public void setBirthDateMonthTf() {
        birthDateMonthTf.setText("");
    }

    public TextField getBirthDateYearTf() {
        return birthDateYearTf;
    }

    public void setBirthDateYearTf() {
        birthDateYearTf.setText("");
    }

    public TextField getUserNameTf() {
        return userNameTf;
    }

    public void setUserNameTf() {
        userNameTf.setText("");
    }

    public TextField getPasswordTf() {
        return passwordTf;
    }

    public void setPasswordTf() {
        passwordTf.setText("");
    }

    public TextField getSalaryTf() {
        return salaryTf;
    }

    public TextField getEmailTf() {
        return emailTf;
    }

    public TextField getPhoneNoTf() {
        return phoneNoTf;
    }

    public void setSalaryTf() {
        salaryTf.setText("");
    }

    public void setEmailTf() {
        emailTf.setText("");
    }

    public void setPhoneNoTf() {
        phoneNoTf.setText("");
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

    public Button getReturnButton() {
        return returnButton;
    }

    private GridPane setUpViewForUserAdding() {
        GridPane addUserPane = new GridPane();
        addUserPane.setAlignment(Pos.CENTER);
        addUserPane.setPadding(new Insets(0, 65, 0, 50));
        addUserPane.setVgap(10);
        addUserPane.setHgap(10);
        //this.setStyle("-fx-border-width: 2 ; -fx-border-color: BLACK");

        gender.getItems().addAll(Gender.values());
        role.getItems().addAll(Role.values());

        addUserPane.add(searchBarLb, 0, 0);
        addUserPane.add(searchBarTf, 1, 0);
        addUserPane.add(firstNameLB, 0, 4);
        addUserPane.add(firstNameTF, 1, 4);
        addUserPane.add(middleNameLB, 0, 5);
        addUserPane.add(middleNameTF, 1, 5);
        addUserPane.add(lastNameLB, 0, 6);
        addUserPane.add(lastNameTF, 1, 6);
        addUserPane.add(birthDateDayLb, 0, 7);
        addUserPane.add(birthDateDayTf, 1, 7);
        addUserPane.add(birthDateMonthLb, 0, 8);
        addUserPane.add(birthDateMonthTf, 1, 8);
        addUserPane.add(birthDateYearLb, 0, 9);
        addUserPane.add(birthDateYearTf, 1, 9);
        addUserPane.add(genderLB, 0, 10);
        addUserPane.add(gender, 1, 10);
        addUserPane.add(roleLB, 0, 11);
        addUserPane.add(role, 1, 11);
        addUserPane.add(salaryLb, 0, 12);
        addUserPane.add(salaryTf, 1, 12);
        addUserPane.add(emailLb, 0, 13);
        addUserPane.add(emailTf, 1, 13);
        addUserPane.add(phoneNoLb, 0, 14);
        addUserPane.add(phoneNoTf, 1, 14);
        addUserPane.add(userNameLb, 0, 15);
        addUserPane.add(userNameTf, 1, 15);
        addUserPane.add(passwordLb, 0, 16);
        addUserPane.add(passwordTf, 1, 16);
        addUserPane.add(submitBtn, 1, 18);
        return addUserPane;
    }
}
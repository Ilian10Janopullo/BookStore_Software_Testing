package org.example.controller;

import org.example.dao.UsersDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.example.model.*;
import org.example.view.TableManagingUsersView;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class ManageUsersController {
    private final TableManagingUsersView view;
    private final UsersDAO usersDAO;
    private final FilteredList<UsersOfTheSystem> filteredUsers;
    private final Stage stage;
    private final UsersOfTheSystem user;
    private final ObservableList<UsersOfTheSystem> users;


    public ManageUsersController(Stage stage, UsersOfTheSystem user) {
        this.user = user;
        this.stage = stage;
        usersDAO = new UsersDAO();
        users = FXCollections.observableArrayList(usersDAO.getAll());
        this.view = new TableManagingUsersView();
        filteredUsers = new FilteredList<>(users, b -> true);
        this.view.getTableView().setItems(usersDAO.getAll());
        this.view.getReturnButton().setOnAction(this::Back);
        this.view.getSubmitBtn().setOnAction(this::Submit);
        this.view.getBtnDelete().setOnAction(this::onUserDelete);
        setEditListeners();
    }

    public boolean checkFirstName(String name) {
        if (!name.matches("[a-zA-Z]{1,25}")) {
            Alert alertForFirstName;
            alertForFirstName = new Alert(Alert.AlertType.ERROR);
            alertForFirstName.setContentText("First name is not correct!");
            alertForFirstName.setTitle("Adding failure!");
            alertForFirstName.show();
            return false;
        }
        return true;
    }

    public boolean checkEMail(String email) {
        if (!email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            Alert alertForEmail;
            alertForEmail = new Alert(Alert.AlertType.ERROR);
            alertForEmail.setContentText("E-mail format is not correct!");
            alertForEmail.setTitle("Adding failure!");
            alertForEmail.show();
            return false;
        }

        for (UsersOfTheSystem user : users) {
            if (email.equals(user.getEmail())) {
                Alert alertForEmail;
                alertForEmail = new Alert(Alert.AlertType.ERROR);
                alertForEmail.setContentText("This e-Mail is already in use!");
                alertForEmail.setTitle("Adding failure!");
                alertForEmail.show();
                return false;
            }
        }
        return true;
    }


    public boolean checkPhoneNumber(String phoneNo) {
        if (!phoneNo.matches("^06[8-9]\\d{7}$")) {
            Alert alertForPhoneNo;
            alertForPhoneNo = new Alert(Alert.AlertType.ERROR);
            alertForPhoneNo.setContentText("Phone number format is not correct!");
            alertForPhoneNo.setTitle("Adding failure!");
            alertForPhoneNo.show();
            return false;
        }
        for (UsersOfTheSystem user : users) {
            if (phoneNo.equals(user.getPhoneNo())) {
                Alert alertForPhoneNo;
                alertForPhoneNo = new Alert(Alert.AlertType.ERROR);
                alertForPhoneNo.setContentText("This phone number is already in use!");
                alertForPhoneNo.setTitle("Adding failure!");
                alertForPhoneNo.show();
                return false;
            }
        }
        return true;
    }

    public boolean checkSalary(String salary) {
        double s = Double.parseDouble(salary);
        if (s < 300 || s > 10000) {
            Alert alertForSalary;
            alertForSalary = new Alert(Alert.AlertType.ERROR);
            alertForSalary.setContentText("Salary is not acceptable");
            alertForSalary.setTitle("Adding failure!");
            alertForSalary.show();
            return false;
        }
        return true;
    }

    public boolean checkMiddleName(String middleName) {
        if (middleName.matches("[a-zA-Z]{1,25}") || middleName.isEmpty()) {
            return true;
        }
        Alert middleNameAlert;
        middleNameAlert = new Alert(Alert.AlertType.ERROR);
        middleNameAlert.setContentText("Middle name is not correct!");
        middleNameAlert.setTitle("Adding failure!");
        middleNameAlert.show();
        return false;
    }

    public boolean checkLastName(String lastName) {
        if (!lastName.matches("[a-zA-Z]{1,25}")) {
            Alert alertForLastName;
            alertForLastName = new Alert(Alert.AlertType.ERROR);
            alertForLastName.setContentText("Last name is not correct!");
            alertForLastName.setTitle("Adding failure!");
            alertForLastName.show();
            return false;
        }
        return true;
    }

    public boolean checkGender(int index) {
        if (index == -1) {
            Alert alertForGender;
            alertForGender = new Alert(Alert.AlertType.ERROR);
            alertForGender.setContentText("Select a gender!");
            alertForGender.setTitle("Adding failure!");
            alertForGender.show();
            return false;
        }
        return true;
    }

    public boolean checkRole(int index) {
        if (index == -1) {
            Alert alertForRole;
            alertForRole = new Alert(Alert.AlertType.ERROR);
            alertForRole.setContentText("Select a role!");
            alertForRole.setTitle("Adding failure!");
            alertForRole.show();
            return false;
        }
        return true;
    }


    public boolean checkUsername(String username) {

        if (!username.matches("[a-z0-9_]{5,25}")) {
            Alert alertForUsername;
            alertForUsername = new Alert(Alert.AlertType.ERROR);
            alertForUsername.setContentText("Invalid username format!");
            alertForUsername.setTitle("Adding failure!");
            alertForUsername.show();
            return false;
        }

        for (UsersOfTheSystem user : users) {
            if (user.getUsername().equals(username)) {
                Alert alertForUsername;
                alertForUsername = new Alert(Alert.AlertType.ERROR);
                alertForUsername.setContentText("This username is already in use!");
                alertForUsername.setTitle("Adding failure!");
                alertForUsername.show();
                return false;
            }
        }
        return true;
    }

    public boolean checkPassword(String password) {
        if (password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")) {
            return true;
        } else {
            Alert alertForPassword;
            alertForPassword = new Alert(Alert.AlertType.ERROR);
            alertForPassword.setContentText("""
                    At least 8 chars

                    Contains at least one digit

                    Contains at least one lower alpha char and one upper alpha char

                    Contains at least one char within a set of special chars (@#%$^ etc.)

                    Does not contain space, tab, etc.!""");
            alertForPassword.setTitle("Adding failure!");
            alertForPassword.show();
            return false;
        }
    }

    public boolean checkBirthdate(int day, int month, int year) {

        Calendar calendar = new GregorianCalendar();
        int currentYear = calendar.get(Calendar.YEAR);

        if (currentYear - year >= 65 || currentYear - year < 16) {
            Alert alertForBirthday;
            alertForBirthday = new Alert(Alert.AlertType.ERROR);
            alertForBirthday.setContentText("This person cannot be registered for work due to his age!");
            alertForBirthday.setTitle("Adding failure!");
            alertForBirthday.show();
            return false;
        }
        if ((month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && (day > 0 && day < 32)) {
            return true;
        } else if ((month == 4 || month == 6 || month == 9 || month == 11) && (day > 0 && day < 31)) {
            return true;
        } else if (month == 2 && (day > 0 && day < 30) && ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)) {
            return true;
        } else if (month == 2 && (day > 0 && day < 29)) {
            return true;
        } else {
            Alert alertForBirthday;
            alertForBirthday = new Alert(Alert.AlertType.ERROR);
            alertForBirthday.setContentText("Wrong format for the birthday!");
            alertForBirthday.setTitle("Adding failure!");
            alertForBirthday.show();
            return false;
        }
    }

    public TableManagingUsersView getView() {
        return view;
    }

    private void setEditListeners() {
        this.view.getFirstNameColumn().setOnEditCommit(e -> {
            if (checkFirstName(e.getNewValue())) {
                UsersOfTheSystem user = usersDAO.getAll().get(e.getTablePosition().getRow());
                user.setFirstName(e.getNewValue());
                user.setFullName();
            } else {
                usersDAO.getAll().get(e.getTablePosition().getRow()).setFirstName(e.getOldValue());
            }
        });
        this.view.getLastNameColumn().setOnEditCommit(e -> {
            if (checkLastName(e.getNewValue())) {
                UsersOfTheSystem user = usersDAO.getAll().get(e.getTablePosition().getRow());
                user.setLastName(e.getNewValue());
                user.setFullName();
            } else {
                usersDAO.getAll().get(e.getTablePosition().getRow()).setLastName(e.getOldValue());
            }
        });

        this.view.getGenderColumn().setOnEditCommit(e -> usersDAO.getAll().get(e.getTablePosition().getRow()).setGender(e.getNewValue()));

        this.view.getRoleColumn().setOnEditCommit(e -> {
            usersDAO.getAll().get(e.getTablePosition().getRow()).setRole(e.getNewValue());
            UsersOfTheSystem userSelected = usersDAO.getAll().get(e.getTablePosition().getRow());

            if (!e.getNewValue().equals(e.getOldValue())) {
                if (!userSelected.getMiddleName().isEmpty()) {
                    if (e.getNewValue().equals(Role.ADMIN.toString())) {
                        UsersOfTheSystem newUser = new Admin(userSelected.getFirstName(), userSelected.getLastName(), userSelected.getBirthdate(), userSelected.getGender(), userSelected.getUsername(), userSelected.getPassword(), userSelected.getRole(), userSelected.getEmail(), userSelected.getPhoneNo(), userSelected.getSalary());
                        newUser.setUserID(userSelected.getUserID());
                        newUser.setEnrollmentDate(userSelected.getEnrollmentDate());
                        newUser.setStatus(userSelected.getStatus().toString());
                        newUser.setRevenueMade(userSelected.getRevenueMade());
                        newUser.setNrOfBooksSold(userSelected.getNrOfBooksSold());

                        users.remove(userSelected);
                        users.add(newUser);
                        usersDAO.delete(userSelected);
                        usersDAO.addToFile(newUser);
                    }
                    if (e.getNewValue().equals(Role.MANAGER.toString())) {
                        UsersOfTheSystem newUser = new Manager(userSelected.getFirstName(), userSelected.getLastName(), userSelected.getBirthdate(), userSelected.getGender(), userSelected.getUsername(), userSelected.getPassword(), userSelected.getRole(), userSelected.getEmail(), userSelected.getPhoneNo(), userSelected.getSalary());
                        newUser.setUserID(userSelected.getUserID());
                        newUser.setEnrollmentDate(userSelected.getEnrollmentDate());
                        newUser.setStatus(userSelected.getStatus().toString());
                        newUser.setRevenueMade(userSelected.getRevenueMade());
                        newUser.setNrOfBooksSold(userSelected.getNrOfBooksSold());

                        users.remove(userSelected);
                        users.add(newUser);
                        usersDAO.delete(userSelected);
                        usersDAO.addToFile(newUser);
                    }
                    if (e.getNewValue().equals(Role.LIBRARIAN.toString())) {
                        UsersOfTheSystem newUser = new Librarian(userSelected.getFirstName(), userSelected.getLastName(), userSelected.getBirthdate(), userSelected.getGender(), userSelected.getUsername(), userSelected.getPassword(), userSelected.getRole(), userSelected.getEmail(), userSelected.getPhoneNo(), userSelected.getSalary());
                        newUser.setUserID(userSelected.getUserID());
                        newUser.setEnrollmentDate(userSelected.getEnrollmentDate());
                        newUser.setStatus(userSelected.getStatus().toString());
                        newUser.setRevenueMade(userSelected.getRevenueMade());
                        newUser.setNrOfBooksSold(userSelected.getNrOfBooksSold());

                        users.remove(userSelected);
                        users.add(newUser);
                        usersDAO.delete(userSelected);
                        usersDAO.addToFile(newUser);
                    }
                } else {
                    if (e.getNewValue().equals(Role.ADMIN.toString())) {
                        UsersOfTheSystem newUser = new Admin(userSelected.getFirstName(), userSelected.getLastName(), userSelected.getMiddleName(), userSelected.getBirthdate(), userSelected.getGender(), userSelected.getUsername(), userSelected.getPassword(), userSelected.getRole(), userSelected.getEmail(), userSelected.getPhoneNo(), userSelected.getSalary());
                        newUser.setUserID(userSelected.getUserID());
                        newUser.setEnrollmentDate(userSelected.getEnrollmentDate());
                        newUser.setStatus(userSelected.getStatus().toString());
                        newUser.setRevenueMade(userSelected.getRevenueMade());
                        newUser.setNrOfBooksSold(userSelected.getNrOfBooksSold());

                        users.remove(userSelected);
                        users.add(newUser);
                        usersDAO.delete(userSelected);
                        usersDAO.addToFile(newUser);
                    }
                    if (e.getNewValue().equals(Role.MANAGER.toString())) {
                        UsersOfTheSystem newUser = new Manager(userSelected.getFirstName(), userSelected.getLastName(), userSelected.getLastName(), userSelected.getBirthdate(), userSelected.getGender(), userSelected.getUsername(), userSelected.getPassword(), userSelected.getRole(), userSelected.getEmail(), userSelected.getPhoneNo(), userSelected.getSalary());
                        newUser.setUserID(userSelected.getUserID());
                        newUser.setEnrollmentDate(userSelected.getEnrollmentDate());
                        newUser.setStatus(userSelected.getStatus().toString());
                        newUser.setRevenueMade(userSelected.getRevenueMade());
                        newUser.setNrOfBooksSold(userSelected.getNrOfBooksSold());

                        users.remove(userSelected);
                        users.add(newUser);
                        usersDAO.delete(userSelected);
                        usersDAO.addToFile(newUser);
                    }
                    if (e.getNewValue().equals(Role.LIBRARIAN.toString())) {
                        UsersOfTheSystem newUser = new Librarian(userSelected.getFirstName(), userSelected.getLastName(), userSelected.getLastName(), userSelected.getBirthdate(), userSelected.getGender(), userSelected.getUsername(), userSelected.getPassword(), userSelected.getRole(), userSelected.getEmail(), userSelected.getPhoneNo(), userSelected.getSalary());
                        newUser.setUserID(userSelected.getUserID());
                        newUser.setEnrollmentDate(userSelected.getEnrollmentDate());
                        newUser.setStatus(userSelected.getStatus().toString());
                        newUser.setRevenueMade(userSelected.getRevenueMade());
                        newUser.setNrOfBooksSold(userSelected.getNrOfBooksSold());

                        users.remove(userSelected);
                        users.add(newUser);
                        usersDAO.delete(userSelected);
                        usersDAO.addToFile(newUser);
                    }
                }
            }
        });

        this.view.getStatusColumn().setOnEditCommit(e -> usersDAO.getAll().get(e.getTablePosition().getRow()).setStatus(e.getNewValue()));

        this.view.getMiddleNameColumn().setOnEditCommit(e -> {

            if (checkMiddleName(e.getNewValue())) {
                UsersOfTheSystem user = usersDAO.getAll().get(e.getTablePosition().getRow());
                user.setMiddleName(e.getNewValue());
                user.setFullName();
            } else {
                usersDAO.getAll().get(e.getTablePosition().getRow()).setMiddleName(e.getOldValue());
            }
        });

        this.view.getNrOfBooksSoldColumn().setEditable(false);

        this.view.getFullNameColumn().setOnEditCommit(e -> {
            usersDAO.getAll().get(e.getTablePosition().getRow()).setFullName();
            Alert alertForFullNameChange;
            alertForFullNameChange = new Alert(Alert.AlertType.ERROR);
            alertForFullNameChange.setContentText("Full name cannot be changed!");
            alertForFullNameChange.setTitle("Updating failure!");
            alertForFullNameChange.show();
        });

        this.view.getUserIDColumn().setEditable(false);

        this.view.getRevenueMadeColumn().setEditable(false);

        this.view.getEnrollmentDateColumn().setEditable(false);

        this.view.getUserNameColumn().setOnEditCommit(e -> {
            if (checkUsername(e.getNewValue())) {
                usersDAO.getAll().get(e.getTablePosition().getRow()).setUsername(e.getNewValue());
            } else {
                usersDAO.getAll().get(e.getTablePosition().getRow()).setUsername(e.getOldValue());
            }
        });

        this.view.getPasswordColumn().setOnEditCommit(e -> {
            if (checkPassword(e.getNewValue())) {
                usersDAO.getAll().get(e.getTablePosition().getRow()).setPassword(e.getNewValue());
            } else {
                usersDAO.getAll().get(e.getTablePosition().getRow()).setPassword(e.getOldValue());
            }
        });

        this.view.getEmailColumn().setOnEditCommit(e -> {
            if (checkEMail(e.getNewValue())) {
                usersDAO.getAll().get(e.getTablePosition().getRow()).setEmail(e.getNewValue());
            } else {
                usersDAO.getAll().get(e.getTablePosition().getRow()).setEmail(e.getOldValue());
            }
        });

        this.view.getSalaryColumn().setOnEditCommit(e -> {
            if (checkSalary((e.getNewValue() + ""))) {
                usersDAO.getAll().get(e.getTablePosition().getRow()).setSalary(e.getNewValue());
            } else {
                usersDAO.getAll().get(e.getTablePosition().getRow()).setSalary(e.getOldValue());
            }
        });

        this.view.getPhoneNoColumn().setOnEditCommit(e -> {
            if (checkPhoneNumber(e.getNewValue())) {
                usersDAO.getAll().get(e.getTablePosition().getRow()).setPhoneNo(e.getNewValue());
            } else {
                usersDAO.getAll().get(e.getTablePosition().getRow()).setPhoneNo(e.getOldValue());
            }
        });


        this.view.getBirthDateColumn().setEditable(false);

        this.view.getSearchBarTf().textProperty().addListener((observable, oldValue, newValue) ->
                filteredUsers.setPredicate(user -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilterEntered = newValue.toLowerCase();
                    if (user.getFullName().toLowerCase().contains(lowerCaseFilterEntered)) {
                        return true;
                    } else if (user.getGender().toString().toLowerCase().contains(lowerCaseFilterEntered)) {
                        return true;
                    } else if ((user.getNrOfBooksSold() + "").toLowerCase().contains(lowerCaseFilterEntered)) {
                        return true;
                    } else if (user.getRole().toString().toLowerCase().contains(lowerCaseFilterEntered)) {
                        return true;
                    } else if (user.getUserID().toLowerCase().contains(lowerCaseFilterEntered)) {
                        return true;
                    } else
                        return user.getStatus().toString().toLowerCase().contains(lowerCaseFilterEntered);
                }));

        this.view.getTableView().setItems(filteredUsers);

        this.view.getBtnUpdate().setOnAction(e -> {
            Alert alert;
            if (this.usersDAO.updateAll()) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Updated successfully!");
            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Update failed!");
            }
            alert.setTitle("Update result");
            alert.show();
        });
    }

    public void onUserDelete(ActionEvent event) {
        ObservableList<UsersOfTheSystem> selectedUsers = this.view.getTableView().getSelectionModel().getSelectedItems();

        Alert alert;
        if (usersDAO.deleteAll(selectedUsers)) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Deleted successfully!");
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Deletion failed!");
        }
        alert.setTitle("Delete result");
        alert.show();
        users.removeAll(selectedUsers);
        //users = FXCollections.observableArrayList(usersDAO.getAll());
        view.getTableView().refresh();
    }

    @SuppressWarnings("MagicConstant")
    public void Submit(ActionEvent event) {

        UsersOfTheSystem newUser;
        boolean stopChecking;
        boolean checkResult;

        String firstName;
        String lastName;
        Gender gender;
        String middleName;
        Role role;
        String username;
        String password;
        String day;
        String month;
        String year;
        String email;
        String phoneNo;
        String salary;

        firstName = view.getFirstNameTF();

        stopChecking = checkFirstName(firstName);
        if (!stopChecking) {
            view.setFirstNameTF("");
        }
        checkResult = stopChecking;

        lastName = view.getLastNameTF();
        stopChecking = checkLastName(lastName);
        if (!stopChecking) {
            view.setLastNameTF("");
        }

        if (!checkResult || !stopChecking) {
            checkResult = false;
        }

        middleName = view.getMiddleNameTF();
        if (middleName.isEmpty()) {
            stopChecking = true;
        } else {
            stopChecking = checkMiddleName(middleName);
        }

        if (!stopChecking) {
            view.setMiddleNameTF("");
        }

        if (!checkResult || !stopChecking) {
            checkResult = false;
        }


        gender = view.getGenderComboBox().getValue();
        int index = view.getSelectedIndexOfGender();
        stopChecking = checkGender(index);

        if (!checkResult || !stopChecking) {
            checkResult = false;
        }

        role = view.getRoleComboBox().getValue();
        int index2 = view.getSelectedIndexOfRole();
        stopChecking = checkRole(index2);

        if (!checkResult || !stopChecking) {
            checkResult = false;
        }


        email = view.getEmailTf().getText();
        stopChecking = checkEMail(email);
        if (!stopChecking) {
            view.setEmailTf();
        }

        if (!checkResult || !stopChecking) {
            checkResult = false;
        }

        salary = view.getSalaryTf().getText();
        stopChecking = checkSalary(salary);
        if (!stopChecking) {
            view.setSalaryTf();
        }

        if (!checkResult || !stopChecking) {
            checkResult = false;
        }

        phoneNo = view.getPhoneNoTf().getText();
        stopChecking = checkPhoneNumber(phoneNo);
        if (!stopChecking) {
            view.setPhoneNoTf();
        }

        if (!checkResult || !stopChecking) {
            checkResult = false;
        }

        username = view.getUserNameTf().getText();
        stopChecking = checkUsername(username);
        if (!stopChecking) {
            view.setUserNameTf();
        }

        if (!checkResult || !stopChecking) {
            checkResult = false;
        }

        password = view.getPasswordTf().getText();
        stopChecking = checkPassword(password);
        if (!stopChecking) {
            view.setPasswordTf();
        }

        if (!checkResult || !stopChecking) {
            checkResult = false;
        }

        day = view.getBirthDateDayTf().getText();
        month = view.getBirthDateMonthTf().getText();
        year = view.getBirthDateYearTf().getText();

        if (day.isEmpty()) {
            stopChecking = false;
            view.setBirthDateDayTf();
            Alert alertForBirthday;
            alertForBirthday = new Alert(Alert.AlertType.ERROR);
            alertForBirthday.setContentText("Date field cannot be empty!");
            alertForBirthday.setTitle("Adding failure!");
            alertForBirthday.show();
        } else if (month.isEmpty()) {
            stopChecking = false;
            view.setBirthDateMonthTf();
            Alert alertForBirthday;
            alertForBirthday = new Alert(Alert.AlertType.ERROR);
            alertForBirthday.setContentText("Month field cannot be empty!");
            alertForBirthday.setTitle("Adding failure!");
            alertForBirthday.show();
        } else if (year.isEmpty()) {
            stopChecking = false;
            view.setBirthDateYearTf();
            Alert alertForBirthday;
            alertForBirthday = new Alert(Alert.AlertType.ERROR);
            alertForBirthday.setContentText("Year field cannot be empty!");
            alertForBirthday.setTitle("Adding failure!");
            alertForBirthday.show();
        } else {
            stopChecking = checkBirthdate(Integer.parseInt(day), Integer.parseInt(month), Integer.parseInt(year));
        }
        if (!stopChecking) {
            view.setBirthDateDayTf();
            view.setBirthDateMonthTf();
            view.setBirthDateYearTf();
        }

        if (!checkResult || !stopChecking) {
            checkResult = false;
        }

        if (checkResult) {

            Calendar calendar = new GregorianCalendar();
            calendar.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));

            if (role == Role.ADMIN) {
                newUser = new Admin(firstName, lastName, calendar.getTime(), gender, username, password, role, email, phoneNo, Double.parseDouble(salary));
            } else if (role == Role.MANAGER) {
                newUser = new Manager(firstName, lastName, calendar.getTime(), gender, username, password, role, email, phoneNo, Double.parseDouble(salary));
            } else {
                newUser = new Librarian(firstName, lastName, calendar.getTime(), gender, username, password, role, email, phoneNo, Double.parseDouble(salary));
            }

            boolean res = usersDAO.addToFile(newUser);
            if (!res) {
                Alert alert;
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("User registering failed!");
                alert.setTitle("Registering result!");
                alert.show();
            } else {
                users.add(newUser);
                this.usersDAO.updateAll();
                view.getTableView().refresh();
                view.setFirstNameTF("");
                view.setMiddleNameTF("");
                view.setLastNameTF("");
                view.setPhoneNoTf();
                view.setSalaryTf();
                view.setEmailTf();
                view.setGenderComboBox();
                view.setUserNameTf();
                view.setPasswordTf();
                view.setRoleComboBox();
                view.setBirthDateDayTf();
                view.setBirthDateMonthTf();
                view.setBirthDateYearTf();
            }
        }
    }

    public void Back(ActionEvent event) {
        if (user.getRole() == Role.ADMIN) {
            AdminMenuController controller = new AdminMenuController(stage, user, true);
            stage.getScene().setRoot(controller.getView());
        }
    }
}
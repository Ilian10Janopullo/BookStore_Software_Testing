package org.example.controller;

import org.example.dao.PermissionsDAO;
import org.example.dao.UsersDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.example.model.Librarian;
import org.example.model.Manager;
import org.example.model.Role;
import org.example.model.UsersOfTheSystem;
import org.example.view.TableViewCheckingUsers;

import java.util.ArrayList;

public class CheckUsersController {
    private final TableViewCheckingUsers view;
    private static ObservableList<String> permissionsCombo;
    private final FilteredList<UsersOfTheSystem> filteredUsers;
    private final Stage stage;
    private final UsersOfTheSystem user;


    public CheckUsersController(Stage stage, UsersOfTheSystem user) {
        this.user = user;
        PermissionsDAO permissionsDAO = new PermissionsDAO();
        permissionsCombo = FXCollections.observableArrayList(permissionsDAO.getAll());
        this.stage = stage;
        UsersDAO usersDAO = new UsersDAO();
        ObservableList<UsersOfTheSystem> users = FXCollections.observableArrayList(usersDAO.getAll());
        ArrayList<UsersOfTheSystem> usersToBeShown = new ArrayList<>();
        if(this.user instanceof Manager){
            for(UsersOfTheSystem us : users){
                if(us instanceof Manager || us instanceof Librarian){
                    usersToBeShown.add(us);
                }
            }
        }

        if(this.user instanceof Librarian){
            for(UsersOfTheSystem us : users){
                if( us instanceof Librarian){
                    usersToBeShown.add(us);
                }
            }
        }

        ObservableList<UsersOfTheSystem> usersToBeShownOnTheTable = FXCollections.observableArrayList(usersToBeShown);

        this.view = new TableViewCheckingUsers();
        filteredUsers = new FilteredList<>(usersToBeShownOnTheTable, b -> true);
        this.view.getTableView().setItems(usersToBeShownOnTheTable);
        this.view.getReturnButton().setOnAction(this::Back);
        setEditListeners();
    }

    public TableViewCheckingUsers getView() {
        return view;
    }

    private void setEditListeners() {

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

        this.view.getResetBtn().setOnAction(e -> this.view.setSearchBarTf());
    }

    public void Back(ActionEvent event) {
        if (user.getRole() == Role.ADMIN) {
            AdminMenuController controller = new AdminMenuController(stage, user, true);
            stage.getScene().setRoot(controller.getView());
        } else if (user.getRole() == Role.MANAGER) {
            backFunction(0);
        } else {
            backFunction(1);
        }
    }

    private void backFunction(int i){
        BackFunctionMenu.mainMenuBack(i, permissionsCombo, stage, user);
    }
}
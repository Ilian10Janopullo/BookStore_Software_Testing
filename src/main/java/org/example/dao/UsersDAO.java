package org.example.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.model.UsersOfTheSystem;

import java.io.File;
import java.util.List;

public class UsersDAO implements dao<UsersOfTheSystem> {
    public static final String FILE_PATH = "src/main/database/users.dat";
    private static final File DATA_FILE = new File(FILE_PATH);
    private static final ObservableList<UsersOfTheSystem> users = FXCollections.observableArrayList();

    public ObservableList<UsersOfTheSystem> getAll() {
        if (users.isEmpty()) {
            loadFromFileImplementation(users, DATA_FILE);
        }
        return users;
    }

    public boolean addToFile(UsersOfTheSystem user) {
        return addToFileImplementation(user, users, DATA_FILE);
    }

    public boolean delete(UsersOfTheSystem userToDelete) {
        return deleteImplementation(userToDelete, users, DATA_FILE);
    }

    public boolean deleteAll(List<UsersOfTheSystem> usersToDelete) {
        return deleteAllImplementation(users, usersToDelete, DATA_FILE);
    }

    public boolean updateAll() {
        return updateAllImplementation(users, DATA_FILE);
    }
}

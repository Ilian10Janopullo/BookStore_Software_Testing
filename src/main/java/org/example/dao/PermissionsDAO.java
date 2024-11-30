package org.example.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;


public class PermissionsDAO {
    public static final String FILE_PATH = "src/main/database/permissions.dat";
    private static final File DATA_FILE = new File(FILE_PATH);
    private final ObservableList<String> permissionsCombo = FXCollections.observableArrayList();

    public ObservableList<String> getAll() {
        if (permissionsCombo.isEmpty()) {
            loadFromFile();
        }
        return permissionsCombo;
    }

    public void loadFromFile() {
        try (DataInputStream reader = new DataInputStream(new FileInputStream(DATA_FILE))) {
            while (reader.available() > 0) {
                permissionsCombo.add(reader.readUTF());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public boolean update(ObservableList<String> list) {

        permissionsCombo.clear();
        permissionsCombo.addAll(list);

        try (DataOutputStream writer = new DataOutputStream(new FileOutputStream(DATA_FILE))) {
            for (String permissionToBeUpdated : permissionsCombo) {
                writer.writeUTF(permissionToBeUpdated);
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
}

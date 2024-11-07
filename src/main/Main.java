package main;


import controller.LoginController;
import dao.AuthorsDAO;
import dao.PermissionsDAO;
import dao.UsersDAO;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main extends Application {
    public static void main(String[] args) {
        seedData();
        Application.launch(args);
    }

    private static void seedData() {
        File fileOfUsers = new File(UsersDAO.FILE_PATH);
        Calendar calendar1 = new GregorianCalendar();
        Calendar calendar2 = new GregorianCalendar();
        Calendar calendar3 = new GregorianCalendar();

        calendar1.set(2003, Calendar.OCTOBER, 2);
        calendar2.set(2001, Calendar.DECEMBER, 28);
        calendar3.set(2005, Calendar.JUNE, 15);

        if (fileOfUsers.length() == 0) {
            UsersOfTheSystem[] users = {
                    new Admin("Ilian", "Janopullo", calendar1.getTime(), Gender.MALE, "ijanopullo22", "1234", Role.ADMIN, "ijanopullo22@gmail.com", "0695383073", 700),
                    new Manager("Arion", "Samarxhiu", calendar2.getTime(), Gender.MALE, "asamarxhiu22", "1245", Role.MANAGER, "asamarxhiu22@yahoo.com", "0687592478", 500),
                    new Librarian("Altea", "Janopullo", calendar3.getTime(), Gender.FEMALE, "ajanopullo23", "2222", Role.LIBRARIAN, "ajanopullo23@gmail.com", "0692564258", 420)
            };
            try (ObjectOutputStream outputStream2 = new ObjectOutputStream(new FileOutputStream(fileOfUsers))) {
                for (UsersOfTheSystem user : users) {
                    outputStream2.writeObject(user);
                }
            } catch (IOException ex) {
                // log
            }
        }

        File fileOfAuthors = new File(AuthorsDAO.FILE_PATH);
        if (fileOfAuthors.length() == 0) {
            Author[] authors = {
                    new Author("Ismail", "Kadare", Gender.MALE),
                    new Author("Naim", "Frasheri", Gender.FEMALE),
            };
            try (ObjectOutputStream outputStream3 = new ObjectOutputStream(new FileOutputStream(fileOfAuthors))) {
                for (Author author : authors) {
                    outputStream3.writeObject(author);
                }
            } catch (IOException ex) {
                // log
            }
        }

        File fileOfPermissions = new File(PermissionsDAO.FILE_PATH);
        if (fileOfPermissions.length() == 0) {
            String[] strings = {
                    "PermissionCombo11Controller",
                    "PermissionCombo2Controller"
            };
            try (DataOutputStream outputStream4 = new DataOutputStream(new FileOutputStream(fileOfPermissions))) {
                for (String string : strings) {
                    outputStream4.writeUTF(string);
                }
            } catch (IOException ex) {
                // log
            }
        }
    }

    @Override
    public void start(Stage stage) {
        LoginController controller = new LoginController(stage);
        Scene scene = new Scene(controller.getView(), 1525, 600);
        stage.setResizable(false);
        stage.setTitle("Bookstore");
        stage.setScene(scene);
        stage.show();
    }
}

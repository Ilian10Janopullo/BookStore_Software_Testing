package controller;

import dao.AuthorsDAO;
import dao.BooksDAO;
import dao.PermissionsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.*;
import view.TableViewManagingAuthors;

public class ManagingAuthorsController {
    private static ObservableList<String> permissionsCombo;
    private final TableViewManagingAuthors view;
    private final AuthorsDAO authorsDAO;
    private final FilteredList<Author> filteredAuthors;
    private final BooksDAO booksDAO;
    private final Stage stage;
    private final UsersOfTheSystem user;
    private ObservableList<Book> books;
    private final ObservableList<Author> authors;

    public ManagingAuthorsController(Stage stage, UsersOfTheSystem user) {
        this.user = user;
        this.stage = stage;
        booksDAO = new BooksDAO();
        authorsDAO = new AuthorsDAO();
        books = FXCollections.observableArrayList(booksDAO.getAll());
        authors = FXCollections.observableArrayList(authorsDAO.getAll());
        PermissionsDAO permissionsDAO = new PermissionsDAO();
        permissionsCombo = FXCollections.observableArrayList(permissionsDAO.getAll());
        this.view = new TableViewManagingAuthors();
        filteredAuthors = new FilteredList<>(authors, b -> true);
        this.view.getTableView().setItems(authorsDAO.getAll());
        this.view.getReturnButton().setOnAction(this::Back);
        this.view.getSubmitBtn().setOnAction(this::Submit);
        this.view.getBtnDelete().setOnAction(this::onAuthorDelete);
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

    public TableViewManagingAuthors getView() {
        return view;
    }

    private void setEditListeners() {
        this.view.getFirstNameColumn().setOnEditCommit(e -> {
            if (checkFirstName(e.getNewValue())) {
                Author author = authorsDAO.getAll().get(e.getTablePosition().getRow());
                author.setFirstName(e.getNewValue());
                author.setFullName();
            } else {
                authorsDAO.getAll().get(e.getTablePosition().getRow()).setFirstName(e.getOldValue());
            }
        });
        this.view.getLastNameColumn().setOnEditCommit(e -> {
            if (checkLastName(e.getNewValue())) {
                Author author = authorsDAO.getAll().get(e.getTablePosition().getRow());
                author.setLastName(e.getNewValue());
                author.setFullName();
            } else {
                authorsDAO.getAll().get(e.getTablePosition().getRow()).setLastName(e.getOldValue());
            }
        });

        this.view.getGenderColumn().setOnEditCommit(e -> authorsDAO.getAll().get(e.getTablePosition().getRow()).setGender(e.getNewValue()));

        this.view.getMiddleNameColumn().setOnEditCommit(e -> {

            if (checkMiddleName(e.getNewValue())) {
                Author author = authorsDAO.getAll().get(e.getTablePosition().getRow());
                author.setMiddleName(e.getNewValue());
                author.setFullName();
            } else {
                authorsDAO.getAll().get(e.getTablePosition().getRow()).setMiddleName(e.getOldValue());
            }
        });

        this.view.getNrOfBooksColumn().setEditable(false);

        this.view.getNrOfBooksSoldColumn().setEditable(false);

        this.view.getFullNameColumn().setOnEditCommit(e -> {
            authorsDAO.getAll().get(e.getTablePosition().getRow()).setFullName();
            Alert alertForFullNameChange;
            alertForFullNameChange = new Alert(Alert.AlertType.ERROR);
            alertForFullNameChange.setContentText("Full name cannot be changed!");
            alertForFullNameChange.setTitle("Updating failure!");
            alertForFullNameChange.show();
        });

        this.view.getSearchBarTf().textProperty().addListener((observable, oldValue, newValue) ->
                filteredAuthors.setPredicate(author -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilterEntered = newValue.toLowerCase();
                    if (author.getFullName().toLowerCase().contains(lowerCaseFilterEntered)) {
                        return true;
                    } else if (author.getGender().toString().toLowerCase().contains(lowerCaseFilterEntered)) {
                        return true;
                    } else
                        return (author.getNrOfBooks() + "").toLowerCase().contains(lowerCaseFilterEntered);
                }));

        this.view.getTableView().setItems(filteredAuthors);

        this.view.getBtnUpdate().setOnAction(e -> {
            Alert alert;
            if (this.authorsDAO.updateAll()) {
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

    public void onAuthorDelete(ActionEvent event) {
        ObservableList<Author> selectedAuthor = this.view.getTableView().getSelectionModel().getSelectedItems();
        ObservableList<Book> booksRelatedToAuthor = FXCollections.observableArrayList();
        for (Author author : selectedAuthor) {
            for (Book book : books) {
                if (book.getAuthor().toString().equals(author.toString())) {
                    booksRelatedToAuthor.add(book);
                }
            }
        }
        Alert alert;
        if (authorsDAO.deleteAll(selectedAuthor)) {
            books.removeAll(booksRelatedToAuthor);
            booksDAO.deleteAll(booksRelatedToAuthor);
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Deleted successfully!");
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Deletion failed!");
        }
        alert.setTitle("Delete result");
        alert.show();
        books = FXCollections.observableArrayList(booksDAO.getAll());
        authors.removeAll(selectedAuthor);

    }

    public void Submit(ActionEvent event) {

        Author newAuthor;
        boolean stopChecking ;
        boolean checkResult ;

        String firstName;
        String lastName;
        Gender gender;
        String middleName;

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


        if (checkResult) {

            if (middleName.isEmpty()) {
                newAuthor = new Author(firstName, lastName, gender);
            } else {
                newAuthor = new Author(firstName, middleName, lastName, gender);
            }

            boolean res = authorsDAO.addToFile(newAuthor);
            if (!res) {
                Alert alert;
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Author registering failed!");
                alert.setTitle("Registering result!");
                alert.show();
            } else {
                authors.add(newAuthor);
                this.authorsDAO.updateAll();
                view.setFirstNameTF("");
                view.setMiddleNameTF("");
                view.setLastNameTF("");
                view.setGenderComboBox();
            }
        }
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
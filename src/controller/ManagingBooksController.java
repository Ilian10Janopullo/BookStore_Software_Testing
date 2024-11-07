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
import view.TableViewManagingBooks;

import java.util.ArrayList;

public class ManagingBooksController {
    private static ObservableList<String> permissionsCombo;
    private final TableViewManagingBooks view;
    private final AuthorsDAO authorsDAO;
    private final FilteredList<Book> filteredBooks;
    private final Stage stage;
    private final UsersOfTheSystem user;
    private final BooksDAO booksDAO;
    private final ObservableList<Book> books;
    private final ObservableList<Author> authors;

    public ManagingBooksController(Stage stage, UsersOfTheSystem user) {
        this.user = user;
        this.stage = stage;
        this.view = new TableViewManagingBooks();
        PermissionsDAO permissionsDAO = new PermissionsDAO();
        permissionsCombo = FXCollections.observableArrayList(permissionsDAO.getAll());
        booksDAO = new BooksDAO();
        authorsDAO = new AuthorsDAO();
        books = FXCollections.observableArrayList(booksDAO.getAll());
        authors = FXCollections.observableArrayList(authorsDAO.getAll());
        filteredBooks = new FilteredList<>(books, b -> true);
        this.view.getTableView().setItems(booksDAO.getAll());
        this.view.getReturnButton().setOnAction(this::Back);
        this.view.getSubmitBtn().setOnAction(this::Submit);
        this.view.getBtnDelete().setOnAction(this::onBookDelete);
        setEditListeners();
    }

    public static boolean CheckISBN10(String isbn) {
        int sum = 0;
        String dStr;
        for (int i = 0; i < 10; i++) {
            dStr = isbn.substring(i, i + 1);
            if (i < 9 || ("X".equals(dStr))) {
                sum += Integer.parseInt(dStr) * (10 - i);
            } else {
                sum += 10;
            }
        }
        return (sum % 11 == 0);
    }


    public static boolean CheckISBN13(String isbn) {
        if (isbn == null || isbn.length() != 13 || !isbn.matches("[0-9]+")) {
            return false;
        }

        int sum = 0;
        for (int i = 0; i < 13; i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }

        int checkDigit = Character.getNumericValue(isbn.charAt(12));
        int calculatedCheckDigit = (10 - (sum % 10)) % 10;

        return checkDigit == calculatedCheckDigit;
    }


    public static boolean checkTitle(String title) {
        if (!title.matches("^[A-Za-z0-9\\s\\-.,:;'\"()!?&]+$")) {
            Alert alertForTitle;
            alertForTitle = new Alert(Alert.AlertType.ERROR);
            alertForTitle.setContentText("Title is not correct!");
            alertForTitle.setTitle("Adding failure!");
            alertForTitle.show();
            return false;
        }
        return true;
    }

    public static boolean checkIsbn(String isbn, ObservableList<Book> books) {

        boolean checkForValidations;
        boolean checkIsbnForUniqueness = true;

        String isbnChecker = isbn.replaceAll("([ \\-])", "");
        if (isbnChecker.length() == 10) {
            checkForValidations = CheckISBN10(isbnChecker);
        } else if (isbnChecker.length() == 13) {
            checkForValidations = CheckISBN13(isbnChecker);
        } else {
            checkForValidations = false;
        }

        for (Book book : books) {
            String isbnCheckerForPreviousBooks = book.getIsbn().replaceAll("([ \\-])", "");
            if (isbnCheckerForPreviousBooks.equals(isbnChecker)) {
                checkIsbnForUniqueness = false;
                break;
            }
        }

        if (checkForValidations || !checkIsbnForUniqueness || isbn.isEmpty()) {
            Alert alertForIsbn;
            alertForIsbn = new Alert(Alert.AlertType.ERROR);
            alertForIsbn.setContentText("ISBN is not correct or is not unique!");
            alertForIsbn.setTitle("Adding failure!");
            alertForIsbn.show();
            return false;
        }
        return true;
    }

    public static boolean checkPrice(String price1) {
        double price;
        try {
            price = Double.parseDouble(price1);
            if (price < 0 || price > 9999) {
                Alert alertForPrice;
                alertForPrice = new Alert(Alert.AlertType.ERROR);
                alertForPrice.setContentText("Price is not correct!");
                alertForPrice.setTitle("Adding failure!");
                alertForPrice.show();

                return false;
            }
            return true;
        } catch (NumberFormatException ex) {
            Alert alertForPrice;
            alertForPrice = new Alert(Alert.AlertType.ERROR);
            alertForPrice.setContentText("Price is not correct!");
            alertForPrice.setTitle("Adding failure!");
            alertForPrice.show();
            return false;
        }
    }

    public static boolean checkDescription(String description) {
        if (description.isEmpty()) {
            Alert alertForDescription;
            alertForDescription = new Alert(Alert.AlertType.ERROR);
            alertForDescription.setContentText("Description field is empty!");
            alertForDescription.setTitle("Adding failure!");
            alertForDescription.show();
            return false;
        }
        return true;
    }

    private static boolean checkForAuthor(int index) {
        if (index == -1) {
            Alert alertForAuthors;
            alertForAuthors = new Alert(Alert.AlertType.ERROR);
            alertForAuthors.setContentText("No author selected!");
            alertForAuthors.setTitle("Adding failure!");
            alertForAuthors.show();
            return false;
        }
        return true;
    }

    private static boolean checkPaperBook(boolean isPaperback, boolean isEBook) {
        if (!isPaperback && !isEBook) {
            Alert alertForRadioSelection;
            alertForRadioSelection = new Alert(Alert.AlertType.ERROR);
            alertForRadioSelection.setContentText("Choose between paperback and e-book!");
            alertForRadioSelection.setTitle("Adding failure!");
            alertForRadioSelection.show();
            return false;
        }
        return true;
    }

    public static boolean checkGenres(int counter) {
        if (counter == 0) {
            Alert alertForGenreSelection;
            alertForGenreSelection = new Alert(Alert.AlertType.ERROR);
            alertForGenreSelection.setContentText("No genre selected!");
            alertForGenreSelection.setTitle("Adding failure!");
            alertForGenreSelection.show();
            return false;
        }
        return true;
    }

    public static boolean checkQuantity(String quantityString) {
        try {
            int quantity = Integer.parseInt(quantityString);
            if (quantity <= 0) {
                Alert alertForQuantity;
                alertForQuantity = new Alert(Alert.AlertType.ERROR);
                alertForQuantity.setContentText("Quantity error!");
                alertForQuantity.setTitle("Adding failure!");
                alertForQuantity.show();
                return false;
            }
            return true;
        } catch (NumberFormatException ex) {
            Alert alertForQuantity;
            alertForQuantity = new Alert(Alert.AlertType.ERROR);
            alertForQuantity.setContentText("Quantity error!");
            alertForQuantity.setTitle("Adding failure!");
            alertForQuantity.show();
            return false;
        }
    }

    public TableViewManagingBooks getView() {
        return view;
    }

    private void setEditListeners() {
        this.view.getTitleColumn().setOnEditCommit(e -> {
            if (checkTitle(e.getNewValue())) {
                booksDAO.getAll().get(e.getTablePosition().getRow()).setTitle(e.getNewValue());
            } else {
                booksDAO.getAll().get(e.getTablePosition().getRow()).setTitle(e.getOldValue());
            }
        });
        this.view.getIsbnColumn().setOnEditCommit(e -> {
            if (checkIsbn(e.getNewValue(), books)) {
                booksDAO.getAll().get(e.getTablePosition().getRow()).setIsbn(e.getNewValue());
            } else {
                booksDAO.getAll().get(e.getTablePosition().getRow()).setIsbn(e.getOldValue());
            }
        });

        this.view.getAuthorColumn().setOnEditCommit(e -> {
            Book book = booksDAO.getAll().get(e.getTablePosition().getRow());
            Author authorFrom = book.getAuthor();
            book.setAuthor(e.getNewValue());
            Author authorTo = book.getAuthor();
            for (Author author : authors) {
                if (author.equals(authorFrom)) {
                    author.setNrOfBooks(author.getNrOfBooks() - 1);
                }
                if (author.equals(authorTo)) {
                    author.setNrOfBooks(author.getNrOfBooks() + 1);
                }
            }
            authorsDAO.updateAll();
        });

        this.view.getPriceColumn().setOnEditCommit(e -> {
            if (checkPrice((e.getNewValue()) + "")) {
                booksDAO.getAll().get(e.getTablePosition().getRow()).setPrice(e.getNewValue());
            } else {
                booksDAO.getAll().get(e.getTablePosition().getRow()).setPrice(e.getOldValue());
            }
        });

        this.view.getQuantityColumn().setOnEditCommit(e -> {
            if (checkQuantity((e.getNewValue()) + "")) {
                booksDAO.getAll().get(e.getTablePosition().getRow()).setQuantity(e.getNewValue());
            } else {
                booksDAO.getAll().get(e.getTablePosition().getRow()).setQuantity(e.getOldValue());
            }
        });

        this.view.getDescriptionColumn().setOnEditCommit(e -> {
            if (checkDescription(e.getNewValue())) {
                booksDAO.getAll().get(e.getTablePosition().getRow()).setDescription(e.getNewValue());
            } else {
                booksDAO.getAll().get(e.getTablePosition().getRow()).setDescription(e.getOldValue());
            }
        });

        this.view.getPaperbackColumn().setOnEditCommit(e -> booksDAO.getAll().get(e.getTablePosition().getRow()).setPaperback(e.getNewValue()));

        this.view.getGenreColumn().setOnEditCommit(e -> booksDAO.getAll().get(e.getTablePosition().getRow()).setGenre(e.getNewValue()));

        this.view.getCopiesSoldColumn().setEditable(false);

        this.view.getSearchBarTf().textProperty().addListener((observable, oldValue, newValue) ->
                filteredBooks.setPredicate(book -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilterEntered = newValue.toLowerCase();
                    if (book.getTitle().toLowerCase().contains(lowerCaseFilterEntered)) {
                        return true;
                    } else if (book.getIsbn().toLowerCase().contains(lowerCaseFilterEntered)) {
                        return true;
                    } else if (book.getGenres().toString().toLowerCase().contains(lowerCaseFilterEntered)) {
                        return true;
                    } else if ((book.getPrice() + "").contains(lowerCaseFilterEntered)) {
                        return true;
                    } else {
                        return book.getAuthor().toString().toLowerCase().contains(lowerCaseFilterEntered);
                    }
                }));
        this.view.getTableView().setItems(filteredBooks);


        this.view.getBtnUpdate().setOnAction(e -> {
            Alert alert;
            if (this.booksDAO.updateAll()) {
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

    public void onBookDelete(ActionEvent event) {

        ObservableList<Book> selectedBooks = this.view.getTableView().getSelectionModel().getSelectedItems();

        ArrayList<Author> authorsRelatedToBook = new ArrayList<>();
        for (Book book : selectedBooks) {
            authorsRelatedToBook.add(book.getAuthor());
        }


        Alert alert;
        if (booksDAO.deleteAll(selectedBooks)) {
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Deleted successfully!");
            for (Author author : authors) {
                for (Author author1 : authorsRelatedToBook) {
                    if (author.equals(author1)) {
                        author.setNrOfBooks(author.getNrOfBooks() - 1);
                    }
                }
            }
            books.removeAll(selectedBooks);
            authorsDAO.updateAll();
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Deletion failed!");
        }
        alert.setTitle("Delete result");
        alert.show();
    }

    public void Submit(ActionEvent event) {

        try {
            Book newBook;
            boolean stopChecking;
            boolean checkResult;

            String isbn;
            double price;
            Author author;
            boolean isPaperback;
            String title;
            String description;
            int quantity;


            isbn = view.getIsbnTF();

            stopChecking = checkIsbn(isbn, books);
            if (!stopChecking) {
                view.setIsbnTF("");
            }
            checkResult = stopChecking;

            title = view.getTitleTF();
            stopChecking = checkTitle(title);
            if (!stopChecking) {
                view.setTitleTF("");
            }

            if (!checkResult || !stopChecking) {
                checkResult = false;
            }

            String priceInString = view.getPriceTF();
            stopChecking = checkPrice(priceInString);
            price = Double.parseDouble(priceInString);
            if (!stopChecking) {
                view.setPriceTF("");
            }

            if (!checkResult || !stopChecking) {
                checkResult = false;
            }


            description = view.getDescriptionTA();
            stopChecking = checkDescription(description);

            if (!checkResult || !stopChecking) {
                checkResult = false;
            }

            author = view.getAuthorComboBox().getValue();
            int index = view.getSelectedIndexOfAuthor();
            stopChecking = checkForAuthor(index);

            if (!checkResult || !stopChecking) {
                checkResult = false;
            }

            isPaperback = view.getRbPaperback().isSelected();
            boolean isEBook = view.getRbEbook().isSelected();
            stopChecking = checkPaperBook(isPaperback, isEBook);

            if (!checkResult || !stopChecking) {
                checkResult = false;
            }


            int counter = view.getCheckComboBox().getCheckModel().getCheckedItems().size();
            stopChecking = checkGenres(counter);

            if (!checkResult || !stopChecking) {
                checkResult = false;
            }

            String quantityString = view.getQuantityTf();
            stopChecking = checkQuantity(quantityString);
            quantity = Integer.parseInt(quantityString);
            if (!stopChecking) {
                view.setPriceTF("");
            }

            if (!checkResult || !stopChecking) {
                checkResult = false;
            }

            if (checkResult) {
                newBook = new Book(isbn, title, description, price, author, isPaperback, quantity);

                for (int i = 0; i < view.getCheckComboBox().getCheckModel().getItemCount(); i++) {
                    if (view.getCheckComboBox().getCheckModel().isChecked(i))
                        newBook.addGenre(Genre.values()[i]);
                }

                boolean res = booksDAO.addToFile(newBook);
                if (!res) {
                    Alert alert;
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Book registering failed!");
                    alert.setTitle("Registering result!");
                    alert.show();
                } else {
                    books.add(newBook);
                    authors.get(index).setNrOfBooks(authors.get(index).getNrOfBooks() + 1);
                    authorsDAO.updateAll();
                    view.setIsbnTF("");
                    view.setTitleTF("");
                    view.setPriceTF("");
                    view.setDescriptionTA("");
                    view.setRbEbook();
                    view.setRbPaperback();
                    view.setAuthorComboBox();
                    view.setQuantityTf("");
                    view.setCheckComboBox();
                }
            }
        } catch (IndexOutOfBoundsException ex){
            ex.fillInStackTrace();
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
package org.example.view;

import org.example.dao.AuthorsDAO;
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
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.example.model.Author;
import org.example.model.Book;
import org.example.model.Genre;
import org.controlsfx.control.CheckComboBox;

import java.io.FileInputStream;

public class TableViewManagingBooks extends BorderPane {

    final ObservableList<String> genres = FXCollections.observableArrayList();
    final CheckComboBox<String> checkComboBox = new CheckComboBox<>(genres);
    private final TableView<Book> tableView = new TableView<>();
    private final TableColumn<Book, String> titleColumn;
    private final TableColumn<Book, String> isbnColumn;
    private final TableColumn<Book, String> descriptionColumn;
    private final TableColumn<Book, Double> priceColumn;
    private final TableColumn<Book, String> authorColumn;
    private final TableColumn<Book, String> genreColumn;
    private final TableColumn<Book, Integer> quantityColumn;
    private final TableColumn<Book, Boolean> paperbackColumn;
    private final TableColumn<Book, Integer> copiesSoldColumn;
    private final AuthorsDAO authorsDAO;
    private final TextField titleTF = new TextField();
    private final Label titleLbl = new Label("Title");
    private final TextField isbnTF = new TextField();
    private final Label isbnLbl = new Label("ISBN");
    private final TextField priceTF = new TextField();
    private final Label priceLbl = new Label("Price");
    private final Label versionLbl = new Label("Version");
    private final RadioButton rbPaperback = new RadioButton("Paperback");
    private final RadioButton rbEbook = new RadioButton("E-book");
    private final Label descriptionLbl = new Label("Description");
    private final TextArea descriptionTA = new TextArea();
    private final Label authorsLbl = new Label("Select an author: ");
    private final ComboBox<Author> authorComboBox = new ComboBox<>();
    private final Label genreLbl = new Label("Genres: ");
    private final Label searchBarLb = new Label("Search Bar : ");
    private final TextField searchBarTf = new TextField();

    private final Label quantityLbl = new Label("Quantity: ");
    private final TextField quantityTf = new TextField();

    private final Button submitBtn = new Button("");
    private final Button btnDelete = new Button("");
    private final Button btnUpdate = new Button("");
    private final Button returnButton = new Button("");


    public TableViewManagingBooks() {
        authorsDAO = new AuthorsDAO();
        tableView.setEditable(true);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        titleColumn = new TableColumn<>("Title");
        titleColumn.setMinWidth(100);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setMinWidth(100);
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        isbnColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setMinWidth(100);
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());

        paperbackColumn = new TableColumn<>("Is paperback?");
        paperbackColumn.setMinWidth(100);
        paperbackColumn.setCellValueFactory(new PropertyValueFactory<>("paperback"));
        paperbackColumn.setCellFactory(ComboBoxTableCell.forTableColumn(true, false));

        priceColumn = new TableColumn<>("Price");
        priceColumn.setMinWidth(100);
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

        quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setMinWidth(100);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        authorColumn = new TableColumn<>("Author");
        authorColumn.setMinWidth(100);
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        ObservableList<Author> authors = FXCollections.observableArrayList(authorsDAO.getAll());
        ObservableList<String> authorsFullName = FXCollections.observableArrayList();
        for (Author author : authors) {
            authorsFullName.add(author.toString());
        }
        authorColumn.setCellFactory(ComboBoxTableCell.forTableColumn(authorsFullName));

        genreColumn = new TableColumn<>("Genres");
        genreColumn.setMinWidth(300);
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genres"));
        genreColumn.setCellFactory(ComboBoxTableCell.forTableColumn(genres));

        copiesSoldColumn = new TableColumn<>("Copies Sold");
        copiesSoldColumn.setMinWidth(100);
        copiesSoldColumn.setCellValueFactory(new PropertyValueFactory<>("copiesSold"));
        copiesSoldColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));


        tableView.getColumns().addAll(titleColumn, isbnColumn, descriptionColumn, genreColumn, authorColumn, paperbackColumn, priceColumn, quantityColumn, copiesSoldColumn);

        returnButton.setPrefWidth(80);
        btnUpdate.setPrefWidth(80);
        btnDelete.setPrefWidth(80);
        submitBtn.setPrefWidth(150);

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
        hBox.setSpacing(35);
        hBox.getChildren().addAll(returnButton, btnDelete, btnUpdate);
        flowPane.getChildren().add(hBox);

        this.setCenter(tableView);
        setMargin(tableView, new Insets(0, 12, 0, 0));
        this.setBottom(flowPane);


        GridPane addBookPane = setUpView();
        this.setLeft(addBookPane);
        this.setStyle("-fx-font-family: 'Times New Roman' ; -fx-font-size: 13; ");
        setAlignment(flowPane, Pos.CENTER);
        setMargin(flowPane, new Insets(6, 12, 12, 25));
    }

    public TableColumn<Book, String> getTitleColumn() {
        return titleColumn;
    }

    public TableColumn<Book, String> getIsbnColumn() {
        return isbnColumn;
    }

    public TableColumn<Book, String> getDescriptionColumn() {
        return descriptionColumn;
    }

    public TableColumn<Book, Double> getPriceColumn() {
        return priceColumn;
    }

    public TableColumn<Book, String> getAuthorColumn() {
        return authorColumn;
    }

    public TableColumn<Book, Integer> getQuantityColumn() {
        return quantityColumn;
    }

    public TableColumn<Book, Boolean> getPaperbackColumn() {
        return paperbackColumn;
    }

    public TableColumn<Book, String> getGenreColumn() {
        return genreColumn;
    }

    public TableColumn<Book, Integer> getCopiesSoldColumn() {
        return copiesSoldColumn;
    }

    public TableView<Book> getTableView() {
        return tableView;
    }

    public Button getBtnDelete() {
        return btnDelete;
    }

    public Button getBtnUpdate() {
        return btnUpdate;
    }

    public Button getReturnButton() {
        return returnButton;
    }

    public String getQuantityTf() {
        return quantityTf.getText();
    }

    public void setQuantityTf(String string) {
        quantityTf.setText(string);
    }

    public int getSelectedIndexOfAuthor() {
        return authorComboBox.getSelectionModel().getSelectedIndex();
    }

    public void setRbPaperback() {
        this.rbPaperback.setSelected(false);
    }

    public void setRbEbook() {
        this.rbEbook.setSelected(false);
    }

    public void setAuthorComboBox() {
        this.authorComboBox.getSelectionModel().clearSelection();
    }

    public void setCheckComboBox() {
        this.checkComboBox.getCheckModel().clearChecks();
    }

    public String getTitleTF() {
        return titleTF.getText();
    }

    public void setTitleTF(String string) {
        this.titleTF.setText(string);
    }

    public String getIsbnTF() {
        return isbnTF.getText();
    }

    public void setIsbnTF(String string) {
        this.isbnTF.setText(string);
    }

    public String getPriceTF() {
        return priceTF.getText();
    }

    public void setPriceTF(String string) {
        this.priceTF.setText(string);
    }

    public RadioButton getRbPaperback() {
        return rbPaperback;
    }

    public RadioButton getRbEbook() {
        return rbEbook;
    }

    public String getDescriptionTA() {
        return descriptionTA.getText();
    }

    public void setDescriptionTA(String string) {
        this.descriptionTA.setText(string);
    }

    public TextField getSearchBarTf() {
        return searchBarTf;
    }

    public ComboBox<Author> getAuthorComboBox() {
        return authorComboBox;
    }

    public CheckComboBox<String> getCheckComboBox() {
        return checkComboBox;
    }

    public Button getSubmitBtn() {
        return submitBtn;
    }

    private GridPane setUpView() {
        GridPane addBookPane = new GridPane();
        addBookPane.setAlignment(Pos.CENTER);
        addBookPane.setPadding(new Insets(0, 10, 0, 10));
        addBookPane.setVgap(10);
        addBookPane.setHgap(10);
        //this.setStyle("-fx-border-width: 2 ; -fx-border-color: BLACK");

        ToggleGroup group = new ToggleGroup();
        rbPaperback.setToggleGroup(group);
        rbEbook.setToggleGroup(group);
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(rbPaperback, rbEbook);


        descriptionTA.setPrefColumnCount(20);
        descriptionTA.setPrefRowCount(5);
        descriptionTA.setWrapText(true);

        ObservableList<Author> authors = FXCollections.observableArrayList(authorsDAO.getAll());
        authorComboBox.getItems().addAll(authors);

        // genres

        for (Genre g : Genre.values()) {
            genres.add(g.toString());
        }

        checkComboBox.getCheckModel().getCheckedItems();

        addBookPane.add(searchBarLb, 0, 0);
        addBookPane.add(searchBarTf, 1, 0);
        addBookPane.add(titleLbl, 0, 5);
        addBookPane.add(titleTF, 1, 5);
        addBookPane.add(isbnLbl, 0, 6);
        addBookPane.add(isbnTF, 1, 6);
        addBookPane.add(priceLbl, 0, 7);
        addBookPane.add(priceTF, 1, 7);
        addBookPane.add(quantityLbl, 0, 8);
        addBookPane.add(quantityTf, 1, 8);
        addBookPane.add(versionLbl, 0, 9);
        addBookPane.add(hbox, 1, 9);
        addBookPane.add(descriptionLbl, 0, 10);
        addBookPane.add(descriptionTA, 1, 10);
        addBookPane.add(authorsLbl, 0, 11);
        addBookPane.add(authorComboBox, 1, 11);
        addBookPane.add(genreLbl, 0, 12);
        addBookPane.add(checkComboBox, 1, 12);
        addBookPane.add(submitBtn, 1, 15);
        return addBookPane;
    }
}
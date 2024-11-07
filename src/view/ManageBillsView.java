package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.converter.IntegerStringConverter;
import model.Bill;
import model.BooksOrdered;

import java.io.FileInputStream;
import java.util.Date;
import java.util.GregorianCalendar;

public class ManageBillsView extends BorderPane {

    private final TableView<Bill> tableViewOfBills = new TableView<>();
    private final TableColumn<Bill, String> idBillColumn;
    private final TableColumn<Bill, String> idUserColumn;
    private final TableColumn<Bill, Date> dateBillColumn;
    private final TableColumn<Bill, Double> totalAmountColumn;

    private final TableView<BooksOrdered> tableViewOfBooksOrdered = new TableView<>();
    private final TableColumn<BooksOrdered, String> titleOfOrderedBooksColumn;
    private final TableColumn<BooksOrdered, String> isbnOfOrderedBooksColumn;
    private final TableColumn<BooksOrdered, Double> priceOfOrderedBooksColumn;
    private final TableColumn<BooksOrdered, Integer> quantityToOrderColumn;


    private final Button btnUpdate = new Button("");
    private final Button btnRemove = new Button("");
    private final Button returnButton = new Button("");
    private final Button showAllButton = new Button("");
    private final TextField searchBarTf = new TextField();
    private final Label revenueLb2 = new Label();

    private final Label fromDateLb = new Label("Starting Date : ");
    private final DatePicker fromDate = new DatePicker();
    private final DatePicker toDate = new DatePicker();
    private final Button searchButton2 = new Button("");

    public ManageBillsView() {

        tableViewOfBills.setEditable(true);
        tableViewOfBills.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        idBillColumn = new TableColumn<>("Bill ID");
        idBillColumn.setMinWidth(140);
        idBillColumn.setCellValueFactory(new PropertyValueFactory<>("billID"));

        idUserColumn = new TableColumn<>("Seller ID");
        idUserColumn.setMinWidth(140);
        idUserColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));

        dateBillColumn = new TableColumn<>("Date");
        dateBillColumn.setMinWidth(140);
        dateBillColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

        totalAmountColumn = new TableColumn<>("Total");
        totalAmountColumn.setMinWidth(140);
        totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        tableViewOfBooksOrdered.setEditable(true);
        tableViewOfBooksOrdered.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        titleOfOrderedBooksColumn = new TableColumn<>("Title");
        titleOfOrderedBooksColumn.setMinWidth(115);
        titleOfOrderedBooksColumn.setCellValueFactory(new PropertyValueFactory<>("titleOfBookOrdered"));

        isbnOfOrderedBooksColumn = new TableColumn<>("ISBN");
        isbnOfOrderedBooksColumn.setMinWidth(115);
        isbnOfOrderedBooksColumn.setCellValueFactory(new PropertyValueFactory<>("isbnOfBookOrdered"));


        priceOfOrderedBooksColumn = new TableColumn<>("Price");
        priceOfOrderedBooksColumn.setMinWidth(115);
        priceOfOrderedBooksColumn.setCellValueFactory(new PropertyValueFactory<>("priceOfBookOrdered"));

        quantityToOrderColumn = new TableColumn<>("Quantity Ordered");
        quantityToOrderColumn.setMinWidth(115);
        quantityToOrderColumn.setCellValueFactory(new PropertyValueFactory<>("quantityToOrderOfBookOrdered"));
        quantityToOrderColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));


        tableViewOfBills.getColumns().addAll(idBillColumn, idUserColumn, dateBillColumn, totalAmountColumn);
        tableViewOfBooksOrdered.getColumns().addAll(titleOfOrderedBooksColumn, isbnOfOrderedBooksColumn, priceOfOrderedBooksColumn, quantityToOrderColumn);

        returnButton.setPrefWidth(100);
        btnUpdate.setPrefWidth(100);
        btnRemove.setPrefWidth(100);
        showAllButton.setPrefWidth(100);
        searchButton2.setPrefWidth(100);

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
        btnRemove.setGraphic(deleteIcon);
        btnRemove.setContentDisplay(ContentDisplay.RIGHT);

        try {
            input = new FileInputStream("images/refresh-button.png");
        }catch(java.io.FileNotFoundException e) {
            System.out.println("Check images for bookshop");
        }
        ImageView refreshIcon = new ImageView(new Image(input));
        refreshIcon.setFitHeight(25);
        refreshIcon.setFitWidth(25);
        showAllButton.setGraphic(refreshIcon);
        showAllButton.setContentDisplay(ContentDisplay.RIGHT);

        try {
            input = new FileInputStream("images/loupe.png");
        }catch(java.io.FileNotFoundException e) {
            System.out.println("Check images for bookshop");
        }
        ImageView loupeIcon = new ImageView(new Image(input));
        loupeIcon.setFitHeight(25);
        loupeIcon.setFitWidth(25);
        searchButton2.setGraphic(loupeIcon);
        searchButton2.setContentDisplay(ContentDisplay.RIGHT);

        this.setStyle("-fx-font-family: 'Times New Roman' ; -fx-font-size: 14;");

        GridPane gridPane = new GridPane();
        Label revenueLb = new Label("Revenue : ");
        gridPane.add(revenueLb, 0, 0);
        gridPane.add(revenueLb2, 1, 0);
        gridPane.add(fromDateLb, 0, 1);
        gridPane.add(fromDate, 1, 1);
        Label toDateLb = new Label("Ending Date : ");
        gridPane.add(toDateLb, 0, 2);
        gridPane.add(toDate, 1, 2);
        gridPane.setVgap(35);
        gridPane.setHgap(12);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(gridPane, searchButton2, showAllButton, btnRemove, btnUpdate, returnButton);
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(30);


        HBox hBox1 = new HBox();
        hBox1.setSpacing(50);
        hBox1.setAlignment(Pos.CENTER);
        hBox1.getChildren().addAll(tableViewOfBills, tableViewOfBooksOrdered);


        FlowPane flowPane2 = new FlowPane(2, 2);
        HBox hBox2 = new HBox();
        hBox2.setAlignment(Pos.CENTER);
        hBox2.setSpacing(10);
        Label searchBarLb = new Label("Search Bar : ");
        hBox2.getChildren().addAll(searchBarLb, searchBarTf);
        flowPane2.getChildren().add(hBox2);

        this.setLeft(vBox);
        this.setCenter(hBox1);
        this.setTop(flowPane2);


        setMargin(hBox1, new Insets(20, 0, 40, 0));
        setMargin(vBox, new Insets(20, 0, 20, 30));
        setMargin(flowPane2, new Insets(0, 0, 40, 700));

    }

    public TableView<Bill> getTableViewOfBills() {
        return tableViewOfBills;
    }

    public TableColumn<Bill, String> getIdBillColumn() {
        return idBillColumn;
    }

    public TableColumn<Bill, String> getIdUserColumn() {
        return idUserColumn;
    }

    public TableColumn<Bill, Date> getDateBillColumn() {
        return dateBillColumn;
    }

    public TableColumn<Bill, Double> getTotalAmountColumn() {
        return totalAmountColumn;
    }

    public TableView<BooksOrdered> getTableViewOfBooksOrdered() {
        return tableViewOfBooksOrdered;
    }

    public TableColumn<BooksOrdered, String> getTitleOfOrderedBooksColumn() {
        return titleOfOrderedBooksColumn;
    }

    public TableColumn<BooksOrdered, String> getIsbnOfOrderedBooksColumn() {
        return isbnOfOrderedBooksColumn;
    }

    public TableColumn<BooksOrdered, Double> getPriceOfOrderedBooksColumn() {
        return priceOfOrderedBooksColumn;
    }

    public TableColumn<BooksOrdered, Integer> getQuantityToOrderColumn() {
        return quantityToOrderColumn;
    }

    public Button getBtnUpdate() {
        return btnUpdate;
    }

    public Button getBtnRemove() {
        return btnRemove;
    }

    public Button getReturnButton() {
        return returnButton;
    }

    public Button getShowAllButton() {
        return showAllButton;
    }

    public TextField getSearchBarTf() {
        return searchBarTf;
    }

    public Label getFromDateLb() {
        return fromDateLb;
    }

    public Date getFromDate() {
        int year = fromDate.getValue().getYear();
        int month = fromDate.getValue().getMonthValue() - 1;
        int day = fromDate.getValue().getDayOfMonth();

        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month, day);
        return gregorianCalendar.getTime();
    }

    public Date getToDate() {

        int year = toDate.getValue().getYear();
        int month = toDate.getValue().getMonthValue() - 1;
        int day = toDate.getValue().getDayOfMonth();

        GregorianCalendar gregorianCalendar = new GregorianCalendar(year, month, day + 1);
        return gregorianCalendar.getTime();
    }


    public Button getSearchButton2() {
        return searchButton2;
    }

    public void setToDate() {
        this.toDate.setValue(null);
    }

    public void setFromDate() {
        this.fromDate.setValue(null);
    }

    public void setRevenueTf(String string) {
        revenueLb2.setText(string);
    }

    public void setSearchBarTf() {
        searchBarTf.setText("");
    }
}

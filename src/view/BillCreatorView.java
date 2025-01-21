    package view;

    import javafx.geometry.Insets;
    import javafx.geometry.Pos;
    import javafx.scene.control.*;
    import javafx.scene.control.cell.PropertyValueFactory;
    import javafx.scene.control.cell.TextFieldTableCell;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.scene.layout.BorderPane;
    import javafx.scene.layout.FlowPane;
    import javafx.scene.layout.HBox;
    import javafx.util.converter.IntegerStringConverter;
    import model.Author;
    import model.Book;
    import model.BooksOrdered;
    import model.Genre;

    import java.io.FileInputStream;

    public class BillCreatorView extends BorderPane {
        private final TableView<Book> tableViewOgBooksInStock = new TableView<>();
        private final TableColumn<Book, String> titleColumnOfBooksInStock;
        private final TableColumn<Book, String> isbnColumnOfBooksInStock;
        private final TableColumn<Book, String> descriptionColumnOfBooksInStock;
        private final TableColumn<Book, Double> priceColumnOfBooksInStock;
        private final TableColumn<Book, Author> authorColumnOfBooksInStock;
        private final TableColumn<Book, Genre> genreColumnOfBooksInStock;
        private final TableColumn<Book, Integer> quantityColumnOfBooksInStock;
        private final TableColumn<Book, Boolean> paperbackColumnOfBooksInStock;


        private final TableView<BooksOrdered> tableViewOfBooksToOrder = new TableView<>();
        private final TableColumn<BooksOrdered, String> titleOfOrderedBooksColumn;
        private final TableColumn<BooksOrdered, String> isbnOfOrderedBooksColumn;
        private final TableColumn<BooksOrdered, Double> priceOfOrderedBooksColumn;
        private final TableColumn<BooksOrdered, Integer> quantityToOrderColumn;


        private final Button submitBtn = new Button("");
        private final Button btnRemove = new Button("");

        private final Button resetBtn = new Button("");
        private final Button returnButton = new Button("");

        private final TextField searchBarTf = new TextField();

        private final Label totalAmountFieldLb = new Label();


        public BillCreatorView() {
            tableViewOgBooksInStock.setEditable(true);
            tableViewOgBooksInStock.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            titleColumnOfBooksInStock = new TableColumn<>("Title");
            titleColumnOfBooksInStock.setMinWidth(115);
            titleColumnOfBooksInStock.setCellValueFactory(new PropertyValueFactory<>("title"));

            isbnColumnOfBooksInStock = new TableColumn<>("ISBN");
            isbnColumnOfBooksInStock.setMinWidth(115);
            isbnColumnOfBooksInStock.setCellValueFactory(new PropertyValueFactory<>("isbn"));

            descriptionColumnOfBooksInStock = new TableColumn<>("Description");
            descriptionColumnOfBooksInStock.setMinWidth(115);
            descriptionColumnOfBooksInStock.setCellValueFactory(new PropertyValueFactory<>("description"));

            paperbackColumnOfBooksInStock = new TableColumn<>("Is paperback?");
            paperbackColumnOfBooksInStock.setMinWidth(115);
            paperbackColumnOfBooksInStock.setCellValueFactory(new PropertyValueFactory<>("paperback"));

            priceColumnOfBooksInStock = new TableColumn<>("Price");
            priceColumnOfBooksInStock.setMinWidth(115);
            priceColumnOfBooksInStock.setCellValueFactory(new PropertyValueFactory<>("price"));

            quantityColumnOfBooksInStock = new TableColumn<>("Quantity In Stock");
            quantityColumnOfBooksInStock.setMinWidth(115);
            quantityColumnOfBooksInStock.setCellValueFactory(new PropertyValueFactory<>("quantity"));
            quantityColumnOfBooksInStock.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));


            authorColumnOfBooksInStock = new TableColumn<>("Author");
            authorColumnOfBooksInStock.setMinWidth(115);
            authorColumnOfBooksInStock.setCellValueFactory(new PropertyValueFactory<>("author"));

            genreColumnOfBooksInStock = new TableColumn<>("Genres");
            genreColumnOfBooksInStock.setMinWidth(115);
            genreColumnOfBooksInStock.setCellValueFactory(new PropertyValueFactory<>("genres"));

            tableViewOfBooksToOrder.setEditable(true);
            tableViewOfBooksToOrder.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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


            tableViewOgBooksInStock.getColumns().addAll(titleColumnOfBooksInStock, isbnColumnOfBooksInStock, descriptionColumnOfBooksInStock, genreColumnOfBooksInStock, authorColumnOfBooksInStock, paperbackColumnOfBooksInStock, priceColumnOfBooksInStock, quantityColumnOfBooksInStock);
            tableViewOfBooksToOrder.getColumns().addAll(titleOfOrderedBooksColumn, isbnOfOrderedBooksColumn, priceOfOrderedBooksColumn, quantityToOrderColumn);

            returnButton.setPrefWidth(80);
            resetBtn.setPrefWidth(80);
            btnRemove.setPrefWidth(80);
            submitBtn.setPrefWidth(80);

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
                input = new FileInputStream("images/refresh-button.png");
            }catch(java.io.FileNotFoundException e) {
                System.out.println("Check images for bookshop");
            }
            ImageView refreshIcon = new ImageView(new Image(input));
            refreshIcon.setFitHeight(25);
            refreshIcon.setFitWidth(25);
            resetBtn.setGraphic(refreshIcon);
            resetBtn.setContentDisplay(ContentDisplay.RIGHT);

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
                input = new FileInputStream("images/submit.png");
            }catch(java.io.FileNotFoundException e) {
                System.out.println("Check images for bookshop");
            }
            ImageView submitIcon = new ImageView(new Image(input));
            submitIcon.setFitHeight(25);
            submitIcon.setFitWidth(25);
            submitBtn.setGraphic(submitIcon);
            submitBtn.setContentDisplay(ContentDisplay.RIGHT);

            HBox hBox3 = new HBox();
            hBox3.setAlignment(Pos.CENTER);
            hBox3.setSpacing(10);
            Label totalAmountLb = new Label("Total Amount : ");
            hBox3.getChildren().addAll(totalAmountLb, totalAmountFieldLb);

            FlowPane flowPane = new FlowPane(4, 4);
            HBox hBox = new HBox();
            hBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER);
            hBox.getChildren().addAll(returnButton, resetBtn, btnRemove, submitBtn);
            flowPane.getChildren().add(hBox);
            flowPane.getChildren().add(hBox3);
            flowPane.setHgap(220);
            flowPane.setStyle("-fx-font-family: 'Times New Roman' ; -fx-font-size: 14; ");

            FlowPane flowPane1 = new FlowPane(2, 2);
            HBox hBox1 = new HBox();
            hBox1.setSpacing(50);
            hBox1.setAlignment(Pos.CENTER);
            hBox1.getChildren().addAll(tableViewOgBooksInStock, tableViewOfBooksToOrder);
            flowPane1.getChildren().add(hBox1);
            flowPane1.setStyle("-fx-font-family: 'Times New Roman' ; -fx-font-size: 13; ");

            FlowPane flowPane2 = new FlowPane(2, 2);
            HBox hBox2 = new HBox();
            hBox2.setAlignment(Pos.CENTER);
            hBox2.setSpacing(10);
            Label searchBarLb = new Label("Search Bar : ");
            hBox2.getChildren().addAll(searchBarLb, searchBarTf);
            flowPane2.getChildren().add(hBox2);
            flowPane2.setStyle("-fx-font-family: 'Times New Roman' ; -fx-font-size: 14; ");


            this.setBottom(flowPane);
            this.setCenter(flowPane1);
            this.setTop(flowPane2);


            setMargin(flowPane1, new Insets(20, 0, 20, 50));
            setMargin(flowPane, new Insets(20, 0, 20, 700));
            setMargin(flowPane2, new Insets(0, 0, 20, 700));

        }

        public TableView<Book> getTableViewOgBooksInStock() {
            return tableViewOgBooksInStock;
        }

        public TableColumn<Book, String> getTitleColumn() {
            return titleColumnOfBooksInStock;
        }

        public TableColumn<Book, String> getIsbnColumn() {
            return isbnColumnOfBooksInStock;
        }

        public TableColumn<Book, String> getDescriptionColumn() {
            return descriptionColumnOfBooksInStock;
        }

        public TableColumn<Book, Double> getPriceColumn() {
            return priceColumnOfBooksInStock;
        }

        public TableColumn<Book, Author> getAuthorColumn() {
            return authorColumnOfBooksInStock;
        }

        public TableColumn<Book, Genre> getGenreColumn() {
            return genreColumnOfBooksInStock;
        }

        public TableColumn<Book, Integer> getQuantityColumn() {
            return quantityColumnOfBooksInStock;
        }

        public TableColumn<Book, Boolean> getPaperbackColumn() {
            return paperbackColumnOfBooksInStock;
        }

        public TableView<BooksOrdered> getTableViewOfBooksToOrder() {
            return tableViewOfBooksToOrder;
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

        public Button getSubmitBtn() {
            return submitBtn;
        }

        public Button getBtnRemove() {
            return btnRemove;
        }


        public Button getReturnButton() {
            return returnButton;
        }

        public TextField getSearchBarTf() {
            return searchBarTf;
        }

        public Label getTotalAmountFieldLb() {
            return totalAmountFieldLb;
        }

        public void setTotalAmountFieldLb(double price) {
            this.totalAmountFieldLb.setText(price + "");
        }

        public Button getResetBtn() {
            return resetBtn;
        }
    }


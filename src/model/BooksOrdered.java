package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BooksOrdered implements Serializable {
    @Serial
    private static final long serialVersionUID = 49479879851447L;

    private String titleOfBookOrdered;
    private String isbnOfBookOrdered;
    private double priceOfBookOrdered;
    private int quantityToOrderOfBookOrdered;
    private final Date date;

    public BooksOrdered(String isbn, String title, double price) {
        this.isbnOfBookOrdered = isbn;
        this.titleOfBookOrdered = title;
        this.priceOfBookOrdered = price;
        this.quantityToOrderOfBookOrdered = 0;
        Calendar calendar = new GregorianCalendar();
        this.date = calendar.getTime();
    }

    public String getIsbn() {
        return isbnOfBookOrdered;
    }

    public String getTitle() {
        return titleOfBookOrdered;
    }

    public double getPrice() {
        return priceOfBookOrdered;
    }

    public void setQuantityToOrder(int quantityToOrder) {
        this.quantityToOrderOfBookOrdered = quantityToOrder;
    }

    public String getTitleOfBookOrdered() {
        return titleOfBookOrdered;
    }

    public void setTitleOfBookOrdered(String titleOfBookOrdered) {
        this.titleOfBookOrdered = titleOfBookOrdered;
    }

    public String getIsbnOfBookOrdered() {
        return isbnOfBookOrdered;
    }

    public void setIsbnOfBookOrdered(String isbnOfBookOrdered) {
        this.isbnOfBookOrdered = isbnOfBookOrdered;
    }

    public double getPriceOfBookOrdered() {
        return priceOfBookOrdered;
    }

    public void setPriceOfBookOrdered(double priceOfBookOrdered) {
        this.priceOfBookOrdered = priceOfBookOrdered;
    }

    public int getQuantityToOrderOfBookOrdered() {
        return quantityToOrderOfBookOrdered;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof BooksOrdered) {
            return (this.isbnOfBookOrdered.equals(((BooksOrdered) object).isbnOfBookOrdered) && (this.date.equals(((BooksOrdered) object).date)));
        }
        return false;
    }

    @Override
    public String toString() {
        return this.titleOfBookOrdered + "\t Price : " + priceOfBookOrdered + "\t Quantity : " + quantityToOrderOfBookOrdered + "\n";
    }
}
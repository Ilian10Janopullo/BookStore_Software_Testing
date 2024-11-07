package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class Bill implements Serializable {
    @Serial
    private static final long serialVersionUID = 564484984984L;
    private final String billID;
    private final String userID;
    private final Date date;
    private ArrayList<BooksOrdered> books;
    private double totalAmount;

    public Bill(ArrayList<BooksOrdered> books, String userID) {
        this.billID = UUID.randomUUID().toString();
        this.userID = userID;
        this.date = new GregorianCalendar().getTime();
        this.books = books;
        setTotalAmount(books);
    }

    public String getBillID() {
        return billID;
    }

    public ArrayList<BooksOrdered> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<BooksOrdered> books) {
        this.books = books;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(ArrayList<BooksOrdered> books) {
        this.totalAmount = 0;
        for (BooksOrdered book : books) {
            this.totalAmount += (book.getPrice() * book.getQuantityToOrderOfBookOrdered());
        }
    }

    public Date getDate() {
        return date;
    }

    public String getUserID() {
        return userID;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Bill) {
            return (this.billID.equals(((Bill) object).billID));
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder billToBePrinted;
        billToBePrinted = new StringBuilder("Bill ID : " + billID + "\n");
        billToBePrinted.append("User ID : ").append(userID).append("\n");
        billToBePrinted.append("Date/Time : ").append(this.date.toString()).append("\n");
        for (BooksOrdered book : books) {
            billToBePrinted.append("Book : ").append(book.toString());
        }
        billToBePrinted.append("Total : ").append(this.totalAmount).append("\n");
        billToBePrinted.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
        return billToBePrinted.toString();
    }
}

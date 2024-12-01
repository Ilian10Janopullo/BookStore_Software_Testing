package org.example.model;

import org.example.dao.AuthorsDAO;
import javafx.scene.control.Alert;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
@SuppressWarnings("unused")
public class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 5296705482940410483L;
    private String isbn;
    private String title;
    private String description;
    private double price;
    private int quantity;
    private int copiesSold;
    private Author author;
    private final ArrayList<Genre> genres = new ArrayList<>();
    private boolean paperback; // or e-book

    public Book(String isbn, String title, String description, double price, Author author,
                boolean paperback, int quantity) {
        this.isbn = isbn;
        this.title = title;
        this.price = price;
        this.author = author;
        this.description = description;
        this.paperback = paperback;
        this.quantity = quantity;
        this.copiesSold = 0;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(String string) {
        AuthorsDAO authorsDAO = new AuthorsDAO();

        for (Author author : authorsDAO.getAll()) {
            if (author.toString().equals(string)) {
                this.author = author;
                return;
            }
        }

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("Author not found!");
        alert.setTitle("Adding failure!");
        alert.show();
    }


    public int getCopiesSold() {
        return this.copiesSold;
    }

    public void setCopiesSold(int copiesSold) {
        this.copiesSold += copiesSold;
    }

    public void setGenre(String string) {
        for (Genre g : Genre.values()) {
            if (g.toString().equals(string)) {
                genres.add(g);
                break;
            }
        }
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    public void addGenres(Genre... genres) {
        for (Genre genre : genres)
            this.addGenre(genre);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPaperback() {
        return paperback;
    }

    public void setPaperback(boolean paperback) {
        this.paperback = paperback;
    }

    @Override
    public String toString() {
        return this.title + " by " + this.author.toString() + ", " + this.price + " EUR";
    }
}
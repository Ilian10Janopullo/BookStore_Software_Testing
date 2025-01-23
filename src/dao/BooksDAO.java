package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Book;

import java.io.*;
import java.util.List;

public class BooksDAO implements dao {
    public static String FILE_PATH = "database/books.dat";
    private static final File DATA_FILE = new File(FILE_PATH);
    private final ObservableList<Book> books = FXCollections.observableArrayList();

    public ObservableList<Book> getAll() {
        if (books.isEmpty()) {
            loadFromFileImplementation(books, DATA_FILE);
        }
        return books;
    }


    public boolean addToFile(Book book) {
        return addToFileImplementation(book, books, DATA_FILE);
    }

    public boolean delete(Book bookToDelete) {
        return deleteImplementation(bookToDelete, books, DATA_FILE);
    }

    public boolean deleteAll(List<Book> booksToDelete) {
       return deleteAllImplementation(books, booksToDelete, DATA_FILE);
    }

    public boolean updateAll() {
       return updateAllImplementation(books, DATA_FILE);
    }
}

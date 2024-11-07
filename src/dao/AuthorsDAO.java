package dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Author;

import java.io.*;
import java.util.List;

public class AuthorsDAO implements dao {
    public static final String FILE_PATH = "database/authors.dat";
    private static final File DATA_FILE = new File(FILE_PATH);
    private final ObservableList<Author> authors = FXCollections.observableArrayList();

    public ObservableList<Author> getAll() {
        if (authors.isEmpty()) {
            loadFromFileImplementation(authors, DATA_FILE);
        }
        return authors;
    }

    public boolean addToFile(Author author) {
        return addToFileImplementation(author, authors, DATA_FILE);
    }

    public boolean delete(Author authorToDelete) {
        return deleteImplementation(authorToDelete, authors, DATA_FILE);
    }

    public boolean deleteAll(List<Author> authorsToDelete) {
        return deleteAllImplementation(authors, authorsToDelete, DATA_FILE);
    }

    public boolean updateAll() {
       return updateAllImplementation(authors, DATA_FILE);
    }
}

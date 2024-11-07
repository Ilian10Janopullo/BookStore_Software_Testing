package dao;

import javafx.collections.ObservableList;


import java.io.*;
import java.util.List;

public interface dao<T> {

    default void loadFromFileImplementation(ObservableList<T> list, File DATA_FILE) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            while (true) {
                T t = (T) inputStream.readObject();
                list.add(t);
            }
        } catch (EOFException ignored) {
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    default boolean addToFileImplementation(T t, ObservableList<T> list, File DATA_FILE) {
        try (FileOutputStream outputStream = new FileOutputStream(DATA_FILE, true)) {
            ObjectOutputStream writer;
            if (DATA_FILE.length() > 0) {
                writer = new HeaderlessObjectOutputStream(outputStream);
            } else {
                writer = new ObjectOutputStream(outputStream);
            }
            writer.writeObject(t);
            list.add(t);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    default boolean deleteImplementation(T t, ObservableList<T> list, File DATA_FILE) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            for (T toDelete : list) {
                if (!toDelete.equals(t))
                    outputStream.writeObject(toDelete);
            }
            list.remove(t);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    default boolean deleteAllImplementation(ObservableList<T> list, List<T> listToDelete, File DATA_FILE) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            for (T t : list) {
                if (!listToDelete.contains(t))
                    outputStream.writeObject(t);
            }
            list.removeAll(listToDelete);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    default boolean updateAllImplementation(ObservableList<T> list, File DATA_FILE) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            for (T t : list) {
                outputStream.writeObject(t);
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

}

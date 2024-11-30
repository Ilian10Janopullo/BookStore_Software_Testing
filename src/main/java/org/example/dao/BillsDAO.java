package org.example.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.model.Bill;

import java.io.File;


public class BillsDAO implements dao {
    public static final String FILE_PATH = "src/main/database/bills.dat";
    private static final File DATA_FILE = new File(FILE_PATH);
    private final ObservableList<Bill> bills = FXCollections.observableArrayList();

    public ObservableList<Bill> getAll() {
        if (bills.isEmpty()) {
            loadFromFileImplementation(bills, DATA_FILE);
        }
        return bills;
    }


    public boolean addToFile(Bill bill) {
        return addToFileImplementation(bill, bills, DATA_FILE);
    }

    public boolean delete(Bill billToDelete) {
        return deleteImplementation(billToDelete, bills, DATA_FILE);
    }

//    public boolean deleteAll(List<Bill> billsToDelete) {
//        return deleteAllImplementation(bills, billsToDelete, DATA_FILE);
//    }

    public boolean updateAll() {
       return updateAllImplementation(bills, DATA_FILE);
    }
}

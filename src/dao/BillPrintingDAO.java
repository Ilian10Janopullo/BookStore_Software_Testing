package dao;

import javafx.collections.ObservableList;
import model.Bill;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class BillPrintingDAO {

    public static final String FILE_PATH = "database/billsPrinted.txt";
    private static final File DATA_FILE = new File(FILE_PATH);

    public void addBillToFile(ObservableList<Bill> billsToBeAdded) throws IOException {
        PrintWriter outputToFile = new PrintWriter(DATA_FILE);
        for (Bill bill : billsToBeAdded) {
            outputToFile.print(bill.toString());
        }
        outputToFile.close();
    }
}

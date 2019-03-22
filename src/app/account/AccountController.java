package app.account;


import app.Entities.Account;
import app.Entities.Transaction;
import app.db.DB;
import app.home.HomeController;
import app.transaction.TransactionController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class AccountController {
    Account account = null;
    @FXML
    VBox transactionBox;
    @FXML
    Button loadAll;

    List<Transaction> transactions;

    @FXML
    private void initialize() {
        System.out.println("initialize account");
        Platform.runLater(() -> loadMoreTransactions(account.getAccountNumber()));
    }

    void loadMoreTransactions(int accnumber) {
            transactions = (List<Transaction>) DB.getTransactions(accnumber, 0, 10);
            displayTransaction(transactions);
    }

    void displayTransaction(List<Transaction> transactions) {
        transactionBox.getChildren().clear();
        transactions.forEach(transaction -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/transaction/transaction.fxml"));
                Parent fxmlInstance = loader.load();
                Scene scene = new Scene(fxmlInstance);

                TransactionController controller = loader.getController();
                controller.setTransaction(transaction, account.getAccountNumber());

                transactionBox.getChildren().add(scene.getRoot());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setAccount(int accountNumber) {
        account = DB.getAccount(accountNumber);
    }

    @FXML
    void clickLoadTransactions(Event e) {
        loadMoreTransactions(account.getAccountNumber());
    }

    @FXML
    void loadAll(Event e) {
        transactions = (List<Transaction>) DB.getTransactions(account.getAccountNumber(),0, Integer.MAX_VALUE);
        displayTransaction(transactions);
    }
}

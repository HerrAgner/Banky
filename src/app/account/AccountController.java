package app.account;


import app.Entities.Account;
import app.Entities.Transaction;
import app.db.DB;
import app.login.LoginController;
import app.transaction.TransactionController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import java.io.IOException;
import java.util.List;

public class AccountController {
    Account account = null;
    @FXML ListView transactionBox;
    @FXML Button loadAll;
    List<Transaction> transactions;

    @FXML
    private void initialize() {
        if (this.account == null) {
            account = LoginController.getUser().getAccountList().get(0);
        }
        Platform.runLater(() -> loadTransactions());
    }

    @FXML
    void loadTransactions(){
        transactions = (List<Transaction>) DB.getTransactions(account.getAccountNumber(), 0, 10);
        displayTransaction(transactions);
        if(transactions.size() < 10){
            loadAll.setVisible(false);
        }
    }

    @FXML
    public void loadAllTransactions(){
        transactions = (List<Transaction>) DB.getTransactions(account.getAccountNumber(), 0, Integer.MAX_VALUE);
        displayTransaction(transactions);
    }
    void displayTransaction(List<Transaction> transactions) {
        transactionBox.getItems().add(accountBalance());
        transactionBox.getItems().clear();
        transactions.forEach(transaction -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/transaction/transaction.fxml"));
                Parent fxmlInstance = loader.load();
                Scene scene = new Scene(fxmlInstance);

                TransactionController controller = loader.getController();
                controller.setTransaction(transaction, account.getAccountNumber());

                transactionBox.getItems().add(scene.getRoot());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    HBox accountBalance() {
        HBox balanceBox = new HBox();
        Label text = new Label("Account balance: ");
        Label balance = new Label(String.valueOf(account.getBalance()));

        text.setMaxWidth(300);
        text.setMinWidth(50);
        balance.setMaxWidth(300);
       balance.setMinWidth(50);
       text.setFont(new Font(30));
        balance.setFont(new Font(30));


        balanceBox.getChildren().addAll(text, balance);
        return balanceBox;
    }

    public void setAccount(String accountNumber) {
        account = DB.getAccount(accountNumber);
    }
}

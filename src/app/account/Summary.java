package app.account;

import app.Entities.Account;
import app.Entities.Transaction;
import app.db.Database;
import app.db.ObjectMapper;
import app.login.LoginController;
import app.transaction.TransactionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.*;

public class Summary {
    @FXML Label sum_accounts;
    @FXML ListView summary;
    List<Account> summaryOfAccounts = (List<Account>) getSummary();

    @FXML
    void initialize() {
        sum_accounts.setText("Balance of all accounts: " + summaryOfAccounts.get(0).getBalance());
        summaryOfAccounts.forEach(account -> {
            if (account != summaryOfAccounts.get(0)) {
                HBox accountBox = new HBox();
                Label accountLabel = new Label();
                Label balanceLabel = new Label();
                accountLabel.setText("Account number: " + account.getOwner());
                balanceLabel.setText(" Balance: " + account.getBalance());
                accountBox.getChildren().addAll(accountLabel, balanceLabel);
                summary.getItems().add(accountBox);
            }
        });
        addLastTransactions();
    }

    private void addLastTransactions() {
        List<?> transactions = null;
        CallableStatement stmt = null;
        String personnummber = LoginController.getUser().getId();
        try {
            stmt = Database.getInstance().getConn().prepareCall("{call all_transactions(?,?)}");
            stmt.setString(1, personnummber);
            stmt.setInt(2, 5);
            transactions = new ObjectMapper<>(Transaction.class).map(stmt.executeQuery());
        } catch (Exception e) {
        }
        addSpaces();
        transactions.forEach(o -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/transaction/transaction.fxml"));
            Parent fxmlInstance = null;
            try {
                fxmlInstance = loader.load();
            Scene scene = new Scene(fxmlInstance);
            TransactionController controller = loader.getController();
            controller.setTransaction((Transaction) o);
            summary.getItems().add(scene.getRoot());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void addSpaces() {
        summary.getItems().add(new Label("------------"));
        summary.getItems().add(new Label("Last five transactions:"));
        Label message = new Label("Message");
        Label amount = new Label("Amount");
        Label date = new Label("Date");
        Label from = new Label("From");
        message.setMinWidth(130);
        amount.setMinWidth(50);
        date.setMinWidth(120);
        HBox messages = new HBox();
        messages.setSpacing(60);
        messages.getChildren().addAll(message,date,amount,from);
        summary.getItems().addAll(messages);
    }

    private List<?> getSummary() {
        List<?> accounts = null;
        CallableStatement stmt = null;
        try {
            stmt = Database.getInstance().getConn().prepareCall("call banky.account_balance(?);");
            stmt.setString(1, LoginController.getUser().getId());
            accounts = new ObjectMapper<>(Account.class).map(stmt.executeQuery());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }
}
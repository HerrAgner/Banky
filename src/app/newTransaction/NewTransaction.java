package app.newTransaction;

import app.Entities.Account;
import app.account.AccountController;
import app.db.DB;
import app.home.HomeController;
import app.login.LoginController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class NewTransaction {
    @FXML
    ComboBox comboBox;
    @FXML
    TextField toAccount;
    @FXML
    TextField amount;
    @FXML
    Button confirm;
    @FXML
    TextField messageBox;
    @FXML
    HBox node;

    @FXML
    void initialize() {
        comboBox.getItems().removeAll(comboBox.getItems());
        LoginController.getUser().getAccountList().forEach(account -> {
            comboBox.getItems().add(account.getAccountNumber());
        });
        comboBox.getSelectionModel().selectFirst();
    }

    @FXML
    void transaction() throws IOException {
        Platform.runLater(() -> {
            try {
                DB.newTransaction(
                        Integer.parseInt(comboBox.getSelectionModel().getSelectedItem().toString()),
                        Integer.parseInt(toAccount.getText()),
                        Float.parseFloat(amount.getText()),
                        messageBox.getText());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            clearFields();
        });

    }

    private void clearFields() {
        toAccount.clear();
        amount.clear();
        messageBox.clear();
        comboBox.getSelectionModel().clearSelection();
    }
}

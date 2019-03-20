package app.newTransaction;

import app.Entities.Account;
import app.account.AccountController;
import app.db.DB;
import app.login.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;

import java.util.List;

public class NewTransaction {
    @FXML
    ComboBox comboBox;

    @FXML
    void initialize() {
        comboBox.getItems().removeAll(comboBox.getItems());
        LoginController.getUser().getAccountList().forEach(account -> {
            comboBox.getItems().add(account.getAccountNumber() + " " + account.getType());
        });
        comboBox.getSelectionModel().selectFirst();
    }
}

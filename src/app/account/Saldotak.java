package app.account;

import app.db.DB;
import app.login.LoginController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Saldotak {

    @FXML
    public VBox saldotakVBox;

    @FXML
    ComboBox comboBoxTak;

    @FXML
    TextField textFieldTak;

    @FXML
    Button confirmTak;

    @FXML
    Label currentSaldoTak;

    @FXML
    void initialize() {
        confirmTak.setOnAction(actionEvent -> saldotak());
        listener();
    }

    private void listener() {
        comboBoxTak.valueProperty().addListener((ov, oldValue, newValue) -> LoginController.getUser().getAccountList().forEach(account -> {
            account.loadSaldotak();
            if (account.getAccountNumber().equals(newValue)) {
                currentSaldoTak.setText(String.valueOf(account.getSaldotak()));
            }
        }));
    }

    void saldotak() {
        Platform.runLater(() -> {
            LoginController.getUser().getAccountList().forEach(account -> {
                if (account.getAccountNumber().equals(comboBoxTak.getSelectionModel().getSelectedItem())) {
                    account.setSaldotak(Double.parseDouble(textFieldTak.getText()));
                    currentSaldoTak.setText(textFieldTak.getText());
                    System.out.println(account.getSaldotak());
                    System.out.println(account.getAccountNumber());
                    PreparedStatement ps = DB.prep("UPDATE accounts SET saldotak = " +account.getSaldotak() + " WHERE account_number = " + account.getAccountNumber() + ";");
                    try {
                        ps.execute();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    @FXML
    void fillAccountBox() {
        comboBoxTak.getItems().removeAll(comboBoxTak.getItems());
        LoginController.getUser().getAccountList().forEach(account -> {
            comboBoxTak.getItems().add(account.getAccountNumber());
        });
        comboBoxTak.getSelectionModel().selectFirst();
    }
}

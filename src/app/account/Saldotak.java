package app.account;

import app.Entities.Account;
import app.db.DB;
import app.db.Database;
import app.login.LoginController;
import app.transaction.newTransaction.NewTransaction;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    Label current_amount;
    @FXML
    Label amount_left;

    @FXML
    Button reset;

    String currentAccountNumber = "";
    double currentAmount;

    @FXML
    void initialize() {
        confirmTak.setOnAction(actionEvent -> saldotak());
        reset.setOnAction(actionEvent -> resetSaldotak());
        listener();
        NewTransaction.addTextLimiter(textFieldTak, false);
    }

    private void listener() {
        comboBoxTak.valueProperty().addListener((ov, oldValue, newValue) -> LoginController.getUser().getAccountList().forEach(account -> {
            account.loadSaldotak();
            if (account.getAccountNumber().equals(newValue)) {
                currentAccountNumber = account.getAccountNumber();
                setLabelText(String.valueOf(account.getSaldotak()));
                currentAmount();
            }
        }));
    }

    private void currentAmount() {
        try {
            CallableStatement cs = Database.getInstance().getConn().prepareCall("{CALL get_saldotak_current(?,?)}");
            cs.setString(1, currentAccountNumber);
            cs.setString(2, "7");
            ResultSet rs = cs.executeQuery();
            if (rs.next()){
                current_amount.setText(String.valueOf(rs.getObject(1)));
                currentAmount = rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void resetSaldotak() {
        LoginController.getUser().getAccountList().forEach(account -> {
            if (account.getAccountNumber().equals(comboBoxTak.getSelectionModel().getSelectedItem())) {
                account.setSaldotak(0);
            }
        });
    }

    private void setLabelText(String tak) {
        if (tak.equals("0.0")) {
            amount_left.setText("N/A");
            currentSaldoTak.setText("No saldotak.");
        } else {
            amount_left.setText(String.valueOf(Double.parseDouble(tak) - currentAmount));
            currentSaldoTak.setText(tak);
        }
    }


    void saldotak() {
        Platform.runLater(() -> {
            LoginController.getUser().getAccountList().forEach(account -> {
                if (account.getAccountNumber().equals(comboBoxTak.getSelectionModel().getSelectedItem())) {
                    account.setSaldotak(Double.parseDouble(textFieldTak.getText()));
                    setLabelText(String.valueOf(account.getSaldotak()));
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

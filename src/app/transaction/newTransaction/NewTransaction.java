package app.transaction.newTransaction;

import app.Entities.Account;
import app.db.DB;
import app.db.Database;
import app.login.LoginController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    DatePicker datepicker;
    @FXML
    ComboBox dateBoxNumber;
    @FXML
    ComboBox dateBoxOccurrence;
    @FXML
    Label result;
    @FXML
    CheckBox autogiroCheckBox;

    @FXML
    void initialize() {
        load();
    }

    public void load() {
        fillAccountBox();
        fillDateBox();
        toggleAutogiro();
        addTextLimiter(amount, true);
    }


    @FXML
    void transaction() {
        Platform.runLater(() -> {
            String eventname = transactionType() + Instant.now().toEpochMilli();
            if (validateSaldotak(comboBox.getSelectionModel().getSelectedItem().toString(), Double.parseDouble(amount.getText()))) {
                DB.scheduledTransaction(eventname,
                        convertBoxToTime(),
                        comboBox.getSelectionModel().getSelectedItem().toString(),
                        convertAccountNumber(),
                        Double.parseDouble(amount.getText()),
                        messageBox.getText());
                clearFields();
                resultText(eventname);
            } else {
                result.setText("Saldotak is reached. Lower the transaction amount or raise the saldotak.\nCurrent saldotak: "+returnAccount(comboBox.getSelectionModel().getSelectedItem().toString()).getSaldotak());
            }
        });
    }

    private void resultText(String name) {
        if (DB.validateInsert(name)) {
            result.setTextFill(Color.GREEN);
            result.setText("NEW TRANSACTION MADE \n" +
                    "From account: " + comboBox.getSelectionModel().getSelectedItem().toString() +
                    "\nTo account: " + toAccount.getText() +
                    "\nwith amount: " + amount.getText() +
                    "\nwith message: " + messageBox.getText() +
                    "\n" + datepicker.getValue());
        } else {
            result.setTextFill(Color.RED);
            result.setText("Transaction failed");
        }
    }
    private Account returnAccount(String accountNumber){
        final Account[] curr = new Account[1];
        LoginController.getUser().getAccountList().forEach(account -> {
            if (account.getAccountNumber().equals(accountNumber)) {
                curr[0] = account;
            }
        });
        return curr[0];
    }

    private boolean validateSaldotak(String accNR, double currentTransactionAmount) {
        return DB.saldotak(accNR, returnAccount(accNR).getSaldotak(), 7, currentTransactionAmount);
    }

    private String transactionType() {
        String accNumber = toAccount.getText();
        Pattern pg = Pattern.compile("^(\\d{3,4}-\\d{4})");
        Pattern bg = Pattern.compile("^(\\d{5,7}-\\d)");
        if (pg.matcher(accNumber).matches()) {
            return "PG";
        } else if (bg.matcher(accNumber).matches()) {
            return "BG";
        } else if (dateBoxNumber.getSelectionModel().getSelectedItem() != null && dateBoxOccurrence.getSelectionModel().getSelectedItem() != null) {
            return "AG";
        } else {
            return "TX";
        }
    }

    private String convertAccountNumber() {
        String accNumber = toAccount.getText().replaceAll("\\D", "");
        return accNumber;
    }

    private String convertBoxToTime() {
        String when;
        String time;
        String date = "STARTS '" + Timestamp.valueOf(datepicker.getValue().atTime(LocalTime.now())) + "'";

        if (dateBoxNumber.getSelectionModel().getSelectedItem() == null && dateBoxOccurrence.getSelectionModel().getSelectedItem() == null) {
            when = "AT";
            time = "CURRENT_TIMESTAMP";
            return when + " " + time;
        } else if (dateBoxNumber.getSelectionModel().getSelectedItem() == null && dateBoxOccurrence.getSelectionModel().getSelectedItem() != null) {
            when = "EVERY " + dateBoxNumber.getSelectionModel().getSelectedItem().toString();
            time = "day";
        } else {
            when = "EVERY " + dateBoxNumber.getSelectionModel().getSelectedItem().toString();
            time = dateBoxOccurrence.getSelectionModel().getSelectedItem().toString();
        }
        return when + " " + time + " " + date;
    }

    @FXML
    void fillAccountBox() {
        comboBox.getItems().removeAll(comboBox.getItems());
        LoginController.getUser().getAccountList().forEach(account -> {
            comboBox.getItems().add(account.getAccountNumber());
        });
        comboBox.getSelectionModel().selectFirst();
    }

    @FXML
    void fillDateBox() {
        dateBoxOccurrence.getItems().addAll("day", "week", "month", "year");
        for (int i = 1; i <= 31; i++) {
            dateBoxNumber.getItems().add(i);
        }
        dateBoxNumber.setDisable(true);
        dateBoxOccurrence.setDisable(true);
        datepicker.setValue(LocalDate.now());
    }

    private void toggleAutogiro() {
        autogiroCheckBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
            autogiroCheckBox.setSelected(newValue);
            Platform.runLater(() -> {
                if (autogiroCheckBox.isSelected()) {
                    dateBoxNumber.setDisable(false);
                    dateBoxOccurrence.setDisable(false);
                    dateBoxNumber.getSelectionModel().selectFirst();
                    dateBoxOccurrence.getSelectionModel().selectFirst();

                } else {
                    dateBoxNumber.setDisable(true);
                    dateBoxOccurrence.setDisable(true);
                    dateBoxNumber.getSelectionModel().clearSelection();
                    dateBoxOccurrence.getSelectionModel().clearSelection();
                }
            });
        });
    }

    public static void addTextLimiter(final TextField tf, Boolean spaces) {
        tf.textProperty().addListener((ov, oldValue, newValue) -> {
            String text;
            if (spaces) {
                text = newValue.replaceAll("[^\\d .]()|[ ]+( )", "$1$2");
            } else {
                text = newValue.replaceAll("[^\\d]", "");
            }
//                    .replaceAll("[ ]{2,}", " ");
//            text = text.replace("  "," ");
            tf.setText(text);
        });
    }

    @FXML
    private void confirmButtonListener(){
        if (!toAccount.getText().isEmpty() && !amount.getText().isEmpty() && !messageBox.getText().isEmpty()) {
            transaction();
        } else {
            result.setText("Please fill in all fields");
        }
    }

    private void clearFields() {
        toAccount.clear();
        amount.clear();
        messageBox.clear();
//        dateBoxNumber.getSelectionModel().selectFirst();
//        dateBoxOccurrence.getSelectionModel().selectFirst();
        datepicker.setValue(LocalDate.now());
    }


}

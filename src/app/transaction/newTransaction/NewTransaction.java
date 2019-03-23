package app.transaction.newTransaction;

import app.db.DB;
import app.login.LoginController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
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
    void initialize() {
        load();
    }

    public void load() {
        fillAccountBox();
        fillDateBox();
    }


    @FXML
    void transaction() throws IOException {
        Platform.runLater(() -> {
//            "AT \""+Timestamp.valueOf(datepicker.getValue().atTime(LocalTime.from(LocalDateTime.now().plusMinutes(1))))+"\""
//            try {
//                DB.newTransaction(
//                        Integer.parseInt(comboBox.getSelectionModel().getSelectedItem().toString()),
//                        Integer.parseInt(toAccount.getText()),
//                        Float.parseFloat(amount.getText()),
//                        messageBox.getText());
            DB.scheduledTransaction(transactionType(),
                    convertBoxToTime(),
                    Integer.parseInt(comboBox.getSelectionModel().getSelectedItem().toString()),
                    convertAccountNumber(),
                    Float.parseFloat(amount.getText()),
                    messageBox.getText());
            result.setText("NEW TRANSACTION MADE \n" +
                    "From account: " + comboBox.getSelectionModel().getSelectedItem().toString() +
                    "\nTo account: " + toAccount.getText() +
                    "\nwith amount: " + amount.getText() +
                    "\nwith message: " + messageBox.getText() +
                    "\n"+ datepicker.getValue());
            clearFields();
        });
    }

    private String transactionType() {
        String accNumber = toAccount.getText();
        Pattern pg = Pattern.compile("^(\\d{3,4}-\\d{4})");
        Pattern bg = Pattern.compile("^(\\d{5,7}-\\d)");
        if (pg.matcher(accNumber).matches()) {
            System.out.println("pg");
            return "PG";
        } else if (bg.matcher(accNumber).matches()) {
            System.out.println("bg");
            return "BG";
        } else if (!dateBoxNumber.getSelectionModel().getSelectedItem().equals("now") && !dateBoxOccurrence.getSelectionModel().getSelectedItem().equals("now")) {
            return "Autogiro";
        } else {
            return "Transaction";
        }
    }

    private int convertAccountNumber() {
        String accNumber = toAccount.getText().replaceAll("\\D","");
        return Integer.parseInt(accNumber);
    }

    private String convertBoxToTime() {
        String when = null;
        String time = null;
        String date = "STARTS '" + Timestamp.valueOf(datepicker.getValue().atStartOfDay()) + "'";

        if (dateBoxNumber.getSelectionModel().isSelected(0) && dateBoxOccurrence.getSelectionModel().isSelected(0)) {
            when = "AT ";
            time = "CURRENT_TIMESTAMP";
            System.out.println("yo");
            return when + " " + time;
        } else if (dateBoxNumber.getSelectionModel().isSelected(0) && !dateBoxOccurrence.getSelectionModel().isSelected(0)) {
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
        dateBoxOccurrence.getItems().addAll("once", "day", "week", "month", "year");
        dateBoxNumber.getItems().add("once");
        for (int i = 1; i <= 31; i++) {
            dateBoxNumber.getItems().add(i);
        }
        dateBoxNumber.getSelectionModel().selectFirst();
        dateBoxOccurrence.getSelectionModel().selectFirst();
        datepicker.setValue(LocalDate.now());
    }

    private void clearFields() {
        toAccount.clear();
        amount.clear();
        messageBox.clear();
        dateBoxNumber.getSelectionModel().selectFirst();
        dateBoxOccurrence.getSelectionModel().selectFirst();
        datepicker.setValue(LocalDate.now());
    }


}

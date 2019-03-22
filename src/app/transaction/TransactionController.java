package app.transaction;


import app.Entities.Transaction;
import app.login.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TransactionController {

    @FXML Label message;
    @FXML Label amount;
    @FXML Label date;
    @FXML Label from;

    @FXML
    private void initialize(){
        System.out.println("initialize transaction");
    }

    public void setTransaction(Transaction transaction, int from) {
        message.setText(transaction.getMessage());
        if (transaction.getAccount_id() == from) {
            amount.setText(String.format("-%.2f",transaction.getAmount()));
        } else {
            amount.setText(String.format("+%.2f",transaction.getAmount()));
        }
        date.setText(String.format("%s",transaction.getDate()));
        this.from.setText(String.valueOf(transaction.getReceiver()));
        message.setMaxWidth(80);
        amount.setMaxWidth(80);
        date.setMaxWidth(80);
        message.setMinWidth(80);
        amount.setMinWidth(80);
        date.setMinWidth(140);

    }
}

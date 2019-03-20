package app.transaction;


import app.Entities.Transaction;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TransactionController {

    @FXML Label message;
    @FXML Label amount;
    @FXML Label date;

    @FXML
    private void initialize(){
        System.out.println("initialize transaction");
    }

    public void setTransaction(Transaction transaction) {
        message.setText(transaction.getMessage());
        amount.setText(String.format("%.2f",transaction.getAmount()));
        date.setText(String.format("%s",transaction.getDate()));
    }
}

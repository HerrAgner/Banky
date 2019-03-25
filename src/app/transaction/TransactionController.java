package app.transaction;


import app.Entities.Transaction;
import app.login.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;

public class TransactionController {

    @FXML Label message;
    @FXML Label amount;
    @FXML Label date;
    @FXML Label from;
    @FXML
    HBox box;

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
        date.setText(String.format("%s",transaction.getDateAsString()));
        this.from.setText(String.valueOf(transaction.getReceiver()));
        message.setMaxWidth(100);
        amount.setMaxWidth(80);
        message.setMinWidth(100);
        amount.setMinWidth(80);
        date.setMinWidth(100);
        Tooltip tooltip = new Tooltip(transaction.toString());
        Tooltip.install(box, tooltip);
    }
    public void setTransaction(Transaction transaction) {
        message.setText(transaction.getMessage());
        date.setText(String.format("%s",transaction.getDateAsString()));
        from.setText(String.valueOf(transaction.getReceiver()));
        amount.setText(String.valueOf(transaction.getAmount()));
        message.setMaxWidth(100);
        amount.setMaxWidth(80);
        date.setMaxWidth(80);
        message.setMinWidth(100);
        amount.setMinWidth(80);
        date.setMinWidth(100);
        Tooltip tooltip = new Tooltip(transaction.toString());
        Tooltip.install(box, tooltip);

    }

}

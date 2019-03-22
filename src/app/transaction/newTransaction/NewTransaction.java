package app.transaction.newTransaction;

import app.db.DB;
import app.login.LoginController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

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
        load();
    }

    public void load() {
        fillAccountBox();
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
            BorderPane qwe = (BorderPane) this.node.getParent();
            FXMLLoader loader = new FXMLLoader( getClass().getResource( "/app/account/account.fxml" ) );
            try {
                Parent fxmlInstance = loader.load();
                qwe.setCenter(fxmlInstance);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void fillAccountBox() {
        comboBox.getItems().removeAll(comboBox.getItems());
        LoginController.getUser().getAccountList().forEach(account -> {
            comboBox.getItems().add(account.getAccountNumber());
        });
        comboBox.getSelectionModel().selectFirst();
    }

    private void clearFields() {
        toAccount.clear();
        amount.clear();
        messageBox.clear();
        comboBox.getSelectionModel().clearSelection();
    }
}

package app.home;

import app.Entities.Transaction;
import app.Main;
import app.account.AccountController;
import app.db.DB;
import app.login.LoginController;
import app.transaction.TransactionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class HomeController {
    LoginController login = new LoginController();

    @FXML
    VBox account_box;

    @FXML
    VBox borderPaneMid;

    @FXML
    void initialize(){
        // load accounts from db using LoginController.user.getId() and display them
        login.loadUser();
        generateAccounts();
//        List<Transaction> transactions = (List<Transaction>) DB.getTransactions(321321321);
//        DB.getTransactions(321321321);
//        transactions.forEach(transaction -> {
//            Label label = new Label();
//            label.setText(transaction.toString());
//            borderPaneMid.getChildren().add(label);
//        });
    }

    @FXML
    void goToAccount(int id) throws IOException {

        FXMLLoader loader = new FXMLLoader( getClass().getResource( "/app/account/account.fxml" ) );
        Parent fxmlInstance = loader.load();
        Scene scene = new Scene( fxmlInstance, 800, 600 );

        // Make sure that you display "the correct account" based on which one you clicked on
            AccountController controller = loader.getController();
            controller.setAccount(id);

        // If you don't want to have/use the static variable Main.stage
//        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
        Main.stage.setScene(scene);
        Main.stage.show();
    }

    @FXML
    void generateAccounts() {
        LoginController.getUser().getAccountList().forEach(account -> {
            Button accountButton = new Button();
            String id = String.valueOf(account.getAccountNumber());
            accountButton.setId(id);
            accountButton.setOnAction(actionEvent -> {
                try {
                    goToAccount(account.getAccountNumber());
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                asd.clickLoadTransactions(actionEvent, account.getAccountNumber());
            });
            accountButton.setText(account.toString());
            account_box.getChildren().add(accountButton);
        });
    }
}

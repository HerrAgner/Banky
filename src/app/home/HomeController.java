package app.home;

import app.Main;
import app.account.AccountController;
import app.db.DB;
import app.login.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class HomeController {
    LoginController login = new LoginController();

    @FXML
    public VBox account_box;

    @FXML
    VBox borderPaneMid;

    @FXML
    BorderPane borderPane;

    @FXML
    void initialize() throws IOException {
        addMenu();
        // load accounts from db using LoginController.user.getId() and display them
//        login.loadUser();
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
    public void goToAccount(int id) throws IOException {
        FXMLLoader loader = new FXMLLoader( getClass().getResource( "/app/account/account.fxml" ) );
        Parent fxmlInstance = loader.load();
        borderPane.setCenter(fxmlInstance);
//        Scene scene = new Scene( fxmlInstance, 800, 600 );

        // Make sure that you display "the correct account" based on which one you clicked on
            AccountController controller = loader.getController();
            controller.setAccount(id);

        // If you don't want to have/use the static variable Main.stage
//        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
//        Main.stage.setScene(scene);
        Main.stage.show();
    }

    @FXML
    public void generateAccounts() {
        account_box.getChildren().clear();
        LoginController.getUser().getAccountList().forEach(account -> {
            Button accountButton = new Button();
            String id = String.valueOf(account.getAccountNumber());
            accountButton.setId(id);
            accountButton.setMinWidth(200);
            accountButton.setText(account.toString());
            accountButton.setOnAction(actionEvent -> {
                try {
                    goToAccount(account.getAccountNumber());
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                asd.clickLoadTransactions(actionEvent, account.getAccountNumber());
            });
            account_box.getChildren().add(accountButton);
        });
    }

    void switchScene(String pathname) {
        try {
            FXMLLoader loader = new FXMLLoader( getClass().getResource( pathname) );
            Parent fxmlInstance = loader.load();
            borderPane.setCenter(fxmlInstance);

            Main.stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void addMenu() throws IOException {
        Menu m = new Menu("Menu");
        MenuItem m1 = new MenuItem("Create new transaction");
        MenuItem m2 = new MenuItem("Create account");
        MenuItem m3 = new MenuItem("Change account");
        m.getItems().add(m1);
        m.getItems().add(m2);
        m.getItems().add(m3);

        FXMLLoader loader = new FXMLLoader( getClass().getResource("/app/transaction/newTransaction/newTransaction.fxml") );
        Parent fxmlInstance = loader.load();
        m1.setOnAction(actionEvent -> {
            borderPane.setCenter(fxmlInstance);
        });

        FXMLLoader createAccount = new FXMLLoader( getClass().getResource( "/app/account/createAccount.fxml" ) );
        Parent createInstance = createAccount.load();
        m2.setOnAction(actionEvent -> {
            borderPane.setCenter(createInstance);
        });

        FXMLLoader change = new FXMLLoader( getClass().getResource("/app/account/updateAccount/updateAccount.fxml") );
        Parent changeInstance = change.load();
        m3.setOnAction(actionEvent -> borderPane.setCenter(changeInstance));

        MenuBar mb = new MenuBar();
        mb.getMenus().add(m);
        VBox vb = new VBox(mb);
        borderPane.setTop(vb);
    }

}

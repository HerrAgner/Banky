package app.home;

import app.Main;
import app.account.AccountController;
import app.account.updateAccount.UpdateAccount;
import app.db.DB;
import app.login.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class HomeController {
    LoginController login = new LoginController();

    @FXML
    public VBox account_box;

    @FXML
    VBox account_buttons;

    @FXML
    TitledPane bigAccountBox;

    @FXML
    VBox borderPaneMid;

    @FXML
    Button summary_button;

    @FXML
    Button card_payment;

    @FXML
    BorderPane borderPane;

    @FXML
    void initialize() throws IOException {
        addMenu();
        generateAccounts();
    }

    @FXML
    public void goToAccount(String id) throws IOException {
        FXMLLoader loader = new FXMLLoader( getClass().getResource( "/app/account/account.fxml" ) );
        Parent fxmlInstance = loader.load();
        borderPane.setCenter(fxmlInstance);
            AccountController controller = loader.getController();
            controller.setAccount(id);

//        Main.stage.show();
    }

    @FXML
    void cardPayment() {
        ArrayList<String> cardAccount = new ArrayList<>();
        LoginController.getUser().getAccountList().forEach(account -> {
            if(account.getType().equals("card")) {
                cardAccount.add(account.getAccountNumber());
            }
        });
        try {
            DB.newTransaction(cardAccount.get(0), "33334444", 200, "Card Payment");
        } catch (SQLException e) {
            System.out.println("You have no card account");
        }
    }

    @FXML
    public void generateAccounts() {
        account_buttons.getChildren().clear();
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
            });
            account_buttons.getChildren().add(accountButton);

            account.loadSaldotak();
        });
        bigAccountBox.setContent(account_buttons);
    }

    @FXML
    void addSummaryButton() {
            switchScene("/app/account/summary.fxml");

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

    private void addMenu(){
        Menu m = new Menu("Menu");
        MenuItem m1 = new MenuItem("Create new transaction");
        MenuItem m2 = new MenuItem("Create account");
        MenuItem m3 = new MenuItem("Change account");
        MenuItem m4 = new MenuItem("Saldotak");
        MenuItem m5 = new MenuItem("Config PG/BG");
        MenuItem m6 = new MenuItem("Show Scheduled events");
        m.getItems().addAll(m1,m2,m3,m4,m5,m6);

        m1.setOnAction(actionEvent -> switchScene("newTransaction"));
        m2.setOnAction(actionEvent -> switchScene("createAccount"));
        FXMLLoader change = new FXMLLoader( getClass().getResource("/app/account/updateAccount/updateAccount.fxml") );
        m3.setOnAction(actionEvent -> {
            UpdateAccount controller = change.getController();
            controller.loadAccounts();
            switchScene("updateAccount");
        });
        m4.setOnAction(actionEvent -> switchScene("saldotak"));
        m5.setOnAction(actionEvent -> switchScene("AddGiro"));
        m6.setOnAction(actionEvent -> switchScene("ScheduledEvents"));

        MenuBar mb = new MenuBar();
        mb.getMenus().add(m);
        VBox vb = new VBox(mb);
        borderPane.setTop(vb);
    }

}

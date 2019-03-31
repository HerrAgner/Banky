package app.account.updateAccount;

import app.Main;
import app.db.DB;
import app.login.LoginController;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class UpdateAccount {
    @FXML Accordion accordion;

    @FXML
    void initialize() {
    }

    public void loadAccounts() {
        accordion.getPanes().clear();
        LoginController.getUser().generateAccountsOnUser();
        LoginController.getUser().getAccountList().forEach(account -> {
            ComboBox type = new ComboBox();
            Label type1 = new Label("Saving");
            Label type2 = new Label("Sallary");
            Label type3 = new Label("Card");
            type.getItems().addAll(type1, type2, type3);

            Label accountName = new Label("Account name: ");
            Label accountType = new Label("Account type: ");
            Button confirm = new Button("Confirm");
            Button deleteAccount = new Button("Delete account");

            TitledPane pane = new TitledPane();
            TextField accName = new TextField();
            GridPane grid = new GridPane();

            confirm.setOnAction(actionEvent -> {
                Label label = (Label) type.getSelectionModel().getSelectedItem();
                DB.updateAccount(account.getAccountNumber(), accName.getText(), label.getText());
                Button accountButton = (Button)Main.stage.getScene().lookup("#"+account.getAccountNumber());
                accountButton.setText(String.format("%s %s %s", accName.getText(), account.getAccountNumber(), label.getText()));
                pane.setText(String.valueOf(account.getName()));
            });

            deleteAccount.setOnAction(actionEvent -> {
                DB.deleteAccount(account.getAccountNumber());
                LoginController.getUser().getAccountList().remove(account);
                VBox accountBox = (VBox) Main.stage.getScene().lookup("#account_buttons");
                accountBox.getChildren().remove(Main.stage.getScene().lookup("#"+account.getAccountNumber()));
                accordion.getPanes().remove(pane);
            });

            grid.setVgap(4);
            grid.setPadding(new Insets(5, 5, 5, 5));
            grid.add(accountName, 0, 0);
            grid.add(accName, 1, 0);
            grid.add(accountType, 0, 1);
            grid.add(type, 1, 1);
            grid.add(confirm, 1, 2);
            grid.add(deleteAccount, 1, 3);
            pane.setText(account.getName());
            pane.setContent(grid);

            accordion.getPanes().add(pane);
        });
    }
}

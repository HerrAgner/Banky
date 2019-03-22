package app.account;

import app.Entities.Account;
import app.Main;
import app.db.DB;
import app.home.HomeController;
import app.login.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class createAccount {

    @FXML
    VBox create_account;

    @FXML
    void initialize() {
        createAccount();
    }

    private void createAccount() {
        ComboBox type = new ComboBox();
        Label type1 = new Label("Saving");
        Label type2 = new Label("Sallary");
        Label type3 = new Label("Card");
        type.getItems().addAll(type1, type2, type3);

        Label accountName = new Label("Account name: ");
        Label accountType = new Label("Account type: ");
        Label accountNumberLabel = new Label("Account number: ");
        Button confirm = new Button("Confirm");
        TextField accNumber = new TextField();

        TitledPane pane = new TitledPane();
        TextField accName = new TextField();
        GridPane grid = new GridPane();

        confirm.setOnAction(actionEvent -> {
            Label label = (Label) type.getSelectionModel().getSelectedItem();
            DB.createAccount(Integer.parseInt(accNumber.getText()), accName.getText(), label.getText());
            Button accountButton = new Button();
            LoginController.getUser().generateAccountsOnUser();

            VBox accountBox = (VBox) Main.stage.getScene().lookup("#account_box");
            String id = String.valueOf(accNumber.getText());
            accountButton.setId(id);
            accountButton.setMinWidth(200);
            accountButton.setText(String.format("%s %s %s", accName.getText(), accNumber.getText(), label.getText()));

            BorderPane borderPane = (BorderPane) Main.stage.getScene().lookup("#borderPane");

            accountButton.setOnAction(actionEvent2 -> {
                try {
                    FXMLLoader loader = new FXMLLoader( getClass().getResource( "/app/account/account.fxml" ) );
                    Parent fxmlInstance = loader.load();
                    borderPane.setCenter(fxmlInstance);
                    AccountController controller = loader.getController();
                    controller.setAccount(Integer.parseInt(accNumber.getText()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            accountBox.getChildren().add(accountButton);
//            LoginController.getUser().getAccountList().add(new Account());
        });

        grid.setVgap(4);
        grid.setPadding(new Insets(5, 5, 5, 5));
        grid.add(accountName, 0, 0);
        grid.add(accName, 1, 0);
        grid.add(accountType, 0, 1);
        grid.add(type, 1, 1);
        grid.add(accountNumberLabel, 0, 2);
        grid.add(accNumber, 1, 2);
        grid.add(confirm,2,2);
        pane.setContent(grid);

        create_account.getChildren().add(grid);
    }
}

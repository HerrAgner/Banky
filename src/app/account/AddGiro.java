package app.account;

import app.Entities.Account;
import app.db.DB;
import app.db.Database;
import app.login.LoginController;
import app.transaction.newTransaction.NewTransaction;
import com.mysql.cj.log.Log;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddGiro {

    @FXML
    Button addGiroButton;
    @FXML
    Button fetchName;
    @FXML
    TextField textFieldGiro;
    @FXML
    Label giroName;
    @FXML
    ComboBox giroBox;
    @FXML
    Button removeGiro;

    Boolean isAccount;
    String currentAccount;

    @FXML
    void initialize() {
        fetchName.setOnAction(actionEvent -> fetchName());
        addGiroButton.setOnAction(actionEvent -> addAccount());
        giroBox.setOnShown(event -> NewTransaction.fillGiroBox(giroBox));
        removeGiro.setOnAction(actionEvent -> deleteGiroFromAccount());
    }

    private void fetchName() {
        try {
            CallableStatement cs = Database.getInstance().getConn().prepareCall("{call get_company_name(?)}");
            cs.setString(1, textFieldGiro.getText());
            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                giroName.setText(rs.getString(1));
                isAccount = true;
                currentAccount = textFieldGiro.getText();
            } else {
                giroName.setText("No account with that account number.");
                isAccount = false;
                currentAccount = "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addAccount() {
        if (isAccount && !currentAccount.equals("")) {
            try {
                CallableStatement cs = Database.getInstance().getConn().prepareCall("{call create_giro(?,?)}");
                cs.setString(1, LoginController.getUser().getId());
                cs.setString(2, currentAccount);
                cs.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Account account = DB.getAccount(currentAccount);
            LoginController.getUser().getGiroList().add(account);
            giroName.setText("Account added.");
        }
    }

    private void deleteGiroFromAccount() {
        if (!giroBox.getSelectionModel().isEmpty()) {
            LoginController.getUser().getGiroList().forEach(account -> {
                if (account.getCompany().equals(giroBox.getSelectionModel().getSelectedItem())) {
                    LoginController.getUser().getGiroList().remove(account);
                    try {
                        CallableStatement cs = Database.getInstance().getConn().prepareCall("{call delete_giro(?,?)}");
                        cs.setString(1, LoginController.getUser().getId());
                        cs.setString(2, account.getAccountNumber());
                        cs.execute();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}

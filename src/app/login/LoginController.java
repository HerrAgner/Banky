package app.login;

import app.Entities.User;
import app.Main;
import app.db.DB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginController {
    @FXML TextField usernameInput;
    @FXML TextField passwordInput;

    private static User user = null;
    public static User getUser() { return user; }

    @FXML
    private void initialize() {
        globalEventOn();
    }

    public void loadUser(){
        user = DB.getMatchingUser(usernameInput.getText(), passwordInput.getText());
        user.generateAccountsOnUser();
        user.generateGiroOnUser();
        if (user == null) {
            System.out.println("error");
        } else {
            goToHome();
        }
    }

    private void globalEventOn() {
        PreparedStatement ps = DB.prep("SET GLOBAL event_scheduler = ON;");
        try {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void switchScene(String pathname) {
        try {
            Parent bla = FXMLLoader.load(getClass().getResource(pathname));
            Scene scene = new Scene(bla, 840, 620);
            String darkmodeCss = this.getClass().getResource("/app/darkmode.css").toExternalForm();
            Main.stage.setScene(scene);
            Main.stage.getScene().getStylesheets().add(darkmodeCss);
            Main.stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @FXML void goToHome() { switchScene("/app/home/home.fxml"); }
}

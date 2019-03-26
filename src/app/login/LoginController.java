package app.login;


import app.Entities.User;
import app.Main;
import app.db.DB;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {


    @FXML
    TextField usernameInput;
    @FXML
    TextField passwordInput;

    // Use this in other Controllers to get "the currently logged in user".
    private static User user = null;
    public static User getUser() { return user; }

    @FXML
    private void initialize() {
//        System.out.println("initialize login");
    }

    public void loadUser(){
        user = DB.getMatchingUser(usernameInput.getText(), passwordInput.getText());
        user.generateAccountsOnUser();
        if (user == null) {
            System.out.println("error");
        } else {
            goToHome();
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

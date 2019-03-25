package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Calendar;
import java.util.TimeZone;

public class Main extends Application {
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stage = primaryStage;
        // First FXML that should be displayed is the Login
        // after successful login you should get transferred to Home
        Parent root = FXMLLoader.load(getClass().getResource("/app/login/login.fxml"));



        primaryStage.setTitle("Banky");
        primaryStage.setScene(new Scene(root));
        String darkmodeCss = this.getClass().getResource("/app/darkmode.css").toExternalForm();
        primaryStage.getScene().getStylesheets().add(darkmodeCss);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

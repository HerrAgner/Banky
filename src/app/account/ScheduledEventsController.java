package app.account;

import app.db.DB;
import app.login.LoginController;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javafx.scene.paint.Color.rgb;

public class ScheduledEventsController {

    @FXML
    VBox ScheduledEvents;

    @FXML
    HBox fx_button;
    int count = 0;

    @FXML
    void initialize() {
        ScheduledEvents.setSpacing(40);
        fx_button.getChildren().add(refresh());
        loadEvents();
    }

    private Button refresh() {
        Button refresh = new Button("Refresh");
        refresh.setOnAction(actionEvent -> loadEvents());
        return refresh;
    }

    private void loadEvents() {
        ScheduledEvents.getChildren().clear();
        PreparedStatement ps = DB.prep("SELECT * FROM INFORMATION_SCHEMA.EVENTS;");
        Pattern eventData = Pattern.compile("\\('([A-Za-z0-9]*)',\\d+.\\d+,'(\\d+)','(\\d+)','([a-zA-Z0-9åäö]+)");

        try {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String result = (String) rs.getObject("EVENT_DEFINITION");
                System.out.println(result);
                Matcher m = eventData.matcher(result);
                if (m.find()) {
                    LoginController.getUser().getAccountList().forEach(account -> {
                        if (account.getAccountNumber().equals(m.group(2)) || account.getAccountNumber().equals(m.group(3))) {
                            try {
                                createBox(m.group(1), m.group(2), rs.getString("CREATED"), m.group(4), rs.getString("INTERVAL_VALUE"), rs.getString("INTERVAL_FIELD"), m.group(3));
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void createBox(String name, String account, String created, String message, String interval, String field, String toAccount) {
        GridPane gp = new GridPane();
        gp.setVgap(10);
        gp.setHgap(40);
        if (count == 0) {
            count = 1;
            gp.setBackground(new Background(new BackgroundFill(rgb(60, 60, 60), CornerRadii.EMPTY, Insets.EMPTY)));
        } else {
            count = 0;
        }
        Label eventName = new Label("Name of event: " +message);
        Label eventCreated = new Label("Created: " + created);
        Label onAccount = new Label("On account: " + account);
        Label toAcc = new Label("To account: " + toAccount);
        Label intervalValue = new Label("Every " + interval + " " + field);
        Button removeEventButton = new Button("Remove");
        buttonAction(removeEventButton, name);
        gp.add(eventName, 0,0);
        gp.add(eventCreated,0,1);
        gp.add(onAccount,0,2);
        gp.add(toAcc,0,3);
        gp.add(intervalValue,0,4);
        gp.add(removeEventButton,1,0);
        ScheduledEvents.getChildren().add(gp);
    }

    private void buttonAction(Button button, String name) {
       button.setOnAction(actionEvent -> {
            PreparedStatement ps = DB.prep("DROP EVENT IF EXISTS "+ name);
            try {
                ps.execute();
                loadEvents();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}

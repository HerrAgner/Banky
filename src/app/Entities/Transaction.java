package app.Entities;


import app.annotations.Column;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Transaction {
    @Column("transaction_id")
    private int id;
    @Column("message")
    private String message;
    @Column("transaction_amount")
    private float amount;
    @Column("account_id")
    private String account_id;
    @Column("receiver_id")
    private String receiver;
    @Column("transaction_date_time")
    private Timestamp date;

    public String getMessage() { return message; }
    public float getAmount() { return amount; }

    public ZonedDateTime getDate() {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.of("Europe/Berlin"));
    }
    public String getDateAsString(){
        return getDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace('T', ' ');
    }


    public String getAccount_id() {
        return account_id;
    }

    public String getReceiver() {
        return receiver;
    }
    

    @Override
    public String toString() {
        return "date: "+getDateAsString()+", Amount: "+amount+", from: "+account_id+", to: "+receiver+", message: "+message;
    }
}

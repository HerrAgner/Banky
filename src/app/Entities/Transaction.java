package app.Entities;


import app.annotations.Column;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    @Column("transaction_id")
    private int id;
    @Column("message")
    private String message;
    @Column("transaction_amount")
    private float amount;
    @Column("account_id")
    private int account_id;
    @Column("receiver_id")
    private int receiver;
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

    public int getAccount_id() {
        return account_id;
    }

    public int getReceiver() {
        return receiver;
    }

    public Transaction() {

    }

    @Override
    public String toString() {
        return String.format("date: %s\n Amount: %.2f, from: %d, to: %d message: %s\n",date, id, amount, account_id, receiver, message);
    }
}

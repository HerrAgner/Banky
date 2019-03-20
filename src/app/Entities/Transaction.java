package app.Entities;


import app.annotations.Column;

import java.sql.Timestamp;
import java.time.LocalDate;

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

    public Timestamp getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("date: %s\nid %d, Amount: %.2f, from: %d, to: %d message: %s\n",date, id, amount, account_id, receiver, message);
    }
}

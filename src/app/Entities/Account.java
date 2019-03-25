package app.Entities;

import app.annotations.Column;

public class Account {


    @Column("account_number")
    private String accountNumber;
    @Column("balance")
    private double balance;
    @Column ("account_name")
    private String name;
    @Column("account_type")
    private String type;
    @Column ("person_id")
    private String owner;

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getOwner() {
        return owner;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", name , accountNumber, type);
    }
}

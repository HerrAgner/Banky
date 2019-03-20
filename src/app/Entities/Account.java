package app.Entities;

import app.annotations.Column;

public class Account {


    @Column("account_number")
    private int accountNumber;
    @Column("balance")
    private double balance;
    @Column ("account_name")
    private String name;
    @Column("account_type")
    private String type;
    @Column ("person_id")
    private String owner;

    public int getAccountNumber() {
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
        return String.format("%s\n%d\nbalance: %.2f\ntype: %s", name , accountNumber, balance, type);
    }
}

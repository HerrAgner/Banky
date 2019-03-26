package app.Entities;

import app.annotations.Column;
import app.db.DB;
import app.login.LoginController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    private double saldotak = 0;

    public double getSaldotak() {
        return saldotak;
    }

    public void setSaldotak(double saldotak) {
        this.saldotak = saldotak;
    }

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

    public void loadSaldotak() {
        PreparedStatement ps = DB.prep("SELECT saldotak FROM accounts WHERE account_number = "+accountNumber+";");
        try {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                saldotak = (double) rs.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", name , accountNumber, type);
    }
}

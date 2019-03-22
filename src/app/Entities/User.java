package app.Entities;


import app.annotations.Column;
import app.db.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class User {
    @Column("person_id")
    private String id;
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    private HashMap<Integer, Account> accounts;
    List<Account> accountList;

    @Override
    public String toString(){
        return String.format("User: { id: %s, name: %s %s}", id, firstName, lastName);
    }

    public User() {
    }

    public void generateAccountsOnUser(){
        accountList = (List<Account>) DB.getAccounts(id);
    }

    public List<Account> getAccountList() {
        return accountList;
    }
}

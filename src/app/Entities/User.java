package app.Entities;

import app.annotations.Column;
import app.db.DB;
import java.util.List;

public class User {
    @Column("person_id")
    private String id;
    @Column("first_name")
    private String firstName;
    @Column("last_name")
    private String lastName;
    List<Account> accountList;
    List<Account> giroList;

    @Override
    public String toString(){
        return String.format("User: { id: %s, name: %s %s}", id, firstName, lastName);
    }

    public String getId() {
        return id;
    }

    public void generateAccountsOnUser(){
        accountList = (List<Account>) DB.getAccounts(id);
    }

    public void generateGiroOnUser() {
        giroList = (List<Account>) DB.getGiro(id);
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public List<Account> getGiroList() {
        return giroList;
    }
}
package app.db;

import app.Entities.Account;
import app.Entities.User;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/** A Helper class for interacting with the Database using short-commands */
public abstract class DB {

    public static PreparedStatement prep(String SQLQuery){
        return Database.getInstance().prepareStatement(SQLQuery);
    }

    public static User getMatchingUser(String username, String password){
        User result = null;
        PreparedStatement ps = prep("SELECT * FROM persons WHERE person_id = ? AND password = ?");
        try {
            ps.setString(1, username);
            ps.setString(2, password);
            result = (User)new ObjectMapper<>(User.class).mapOne(ps.executeQuery());
            System.out.println(result);
        } catch (Exception e) { }
        return result; // return User;
    }

    public static List<?> getAccounts(String owner) {
        List<?> accounts = null;
        PreparedStatement ps = prep("SELECT * FROM accounts WHERE person_id = ?");
        try {
            ps.setString(1, owner);
            accounts = new ObjectMapper<>(Account.class).map(ps.executeQuery());
        } catch (Exception e) { }
        return accounts;
    }

    /*
        Example method with default parameters
    public static List<Transaction> getTransactions(int accountId){ return getTransactions(accountId, 0, 10); }
    public static List<Transaction> getTransactions(int accountId, int offset){ return getTransactions(accountId, offset, offset + 10); }
    public static List<Transaction> getTransactions(int accountId, int offset, int limit){
        List<Transaction> result = null;
        PreparedStatement ps = prep("bla bla from transactions WHERE account-id = "+accountId+" OFFSET "+offset+" LIMIT "+limit);
        try {
            result = (List<Transaction>)new ObjectMapper<>(Transaction.class).map(ps.executeQuery());
        } catch (Exception e) { e.printStackTrace(); }
        return result; // return User;
    }
    */


}
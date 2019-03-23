package app.db;

import app.Entities.Account;
import app.Entities.Transaction;
import app.Entities.User;
import app.login.LoginController;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/**
 * A Helper class for interacting with the Database using short-commands
 */
public abstract class DB {

    public static PreparedStatement prep(String SQLQuery) {
        return Database.getInstance().prepareStatement(SQLQuery);
    }

    public static User getMatchingUser(String username, String password) {
        User result = null;
        PreparedStatement ps = prep("SELECT * FROM persons WHERE person_id = ? AND password = ?");
        try {
            ps.setString(1, username);
            ps.setString(2, password);
            result = (User) new ObjectMapper<>(User.class).mapOne(ps.executeQuery());
            System.out.println(result);
        } catch (Exception e) {
        }
        return result; // return User;
    }

    public static List<?> getAccounts(String owner) {
        List<?> accounts = null;
        PreparedStatement ps = prep("SELECT * FROM accounts WHERE person_id = ?");
        try {
            ps.setString(1, owner);
            accounts = new ObjectMapper<>(Account.class).map(ps.executeQuery());
        } catch (Exception e) {
        }
        return accounts;
    }

    public static Account getAccount(long account) {
        Account returnAccount = null;
        PreparedStatement ps = prep("SELECT * FROM accounts WHERE account_number = ?");
        try {
            ps.setLong(1, account);
            returnAccount = (Account) new ObjectMapper<>(Account.class).mapOne(ps.executeQuery());
        } catch (Exception e) {
        }
        return returnAccount;
    }

    public static void updateAccount(int accNumber, String name, String type) {
        CallableStatement stmt = null;
        try {
            stmt = Database.getInstance().getConn().prepareCall("{call update_account(?,?,?)}");
            stmt.setInt(1, accNumber);
            stmt.setString(2, name);
            stmt.setString(3, type);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createAccount(int accNumber, String name, String type) {
        CallableStatement stmt = null;
        try {
            stmt = Database.getInstance().getConn().prepareCall("{call create_account(?,?,?,?,?)}");
            stmt.setInt(1, accNumber);
            stmt.setString(2, LoginController.getUser().getAccountList().get(0).getOwner());
            stmt.setDouble(3, 0);
            stmt.setString(4, type);
            stmt.setString(5, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAccount(int accNumber) {
        CallableStatement stmt = null;
        try {
            stmt = Database.getInstance().getConn().prepareCall("{call delete_account(?)}");
            stmt.setInt(1, accNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void newTransaction(int from, int to, float amount, String message) throws SQLException {
        CallableStatement stmt = null;
        stmt = Database.getInstance().getConn().prepareCall("{call transaction(?,?,?,?,?,?)}");
        stmt.setString(1, "transaction");
        stmt.setFloat(2, amount);
        stmt.setInt(3, from);
        stmt.setInt(4, to);
        stmt.setString(5, message);
        stmt.executeUpdate();
    }

    public static void scheduledTransaction(String name, String schedule, int from, int to, float amount, String message) {
        String eventname = name;
        String schdl = schedule;
        try {
//            PreparedStatement stm = Database.getInstance().getConn().prepareStatement("CREATE EVENT dfgh ON SCHEDULE EVERY 1 MONTH DO UPDATE accounts SET account_name = yolsdo WHERE account_number = 321321321;");
            PreparedStatement stmt = Database.getInstance().getConn().prepareStatement("CREATE EVENT "+eventname+" ON SCHEDULE "+schdl+" DO CALL transaction(?,?,?,?,?);");
            stmt.setString(1, eventname);
            stmt.setFloat(2, amount);
            stmt.setInt(3, from);
            stmt.setInt(4, to);
            stmt.setString(5, message);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<?> getTransactions(int accountId) {
        return getTransactions(accountId, 0, 10);
    }

    public static List<?> getTransactions(int accountId, int offset) {
        return getTransactions(accountId, offset, offset + 10);
    }

    public static List<?> getTransactions(int accountId, int offset, int limit) {
        List<?> result = null;
        PreparedStatement ps = prep("SELECT * FROM transactions WHERE account_id = ? OR receiver_id = ? ORDER BY transaction_date_time DESC LIMIT " + limit + " OFFSET " + offset);
        try {
            ps.setInt(1, accountId);
            ps.setInt(2, accountId);
            result = new ObjectMapper<>(Transaction.class).map(ps.executeQuery());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}




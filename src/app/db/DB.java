package app.db;

import app.Entities.Account;
import app.Entities.Transaction;
import app.Entities.User;
import app.login.LoginController;
import javafx.application.Platform;
import javafx.scene.control.DatePicker;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
//            System.out.println(result);
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

    public static Account getAccount(String account) {
        Account returnAccount = null;
        PreparedStatement ps = prep("SELECT * FROM accounts WHERE account_number = ?");
        try {
            ps.setString(1, account);
            returnAccount = (Account) new ObjectMapper<>(Account.class).mapOne(ps.executeQuery());
        } catch (Exception e) {
        }
        return returnAccount;
    }

    public static void updateAccount(String accNumber, String name, String type) {
        CallableStatement stmt = null;
        try {
            stmt = Database.getInstance().getConn().prepareCall("{call update_account(?,?,?)}");
            stmt.setString(1, accNumber);
            stmt.setString(2, name);
            stmt.setString(3, type);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createAccount(String accNumber, String name, String type) {
        CallableStatement stmt = null;
        try {
            stmt = Database.getInstance().getConn().prepareCall("{call create_account(?,?,?,?,?)}");
            stmt.setString(1, accNumber);
            stmt.setString(2, LoginController.getUser().getAccountList().get(0).getOwner());
            stmt.setDouble(3, 0);
            stmt.setString(4, type);
            stmt.setString(5, name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAccount(String accNumber) {
        CallableStatement stmt = null;
        try {
            stmt = Database.getInstance().getConn().prepareCall("{call delete_account(?)}");
            stmt.setString(1, accNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void newTransaction(String from, String to, float amount, String message) throws SQLException {
        CallableStatement stmt = null;
        stmt = Database.getInstance().getConn().prepareCall("{call transaction(?,?,?,?,?)}");
        stmt.setString(1, "transaction");
        stmt.setFloat(2, amount);
        stmt.setString(3, from);
        stmt.setString(4, to);
        stmt.setString(5, message);
        stmt.executeUpdate();
    }

    public static void scheduledTransaction(String name, String schedule, String from, String to, double amount, String message) {
        try {
            PreparedStatement stmt = Database.getInstance().getConn().prepareStatement("CREATE EVENT " + name + " ON SCHEDULE " + schedule + " DO CALL transaction(?,?,?,?,?);");
            stmt.setString(1, name);
            stmt.setDouble(2, amount);
            stmt.setString(3, from);
            stmt.setString(4, to);
            stmt.setString(5, message);
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean saldotak(String accNr, double saldotak, int days, double amount) {
        PreparedStatement stmt = null;
        try {
            stmt = prep("SELECT banky.saldotakValidation(?,?,?,?)");
            stmt.setString(1,accNr);
            stmt.setDouble(2,saldotak);
            stmt.setInt(3,days);
            stmt.setDouble(4,amount);
            ResultSet action = stmt.executeQuery();
            if (action.next()) {
                if ( action.getInt(1) == 1) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

        public static boolean validateInsert (String name){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            PreparedStatement stmt = prep("SELECT * FROM transactions WHERE transaction_type = ?;");
            try {
                stmt.setString(1, name);
                ResultSet as = stmt.executeQuery();
                if (as.next()) {
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        public static List<?> getTransactions (String accountId){
            return getTransactions(accountId, 0, 10);
        }

        public static List<?> getTransactions (String accountId,int offset){
            return getTransactions(accountId, offset, offset + 10);
        }

        public static List<?> getTransactions (String accountId,int offset, int limit){
            List<?> result = null;
            PreparedStatement ps = prep("SELECT * FROM transactions WHERE account_id = ? OR receiver_id = ? ORDER BY transaction_date_time DESC LIMIT " + limit + " OFFSET " + offset);
            try {
                ps.setString(1, accountId);
                ps.setString(2, accountId);
                result = new ObjectMapper<>(Transaction.class).map(ps.executeQuery());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }
    }




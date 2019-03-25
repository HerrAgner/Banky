package app.db;

import java.sql.*;
import java.util.HashMap;

public class Database {
    private static Database ourInstance = new Database();
    public static Database getInstance() {
        return ourInstance;
    }
    private Database() { connectToDb(); }

    final String connectionURL = "jdbc:mysql://localhost/banky?user=root&password=mysql&serverTimezone=Europe/Berlin";
    private Connection conn = null;
    private HashMap<String, PreparedStatement> preparedStatements = new HashMap<>();


    /** Returns a cached PreparedStatement if possible, else caches it for future use */
    public PreparedStatement prepareStatement(String SQLQuery){
        PreparedStatement ps = preparedStatements.get(SQLQuery);
        if (ps == null) {
            try { ps = conn.prepareStatement(SQLQuery); }
            catch (SQLException e) { e.printStackTrace(); }
        }
        return ps;
    }

    public Connection getConn() {
        return conn;
    }

    private void connectToDb(){
        try { conn = DriverManager.getConnection(connectionURL); }
        catch (SQLException e) { e.printStackTrace(); }
    }
}

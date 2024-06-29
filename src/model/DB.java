package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


public class DB {
    // config for mysql connection
    private String url = "jdbc:mysql://localhost:3306/db_tmd?user=root&password=";
    private Statement stmt = null; // query connection
    private ResultSet rs = null; // result
    private Connection conn = null; // mysql connection

    // Constructor
    public DB() throws Exception, SQLException {
        try {
            // creating driver mysql
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            // creating mysql connection
            conn = DriverManager.getConnection(url);
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
        } catch (SQLException e) {
            // throw error when connection failed
            throw e;
        }
    }

    public void createQuery(String query) throws Exception, SQLException {
        // executing query without manipulating data
        try {
            stmt = conn.createStatement();
            // query execution
            rs = stmt.executeQuery(query);
            if (stmt.execute(query)) {
                // get result
                rs = stmt.getResultSet();
            }
        } catch (SQLException e) {
            // throw error when query execution failed
            throw e;
        }
    }

    public void createUpdate(String query) throws Exception, SQLException {
        // executing query for manipulating data
        try {
            stmt = conn.createStatement();
            // query execution
            stmt.executeUpdate(query);
        } catch (SQLException e) {
            // throw error when query execution failed
            throw e;
        }
    }

    public ResultSet getResult() throws Exception {
        ResultSet temp = null;
        try {
            return rs;
        } catch (Exception e) {
            return temp;
        }
    }

    public void closeResult() throws Exception, SQLException {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                rs = null;
                throw e;
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                stmt = null;
                throw e;
            }
        }
    }

    public void closeConnection() throws Exception, SQLException {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                conn = null;
                throw e;
            }
        }
    }

}
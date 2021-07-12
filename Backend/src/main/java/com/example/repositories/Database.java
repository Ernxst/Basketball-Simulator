package com.example.repositories;

import com.example.api.AppLogger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Wrapper class to abstract database operation from the rest of the backend.
 */
public class Database {
    private Connection connection;
    private int ERRORS = 0;

    public Database() {
        try {
            connect();
            AppLogger.log("Connected to database");
        } catch (ClassNotFoundException e) {
            System.err.println("Could not load JDBC driver");
            System.err.println("Exception: " + e);
            e.printStackTrace();
            ERRORS += 1;
        } catch (SQLException e) {
            System.err.println("Could not connect to database");
            System.err.println("Exception: " + e);
            e.printStackTrace();
            ERRORS += 1;
        }
    }

    public static Database getInstance() {
        return DatabaseFactory.INSTANCE;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * @throws ClassNotFoundException if the maven driver package is not included.
     * @throws SQLException           if the connection to the cloud database fails.
     */
    private void connect() throws ClassNotFoundException, SQLException {
        // TODO - Hide backend.db login details
        String URL = "jdbc:db2://dashdb-txn-sbox-yp-lon02-04.services.eu-gb.bluemix.net:50001/BLUDB:user=USERNAME;password=PASSWORD;sslConnection=true;";
        String username = "xhq93883";
        String password = "2vw13s204kc5@b2v";
        URL = URL.replace("USERNAME", username).replace("PASSWORD", password);
        Class.forName("com.ibm.db2.jcc.DB2Driver");
        connection = DriverManager.getConnection(URL);
        // Do not autosave on command execution.
        connection.setAutoCommit(false);
    }

    private void outputSQLException(SQLException ex) {
        System.err.println("SQLException information");
        while (ex != null) {
            ERRORS += 1;
            System.err.println("Error msg: " + ex.getMessage());
            System.err.println("SQLSTATE: " + ex.getSQLState());
            System.err.println("Error code: " + ex.getErrorCode());
            ex.printStackTrace();
            ex = ex.getNextException(); // For drivers that support chained exceptions
        }
    }

    public void close() {
        AppLogger.log("Shutting down");
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException ex) {
            outputSQLException(ex);
        }
        AppLogger.log("Database connection successfully shutdown");
        AppLogger.log("Backend ran into " + ERRORS + " exceptions during execution.");
        System.out.println("Goodbye");
    }

    /**
     * Factory design pattern to ensure singleton access
     */
    private static class DatabaseFactory {
        private static final Database INSTANCE = new Database();
    }
}

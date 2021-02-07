package com.todos.api.utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static ConnectionManager connectionManager = null;
    private Connection connection;

    private ConnectionManager(String dbURL, String dbUser, String pwd, String dbDriver) throws ClassNotFoundException, SQLException {
        Class.forName(dbDriver);
        this.connection = DriverManager.getConnection(dbURL, dbUser, pwd);
    }

    public static ConnectionManager createConnectionManager(String dbURL, String dbUser, String pwd, String dbDriver) throws SQLException, ClassNotFoundException {
        if(connectionManager == null)
            connectionManager = new ConnectionManager(dbURL, dbUser, pwd, dbDriver);
        return connectionManager;
    }

    public Connection getConnection() {
        return this.connection;
    }
}

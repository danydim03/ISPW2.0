package org.example.dao_manager;

import org.example.PropertiesHandler;
import org.example.exceptions.PropertyException;
import org.example.exceptions.ResourceNotFoundException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection instance;
    private final Connection connection;

    private DBConnection() throws SQLException, ResourceNotFoundException, PropertyException {
        String username = PropertiesHandler.getInstance().getProperty("username");
        String password = PropertiesHandler.getInstance().getProperty("password");
        String url = PropertiesHandler.getInstance().getProperty("url");
        this.connection = DriverManager.getConnection(url, username, password);
    }

    public static synchronized DBConnection getInstance() throws SQLException, ResourceNotFoundException, PropertyException {
        if (instance == null) {
            instance = new DBConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}

package com.distributed.core.context;


import com.distributed.domain.entity.Producer;
import com.distributed.domain.entity.Product;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

public class DatabaseContext implements IDatabaseContext {
    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/software";
    private static final String USER = "postgres";
    private static final String PASS = "password";
    private static Connection connection;

    public DatabaseContext() {
        init();
    }

    private void init() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASS);
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet executeQuery(String query) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            return statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

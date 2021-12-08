package com.distributed.core.context;


import com.distributed.domain.entity.Producer;
import com.distributed.domain.entity.Product;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseContext implements IContext {
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/software";
    static final String USER = "postgres";
    static final String PASS = "password";

    public DatabaseContext() {
        init();
    }

    private void init() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Producer> getProducers() {
        return null;
    }

    @Override
    public ArrayList<Product> getProducts() {
        return null;
    }

    @Override
    public void save() {

    }
}

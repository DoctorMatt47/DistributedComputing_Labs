package com.distributed;


import com.distributed.infrastructure.container.DependencyContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Main {
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/software";
    static final String USER = "postgres";
    static final String PASS = "password";

    public static void main(String[] args) {
        var container = new DependencyContainer();
        container.buildController();
    }
}

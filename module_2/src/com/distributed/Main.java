package com.distributed;


import com.distributed.infrastructure.container.ApplicationBuilder;

import java.io.IOException;


public class Main {
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/software";
    static final String USER = "postgres";
    static final String PASS = "password";

    public static void main(String[] args) throws IOException {
        var container = new ApplicationBuilder();
        container.build();
    }
}

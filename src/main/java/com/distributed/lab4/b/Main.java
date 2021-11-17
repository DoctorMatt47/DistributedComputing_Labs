package com.distributed.lab4.b;

import java.io.IOException;
import java.io.PrintStream;

public class Main {
    public static void main(String[] args) {
        Garden garden = new Garden(10, 10);

        new Thread(new Gardener(garden)).start();
        new Thread(new Nature(garden)).start();
        new Thread(new Printer(garden, System.out)).start();

        try (PrintStream stream = new PrintStream("src\\main\\resources\\output.txt")) {
            new Printer(garden, stream).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

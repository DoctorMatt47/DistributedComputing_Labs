package com.distributed.lab4.c;

import java.io.IOException;
import java.io.PrintStream;

public class Main {
    public static void main(String[] args) {
        Garden garden = new Garden(10, 10);

        new Thread(new Gardener(garden)).start();
        new Thread(new Nature(garden)).start();
        new Thread(new Printer(garden, "ConsolePrinter", System.out)).start();

        try (PrintStream stream = new PrintStream("output.txt")) {
            new Printer(garden, "FilePrinter", stream).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

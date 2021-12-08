package com.distributed.lab3.b;

public class Main {
    public static void main(String[] args) {
        new Thread(Barber.barber).start();
        for (int i = 0; i <  5; i++) {
            new Thread(new Customer(i)).start();
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {
            }
        }
    }
}

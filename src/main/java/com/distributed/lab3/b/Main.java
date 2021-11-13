package com.distributed.lab3.b;

public class Main {
    public static void main(String[] args) {
        new Thread(Barber.barber).start();
        for (int i = 0; i < 10; i++) {
            new Thread(new Customer(i, i * 100)).start();
        }
        for (int i = 0; i < 10; i++) {
            new Thread(new Customer(10 + i, 1000 + i * 500)).start();
        }
    }
}

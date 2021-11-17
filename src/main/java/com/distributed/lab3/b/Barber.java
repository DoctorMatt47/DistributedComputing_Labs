package com.distributed.lab3.b;

import java.util.concurrent.Semaphore;

public class Barber {
    public static final Semaphore queueSem = new Semaphore(0, true);
    public static final Semaphore customersSem = new Semaphore(0);
    public static final Semaphore chairSem = new Semaphore(0);
    public static final Semaphore haircutSem = new Semaphore(1);

    public static volatile Customer currentCustomer = null;
    
    public static final Runnable barber = () -> {
        while (true) {
            // Sleeping
            try {
                customersSem.acquire();
            } catch (InterruptedException ignored) {
            }

            System.out.println("Barber: Lets the customer in.");
            queueSem.release();

            System.out.println("Barber: Waiting for customer sit down.");
            try {
                chairSem.acquire();
            } catch (InterruptedException ignored) {
            }

            System.out.printf("Barber: Cutting customer %d hair.\n", currentCustomer.getId());

            try {
                Thread.sleep(1500);
            } catch (InterruptedException ignored) {
            }

            System.out.printf("Barber: Seeing off customer %d.\n", currentCustomer.getId());

            try {
                Thread.sleep(200);
            } catch (InterruptedException ignored) {
            }

            currentCustomer = null;
            haircutSem.release();
        }
    };
}

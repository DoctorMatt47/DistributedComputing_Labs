package com.distributed.lab3.b;

import java.util.concurrent.Semaphore;

public class Barber {
    //binary semaphores
    //set to fair = true which ensures FIFO
    public static final Semaphore barberReady = new Semaphore(0, true);
    public static final Semaphore customersReady = new Semaphore(0);

    public static final Semaphore haircutReady = new Semaphore(0);
    public static final Semaphore haircutDone = new Semaphore(0);

    public static volatile Customer currentCustomer = null;
    
    public static final Runnable barber = () -> {
        while (true) {
            try {
                customersReady.acquire();
            } catch (InterruptedException ignored) {
            }

            barberReady.release();
            try {
                haircutReady.acquire();
            } catch (InterruptedException ignored) {
            }

            System.out.printf("[Barber] Serving customer %d...\n", currentCustomer.getId());

            try {
                Thread.sleep(1500);
            } catch (InterruptedException ignored) {
            }

            System.out.printf("[Barber] Haircut complete for customer %d.\n", currentCustomer.getId());
            currentCustomer = null;
            haircutDone.release();
        }
    };
}

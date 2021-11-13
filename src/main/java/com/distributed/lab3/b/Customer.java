package com.distributed.lab3.b;

public class Customer implements Runnable {
    private final int id;
    private final int arrivalTime;

    public Customer(int id, int arrivalTimeMillis) {
        this.id = id;
        this.arrivalTime = arrivalTimeMillis;
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(arrivalTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Barber.customersReady.release();

        System.out.printf("[Customer %d] Arrived at shop.\n", getId());

        try {
            Barber.barberReady.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Barber.currentCustomer = this;
        Barber.haircutReady.release();
        System.out.printf("[Customer %d] Starting to get haircut...\n", getId());

        try {
            Barber.haircutDone.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.printf("[Customer %d] Got haircut, leaving.\n", getId());
    }
}

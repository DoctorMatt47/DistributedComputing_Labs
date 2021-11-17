package com.distributed.lab3.b;

public class Customer implements Runnable {


    private final int id;

    public Customer(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public void run() {
        System.out.printf("Customer %d: Arrived at barbershop.\n", getId());
        // Wakes up barber
        Barber.customersSem.release();

        try {
            // Staying in the queue
            Barber.queueSem.acquire();
        } catch (InterruptedException ignored) {
        }
        Barber.currentCustomer = this;

        System.out.printf("Customer %d: Sit on the chair.\n", getId());
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Barber.chairSem.release();

        try {
            Barber.haircutSem.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

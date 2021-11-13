package com.distributed.lab3.c;

public class MySemaphore {
    private volatile int permits;

    public MySemaphore(int permits) {
        this.permits = permits;
    }

    public synchronized void acquire() throws InterruptedException {
        while (permits <= 0) {
            wait();
        }

        permits--;
    }

    public synchronized void release() {
        permits++;

        if (permits > 0) {
            notifyAll();
        }
    }

    public synchronized int availablePermits() {
        return permits;
    }
}

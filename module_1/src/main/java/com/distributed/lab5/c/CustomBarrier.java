package com.distributed.lab5.c;

public class CustomBarrier {
    int initialParties;
    int partiesAwait;
    Runnable cyclicBarrierEvent;


    public CustomBarrier(int parties, Runnable cyclicBarrierEvent) {
        initialParties = parties;
        partiesAwait = parties;
        this.cyclicBarrierEvent = cyclicBarrierEvent;
    }


    public synchronized void await() throws InterruptedException {
        //decrements awaiting parties by 1.
        partiesAwait--;

        //If the current thread is not the last to arrive, thread will wait.
        if (partiesAwait > 0) {
            this.wait();
        } else {
            partiesAwait = initialParties;

            notifyAll();

            cyclicBarrierEvent.run();
        }
    }
}

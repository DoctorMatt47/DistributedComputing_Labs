package com.distributed.lab4.b;

public class ReadWriteLock {
    private int readers = 0;

    synchronized void lockWriting() throws InterruptedException {
        while (readers != 0) {
            wait();
        }
        readers = -1;
    }

    synchronized void lockReading() throws InterruptedException {
        while (readers == -1) {
            wait();
        }
        readers++;
    }

    synchronized void unlockWriting() throws IllegalMonitorStateException {
        if (readers != -1) {
            throw new IllegalMonitorStateException("Tried to unlock write lock when nobody is writing.");
        } else {
            readers = 0;
            notifyAll();
        }
    }

    synchronized void unlockReading() throws IllegalMonitorStateException {
        if (readers <= 0) {
            throw new IllegalMonitorStateException("Tried to unlock read lock when nobody is reading.");
        } else {
            readers--;
            notifyAll();
        }
    }
}

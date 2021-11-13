package com.distributed.lab4.c;

import java.util.Random;

public class Nature implements Runnable {
    private final Garden garden;
    private final Random rng = new Random();

    public Nature(Garden garden) {
        this.garden = garden;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(200);
                System.err.println("[Nature] Woke up, getting write lock...");
                garden.getLocker().lockWriting();
            } catch (InterruptedException ignored) {}

            int x = rng.nextInt(garden.getWidth());
            int y = rng.nextInt(garden.getHeight());
            System.err.printf("[Nature] Plant (%d; %d) is wilting...\n", x, y);

            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {}
            garden.setPlantFreshness(x, y, false);
            System.err.printf("[Nature] Plant (%d; %d) has wilted.\n", x, y);

            garden.getLocker().unlockWriting();
        }
    }
}

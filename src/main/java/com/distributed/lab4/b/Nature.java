package com.distributed.lab4.b;

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
                Thread.sleep(1000);
                System.out.println("Nature: Waking up.");
                garden.getLocker().lockWriting();
            } catch (InterruptedException ignored) {}

            int x = rng.nextInt(garden.getWidth());
            int y = rng.nextInt(garden.getHeight());
            System.out.printf("    Nature: Plant (%d; %d) is wilting\n", x, y);

            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {}
            garden.setPlantFreshness(x, y, false);
            System.out.printf("    Nature: Plant (%d; %d) has wilted.\n", x, y);

            System.out.println("Nature: Going to sleep.");
            garden.getLocker().unlockWriting();
        }
    }
}

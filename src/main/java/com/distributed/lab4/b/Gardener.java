package com.distributed.lab4.b;

public class Gardener implements Runnable {
    private final Garden garden;
    
    public Gardener(Garden garden) {
        this.garden = garden;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(2000);
                System.out.println("Gardener: Waking up.");
                garden.getLocker().lockWriting();
            } catch (InterruptedException ignored) {}

            System.out.println("Gardener: Maintaining freshness.");

            for (int x = 0; x < garden.getWidth(); x++) {
                for (int y = 0; y < garden.getHeight(); y++) {

                    if (!garden.isPlantFresh(x, y)) {
                        System.out.printf("    Gardener: Watering plant at (%d; %d).\n", x, y);
                        garden.setPlantFreshness(x, y, true);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ignored) {}
                        System.out.printf("    Gardener: Watered plant at (%d; %d).\n", x, y);
                    }

                }
            }

            System.out.println("Gardener: Going to sleep.");
            garden.getLocker().unlockWriting();
        }
    }
}

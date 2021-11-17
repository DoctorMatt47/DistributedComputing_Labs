package com.distributed.lab4.b;

import java.io.PrintStream;

public class Printer implements Runnable {
    private final Garden garden;
    private final PrintStream stream;

    public Printer(Garden garden, PrintStream stream) {
        this.garden = garden;
        this.stream = stream;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(5000);
                System.out.print("Printer: Waking up.\n");
                garden.getLocker().lockReading();
            } catch (InterruptedException ignored) {}

            System.out.print("Printer: Gathering garden information.\n");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}

            stream.println();
            for (int y = 0; y < garden.getHeight(); y++) {
                for (int x = 0; x < garden.getWidth(); x++) {
                    if (garden.isPlantFresh(x, y))
                        stream.print("+");
                    else
                        stream.print("X");
                }
                stream.println();
            }
            stream.println();
            stream.flush();
            System.out.print("Printer: Going to sleep.\n");
            garden.getLocker().unlockReading();
        }
    }
}

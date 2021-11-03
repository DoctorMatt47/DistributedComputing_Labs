package com.distributed.lab2.a;

@SuppressWarnings("ClassCanBeRecord")
public class BeeFlockTask implements Runnable {
    private final int x;
    private final int y;
    private final Forest forest;
    private final BagOfTasks tasks;

    public BeeFlockTask(Forest forest, int x, int y, BagOfTasks tasks) {
        this.forest = forest;
        this.x = x;
        this.y = y;
        this.tasks = tasks;
    }

    @Override
    public void run() {
        printMessage("Search for winnie started.");
        if (forest.checkWinnie(x, y)) {
            printMessage("Winnie was found. We proceed to punish.");
            tasks.setFoundPooh();
            return;
        }
        printMessage("Winnie was not found");
        printMessage("Returning to the hive");
    }

    private void printMessage(String message) {
        System.out.printf("Bee flock %d at (%d; %d): %s%n", Thread.currentThread().getId(), x, y, message);
    }
}


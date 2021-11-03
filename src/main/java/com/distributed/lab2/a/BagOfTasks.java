package com.distributed.lab2.a;

public class BagOfTasks {
    private int currentX = 0;
    private int currentY = 0;
    private boolean isWinnieFound = false;
    private final Forest forest;

    public BagOfTasks(Forest forest) {
        this.forest = forest;
    }

    public synchronized BeeFlockTask getTask() {
        if (isWinnieFound || currentY == Forest.HEIGHT)
            return null;

        var task = new BeeFlockTask(forest, currentX, currentY, this);
        currentX++;
        if (currentX == Forest.WIDTH) {
            currentX = 0;
            currentY++;
        }
        return task;
    }

    public synchronized void setFoundPooh() {
        isWinnieFound = true;
    }
}

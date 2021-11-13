package com.distributed.lab4.c;


public class Garden {
    private final boolean[][] plants;
    private final int width;
    private final int height;
    private final ReadWriteLock locker = new ReadWriteLock();
    
    public Garden(int width, int height) {
        this.width = width;
        this.height = height;
        this.plants = new boolean[height][];
        for (int i = 0; i < height; i++)
            plants[i] = new boolean[width];
    }
    
    public boolean isPlantFresh(int x, int y) {
        return !plants[y][x];
    }

    public void setPlantFreshness(int x, int y, boolean isFresh) {
        plants[y][x] = !isFresh;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public ReadWriteLock getLocker() {
        return locker;
    }
}

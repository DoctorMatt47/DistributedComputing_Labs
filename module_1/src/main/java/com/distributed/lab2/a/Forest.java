package com.distributed.lab2.a;

import java.util.Random;

public class Forest {
    public static int MAX_SLEEP_TIME = 5000;
    public static int HEIGHT = 4;
    public static int WIDTH = 4;

    private final int winnie_x;
    private final int winnie_y;

    public Forest() {
        var random = new Random();
        winnie_x = random.nextInt(WIDTH);
        winnie_y = random.nextInt(HEIGHT);
    }

    public boolean checkWinnie(int x, int y) {
        var random = new Random();
        try {
            Thread.sleep(random.nextInt(MAX_SLEEP_TIME));
        } catch (InterruptedException ignored) {
        }
        return x == winnie_x && y == winnie_y;
    }
}

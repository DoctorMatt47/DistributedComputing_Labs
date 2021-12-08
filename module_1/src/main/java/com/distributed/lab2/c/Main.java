package com.distributed.lab2.c;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class Main {
    private static final int MONK_COUNT = 32;

    public static void main(String[] args) {
        var fightersXi = new ArrayList<Integer>(MONK_COUNT);
        for (int i = 0; i < MONK_COUNT; i++)
            fightersXi.add(new Random().nextInt(100 * MONK_COUNT) + 100);
        Collections.shuffle(fightersXi);

        int winnerIndex = ForkJoinPool.commonPool().invoke(new Tournament(fightersXi));

        System.out.printf("Tournament winner is %d with Xi: %d\n", winnerIndex, fightersXi.get(winnerIndex));
    }
}

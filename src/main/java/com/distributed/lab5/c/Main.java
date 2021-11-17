package com.distributed.lab5.c;

public class Main {
    private static final int THREAD_COUNT = 3;
    private static final int ARRAY_LENGTH = 5;
    private static final int AVG_ELEMENT = 10;
    
    private static final int[][] arrays = new int[THREAD_COUNT][];
    private static long[] nextArraySums = new long[THREAD_COUNT];
    private static long[] arraySums = new long[THREAD_COUNT];
    
    public static void main(String[] args) {
        var barrier = new CyclicBarrier(THREAD_COUNT, () -> {
            long[] swap = nextArraySums;
            nextArraySums = arraySums;
            arraySums = swap;
            
            for (var i = 0; i < THREAD_COUNT; i++) {
                System.out.printf("Thread %d: [ ", i);
                for (var element : arrays[i])
                    System.out.printf("%d, ", element);
                System.out.printf("] sum = %d; ", arraySums[i]);
            }
            System.out.println();
        });


        for (var threadNum = 0; threadNum < THREAD_COUNT; threadNum++) {
            var i = threadNum;
            arrays[i] = new int[ARRAY_LENGTH];
            for (var j = 0; j < ARRAY_LENGTH; j++)
                arrays[i][j] = (int)(Math.random() * AVG_ELEMENT) * 2;


            new Thread(() -> {
                while (!Thread.interrupted()) {
                    nextArraySums[i] = 0;
                    for (var element : arrays[i])
                        nextArraySums[i] += element;

                    try {
                        Thread.sleep(1000);
                        barrier.await();
                    } catch (InterruptedException e) {
                        return;
                    }
                    
                    var equal = true;
                    var isBiggest = true;
                    var isSmallest = true;
                    for (var j = 0; j < THREAD_COUNT; j++) {
                        if (i == j) continue;
                        if (arraySums[i] != arraySums[j]) {
                            equal = false;
                            if (arraySums[i] < arraySums[j])
                                isBiggest = false;
                            if (arraySums[i] > arraySums[j])
                                isSmallest = false;
                        }
                    }
                    if (equal)
                        return;

                    int randomIndex = (int) (Math.random() * ARRAY_LENGTH);
                    if (isBiggest || (!isSmallest && Math.random() < 0.5)) {
                        arrays[i][randomIndex]--;
                    } else {
                        arrays[i][randomIndex]++;
                    }
                }
            }).start();
        }
    }
}

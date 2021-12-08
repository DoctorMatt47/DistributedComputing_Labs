package com.distributed.lab5.a;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Main {
    private static final int ROOKIES_COUNT = 100;
    private static final int THREAD_COUNT = 2;
    
    private static boolean[] isFacingLeft = new boolean[ROOKIES_COUNT];
    private static boolean[] isNextFacingLeft = new boolean[ROOKIES_COUNT];
    private static boolean isStatic = true;
    private static final Thread[] threads = new Thread[THREAD_COUNT];

    public static void main(String[] args) {
        for (int i = 0; i < isFacingLeft.length; i++)
            isFacingLeft[i] = Math.round(Math.random()) == 1;

        
        CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT, () -> {
            if (isStatic) {
                for (Thread thread : threads)
                    thread.interrupt();
            }
            
            for (int rookie = 0; rookie < isFacingLeft.length; rookie++) {
                if (rookie % (isFacingLeft.length / THREAD_COUNT) == 0)
                    System.out.print("    ");
                System.out.print(isFacingLeft[rookie] ? '<' : '>');
            }
            System.out.println();

            var temp = isNextFacingLeft;
            isNextFacingLeft = isFacingLeft;
            isFacingLeft = temp;

            isStatic = true;
        });
        

        for (int i = 0; i < THREAD_COUNT; i++) {
            var finalI = i;
            threads[i] = new Thread(() -> {
                while (!Thread.interrupted()) {
                    var startRookie = (isFacingLeft.length / THREAD_COUNT) * finalI;
                    var endRookie = Math.min(startRookie + isFacingLeft.length / THREAD_COUNT, isFacingLeft.length);
                    for (int rookie = startRookie; rookie < endRookie; rookie++) {
                        if (isFacingLeft[rookie]) {
                            // Facing left if he is first or see back of left rookie.
                            isNextFacingLeft[rookie] = rookie == 0 || isFacingLeft[rookie - 1];
                        } else {
                            // Facing right if he is last or see back of right rookie.
                            isNextFacingLeft[rookie] = !(rookie == isFacingLeft.length - 1 || !isFacingLeft[rookie + 1]);
                        }

                        if (isNextFacingLeft[rookie] != isFacingLeft[rookie])
                            // If someone changes direction.
                            isStatic = false;
                    }

                    try {
                        barrier.await();
                    } catch (InterruptedException | BrokenBarrierException e) {
                        return;
                    }
                }
            });

            threads[i].start();
        }
    }
}

package com.distributed.lab2.a;

public class Main {
    private static final int THREADS_COUNT = 5;

    public static void main(String[] args) {
        var tasks = new BagOfTasks(new Forest());

        for (int i = 0; i < THREADS_COUNT; i++) {
            new Thread(() -> {
                while (true) {
                    var task = tasks.getTask();
                    if (task == null) break;
                    task.run();
                }
                System.out.printf("Bee flock %d: No more tasks\n", Thread.currentThread().getId());
            }).start();
        }
    }
}

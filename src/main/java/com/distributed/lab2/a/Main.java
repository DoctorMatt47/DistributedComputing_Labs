package com.distributed.lab2.a;

public class Main {
    public static void main(String[] args) {
        var tasks = new BagOfTasks(new Forest());

        for (int i = 0; i < Runtime.getRuntime().availableProcessors(); i++) {
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

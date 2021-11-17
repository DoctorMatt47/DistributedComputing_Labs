package com.distributed.lab7;

import java.util.concurrent.RecursiveAction;

public class MatrixMultiplicationTask extends RecursiveAction {
    public static int SIZE;
    public static double[][] result;
    public static double[][] left;
    public static double[][] right;

    private int i;

    public MatrixMultiplicationTask() {
        i = 0;
    }

    private MatrixMultiplicationTask(int index) {
        this.i = index;
    }

    @Override
    protected void compute() {
        if (i >= SIZE) return;
        var comp = new MatrixMultiplicationTask(i + 1);
        comp.fork();
        for (int j = 0; j < SIZE; j++) {
            for (int k = 0; k < left.length; k++) {
                result[i][j] += left[i][k] * right[k][j];
            }
        }
        comp.join();
    }
}

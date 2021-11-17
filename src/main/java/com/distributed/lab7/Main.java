package com.distributed.lab7;

import java.util.concurrent.ForkJoinPool;

public class Main {
    static final int SIZE = 512;
    static final int MAX_ELEM = 10;

    public static void main(String[] args) {
        var left = Matrix.generateMatrix(SIZE, MAX_ELEM);
        var right = Matrix.generateMatrix(SIZE, MAX_ELEM);

        var result = new double[SIZE][];
        for (int i = 0; i < SIZE; i++) {
            result[i] = new double[SIZE];
        }

        MatrixMultiplicationTask.SIZE = SIZE;
        MatrixMultiplicationTask.left = left;
        MatrixMultiplicationTask.right = right;
        MatrixMultiplicationTask.result = result;

        var startParallel = System.currentTimeMillis();
        ForkJoinPool.commonPool().invoke(new MatrixMultiplicationTask());
        var finishParallel = System.currentTimeMillis();
        System.out.println("Parallel algorithm elapsed time: " + (finishParallel - startParallel) + " ms");

        var startNative = System.currentTimeMillis();
        var nativeResult = Matrix.multiplication(left, right);
        var finishNative = System.currentTimeMillis();
        System.out.println("Native algorithm elapsed time: " + (finishNative - startNative) + " ms");

        System.out.println(Matrix.check(result, nativeResult));
    }
}


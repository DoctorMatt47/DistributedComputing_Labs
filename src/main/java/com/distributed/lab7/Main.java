package com.distributed.lab7;

import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

public class Main {
    static final int SIZE = 1024;
    static final int MAX_ELEM = 10;

    public static void main(String[] args) {
        var left = Matrix.generateMatrix(SIZE, MAX_ELEM);
        var right = Matrix.generateMatrix(SIZE, MAX_ELEM);
        var parallelResult = Matrix.generateMatrix(SIZE, 0);
        var nativeResult = Matrix.generateMatrix(SIZE, 0);

        MatrixMultiplicationTask.SIZE = SIZE;
        MatrixMultiplicationTask.left = left;
        MatrixMultiplicationTask.right = right;
        MatrixMultiplicationTask.result = parallelResult;

        var startParallel = System.currentTimeMillis();
        //ForkJoinPool.commonPool().invoke(new MatrixMultiplicationTask());
        Matrix.parallelMultiplication(left, right, parallelResult);
        var finishParallel = System.currentTimeMillis();
        System.out.println("Parallel algorithm elapsed time: " + (finishParallel - startParallel) + " ms");

        var startNative = System.currentTimeMillis();
        Matrix.multiplication(left, right, nativeResult);
        var finishNative = System.currentTimeMillis();
        System.out.println("Native algorithm elapsed time: " + (finishNative - startNative) + " ms");

        System.out.println(Matrix.check(parallelResult, nativeResult));
    }
}


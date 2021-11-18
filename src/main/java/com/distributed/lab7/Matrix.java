package com.distributed.lab7;

import java.util.stream.IntStream;

public class Matrix {
    public static double[][] generateMatrix(int size, int maxElem) {
        double[][] matrix = new double[size][];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new double[size];
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = Math.random() * maxElem;
            }
        }
        return matrix;
    }

    public static boolean check(double[][] left, double[][] right) {
        for (int i = 0; i < left.length; i++) {
            for (int j = 0; j < left[i].length; j++) {
                if (left[i][j] != right[i][j])
                    return false;
            }
        }
        return true;
    }

    public static void multiplication(double[][] left, double[][] right, double[][] nativeResult) {
        for (int i = 0; i < left.length; i++) {
            for (int j = 0; j < left[i].length; j++) {
                for (int k = 0; k < left[i].length; k++) {
                    nativeResult[i][j] += left[i][k] * right[k][j];
                }
            }
        }
    }

    public static void parallelMultiplication(double[][] left, double[][] right, double[][] parallelResult) {
        IntStream.range(0, left.length).parallel().forEach(i -> {
            for (int j = 0; j < left.length; j++) {
                for (int k = 0; k < left.length; k++) {
                    parallelResult[i][j] += left[i][k] * right[k][j];
                }
            }
        });
    }
}

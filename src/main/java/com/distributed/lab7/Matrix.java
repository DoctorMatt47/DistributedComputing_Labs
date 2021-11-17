package com.distributed.lab7;

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

    public static double[][] multiplication(double[][] left, double[][] right) {
        var matrixToReturn = new double[left.length][];
        for (int i = 0; i < left.length; i++) {
            matrixToReturn[i] = new double[left[i].length];
            for (int j = 0; j < left[i].length; j++) {
                for (int k = 0; k < left[i].length; k++) {
                    matrixToReturn[i][j] += left[i][k] * right[k][j];
                }
            }
        }
        return matrixToReturn;
    }
}

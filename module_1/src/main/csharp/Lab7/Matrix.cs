using System;
using System.Threading.Tasks;

namespace Lab7
{
    public static class Matrix
    {
        public static double[][] GenerateMatrix(int size, int maxElem)
        {
            var matrix = new double[size][];
            for (var i = 0; i < matrix.Length; i++)
            {
                matrix[i] = new double[size];
                for (var j = 0; j < matrix[i].Length; j++)
                    matrix[i][j] = new Random().NextDouble() * maxElem;
            }
            return matrix;
        }

        public static bool Check(double[][] left, double[][] right)
        {
            for (var i = 0; i < left.Length; i++)
            for (var j = 0; j < left[i].Length; j++)
                if (Math.Abs(left[i][j] - right[i][j]) > 1e-5)
                    return false;
            return true;
        }
        
        
        public static void Multiplication(double[][] left, double[][] right, double[][] result)
        {
            for (var i = 0; i < left.Length; i++)
            for (var j = 0; j < left[i].Length; j++)
            for (var k = 0; k < left[i].Length; k++) 
                result[i][j] += left[i][k] * right[k][j];
        }
        
        public static void ParallelMultiplication(double[][] left, double[][] right, double[][] result)
        {
            Parallel.For(0, left.Length, i =>
            {
                for (var j = 0; j < left[i].Length; j++)
                {
                    for (var k = 0; k < left[i].Length; k++)
                        result[i][j] += left[i][k] * right[k][j];
                }
            });
        }
    }
}
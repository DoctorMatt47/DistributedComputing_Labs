using System;
using System.Diagnostics;
using System.Runtime.InteropServices;
using System.Threading.Tasks;

namespace Lab7
{
    internal class Program
    {
        private const int Size = 1024;
        private const int MaxElem = 10;
        
        private static void Main(string[] args)
        {
            var left = Matrix.GenerateMatrix(Size, MaxElem);
            var right = Matrix.GenerateMatrix(Size, MaxElem);
            var parallelResult = Matrix.GenerateMatrix(Size, 0);
            var nativeResult = Matrix.GenerateMatrix(Size, 0);
            var watch = new Stopwatch();


            watch.Start();
            Matrix.ParallelMultiplication(left, right, parallelResult);
            watch.Stop();
            Console.WriteLine($"Parallel algorithm elapsed time: {watch.ElapsedMilliseconds} ms");
            watch.Reset();
            
            watch.Start();
            Matrix.Multiplication(left, right, nativeResult);
            watch.Stop();
            Console.WriteLine($"Native algorithm elapsed time: {watch.ElapsedMilliseconds} ms");
            watch.Reset();
            
            Console.WriteLine(Matrix.Check(nativeResult, parallelResult));
        }
    }
}
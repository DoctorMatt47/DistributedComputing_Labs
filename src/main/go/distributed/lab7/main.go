package main

import (
	"log"
	"math/rand"
	"sync"
	"time"
)

func check(left [][]float64, right [][]float64, size int) bool {
	for i := 0; i < size; i++ {
		for j := 0; j < size; j++ {
			if left[i][j] != right[i][j] {
				return false
			}
		}
	}
	return true
}

func multiplication(left [][]float64, right [][]float64, result [][]float64, size int) {
	for i := 0; i < size; i++ {
		for j := 0; j < size; j++ {
			for k := 0; k < size; k++ {
				result[i][j] += left[i][k] * right[k][j]
			}
		}
	}
}

func parallelMultiplication(left [][]float64, right [][]float64, result [][]float64, size int, i int, wg *sync.WaitGroup) {
	for j := 0; j < size; j++ {
		for k := 0; k < size; k++ {
			result[i][j] += left[i][k] * right[k][j]
		}
	}
	wg.Done()
}

func generateMatrix(size int, maxElem int) [][]float64 {
	matrix := make([][]float64, size)
	for i := 0; i < size; i++ {
		matrix[i] = make([]float64, size)
		for j := 0; j < size; j++ {
			matrix[i][j] = rand.Float64() * float64(maxElem)
		}
	}
	return matrix
}

func main() {
	matrixSize := 1024
	left := generateMatrix(matrixSize, 10)
	right := generateMatrix(matrixSize, 10)
	parallelResult := generateMatrix(matrixSize, 0)
	nativeResult := generateMatrix(matrixSize, 0)

	rand.Seed(time.Now().UTC().UnixNano())
	wg := new(sync.WaitGroup)
	wg.Add(matrixSize)

	startParallel := time.Now()

	for i := 0; i < matrixSize; i++ {
		go parallelMultiplication(left, right, parallelResult, matrixSize, i, wg)
	}

	wg.Wait()

	elapsedParallel := time.Since(startParallel)
	log.Printf("Parallel: %s", elapsedParallel)

	startNative := time.Now()

	multiplication(left, right, nativeResult, matrixSize)

	elapsedNative := time.Since(startNative)
	log.Printf("Native: %s", elapsedNative)

	print(check(parallelResult, nativeResult, matrixSize))

}

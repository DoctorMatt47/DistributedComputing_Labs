package main

import (
	"fmt"
	"math/rand"
	"sync"
	"time"
)

type Barrier struct {
	arrivedParties int
	totalParties   int
	lock           sync.Mutex
	barrierChan    chan int
	runnable       func()
}

func NewBarrier(parties int, runnable func()) *Barrier {
	b := Barrier{
		arrivedParties: 0,
		totalParties:   parties,
		barrierChan:    make(chan int, parties),
		runnable:       runnable,
	}
	return &b
}

func (b *Barrier) Await() {
	b.lock.Lock()
	b.arrivedParties += 1

	// Last thread will release all previous
	if b.arrivedParties == b.totalParties {
		b.runnable()
		b.arrivedParties = 0
		for i := 0; i < b.totalParties; i++ {
			b.barrierChan <- 1
		}
	}
	b.lock.Unlock()

	<-b.barrierChan
}

func stringManipulator(index int, s []byte, abCounts []int, barrier1 *Barrier, barrier2 *Barrier, wg *sync.WaitGroup) {
manipulatorLoop:
	for true {
		barrier1.Await()

		// Finds ab count for this thread string
		abCounts[index] = 0
		for i := 0; i < len(s); i++ {
			if s[i] == 'A' {
				abCounts[index]++
			} else if s[i] == 'B' {
				abCounts[index]++
			}
		}

		barrier2.Await()

		isHighest := true
		highest := abCounts[index]
		for i := 0; i < len(abCounts); i++ {
			// Finds number of equal ab counts
			matchCount := 0
			for j := 0; j < len(abCounts); j++ {
				if abCounts[i] == abCounts[j] {
					matchCount++
				}
				if matchCount == 3 {
					break manipulatorLoop
				}
			}
			// Finds thread with the highest ab count
			if abCounts[i] > highest {
				isHighest = false
				highest = abCounts[i]
			}
		}

		for i := 0; i < len(s); i++ {
			// If highest: abCount--
			if isHighest {
				if s[i] == 'A' {
					s[i] = 'C'
					break
				} else if s[i] == 'B' {
					s[i] = 'D'
					break
				}
			// If diff from highest more than 1: abCount++
			} else if highest - abCounts[index] > 1 {
				if s[i] == 'C' {
					s[i] = 'A'
					break
				} else if s[i] == 'D' {
					s[i] = 'B'
					break
				}
			}
			// If diff from highest less than 2: do not change abCount
		}
	}
	wg.Done()
}

func main() {
	rand.Seed(time.Now().UTC().UnixNano())
	wg := new(sync.WaitGroup)
	wg.Add(4)

	abCounts := [4]int{}
	strings := [4][]byte{}

	barrier1 := NewBarrier(4, func() {})
	barrier2 := NewBarrier(4, func() {
		for i := 0; i < 4; i++ {
			fmt.Printf("%s [abCount: %d], ", strings[i], abCounts[i])
		}
		fmt.Println()
	})

	abcd := [4]byte{'A', 'B', 'C', 'D'}
	for i := 0; i < 4; i++ {
		strings[i] = make([]byte, 30)
		for j := 0; j < 30; j++ {
			strings[i][j] = abcd[int(rand.Float32()*4)]
		}

		go stringManipulator(i, strings[i], abCounts[:], barrier1, barrier2, wg)
	}

	wg.Wait()
}


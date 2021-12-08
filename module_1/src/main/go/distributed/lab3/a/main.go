package main

import (
	"context"
	"fmt"
	"golang.org/x/sync/semaphore"
	"sync/atomic"
	"time"
)

const maxHoney = 10
const beesCount = 3

type Winnie struct {
	semaphore *semaphore.Weighted
	honey     int32
	honeyFull chan bool
}

func (w *Winnie) simulate() {
	for {
		fmt.Println("Winnie: Sleeping")

		var _ = <-w.honeyFull

		fmt.Println("Winnie: Going to eat")
		time.Sleep(2000 * time.Millisecond)
		w.honey = 0

		w.semaphore.Release(maxHoney)
	}

}

func simulateBee(context context.Context, id int, winnie *Winnie) {
	for {
		fmt.Printf("Bee %d: Searching for honey\n", id)

		time.Sleep(1000 * time.Millisecond)

		_ = winnie.semaphore.Acquire(context, 1)

		var newHoney = atomic.AddInt32(&winnie.honey, 1)
		fmt.Printf("Bee %d: Added honey, now %d\n", id, newHoney)

		if newHoney == maxHoney {
			fmt.Printf("Bee %d: Wake up!\n", id)
			winnie.honeyFull <- true
		}
	}
}

func main() {
	var winnie = Winnie{
		semaphore: semaphore.NewWeighted(maxHoney),
		honey:     0,
		honeyFull: make(chan bool),
	}
	go winnie.simulate()

	var ctx = context.Background()
	for i := 0; i < beesCount; i++ {
		go simulateBee(ctx, i, &winnie)
	}

	//Press any key to exit
	_, _ = fmt.Scanln()
}

package main

import (
	"fmt"
)

const totalItems = 15
const bufferSize = 3

var stolenItemsCount = 0

type ArmyItem struct {
	id int
}

func (item ArmyItem) String() string {
	return fmt.Sprintf("Item %d", item.id)
}

func steal(stolenItems chan ArmyItem) {
	for i := 0; i < totalItems; i++ {
		var stolenItem = ArmyItem{id: i}
		fmt.Printf("Stolen %s\n", stolenItem)
		stolenItems <- stolenItem
	}
	close(stolenItems)
}

func loadInTruck(stolenItems chan ArmyItem, truckItems chan ArmyItem) {
	for item := range stolenItems {
		fmt.Printf("Loaded %s in truck\n", item)
		truckItems <- item
	}
	close(truckItems)
}

func countFromTruck(truckItems chan ArmyItem, done chan bool) {
	for range truckItems {
		stolenItemsCount++
		fmt.Printf("Stolen items count: %d\n", stolenItemsCount)
	}
	done <- true
}

func main() {
	var stolenItems = make(chan ArmyItem, bufferSize)
	var truckItems = make(chan ArmyItem, bufferSize)
	var done = make(chan bool, 1)

	go steal(stolenItems)
	go loadInTruck(stolenItems, truckItems)
	go countFromTruck(truckItems, done)
	<-done
}
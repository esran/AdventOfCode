package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"sort"
	"strconv"
)

type Elf struct {
	calories      []int
	totalCalories int
}

func main() {
	// read input file line by line and parse into elvish data structures

	input, err := os.Open("input.txt")
	if err != nil {
		log.Fatal(err)
	}
	defer func(input *os.File) {
		err := input.Close()
		if err != nil {
			log.Fatal(err)
		}
	}(input)

	scanner := bufio.NewScanner(input)

	var elves []Elf
	var elf Elf
	for scanner.Scan() {
		if scanner.Text() == "" {
			elves = append(elves, elf)
			elf = Elf{
				totalCalories: 0,
			}
		} else {
			// convert the text to integer
			i, err := strconv.Atoi(scanner.Text())
			if err == nil {
				elf.calories = append(elf.calories, i)
				elf.totalCalories += i
			}
		}
	}

	if elf.totalCalories > 0 {
		elves = append(elves, elf)
	}

	fmt.Printf("elves: %d\n", len(elves))

	// part 1
	// find the elf with the most calories
	maxCalories := 0
	for _, elf := range elves {
		calories := 0
		for _, calorie := range elf.calories {
			calories += calorie
		}
		if calories > maxCalories {
			//maxElf = e
			maxCalories = calories
		}
	}

	fmt.Printf("Part 1: %d\n", maxCalories)

	// part 2
	// fine the top 3 elves with the most calories
	sortFunc := func(i, j int) bool {
		return elves[i].totalCalories > elves[j].totalCalories
	}
	sort.Slice(elves, sortFunc)

	fmt.Printf("Part 2: %d\n", elves[0].totalCalories+elves[1].totalCalories+elves[2].totalCalories)
}

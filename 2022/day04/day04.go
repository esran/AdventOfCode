package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strconv"
	"strings"
)

type sections struct {
	lower int
	upper int
}
type pair struct {
	a sections
	b sections
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

	var pairs []pair
	for scanner.Scan() {
		parts := strings.Split(scanner.Text(), ",")
		partsA := strings.Split(parts[0], "-")
		partsB := strings.Split(parts[1], "-")
		lowerA, err := strconv.Atoi(partsA[0])
		if err != nil {
			log.Fatal(err)
		}
		upperA, err := strconv.Atoi(partsA[1])
		if err != nil {
			log.Fatal(err)
		}
		lowerB, err := strconv.Atoi(partsB[0])
		if err != nil {
			log.Fatal(err)
		}
		upperB, err := strconv.Atoi(partsB[1])
		if err != nil {
			log.Fatal(err)
		}
		pairs = append(pairs,
			pair{
				sections{
					lowerA,
					upperA,
				},
				sections{
					lowerB,
					upperB,
				},
			})
	}

	fullOverlap := func(a sections, b sections) bool {
		return a.lower <= b.lower && a.upper >= b.upper || b.lower <= a.lower && b.upper >= a.upper
	}

	count := 0
	for _, pair := range pairs {
		if fullOverlap(pair.a, pair.b) {
			count++
		}
	}

	fmt.Printf("Part 1: %d\n", count)

	partialOverlap := func(a sections, b sections) bool {
		if a.lower <= b.lower && a.upper >= b.lower {
			return true
		}
		if b.lower <= a.lower && b.upper >= a.lower {
			return true
		}
		if a.lower <= b.upper && a.upper >= b.upper {
			return true
		}
		if b.lower <= a.upper && b.upper >= a.upper {
			return true
		}
		return false
	}

	count = 0
	for _, pair := range pairs {
		if partialOverlap(pair.a, pair.b) {
			count++
		}
	}

	fmt.Printf("Part 2: %d\n", count)
}

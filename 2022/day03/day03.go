package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
)

type pack struct {
	a []rune
	b []rune
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

	var packs []pack
	for scanner.Scan() {
		runes := []rune(scanner.Text())
		packs = append(packs, pack{runes[:len(runes)/2], runes[len(runes)/2:]})
	}

	getPriority := func(c rune) int {
		runeList := "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
		for i, r := range runeList {
			if r == c {
				return i + 1
			}
		}

		return 0
	}

	contains := func(runeList []rune, c rune) bool {
		for _, r := range runeList {
			if r == c {
				return true
			}
		}

		return false
	}

	totalPriority := 0
	for _, pack := range packs {
		for _, c := range pack.a {
			if contains(pack.b, c) {
				totalPriority += getPriority(c)
				break
			}
		}
	}

	fmt.Printf("Part 1: %d\n", totalPriority)

	packContains := func(pack pack, c rune) bool {
		return contains(pack.a, c) || contains(pack.b, c)
	}

	totalPriority = 0
	for i := range packs {
		if i%3 != 0 {
			continue
		}

		for _, c := range append(packs[i].a, packs[i].b...) {
			if packContains(packs[i+1], c) && packContains(packs[i+2], c) {
				totalPriority += getPriority(c)
				break
			}
		}
	}

	fmt.Printf("Part 2: %d\n", totalPriority)
}

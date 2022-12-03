package main

import (
	"bufio"
	"fmt"
	"log"
	"os"
	"strings"
)

type hint struct {
	a string
	b string
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

	var hints []hint
	for scanner.Scan() {
		parts := strings.Split(scanner.Text(), " ")
		hints = append(hints, hint{parts[0], parts[1]})
	}

	mapPicks := make(map[string]string)
	mapPicks["X"] = "rock"
	mapPicks["Y"] = "paper"
	mapPicks["Z"] = "scissors"
	mapPicks["A"] = "rock"
	mapPicks["B"] = "paper"
	mapPicks["C"] = "scissors"

	pickScores := make(map[string]int)
	pickScores["rock"] = 1
	pickScores["paper"] = 2
	pickScores["scissors"] = 3

	resultScores := make(map[string]int)
	resultScores["draw"] = 3
	resultScores["win"] = 6
	resultScores["lose"] = 0

	calcResult := func(a string, b string) string {
		if a == b {
			return "draw"
		}

		if a == "rock" {
			if b == "paper" {
				return "win"
			}
			return "lose"
		}

		if a == "paper" {
			if b == "scissors" {
				return "win"
			}
			return "lose"
		}

		// a must be scissors
		if b == "rock" {
			return "win"
		}
		return "lose"
	}

	score := 0
	for _, hint := range hints {
		score += resultScores[calcResult(mapPicks[hint.a], mapPicks[hint.b])]
		score += pickScores[mapPicks[hint.b]]
	}

	fmt.Printf("Part 1: %d\n", score)

	// part 2
	hintPicks := make(map[string]string)
	hintPicks["X"] = "lose"
	hintPicks["Y"] = "draw"
	hintPicks["Z"] = "win"

	// if we want to lose to x, pick y
	losePickMap := make(map[string]string)
	losePickMap["rock"] = "scissors"
	losePickMap["paper"] = "rock"
	losePickMap["scissors"] = "paper"

	// if we want to win to x, pick y
	winPickMap := make(map[string]string)
	winPickMap["rock"] = "paper"
	winPickMap["paper"] = "scissors"
	winPickMap["scissors"] = "rock"

	score = 0
	for _, hint := range hints {
		var myPick string
		theirPick := mapPicks[hint.a]

		if hintPicks[hint.b] == "draw" {
			myPick = theirPick
		} else if hintPicks[hint.b] == "win" {
			myPick = winPickMap[theirPick]
		} else {
			myPick = losePickMap[theirPick]
		}

		score += resultScores[calcResult(theirPick, myPick)]
		score += pickScores[myPick]
	}

	fmt.Printf("Part 2: %d\n", score)
}

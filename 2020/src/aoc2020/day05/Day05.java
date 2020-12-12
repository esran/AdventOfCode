package aoc2020.day05;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


// AdventOfCode 2020 Day 4
public class Day05 {
	private static final String Path = "day05-input.txt";

	public static void main(String[] args) {

		var me = new Day05();
		var part1 = me.Part1();
		var part2 = me.Part2();

		System.out.println("Part1: " + part1);
		System.out.println("Part2: " + part2);
	}

	// Data loaded from file and converted to list of Passports
	private List<String> data_;

	public Day05() {
		Load();
	}

	// Load the data into a list of Passports. MakePassport can return
	// the same object multiple times (where the input data is on multiple lines)
	// so we need to distinct it.
	private void Load() {
		ClassLoader classLoader = getClass().getClassLoader();
		BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(classLoader.getResourceAsStream(Path))));
		data_ = reader.lines().collect(Collectors.toList());
	}

	/*
		--- Day 5: Binary Boarding ---

	You board your plane only to discover a new problem: you dropped your boarding pass!
	You aren't sure which seat is yours, and all of the flight attendants are busy with
	the flood of people that suddenly made it through passport control.

	You write a quick program to use your phone's camera to scan all of the nearby
	boarding passes (your puzzle input); perhaps you can find your seat through process
	of elimination.

	Instead of zones or groups, this airline uses binary space partitioning to seat
	people. A seat might be specified like FBFBBFFRLR, where F means "front", B means
	"back", L means "left", and R means "right".

	The first 7 characters will either be F or B; these specify exactly one of the 128
	rows on the plane (numbered 0 through 127). Each letter tells you which half of a
	region the given seat is in. Start with the whole list of rows; the first letter
	indicates whether the seat is in the front (0 through 63) or the back (64 through 127).
	The next letter indicates which half of that region the seat is in, and so on until
	you're left with exactly one row.

	For example, consider just the first seven characters of FBFBBFFRLR:

		Start by considering the whole range, rows 0 through 127.
		F means to take the lower half, keeping rows 0 through 63.
		B means to take the upper half, keeping rows 32 through 63.
		F means to take the lower half, keeping rows 32 through 47.
		B means to take the upper half, keeping rows 40 through 47.
		B keeps rows 44 through 47.
		F keeps rows 44 through 45.
		The final F keeps the lower of the two, row 44.

	The last three characters will be either L or R; these specify exactly one of the 8
	columns of seats on the plane (numbered 0 through 7). The same process as above
	proceeds again, this time with only three steps. L means to keep the lower half,
	while R means to keep the upper half.

	For example, consider just the last 3 characters of FBFBBFFRLR:

		Start by considering the whole range, columns 0 through 7.
		R means to take the upper half, keeping columns 4 through 7.
		L means to take the lower half, keeping columns 4 through 5.
		The final R keeps the upper of the two, column 5.

	So, decoding FBFBBFFRLR reveals that it is the seat at row 44, column 5.

	Every seat also has a unique seat ID: multiply the row by 8, then add the column.
	In this example, the seat has ID 44 * 8 + 5 = 357.

	Here are some other boarding passes:

		BFFFBBFRRR: row 70, column 7, seat ID 567.
		FFFBBBFRRR: row 14, column 7, seat ID 119.
		BBFFBBFRLL: row 102, column 4, seat ID 820.
	 */
	public Integer GetSeatNumber(String s) {
		int row = 0;
		int col = 0;

		for (var ch : s.toCharArray()) {
			if (ch == 'F') {
				row <<= 1;
			} else if (ch == 'B') {
				row <<= 1;
				row++;
			} else if (ch == 'L') {
				col <<= 1;
			} else {
				col <<= 1;
				col++;
			}
		}

		return row * 8 + col;
	}

	/*
	As a sanity check, look through your list of boarding passes. What is the highest seat ID on a boarding pass?
	*/
	private Integer Part1() {
		Optional<Integer> result = data_.stream().map(this::GetSeatNumber).max(Integer::compareTo);
		assert (result.isPresent());
		return result.get();
	}

	/*
	--- Part Two ---

	Ding! The "fasten seat belt" signs have turned on. Time to find your seat.

	It's a completely full flight, so your seat should be the only missing
	boarding pass in your list. However, there's a catch: some of the seats
	at the very front and back of the plane don't exist on this aircraft, so
	they'll be missing from your list as well.

	Your seat wasn't at the very front or back, though; the seats with IDs +1
	and -1 from yours will be in your list.

	What is the ID of your seat?
	*/
	private Integer Part2() {
		Integer maxSeat = Part1();

		List<Integer> allSeats = data_.stream().map(this::GetSeatNumber).collect(Collectors.toList());

		for (Integer mySeat = 1; mySeat.compareTo(maxSeat) != 0; ++mySeat) {
			Integer rearSeat = mySeat - 1;
			Integer frontSeat = mySeat + 1;

			if (allSeats.contains(rearSeat) && allSeats.contains(frontSeat) && !allSeats.contains(mySeat)) {
				return mySeat;
			}
		}

		return -1;
	}

}

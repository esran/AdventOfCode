package aoc2020.day05;

import static org.junit.jupiter.api.Assertions.*;

class Day05Test {

	@org.junit.jupiter.api.Test
	void getSeatNumber() {
		Day05 test = new Day05();

		assertEquals(357, test.GetSeatNumber("FBFBBFFRLR"));
		assertEquals(567, test.GetSeatNumber("BFFFBBFRRR"));
		assertEquals(119, test.GetSeatNumber("FFFBBBFRRR"));
		assertEquals(820, test.GetSeatNumber("BBFFBBFRLL"));
	}
}
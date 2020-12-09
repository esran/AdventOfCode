package aoc2020.day09;

import aoc2020.utils.Utils;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
	private static final String Path = MethodHandles.lookup().lookupClass().getPackageName() + "-input.txt";

	public static void main(String[] args) {

		var me = new Main();

		System.out.println("Part1: " + me.Part1());
//		System.out.println("Part2: " + me.Part2());
	}

	private static final int Preamble = 25;
	List<Long> numbers_;

	Main() {
		numbers_ = Utils.loadFileAsLines(Path).stream().map(Long::valueOf).collect(Collectors.toList());
	}

	/*
	Find the first number (after the preamble) that is not a sum of two of the
	previous [preamble] numbers.
	 */
	Long Part1() {
		List<Long> pre_numbers = new ArrayList<>(numbers_.subList(0, Preamble));
		int position = Preamble;

		while (position < numbers_.size()) {
			if (!IsSumOfTwo(pre_numbers, numbers_.get(position))) {
				return numbers_.get(position);
			}

			pre_numbers.remove(0);
			pre_numbers.add(numbers_.get(position));
			position++;
		}

		throw new RuntimeException("failed part1");
	}

	boolean IsSumOfTwo(List<Long> check_numbers, Long number) {
		for (int pos1 = 0; pos1 < check_numbers.size(); ++pos1) {
			for (int pos2 = pos1 + 1; pos2 < check_numbers.size(); ++pos2) {
				if (number.compareTo(check_numbers.get(pos1) + check_numbers.get(pos2)) == 0) {
					return true;
				}
			}
		}

		return false;
	}
}

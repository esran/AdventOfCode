package aoc2020.day11;

import aoc2020.utils.Utils;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	private static final String Path = MethodHandles.lookup().lookupClass().getPackageName() + "-input.txt";

	public static void main(String[] args) {

		var me = new Main();

		System.out.println("Part1: " + me.Part1());
		System.out.println("Part2: " + me.Part2());
	}

	List<String> data_;

	Main() {
		data_ = Utils.loadFileAsLines(Path);
	}

	Long Part1() {
		while (mutate()) {
			System.out.print('.');
		}
		System.out.println();

		return countOccupied();
	}

	Long countOccupied() {
		var result = data_.stream().map(s -> s.chars().filter(r -> r == '#').count()).reduce(Long::sum);
		assert result.isPresent() : "could not count occupied chairs";
		return result.get();
	}

	Boolean mutate() {
		Map<Integer, String> mutate = getMutation(data_);

		if (mutate.size() == 0) {
			return false;
		} else {
			for (var line : mutate.keySet()) {
				data_.set(line, mutate.get(line));
			}
		}

		return true;
	}

	Map<Integer, String> getMutation(List<String> data) {
		Map<Integer, String> result = new HashMap<>();

		for (var line = 0; line < data.size(); line++) {
			boolean changed = false;
			char[] new_line = data_.get(line).toCharArray();
			for (var chair = 0; chair < data.get(line).length(); chair++) {
				if (!isChair(line, chair)) {
					continue;
				}

				var adjacent = countAdjacent(line, chair);
				if (!checkOccupied(line, chair) && adjacent == 0) {
					new_line[chair] = '#';
					changed = true;
				}
				if (checkOccupied(line, chair) && adjacent >= 4) {
					new_line[chair] = 'L';
					changed = true;
				}
			}

			if (changed) {
				result.put(line, new String(new_line));
			}
		}

		return result;
	}

	Boolean isChair(Integer line, Integer chair) {
		return data_.get(line).charAt(chair) != '.';
	}

	Integer countAdjacent(Integer line, Integer chair) {
		var result = 0;
		for (var adj_line = -1; adj_line < 2; ++adj_line) {
			for (var adj_chair = -1; adj_chair < 2; ++adj_chair) {
				if (adj_line == 0 && adj_chair == 0) {
					continue;
				}
				if (checkOccupied(line + adj_line, chair + adj_chair)) {
					result++;
				}
			}
		}

		return result;
	}

	Boolean checkOccupied(Integer line, Integer chair) {
		if (line < 0 || chair < 0) {
			return false;
		}

		if (line >= data_.size() || chair >= data_.get(0).length()) {
			return false;
		}

		return (data_.get(line).charAt(chair) == '#');
	}

	Integer Part2() {
		return -1;
	}

}

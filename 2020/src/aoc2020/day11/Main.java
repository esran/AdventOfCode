package aoc2020.day11;

import aoc2020.utils.Utils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Main {
	private static final String Path = MethodHandles.lookup().lookupClass().getPackageName() + "-input.txt";

	public static void main(String[] args) {

		var me = new Main();

		System.out.println("Part1: " + me.Part1());
		System.out.println("Part2: " + me.Part2());
	}

	List<String> data_;

	Main() {

	}

	Long countOccupied() {
		var result = data_.stream().map(s -> s.chars().filter(r -> r == '#').count()).reduce(Long::sum);
		assert result.isPresent() : "could not count occupied chairs";
		return result.get();
	}

	Boolean mutate(Function<Pair<Integer, Integer>, Adjacency> countAdjacent) {
		Map<Integer, String> mutate = getMutation(data_, countAdjacent);

		if (mutate.size() == 0) {
			return false;
		} else {
			for (var line : mutate.keySet()) {
				data_.set(line, mutate.get(line));
			}
		}

		return true;
	}

	enum Adjacency {
		ZERO,
		SOME,
		MANY
	}

	Map<Integer, String> getMutation(List<String> data, Function<Pair<Integer, Integer>, Adjacency> countAdjacent) {
		Map<Integer, String> result = new HashMap<>();

		for (var line = 0; line < data.size(); line++) {
			boolean changed = false;
			char[] new_line = data_.get(line).toCharArray();
			for (var chair = 0; chair < data.get(line).length(); chair++) {
				if (!isChair(line, chair)) {
					continue;
				}

				var adjacent = countAdjacent.apply(new ImmutablePair<>(line, chair));

				if (!checkOccupied(line, chair) && adjacent == Adjacency.ZERO) {
					new_line[chair] = '#';
					changed = true;
				}
				if (checkOccupied(line, chair) && adjacent == Adjacency.MANY) {
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

	Adjacency countAdjacentPart1(Pair<Integer, Integer> pair) {
		var line = pair.getLeft();
		var chair = pair.getRight();

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

		if (result == 0) {
			return Adjacency.ZERO;
		}

		return result >= 4 ? Adjacency.MANY : Adjacency.SOME;
	}

	Adjacency countAdjacentPart2(Pair<Integer, Integer> pair) {
		var line = pair.getLeft();
		var chair = pair.getRight();

		var adjacent = 0;
		for (var line_adjust = -1 ; line_adjust < 2 ; ++line_adjust) {
			for (var chair_adjust = -1 ; chair_adjust < 2 ; ++chair_adjust) {
				if (line_adjust == 0 && chair_adjust == 0) {
					continue;
				}

				var check_line = line + line_adjust;
				var check_chair = chair + chair_adjust;

				while (check_line >= 0 && check_line < data_.size()
					&& check_chair >= 0 && check_chair < data_.get(0).length()) {
					if (isChair(check_line, check_chair)) {
						if (checkOccupied(check_line, check_chair)) {
							adjacent++;
						}
						break;
					}
					check_line += line_adjust;
					check_chair += chair_adjust;
				}
			}
		}

		if (adjacent == 0) {
			return Adjacency.ZERO;
		}

		return adjacent >= 5 ? Adjacency.MANY : Adjacency.SOME;
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

	Long Part1() {
		data_ = Utils.loadFileAsLines(Path);
		while (mutate(this::countAdjacentPart1)) {
			System.out.print('.');
		}
		System.out.println();

		return countOccupied();
	}

	Long Part2() {
		data_ = Utils.loadFileAsLines(Path);
		while (mutate(this::countAdjacentPart2)) {
			System.out.print('.');
		}
		System.out.println();

		return countOccupied();
	}

}

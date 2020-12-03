package day03;

import day02.PasswordLine;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

// Add comment here
public class Day03 {
	private static final String Path = "day03-input.txt";

	public static void main(String[] args) {

		var me = new Day03();
		var part1 = me.Part1();
		var part2 = me.Part2();

		System.out.println("Part1: " + part1);
		System.out.println("Part2: " + part2);
	}

	private List<String> lines_;

	private Day03() {
		Load();
	}

	private void Load() {
		ClassLoader classLoader = getClass().getClassLoader();
		BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(classLoader.getResourceAsStream(Path))));
		lines_ = reader.lines().collect(Collectors.toList());
	}

	private long CountTrees(int right, int down) {
		var column = 0;
		long trees = 0;
		for (int line = down; line < lines_.size(); line += down) {
			column += right;
			if (lines_.get(line).charAt(column % lines_.get(line).length()) == '#') {
				trees++;
			}
		}

		return trees;
	}

	private long Part1() {
		return CountTrees(3, 1);
	}

	private long Part2() {
		var a = CountTrees(1, 1);
		var b = CountTrees(3, 1);
		var c = CountTrees(5, 1);
		var d = CountTrees(7, 1);
		var e = CountTrees(1, 2);

		return a * b * c * d * e;
	}
}

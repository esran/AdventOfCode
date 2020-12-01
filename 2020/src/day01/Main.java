package day01;

import org.apache.commons.lang3.time.StopWatch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// Add comment here
public class Main {
	private static final String Path = "day01-input.txt";

	public static void main(String[] args){

		Main me = new Main();
		int part1 = me.Part1();
		int part2 = me.Part2();

		System.out.println("Part1: " + part1);
		System.out.println("Part2: " + part2);

		me.PrintTimes();
	}

	private List<Integer> numbers_;
	private final StopWatch stopWatch_ = new StopWatch();
	private final List<String> checkPoints_ = new ArrayList<>();

	private Main() {
		stopWatch_.start();
		stopWatch_.suspend();
		Load();
	}

	private void PrintTimes() {
		stopWatch_.resume();
		stopWatch_.stop();
		for (String t : checkPoints_) {
			System.out.println(t);
		}
		System.out.println("TOTAL: " + stopWatch_.toString());
	}

	private void Load() {
		try {
			stopWatch_.resume();
			stopWatch_.split();
			ClassLoader classLoader = getClass().getClassLoader();
			BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(classLoader.getResourceAsStream(Path))));
			numbers_ = reader.lines().map(Integer::valueOf).collect(Collectors.toList());
		} finally {
			checkPoints_.add(" LOAD: " + stopWatch_.toSplitString());
			stopWatch_.unsplit();
			stopWatch_.suspend();
		}
	}

	private int Part1 () {
		try {
			stopWatch_.resume();
			stopWatch_.split();
			for (int i = 0; i < numbers_.size(); ++i) {
				for (int j = 1; j < numbers_.size(); ++j) {
					if (numbers_.get(i) + numbers_.get(j) == 2020) {
						return numbers_.get(i) * numbers_.get(j);
					}
				}
			}
		} finally {
			checkPoints_.add("PART1: " + stopWatch_.toSplitString());
			stopWatch_.unsplit();
			stopWatch_.suspend();
		}

		return -1;
	}

	private int Part2 () {
		try {
			stopWatch_.resume();
			stopWatch_.split();
			for (int i = 0; i < numbers_.size(); ++i) {
				for (int j = 1; j < numbers_.size(); ++j) {
					for (int k = 1; k < numbers_.size(); ++k) {
						if (numbers_.get(i) + numbers_.get(j) + numbers_.get(k) == 2020) {
							return numbers_.get(i) * numbers_.get(j) * numbers_.get(k);
						}
					}
				}
			}
		} finally {
			checkPoints_.add("PART2: " + stopWatch_.toSplitString());
			stopWatch_.unsplit();
			stopWatch_.suspend();
		}

		return -1;
	}
}

package day02;

import org.apache.commons.lang3.time.StopWatch;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

// Add comment here
public class Day02 {
	private static final String Path = "day02-input.txt";

	public static void main(String[] args) {

		var me = new Day02();
		var part1 = me.Part1();
		var part2 = me.Part2();

		System.out.println("Part1: " + part1);
		System.out.println("Part2: " + part2);

		me.PrintTimes();
	}

	private List<PasswordLine> passwords_;
	private final List<String> checkPoints_ = new ArrayList<>();

	private Day02() {
		Load();
	}

	private void PrintTimes() {
		for (String t : checkPoints_) {
			System.out.println(t);
		}
	}

	private Boolean IsValidPart1(PasswordLine input) {
		long count = input.password.chars().filter(c -> c == input.letter).count();
		return (input.min <= count && input.max >= count);
	}

	private Boolean IsValidPart2(PasswordLine input) {
		if (input.password.length() < Integer.max(input.min, input.max)) {
			return false;
		}
		return input.password.charAt(input.min-1) == input.letter ^ input.password.charAt(input.max-1) == input.letter;
	}

	private void Load() {
		var stopwatch = new StopWatch();
		stopwatch.start();
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(classLoader.getResourceAsStream(Path))));
			passwords_ = reader.lines().map(PasswordLine::new).collect(Collectors.toList());
		} finally {
			checkPoints_.add(" LOAD: " + stopwatch.toString());
		}
	}

	private long Part1() {
		var stopwatch = new StopWatch();
		stopwatch.start();
		var result = passwords_.stream().filter(this::IsValidPart1).count();
		checkPoints_.add("PART2: " + stopwatch.toString());
		return result;
	}

	private long Part2() {
		var stopwatch = new StopWatch();
		stopwatch.start();
		var result = passwords_.stream().filter(this::IsValidPart2).count();
		checkPoints_.add("PART2: " + stopwatch.toString());
		return result;
	}
}

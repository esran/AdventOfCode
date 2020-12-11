package aoc2020.day10;

import aoc2020.utils.Utils;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {
	private static final String Path = MethodHandles.lookup().lookupClass().getPackageName() + "-input.txt";

	public static void main(String[] args) {

		var me = new Main();

		System.out.println("Part1: " + me.Part1());
//		System.out.println("Part2: " + me.Part2());
	}

	List<Long> adaptors_;
	Main() {
		adaptors_ = Utils.loadFileAsLines(Path).stream().map(Long::valueOf).collect(Collectors.toList());
	}

	Long Part1() {
		Map<Long,Integer> diffs = new HashMap<>();
		Long check = 0L;

		// Go through the adaptors in sequential order tracking the joltage differences
		for (var adaptor : adaptors_.stream().sorted().collect(Collectors.toList())) {
			var diff = adaptor - check;
			diffs.put(diff, diffs.getOrDefault(diff, 0) + 1);
			check = adaptor;
		}

		// Add in the device which is always 3 higher
		diffs.put(3L, diffs.getOrDefault(3L, 0) + 1);

		// Multiply the number of 1 joly differences by the number of 3 jolt differences
		return (long) (diffs.get(1L) * diffs.get(3L));
	}

}

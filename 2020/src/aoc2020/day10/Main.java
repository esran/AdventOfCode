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
		System.out.println("Part2: " + me.Part2());
	}

	List<Integer> adaptors_;
	Main() {
		adaptors_ = Utils.loadFileAsLines(Path).stream().map(Integer::valueOf).collect(Collectors.toList());
	}

	Integer Part1() {
		Map<Integer,Integer> diffs = new HashMap<>();
		Integer check = 0;

		// Go through the adaptors in sequential order tracking the joltage differences
		for (var adaptor : adaptors_.stream().sorted().collect(Collectors.toList())) {
			var diff = adaptor - check;
			diffs.put(diff, diffs.getOrDefault(diff, 0) + 1);
			check = adaptor;
		}

		// Add in the device which is always 3 higher
		diffs.put(3, diffs.getOrDefault(3, 0) + 1);

		// Multiply the number of 1 joly differences by the number of 3 jolt differences
		return diffs.get(1) * diffs.get(3);
	}

	Long Part2() {
		List<Integer> sorted = adaptors_.stream().sorted().collect(Collectors.toList());
		return countChains(0, sorted);
	}

	// Keep track of the chains we've worked out so we don't have to redo them!
	private final Map<Integer,Long> chainCount = new HashMap<>();

	// Count how many chains there are from the starting number given the rest of the
	// adaptors
	Long countChains (Integer start, List<Integer> adaptors) {
		// Have we already worked out this chain?
		if (chainCount.containsKey(start)) {
			return chainCount.get(start);
		}

		// If there are no more adaptors then shortcut
		if (adaptors.size() == 0) {
			chainCount.put(start, 1L);
			return 1L;
		}

		// The next adaptor is always okay to chain to
		Long chains = countChains(adaptors.get(0), adaptors.subList(1, adaptors.size()));

		// Done if there was only one extra adaptor or the following adaptor is out of range
		if (adaptors.size() == 1 || adaptors.get(1) - start > 3) {
			chainCount.put(start, chains);
			return chains;
		}

		chains += countChains(adaptors.get(1), adaptors.subList(2, adaptors.size()));

		// Similar deal for two adaptors or third adaptor is out of range
		if (adaptors.size() == 2 || adaptors.get(2) - start > 3) {
			chainCount.put(start, chains);
			return chains;
		}

		// Last one, can only ever link to max of 3 adaptors (+1, +2, +3)
		chains += countChains(adaptors.get(2), adaptors.subList(3, adaptors.size()));

		chainCount.put(start, chains);
		return chains;
	}

}

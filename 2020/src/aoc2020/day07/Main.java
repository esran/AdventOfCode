package aoc2020.day07;

import aoc2020.utils.Utils;

import java.lang.invoke.MethodHandles;
import java.util.*;

public class Main {
	private static final String Path = MethodHandles.lookup().lookupClass().getPackageName() + "-input.txt";

	public static void main(String[] args) {

		var me = new Main();

		System.out.println("Part1: " + me.Part1());
		System.out.println("Part2: " + me.Part2(Bag.GetBagByColour(colour)));
	}

	Main() {
		for (String line : Utils.loadFileAsLines(Path)) {
			Bag.Parse(line);
		}
	}

	private Set<Bag> GetContainedInSet(Bag bag) {
		Set<Bag> result = new HashSet<>(bag.containedIn);
		for (var other : bag.containedIn) {
			result.addAll(GetContainedInSet(other));
		}

		return result;
	}

	/*
	How many bags does a shiny gold bag contain?
	 */
	private Integer Part2(Bag bag) {
		Integer result = 0;
		for (var other : bag.contains.keySet()) {
			result += bag.contains.get(other);
			result += bag.contains.get(other) * Part2(other);
		}

		return result;
	}

	/*
	How many different bags can a shiny gold bag be in (including nesting)
	 */
	private static final String colour = "shiny gold";
	Integer Part1() {
		return GetContainedInSet(Bag.GetBagByColour(colour)).size();
	}

}

package aoc2020.day07;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Bag {
	// Map of colours to bags
	private static final Map<String, Bag> AllBags = new HashMap<>();

	public String colour;

	// The bags that this bag directly contains
	Map<Bag, Integer> contains = new HashMap<>();

	// The bags that this bag can be directly contained in
	Set<Bag> containedIn = new HashSet<>();

	// Get a bag by the colour. This will create a new bag if the colour
	// does not already exist
	public static Bag GetBagByColour(String colour) {
		var result = AllBags.get(colour);
		if (result == null) {
			result = new Bag(colour);
			AllBags.put(colour, result);
		}
		return result;
	}

	// Construct a bug of the specified colour
	private Bag(String colour) {
		this.colour = colour;
	}

	public static void Parse(String line) {
		Pattern pattern = Pattern.compile("(.*) bags contain (.*)");
		Matcher match = pattern.matcher(line);

		// If no match then assume we're a forward reference to a colour
		if (match.matches()) {

			// New bag
			var colour = match.group(1);
			Bag thisBag = GetBagByColour(colour);

			String all_rules = match.group(2);

			// Shortcut out if this bag can contain no other bags
			if (all_rules.compareTo("no other bags.") == 0) {
				return;
			}

			List<String> rules = Arrays.stream(all_rules.split(", ")).collect(Collectors.toList());
			for (var rule : rules) {
				var p = Pattern.compile("(\\d+) (.*) (bag|bags)\\.{0,1}");
				var m = p.matcher(rule);
				boolean ok = m.matches();
				if (m.matches()) {
					thisBag.contains.put(GetBagByColour(m.group(2)), Integer.valueOf(m.group(1)));
					GetBagByColour(m.group(2)).containedIn.add(thisBag);
				} else {
					throw new RuntimeException("Could not parse rule: '\" + rule + \"'");
				}
			}
		}

	}
}

package aoc2020.day04;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Passport {
	// Over engineered validator enum for passport tags
	enum Tag {
		//	byr (Birth Year) - four digits; at least 1920 and at most 2002.
		BYR("byr", true) {
			@Override
			boolean Validate(String value) {
				return Integer.parseInt(value) >= 1920 && Integer.parseInt(value) <= 2002;
			}
		},
		//	iyr (Issue Year) - four digits; at least 2010 and at most 2020.
		IYR("iyr", true) {
			@Override
			boolean Validate(String value) {
				return Integer.parseInt(value) >= 2010 && Integer.parseInt(value) <= 2020;
			}
		},
		//	eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
		EYR("eyr", true) {
			@Override
			boolean Validate(String value) {
				return Integer.parseInt(value) >= 2020 && Integer.parseInt(value) <= 2030;
			}
		},
		//	hgt (Height) - a number followed by either cm or in:
		//	If cm, the number must be at least 150 and at most 193.
		//	If in, the number must be at least 59 and at most 76.
		HGT("hgt", true) {
			@Override
			boolean Validate(String value) {
				Pattern p = Pattern.compile("(\\d+)(cm|in)");
				Matcher m = p.matcher(value);
				if (!m.matches()) {
					return false;
				}
				if (m.group(2).compareTo("cm") == 0) {
					return Integer.parseInt(m.group(1)) >= 150 && Integer.parseInt(m.group(1)) <= 193;
				}
				return Integer.parseInt(m.group(1)) >= 59 && Integer.parseInt(m.group(1)) <= 76;
			}
		},
		//	hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
		HCL("hcl", true) {
			@Override
			boolean Validate(String value) {
				Pattern p = Pattern.compile("#[0-9a-f]{6}");
				return p.matcher(value).matches();
			}
		},
		//		ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
		ECL("ecl", true) {
			@Override
			boolean Validate(String value) {
				String[] valid = {"amb", "blu", "brn", "gry", "grn", "hzl", "oth"};
				return Arrays.asList(valid).contains(value);
			}
		},
		//		pid (Passport ID) - a nine-digit number, including leading zeroes.
		PID("pid", true) {
			@Override
			boolean Validate(String value) {
				Pattern p = Pattern.compile("\\d{9}");
				return p.matcher(value).matches();
			}
		},
		//	cid (Country ID) - ignored, missing or not.
		CID("cid", false);

		// Default validation is to let anything be allowed
		boolean Validate(String value) {
			return true;
		}

		public final String tag;
		public final boolean required;

		Tag(String tag, boolean required) {
			this.tag = tag;
			this.required = required;
		}
	}

	Map<String, String> values = new HashMap<>();

	void SetValue(String tag, String value) {
		values.put(tag, value);
	}

	// Does this passport contain all the required fields
	boolean IsComplete() {
		for (var tag : Tag.values()) {
			if (tag.required && !values.containsKey(tag.tag)) {
				return false;
			}
		}

		return true;
	}

	// Does this passport contain all fields and are they all valid
	boolean IsValid() {
		for (var tag : Tag.values()) {
			var value = values.get(tag.tag);
			if (tag.required && value == null) {
				return false;
			}
			if (!tag.Validate(value)) {
				return false;
			}
		}

		return true;
	}

	// Whilst we're trying to make passports we might need to repeatedly
	// call so keep track of the current one being constructed.
	static Passport current;

	static {
		current = new Passport();
	}

	// Make a passport from a line of the input. Blank lines indicate the
	// end of a passport. Each line contains one or more key:value pairs
	public static Passport MakePassport(String line) {
		Passport result = current;

		if (line.length() == 0) {
			// on a blank line we return what we've got and start a new passport
			current = new Passport();
		} else {
			// Parse the line for tag:value pairs
			for (var pair : line.split(" ")) {
				var split = pair.split(":");
				current.SetValue(split[0], split[1]);
			}
		}

		return result;
	}
}

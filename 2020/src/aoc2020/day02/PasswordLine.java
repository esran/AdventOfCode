package aoc2020.day02;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordLine {
	int min;
	int max;
	int letter;
	String password;

	PasswordLine(String s) {
		Pattern pattern = Pattern.compile("(\\d+)-(\\d+) (.): (.*)");
		Matcher matcher = pattern.matcher(s);

		if (matcher.matches()) {
			min = Integer.parseInt(matcher.group(1));
			max = Integer.parseInt(matcher.group(2));
			letter = matcher.group(3).charAt(0);
			password = matcher.group(4);
		}
	}
}

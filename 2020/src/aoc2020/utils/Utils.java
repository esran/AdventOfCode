package aoc2020.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Utils {

	public static List<String> loadFileAsLines(String path) {
		System.out.println("Loading: " + path);
		ClassLoader classLoader = MethodHandles.lookup().lookupClass().getClassLoader();
		BufferedReader reader =
			new BufferedReader(new InputStreamReader(Objects.requireNonNull(classLoader.getResourceAsStream(path))));
		return reader.lines().collect(Collectors.toList());
	}

}

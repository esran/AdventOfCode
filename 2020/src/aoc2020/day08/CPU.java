package aoc2020.day08;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CPU {

	private Integer accumulator = 0;

	enum State {
		LOOPED,
		TERMINATED
	}

	static class Statement {
		String command;
		Integer value;

		Statement(String command, Integer value) {
			this.command = command;
			this.value = value;
		}
	}

	CPU() {
	}

	// Executes some code. Will either execute to termination or until
	// a statement would be re-executed.
	public State Execute(List<String> code) {
		accumulator = 0;
		Set<Integer> steps = new HashSet<>();

		Integer step = 0;
		while (!steps.contains(step) && step < code.size()) {
			steps.add(step);
			step += Execute(Parse(code.get(step)));
		}

		return (step == code.size() ? State.TERMINATED : State.LOOPED);
	}

	public Integer Accumulator() {
		return this.accumulator;
	}

	// Executes a statement returning the offset to adjust the program
	// counter by.
	private Integer Execute(Statement stmt) {
		if (stmt.command.compareTo("nop") == 0) {
			return +1;
		}

		if (stmt.command.compareTo("acc") == 0) {
			accumulator += stmt.value;
			return +1;
		}

		if (stmt.command.compareTo("jmp") == 0) {
			return stmt.value;
		}

		throw new RuntimeException("could not process statment: " + stmt);
	}

	// Parse a single line of code into a statement
	public Statement Parse(String line) {
		Pattern p = Pattern.compile("(.*) (.*)");
		Matcher m = p.matcher(line);

		if (!m.matches()) {
			throw new RuntimeException("could not parse command: " + line);
		}

		return new Statement(m.group(1), Integer.parseInt(m.group(2)));
	}
}

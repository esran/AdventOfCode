package aoc2020.day08;

import aoc2020.utils.Utils;

import java.lang.invoke.MethodHandles;
import java.util.List;

public class Main {
	private static final String Path = MethodHandles.lookup().lookupClass().getPackageName() + "-input.txt";

	public static void main(String[] args) {

		var me = new Main();

		System.out.println("Part1: " + me.Part1());
		System.out.println("Part2: " + me.Part2());
	}

	private final List<String> lines_;
	Main() {
		lines_ = Utils.loadFileAsLines(Path);
	}

	public Integer Part1() {
		CPU cpu = new CPU();
		var result = cpu.Execute(lines_);
		assert result == CPU.State.LOOPED : "part 1 did not loop!";
		return cpu.Accumulator();
	}

	public Integer Part2() {
		CPU cpu = new CPU();

		for (var step = 0 ; step < lines_.size() ; ++step) {
			CPU.Statement stmt = cpu.Parse(lines_.get(step));
			if (stmt.command.compareTo("acc") == 0) {
				continue;
			}

			// swap nop <-> jmp
			if (stmt.command.compareTo("nop") == 0) {
				lines_.set(step, "jmp " + stmt.value);
			} else {
				lines_.set(step, "nop " + stmt.value);
			}

			var result = cpu.Execute(lines_);

			// reset line
			lines_.set(step, stmt.command + " " + stmt.value);

			// check for successful termination
			if (result == CPU.State.TERMINATED) {
				return cpu.Accumulator();
			}
		}

		throw new RuntimeException("Part2 could not achieve termination");
	}

}

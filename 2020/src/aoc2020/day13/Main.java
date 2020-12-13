package aoc2020.day13;

import aoc2020.utils.Utils;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
	private static final String Path = MethodHandles.lookup().lookupClass().getPackageName() + "-input.txt";

	public static void main(String[] args) {

		var me = new Main();

		System.out.println("Part1: " + me.Part1());
//		System.out.println("Part2: " + me.Part2());
	}

	static class Bus {
		long id = -1;
		boolean in_service = true;

		Bus(String value) {
			if (value.compareTo("x") == 0) {
				in_service = false;
			} else {
				id = Integer.parseInt(value);
			}
		}
	}

	int EarliestTimestamp;
	List<Bus> Buses;
	Main()
	{
		EarliestTimestamp = Integer.parseInt(Utils.loadFileAsLines(Path).get(0));
		Buses = Arrays.stream(Utils.loadFileAsLines(Path).get(1).split(",")).map(Bus::new).collect(Collectors.toList());
	}

	/*
	Work out the earliest bust we can catch. Multiply its id by the amount of
	of time we have to wait for it.
	 */
	long Part1() {
		Bus earliest = null;
		var wait = -1L;

		for (var bus : Buses.subList(1, Buses.size())) {
			if (!bus.in_service) {
				continue;
			}

			var next = (EarliestTimestamp / bus.id) * bus.id;
			if (next < EarliestTimestamp) {
				next += bus.id;
			}

			if (earliest == null || wait > next - EarliestTimestamp) {
				earliest = bus;
				wait = next - EarliestTimestamp;
			}
		}

		assert earliest != null;
		return earliest.id * wait;
	}

	/*
	Using the bus list, find the earliest time that the buses arrive in those
	positions after that time. Where x indicates we don't care what buses arrive
	at that time.
	 */
	long Part2() {
		// Find highest bus id
		var highest = 0;
		for (var pos = 0 ; pos < Buses.size() ; ++pos) {
			if (!Buses.get(pos).in_service) {
				continue;
			}

			if (Buses.get(pos).id > Buses.get(highest).id) {
				highest = pos;
			}
		}

		// timestamp + highest must be an arrival time for the highest bus
		var timestamp = Buses.get(highest).id - highest;

		while (!IsValidPart2(timestamp)) {
			timestamp += Buses.get(highest).id;
			if (timestamp < 0) {
				throw new RuntimeException("overflow!");
			}
		}

		return timestamp;
	}

	/*
	Work out if the buses turn up in sequence. Note that we are assuming
	here that the first bus is in service
	 */
	boolean IsValidPart2(long timestamp) {
		if (timestamp < 0) {
			return false;
		}

		for (var offset = 0 ; offset < Buses.size() ; ++offset) {
			var bus = Buses.get(offset);
			if (!bus.in_service) {
				continue;
			}

//			System.out.println(timestamp + " " + offset + " " + bus.id + " " + (timestamp + offset) % bus.id);
			if ((timestamp + offset) % bus.id != 0) {
				return false;
			}
		}

		return true;
	}
}

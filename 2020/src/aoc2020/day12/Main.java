package aoc2020.day12;

import aoc2020.utils.Utils;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

public class Main {
	private static final String Path = MethodHandles.lookup().lookupClass().getPackageName() + "-input.txt";

	public static void main(String[] args) {

		var me = new Main();

		System.out.println("Part1: " + me.Part1());
		System.out.println("Part2: " + me.Part2());
	}

	static class Move {
		char direction;
		int amount;

		Move(String line) {
			Pattern p = Pattern.compile("(.)(\\d+)");
			Matcher m = p.matcher(line);
			if (!m.matches()) {
				throw new RuntimeException("could not parse line: " + line);
			}
			this.direction = m.group(1).charAt(0);
			this.amount = Integer.parseInt(m.group(2));
		}
	}

	static class Position {
		int east;
		int north;

		Position() {
			this.east = 0;
			this.north = 0;
		}

		void Move(char direction, int amount) {
			switch (direction) {
				case 'N' -> north += amount;
				case 'E' -> east += amount;
				case 'S' -> north -= amount;
				case 'W' -> east -= amount;
				default -> throw new RuntimeException("invalid direction: " + direction);
			}
		}

		void Adjust(int east, int north) {
			this.east += east;
			this.north += north;
		}

		void Rotate(char direction, int amount) {
			for (; amount > 0; amount -= 90) {
				int old_east = this.east;
				if (direction == 'R') {
					this.east = this.north;
					this.north = -old_east;
				} else {
					this.east = -this.north;
					this.north = old_east;
				}
			}
		}
	}

	List<Move> Moves;

	Main() {
		Moves = Utils.loadFileAsLines(Path).stream().map(Move::new).collect(Collectors.toList());
	}

	/*
	Work out the Manhattan position corresponding to the sequence of moves from the origin, with
	initial facing being East
	 */
	int Part1() {
		Position position = new Position();
		char facing = 'E';
		char direction = 'E';

		for (var move : Moves) {
			boolean moving = true;
			switch (move.direction) {
				case 'N', 'S', 'E', 'W' -> direction = move.direction;
				case 'F' -> direction = facing;
				case 'R', 'L' -> {
					facing = NewDirection(facing, move.direction, move.amount);
					moving = false;
				}
			}

			if (moving) {
				position.Move(direction, move.amount);
			}
		}

		return abs(position.east) + abs(position.north);
	}

	char[] compass = {'N', 'E', 'S', 'W'};

	char NewDirection(char direction, char rotation, int amount) {
		var point = CompassPoint(direction);
		var adjust = rotation == 'R' ? +1 : -1;
		while (amount > 0) {
			point += adjust;
			amount -= 90;
		}
		point %= 4;
		while (point < 0) {
			point += 4;
		}
		return compass[point];
	}

	int CompassPoint(char direction) {
		for (var i = 0; i < compass.length; ++i) {
			if (compass[i] == direction) {
				return i;
			}
		}

		throw new RuntimeException("invalid direction: " + direction);
	}

	/*
	This time, move a waypoint around the ship and move the ship repeatedly based
	on the waypoints position relative to the ship.
	 */
	int Part2() {
		Position waypoint = new Position();
		waypoint.east = 10;
		waypoint.north = 1;
		Position position = new Position();

		for (var move : Moves) {
			switch (move.direction) {
				case 'N', 'S', 'E', 'W' -> waypoint.Move(move.direction, move.amount);
				case 'F' -> {
					for (var i = 0; i < move.amount; ++i) {
						position.Adjust(waypoint.east, waypoint.north);
					}
				}
				case 'R', 'L' -> waypoint.Rotate(move.direction, move.amount);
			}
		}

		return abs(position.east) + abs(position.north);
	}

}

#!/usr/bin/env perl

use strict;
use warnings;

use Carp;

package wires;

sub AddWire
{
	my ($wire, $board) = @_;

	if (not defined $board) {
		$board = {};
		$board->{xover} = ();
        $board->{wires} = 0;
	}

    my $wire_num = $board->{wires} + 1;
    my $steps = 0;
	my $x = 0;
	my $y = 0;

	for my $move (split(',', $wire)) {
		my $dir = substr $move, 0, 1;
		my $len = substr $move, 1;

		my $x_offset = 0;
		my $y_offset = 0;
		if ($dir eq 'R') {
			$x_offset = 1;
		} elsif ($dir eq 'U') {
			$y_offset = 1;
		} elsif ($dir eq 'L') {
			$x_offset = -1;
		} elsif ($dir eq 'D') {
			$y_offset = -1;
		} else {
			die "invalid input: $wire";
		}

		foreach (1..$len) {
			$x += $x_offset;
			$y += $y_offset;
            $steps++;

			$board->{$x} = {} if not defined $board->{$x};

			if (defined $board->{$x}->{$y}) {
                if (not defined $board->{$x}->{$y}->{$wire_num}) {
                    $board->{$x}->{$y}->{$wire_num} = $steps;
                }
			} else {
				$board->{$x}->{$y} = { $wire_num => $steps };
			}

			if (scalar keys %{$board->{$x}->{$y}} > 1) {
                my $steps_total = 0;
                for my $wnum (keys %{$board->{$x}->{$y}}) {
                    $steps_total += $board->{$x}->{$y}->{$wnum};
                }

				my $xover = { x => $x, y => $y, total => $steps_total };

				if (not defined $board->{xover}) {
					$board->{xover} = [ $xover ];
				} else {
					push $board->{xover}, $xover;
				}
			}
		}
	}

    $board->{wires} = $wire_num;

	return $board;
}

sub CalcDistance
{
	my ($xover) = @_;

	Carp::confess 'no xover provided for distance calculation' if not defined $xover;

	return abs($xover->{x}) + abs($xover->{y});
}

sub FindClosestXover
{
	my ($board) = @_;

	my $result;
	my $distance;

	for my $xover (@{$board->{xover}}) {
		if (not defined $result) {
			$result = $xover;
			$distance = CalcDistance($xover);
		}

		my $new_distance = CalcDistance($xover);
		if ($new_distance < $distance) {
			$result = $xover;
			$distance = $new_distance;
		}
	}

	return $result;
}

sub FindCheapestXover
{
    my ($board) = @_;

    my $result;

    for my $xover (@{$board->{xover}}) {
        if (not defined $result) {
            $result = $xover;
            next;
        }

        if ($xover->{total} < $result->{total}) {
            $result = $xover;
        }
    }

    return $result;
}

my $TestData = [
	{ 
		wire1 => 'R75,D30,R83,U83,L12,D49,R71,U7,L72',
		wire2 => 'U62,R66,U55,R34,D71,R55,D58,R83',
		distance => 159,
	},
	{
		wire1 => 'R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51',
		wire2 => 'U98,R91,D20,R16,D67,R40,U7,R15,U6,R7',
		distance => 135,
	},
];

my $TestDataPart2 = [
    {
        wire1 => 'R75,D30,R83,U83,L12,D49,R71,U7,L72',
        wire2 => 'U62,R66,U55,R34,D71,R55,D58,R83',
        steps => 610,
    },
    {
        wire1 => 'R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51',
        wire2 => 'U98,R91,D20,R16,D67,R40,U7,R15,U6,R7',
        steps => 410,
    },
];

use Test::More;
use Data::Dumper;

sub Test
{
	for my $test (@$TestData) {
		my $board = AddWire($test->{wire1});
		$board = AddWire($test->{wire2}, $board);
		my $xover = FindClosestXover($board);
		is(CalcDistance($xover), $test->{distance}, 'correct distance');
	}

    for my $test (@$TestDataPart2) {
        my $board = AddWire($test->{wire1});
        $board = AddWire($test->{wire2}, $board);
        my $xover = FindCheapestXover($board);
        is($xover->{total}, $test->{steps}, 'correct steps');
    }

	done_testing();
}

1;

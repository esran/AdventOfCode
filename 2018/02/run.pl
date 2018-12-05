#!/usr/bin/env perl

use strict;
use warnings;

my $two_count = 0;
my $three_count = 0;

open my $INPUT, "<", "./input"
	or die "could not open input file";

my @packages = ();
my $part2_result;

while(<$INPUT>) {
	# part2 - check for a near match with previous entries
	unless ($part2_result) {
		for my $pkg (@packages) {
			my @prev = split("", $pkg);
			my @curr = split("", $_);

			my $mismatch = 0;
			my $common = "";
			for my $i (0.. scalar @prev - 1) {
				if ($prev[$i] ne $curr[$i]) {
					$mismatch++;
				} else {
					$common .= $prev[$i];
				}
			}

			if ($mismatch == 1) {
				$part2_result = $common;
				last;
			}
		}

		push @packages, $_;
	}

	my @sorted = sort split("", $_);

	my $count = 0;
	my $char = undef;
	my $two_done = undef;
	my $three_done = undef;

	for my $ch (@sorted) {
		if (not $char) {
			$count = 1;
			$char = $ch;
		} elsif ($char eq $ch) {
			$count++;
		} else {
			if ($count == 2 and not $two_done) {
				$two_count++;
				$two_done = 1;
			} elsif ($count == 3 and not $three_done) {
				$three_count++;
				$three_done = 1;
			}

			$char = $ch;
			$count = 1;
		}
	}

	# Need to handle the end of the string
	if ($count == 2 and not $two_done) {
		$two_count++;
		$two_done = 1;
	} elsif ($count == 3 and not $three_done) {
		$three_count++;
		$three_done = 1;
	}
}

close $INPUT;

my $part1_result = $two_count * $three_count;

print "part1: $part1_result\n";
print "part2: $part2_result\n";

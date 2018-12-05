#!/usr/bin/env perl

use strict;
use warnings;

my $fabric = {};
my $claims = {};

print "reading input...\n";

open my $INPUT, "<", "./input"
	or die "could not open input file";

while (<$INPUT>) {
	if ($_ =~ /(#[0-9]+) @ ([0-9]+),([0-9]+): ([0-9]+)x([0-9]+)/) {
		# record claim for part2
		$claims->{$1} = { x => $2, y => $3, xs => $4, ys => $5 };

		# add this claim to the map
		for my $x ($2+1..$2+$4) {
			for my $y ($3+1..$3+$5) {
				if (not defined $fabric->{$x}) {
					$fabric->{$x} = { $y => 1 };
				} elsif (not defined $fabric->{$x}->{$y}) {
					$fabric->{$x}->{$y} = 1;
				} else {
					$fabric->{$x}->{$y}++; 
				}
			}
		}
	}
}

close $INPUT;

my $part1_result = 0;
my $part2_result;

my $num_claims = scalar keys %$claims;

print "found $num_claims claims\n";
print "processing fabric...\n";

for my $x (keys %$fabric) {
	for my $y (keys %{$fabric->{$x}}) {
		if ($fabric->{$x}->{$y} > 1) {
			# count how many locations are overclaimed
			$part1_result++;
		} elsif (not $part2_result) {
			# loop through all claims and identify
			# which claim this is in
			for my $id (keys %$claims) {
				my $claim = $claims->{$id};
				if ($x > $claim->{x}
						and $x <= ($claim->{x} + $claim->{xs})
						and $y > $claim->{y}
						and $y <= ($claim->{y} + $claim->{ys})
				) {
					# having found the right claim increment its count
					# of singularly owned locations
					$claim->{single} = 0 if not $claim->{single};
					$claim->{single}++;

					# printf "claim %5.5s size %3dx%3d has %4d / %4d singles\n", $id, $claim->{xs}, $claim->{ys}, $claim->{single}, ($claim->{xs} * $claim->{ys});

					# this is the result if all its locations are singularly owned
					$part2_result = $id if $claim->{single} == ($claim->{xs} * $claim->{ys});

					# A single use location is obviously only in one claim
					last;
				}
			}
		}
	}
}

print "part1: $part1_result\n";
print "part2: $part2_result\n";

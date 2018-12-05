#!/usr/bin/env perl

use strict;
use warnings;

my $part1_freq;
my $freq = 0;
my %freqs;

while (1) {
	open my $INPUT, "<", "./input"
		or die "could not open input file";

	while (<$INPUT>) {
		$freq += int($_);
		$freqs{$freq} += 1 if $freqs{$freq};
		$freqs{$freq} = 1 if not $freqs{$freq};

		last if $freqs{$freq} > 1;
	}

	close $INPUT;

	$part1_freq = $freq if not $part1_freq;

	last if $freqs{$freq} > 1;
}

print "part1: $part1_freq\n";
print "part2: $freq\n";

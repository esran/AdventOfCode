#!/usr/bin/env perl

use strict;
use warnings;

use wires;

sub LoadInput
{
	my ($file) = @_;

	open my $IN, "<", $file
		or die "could not open input file '$file': $!";

	my @data;
	while (<$IN>) {
		push @data, $_;
	}

	close $IN;

	return \@data;
}

# Check an argument has been passed
die "usage: $0 <input file>" if not defined $ARGV[0];

# Load the data
my $data = &LoadInput($ARGV[0]);

my $board = wires::AddWire($data->[0]);
$board = wires::AddWire($data->[1], $board);
my $xover = wires::FindClosestXover($board);
my $distance = wires::CalcDistance($xover);

print "Distance: $distance\n";
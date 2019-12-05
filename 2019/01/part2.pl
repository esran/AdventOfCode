#!/usr/bin/env perl

use strict;
use warnings;

use fuel;

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

# Process the data to calculate total fuel
my $fuel = 0;
for my $module (@$data) {
	$fuel += fuel::CalcFuelRecursive($module);
}

# Results!
print "Total Fuel: $fuel\n";

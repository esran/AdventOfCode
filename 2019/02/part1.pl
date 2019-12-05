#!/usr/bin/env perl

use strict;
use warnings;

use intcode;

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

# Adjust the data for 1202 error
my @array = split(',', $data->[0]);
$array[1] = 12;
$array[2] = 2;

my $string = intcode::Process(join(',', @array));
@array = split(',', $string);

# Results!
print "Position 0: $array[0]\n";

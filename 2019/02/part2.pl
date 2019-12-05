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

# Save original input
my $orig_input = $data->[0];

# Iterate over values to get the solution
my @array;
noun: for my $noun (0..99) {
	verb: for my $verb (0..99) {
		@array = split(',', $orig_input);
		$array[1] = $noun;
		$array[2] = $verb;
		@array = split(',', intcode::Process(join(',', @array)));

		last noun if $array[0] == 19690720;
	}
}

my $noun = $array[1];
my $verb = $array[2];
my $result = $noun * 100 + $verb;

# Results!
print "Noun * 100 + Verb: $result\n";

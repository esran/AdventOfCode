#!/usr/bin/env perl

use strict;
use warnings;

package intcode;

sub Process
{
	my ($input) = @_;

	my @array = split(/,/, $input);

	my $index = 0;
	while (defined $array[$index] and $array[$index] != 99) {
		my $opcode = $array[$index];

		if ($opcode != 1 and $opcode != 2) {
			die "invalid opcode $opcode at position $index";
		}

		my $pos1 = $array[$index+1];
		my $pos2 = $array[$index+2];
		my $dest = $array[$index+3];

		if ($opcode == 1) {
			$array[$dest] = $array[$pos1] + $array[$pos2];
		} elsif ($opcode == 2) {
			$array[$dest] = $array[$pos1] * $array[$pos2];
		}

		$index += 4;
	}

	return join(',', @array);
}

use Test::More;

my $TestData = {
    '1,0,0,0,99' => '2,0,0,0,99',
    '2,3,0,3,99' => '2,3,0,6,99',
    '2,4,4,5,99,0' => '2,4,4,5,99,9801',
    '1,1,1,4,99,5,6,0,99' => '30,1,1,4,2,5,6,0,99',
};

sub Test
{
	for my $test (keys %$TestData) {
		my $result = Process($test);
		is($result, $TestData->{$test}, "$test -> $TestData->{$test}");
	}
}

1;

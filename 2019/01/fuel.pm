#!/usr/bin/env perl

use strict;
use warnings;

package fuel;

sub CalcFuel
{
	my ($mass) = @_;

	return int($mass / 3) - 2;
}

sub CalcFuelRecursive
{
	my ($mass) = @_;
	my $fuel = CalcFuel($mass);

	my $total = 0;
	while ($fuel > 0) {
		$total += $fuel;
		$fuel = CalcFuel($fuel);
	}

	return $total;
}

use Test::More;

my $TestData = {
	12 => 2,
	14 => 2,
	1969 => 654,
	100756 => 33583,
};

my $TestDataRecursive = {
	14 => 2,
	1969 => 966,
	100756 => 50346,
};

sub Test {
	for my $test (keys %$TestData) {
		my $fuel = CalcFuel($test);
		is ($fuel, $TestData->{$test}, "$test => $TestData->{$test}");
	}

	for my $test (keys %$TestDataRecursive) {
		my $fuel = CalcFuelRecursive($test);
		is ($fuel, $TestDataRecursive->{$test}, "$test => $TestDataRecursive->{$test}");
	}
}

#!/usr/bin/env perl

use strict;
use warnings;

use DateTime;
use Data::Dumper;

open my $INPUT, '<', './input.txt'
	or die 'could not open input.txt';

my $data = {};

while(<$INPUT>) {
	my ($year, $mon, $day, $hour, $minute, $action) = ($_ =~ /\[([0-9]+)-([0-9]+)-([0-9]+) ([0-9]+):([0-9]+)\] (.*)/);

	my $dt = DateTime->new (
		year => $year,
		month => $mon,
		day => $day,
	);

	# shift date a day forward if the timestamp is not 00:xx
	if ($hour != 0) {
		$dt = $dt + DateTime::Duration->new ( days => 1 );
	}

	my $date = sprintf "%4d-%02d-%02d", $dt->year, $dt->month, $dt->day;

	$data->{$date} = {} if not $data->{$date};

	if ($action eq 'wakes up') {
		$data->{$date}->{wakes}->{$minute} = 1;
	} elsif ($action eq 'falls asleep') {
		$data->{$date}->{sleeps}->{$minute} = 1;
	} else {
		my ($guard) = ($action =~ /Guard #([0-9]+) .*/);
		$data->{$date}->{guard} = $guard;
	}
}

my $guards = {};
my $sleepiest;
my $frequent;
for my $date (keys %$data) {
	# get list of sleep times
	my @sleeps = sort keys %{$data->{$date}->{sleeps}};
	my @wakes = sort keys %{$data->{$date}->{wakes}};

	my $guard = $data->{$date}->{guard};

	# initialise guards
	$guards->{$guard} = {} if not defined $guards->{$guard};
	$guards->{$guard}->{total} = 0 if not defined $guards->{$guard}->{total};
	$guards->{$guard}->{max} = { min => 0, value => 0 } if not defined $guards->{$guard}->{max};
	$guards->{$guard}->{alseep} = {} if not defined $guards->{$guard}->{asleep};

	# calc total duration of sleep
	for my $sleep (1..scalar @sleeps) {
		my $start = $sleeps[$sleep-1];
		my $end = $wakes[$sleep-1] - 1;

		# print "date: $date, guard: $guard, $start..$end\n"
		# 	if $guard == 1559;

		for my $min ($start..$end) {
			$guards->{$guard}->{asleep}->{$min} = 0 if not defined $guards->{$guard}->{asleep}->{$min};
			$guards->{$guard}->{asleep}->{$min}++;
			$guards->{$guard}->{total}++;

			if ($guards->{$guard}->{asleep}->{$min} > $guards->{$guard}->{max}->{value}) {
				$guards->{$guard}->{max}->{min} = $min;
				$guards->{$guard}->{max}->{value} = $guards->{$guard}->{asleep}->{$min};
			}
		}

		# print Dumper($guards->{$guard})
		# 	if $guard == 1559;
	}

	$sleepiest = $guard if not defined $sleepiest;
	$sleepiest = $guard if $guards->{$guard}->{total} > $guards->{$sleepiest}->{total};

	$frequent = $guard if not defined $frequent;
	$frequent = $guard if $guards->{$guard}->{max}->{value} > $guards->{$frequent}->{max}->{value};
}

# print Dumper($data);
# print Dumper($guards);

my $part1 = $sleepiest * $guards->{$sleepiest}->{max}->{min};
print "part1: $part1\n";

my $part2 = $frequent * $guards->{$frequent}->{max}->{min};
print "part2: $part2\n";

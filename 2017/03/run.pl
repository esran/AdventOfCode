#!/usr/bin/env perl

my $input = 347991;

my $grid = ();
my $sum_grid = ();

sub do_sum
{
	my ($x, $y) = @_;
	my $sum = 0;

	for $i (-1..1)
	{
		for $j (-1..1)
		{
			next if ($i == 0 and $j == 0);
			next if (not defined $sum_grid->{$x+$i}->{$y+$j});
			$sum += $sum_grid->{$x+$i}->{$y+$j};
		}
	}

	return $sum;
}

# if we're moving in a direction where would we like to go next if possible?
$states->{RIGHT}->{next} = UP;
$states->{UP}->{next}    = LEFT;
$states->{LEFT}->{next}  = DOWN;
$states->{DOWN}->{next}  = RIGHT;

# if we move in a direction what adjustments do we need to make
$states->{RIGHT}->{x} = 1;
$states->{RIGHT}->{y} = 0;
$states->{UP}->{x} = 0;
$states->{UP}->{y} = 1;
$states->{LEFT}->{x} = -1;
$states->{LEFT}->{y} = 0;
$states->{DOWN}->{x} = 0;
$states->{DOWN}->{y} = -1;

# starting state
my $dir = DOWN;
my $num = 1;
my $x = 0;
my $y = 0;
my $larger = undef;

# we can always put 1 in the center
$grid->{$x}->{$y} = $num++;
$sum_grid->{$x}->{$y} = 1;

# now find the positions for everything else
while ($num <= $input)
{
	$turn = $states->{$dir}->{next};
	$turn_x = $x + $states->{$turn}->{x};
	$turn_y = $y + $states->{$turn}->{y};

	if (not defined $grid->{$turn_x}->{$turn_y})
	{
		$dir = $states->{$dir}->{next};
		$x = $turn_x;
		$y = $turn_y;
	}
	else
	{
		$x = $x + $states->{$dir}->{x};
		$y = $y + $states->{$dir}->{y};
	}

	# part 1
	$grid->{$x}->{$y} = $num++;

	# part 2
	$sum_grid->{$x}->{$y} = do_sum($x, $y);
	if (not defined $larger and $sum_grid->{$x}->{$y} > $input)
	{
		$larger = $sum_grid->{$x}->{$y};
	}
}

$d = abs($x) + abs($y);

print "x=$x, y=$y, d=$d\n";
print "larger=$larger\n";

#!/usr/bin/env perl

use strict;

use Digest::MD5 qw(md5_hex);

my $input = "bgvyzdsv";
my $num = 0;
my $hash = "";
my $hash_one="";
my $one = 0;

# Loop until we find the answer
do
{
	$num++;
	$hash = md5_hex($input . $num);

	# Remember this for part one if the hash starts with 5 zeros
	if ($hash =~ /^00000.*$/ && $one <= 0) {
		$one = $num;
		$hash_one = $hash;
	}

	# End the loop when the has starts with 6 zeros
} while (not $hash =~ /^000000.*$/);

printf "Number[1]: $one (hash of $input$one = $hash_one)\n";
printf "Number[2]: $num (hash of $input$num = $hash)\n";

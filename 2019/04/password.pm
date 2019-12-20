package password;
use strict;
use warnings FATAL => 'all';

sub is_valid
{
    my ($password) = @_;

    my $decrease;
    my $double;

    my $prev_char;
    for my $char (split //, $password) {
        if (defined $prev_char) {
            if ($prev_char eq $char) {
                $double = 1;
            } elsif ($prev_char > $char) {
                $decrease = 1;
            }
        }

        $prev_char = $char;
    }

    return ($double and not $decrease) ? 1 : 0;
}

sub is_valid_doubles_only
{
    my ($password) = @_;

    # check they're increasing
    my $prev_char;
    for my $char (split //, $password) {
        if (defined $prev_char and $prev_char > $char) {
            # print "$password not ok (not increasing)\n";
            return 0;
        }
        $prev_char = $char;
    }

    # check for doubles that aren't triple or more
    my @chars = split //, $password;
    my $match_char = $chars[0];
    my $match_len = 1;
    for my $pos (1..@chars-1) {
        if ($chars[$pos] == $match_char) {
            $match_len++;
        } elsif ($match_len == 2) {
            return 1;
        } else {
            $match_char = $chars[$pos];
            $match_len = 1;
        }
    }

    if ($match_len == 2) {
        return 1;
    }

    # print "$password not ok (no double)\n";
    return 0;
}

my $TestData = [
    { value => '111111', result => 1},
    { value => '223450', result => 0},
    { value => '123789', result => 0},
];

my $TestDataPart2 = [
    { value => '112233', result => 1 },
    { value => '123444', result => 0 },
    { value => '111122', result => 1 },
];

use Test::More;

sub Test
{
    for my $test (@$TestData) {
        is(is_valid($test->{value}), $test->{result}, "password validity $test->{value}");
    }

    for my $test (@$TestDataPart2) {
        is(is_valid_doubles_only($test->{value}), $test->{result}, "password validity (doubles) $test->{value}");
    }

    done_testing();
}

1;
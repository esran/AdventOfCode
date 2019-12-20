#!/usr/bin/perl

use strict;
use warnings FATAL => 'all';

use password;

my $valid = 0;
for my $password (359282..820401) {
    if (password::is_valid($password)) {
        $valid++;
    }
}

print "Valid passwords: $valid\n";
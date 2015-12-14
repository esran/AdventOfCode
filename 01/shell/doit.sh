#!/usr/bin/env bash

prog_path=$(readlink -f "$0")
prog_dir=$(dirname "$prog_path")
parent_dir=$(dirname "$prog_dir")

input_file="${1:-$parent_dir/input}"

[[ ! -f $input_file ]] && {
	echo "Error: input file '$input_file' not found"
	exit 1
}

# The unix way...
# Simply count the number if open/close parenthesis
# and subtract one from the other
# Give part two, this method is no good as it doesn't
# find us the first move into the basement
open_paren=$(sed 's/)//g' "$input_file" | wc -c)
close_paren=$(sed 's/(//g' "$input_file" | wc -c)
floor=$((open_paren - close_paren))

echo "Open: $open_paren  Close: $close_paren"
echo "Floor: $floor"

# The shell way...
# Trim the input string one character at a time. If
# we trim ( then increment the count, otherwise
# decrement it.
# For the second part, keep track of the number of moves
# and spot when the floor first goes negative and record
# that move
floor=0
moves=0
basement=0
read input_data < "$input_file"
while [[ ! -z $input_data ]];
do
	try_open=${input_data#\(}
	try_close=${input_data#\)}
	if [[ $try_open != $input_data ]]; then
		(( floor = floor + 1 ))
		input_data="$try_open"
	elif [[ $try_close != $input_data ]]; then
		(( floor = floor - 1 ))
		input_data="$try_close"
	else
		echo "Error: unexpected character in string '$input_data'"
		exit 1
	fi

	(( moves += 1 ))
	[[ $basement -eq 0 && $floor -lt 0 ]] && {
		basement=$moves
	}
done

echo "Floor: $floor"
echo "Basement: $basement"

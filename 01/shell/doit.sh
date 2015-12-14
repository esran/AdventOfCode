#!/usr/bin/env bash

input="$1"

if [[ -z $input ]]; then
	echo "Usage: $0 <input string>"
	exit 1
fi

open_paren=$(echo "$input" | sed 's/)//g' | wc -c)
close_paren=$(echo "$input" | sed 's/(//g' | wc -c)
floor=$((open_paren - close_paren))

echo "Floor: $floor"


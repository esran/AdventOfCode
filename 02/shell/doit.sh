#!/usr/bin/env bash

input_file="${1:-../input}"

if [[ ! -f $input_file ]]; then
	echo "Error: input file '$input_file' not found"
	exit 1
fi

total_paper=0
total_ribbon=0

while read box_dimension
do
	dims=( $(echo "$box_dimension" | tr x "\n" | sort -n) )
	(( total_paper += 2 * dims[0] * dims[1] ))
	(( total_paper += 2 * dims[0] * dims[2] ))
	(( total_paper += 2 * dims[1] * dims[2] ))
	(( total_paper += dims[0] * dims[1] ))

	(( total_ribbon += 2 * dims[0] + 2 * dims[1] ))
	(( total_ribbon += dims[0] * dims[1] * dims[2] ))
done < "$input_file"

echo "Total Paper: $total_paper"
echo "Total Ribbon: $total_ribbon"

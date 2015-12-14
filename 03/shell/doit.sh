#!/usr/bin/env bash

prog_path=$(readlink -f "$0")
prog_dir=$(dirname "$prog_path")
parent_dir=$(dirname "$prog_dir")

input_file="${1:-$parent_dir/input}"

if [[ ! -f $input_file ]]; then
	echo "Error: input file '$input_file' not found"
	exit 1
fi

# NOTE: This was originally reading multiple instruction strings
# from the file in a loop and processing each one. However, when
# tried with the actual puzzle input it fails to read properly in
# loop context!

read inst < "$input_file"

# Use a 2D array to record number of gifts per house
declare -A grid
grid[0,0]=1
xpos=0
ypos=0
moves=0
houses=1		# We start with a delivery to current house

while [[ ! -z $inst ]];
do
	# Find direction by trying each possible char
	# removed form the front of the instruction string
	if [[ ${inst#^} != $inst ]]; then
		(( ypos += 1 ))
	elif [[ ${inst#v} != $inst ]]; then
		(( ypos -= 1 ))
	elif [[ ${inst#>} != $inst ]]; then
		(( xpos += 1 ))
	elif [[ ${inst#<} != $inst  ]]; then
		(( xpos -= 1 ))
	else
		echo "Error: unpexpected input data '$inst'"
		exit 1
	fi

	# If this house hasn't been visited yet then we
	# can increment our houses count
	[[ ! -n ${grid[$xpos,$ypos]} ]] && (( houses += 1 ))

	# Add a gift to this house
	(( grid[$xpos,$ypos] += 1 ))

	# Track total number of moves
	(( moves += 1 ))

	# Remember to trim the direction just processed
	inst=${inst#?}
done

echo "Houses: $houses (in $moves moves)"

# Second Part
declare -A grid_two
grid_two[0,0]=2
santa_xpos=0
santa_ypos=0
robo_xpos=0
robo_ypos=0
houses=1
santa_moves=0
robo_moves=0

read inst < "$input_file"
while [[ ! -z $inst ]];
do
	# Do Santa First...

	# Find direction by trying each possible char
	# removed form the front of the instruction string
	if [[ ${inst#^} != $inst ]]; then
		(( santa_ypos += 1 ))
	elif [[ ${inst#v} != $inst ]]; then
		(( santa_ypos -= 1 ))
	elif [[ ${inst#>} != $inst ]]; then
		(( santa_xpos += 1 ))
	elif [[ ${inst#<} != $inst  ]]; then
		(( santa_xpos -= 1 ))
	else
		echo "Error: unpexpected input data '$inst'"
		exit 1
	fi

	#echo "Santa: [$santa_xpos,$santa_ypos] = ${grid_two[$santa_xpos,$santa_ypos]}"

	# If this house hasn't been visited yet then we
	# can increment our houses count
	[[ ! -n ${grid_two[$santa_xpos,$santa_ypos]} ]] && (( houses += 1 ))

	# Add a gift to this house
	(( grid_two[$santa_xpos,$santa_ypos] += 1 ))

	# Track total number of moves
	(( santa_moves += 1 ))

	# Remember to trim the direction just processed
	inst=${inst#?}

	# Then do Robo-Santa

	# Find direction by trying each possible char
	# removed form the front of the instruction string
	if [[ ${inst#^} != $inst ]]; then
		(( robo_ypos += 1 ))
	elif [[ ${inst#v} != $inst ]]; then
		(( robo_ypos -= 1 ))
	elif [[ ${inst#>} != $inst ]]; then
		(( robo_xpos += 1 ))
	elif [[ ${inst#<} != $inst  ]]; then
		(( robo_xpos -= 1 ))
	else
		echo "Error: unpexpected input data '$inst'"
		exit 1
	fi

	#echo "Robo : [$robo_xpos,$robo_ypos] = ${grid_two[$robo_xpos,$robo_ypos]}"

	# If this house hasn't been visited yet then we
	# can increment our houses count
	[[ ! -n ${grid_two[$robo_xpos,$robo_ypos]} ]] && (( houses += 1 ))

	# Add a gift to this house
	(( grid_two[$robo_xpos,$robo_ypos] += 1 ))

	# Track total number of moves
	(( robo_moves += 1 ))

	# Remember to trim the direction just processed
	inst=${inst#?}
done

echo "Houses: $houses (in $santa_moves for Santa and $robo_moves for Robo-Santa)"

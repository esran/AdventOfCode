#!/usr/bin/env bash

prog_path=$(readlink -f "$0")
prog_dir=$(dirname "$prog_path")
parent_dir=$(dirname "$prog_dir")

input_file="${1:-$parent_dir/input}"

if [[ ! -f $input_file ]]; then
	echo "Error: input file '$input_file' not found"
	exit 1
fi

read input < "$input_file"
num=0
hash="abcdef"

# Much loopage. This is very slow as it calls out to perl every
# time. This was originally md5sum but (I think due to encoding)
# md5sum gives wrong results.
# Due to the slowness of this I've not done part 2 in shell yet.
until [[ ${hash#00000} != $hash ]];
do
	(( num += 1 ))
	hash=$(perl -e "use Digest::MD5 qw(md5_hex); print md5_hex("${input}${num}") . "\n";")

	(( num_100 = num % 100 ))
	[[ $num_100 -eq 0 ]] && printf "\r%10d  %-30.30s" $num $hash
done

printf "\n"

echo "Hash of $num = $hash"

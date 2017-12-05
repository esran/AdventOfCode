#!/usr/bin/env bash

ok=0

while read -r -a line
do
	num=${#line[@]}
	i=0
	fail=
	while (( i < num ))
	do
		(( j = i+1 ))
		while (( j < num ))
		do
			if [[ "${line[$i]}" = "${line[$j]}" ]]
			then
				fail=1
				break 2
			fi
			(( j++ ))
		done
		(( i++ ))
	done

	if [[ -z $fail ]]
	then
		(( ok++ ))
	fi
done < ./input.txt

echo "$ok"

anag_ok=0

while read -r -a line
do
	num=${#line[@]}
	i=0
	fail=
	while (( i < num ))
	do
		(( j = i+1 ))
		while (( j < num ))
		do
			sl1=$(echo "${line[$i]}" | grep -o . | sort | tr -d "\n")
			sl2=$(echo "${line[$j]}" | grep -o . | sort | tr -d "\n")
			if [[ "$sl1" = "$sl2" ]]
			then
				fail=1
				break 2
			fi
			(( j++ ))
		done
		(( i++ ))
	done

	if [[ -z $fail ]]
	then
		(( anag_ok++ ))
	fi
done < ./input.txt

echo "$anag_ok"

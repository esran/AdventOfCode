#!/usr/bin/env bash

sum=0
while read -r -a nums
do
	min=
	max=

	for num in "${nums[@]}"
	do
		if [[ -z $min ]]
		then
			min=$num
			max=$num
		elif (( num < min ))
		then
			min=$num
		elif (( num > max ))
		then
			max=$num
		fi
	done
	(( sum += max - min ))
done < ./input.txt

echo $sum

sum=0
while read -r -a nums
do
	result=
	i=0
	j=0
	while (( i < ${#nums[@]} ))
	do
		j=0
		while (( j < ${#nums[@]} ))
		do
			if (( j == i ))
			then
				(( j++ ))
			fi

			a=${nums[$i]}
			b=${nums[$j]}

			if (( b > a ))
			then
				t=$b
				b=$a
				a=$t
			fi

			(( c = a / b ))
			(( d = b * c ))

			# echo "a=$a, b=$b, c=$c, d=$d"

			if (( d == a ))
			then
				result=$c
				break;
			fi

			(( j++ ))
		done

		if [[ ! -z $result ]]
		then
			break
		fi

		(( i++ ))
	done

	(( sum += result ))
done < ./input.txt

echo $sum

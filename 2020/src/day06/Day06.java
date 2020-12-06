package day06;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;


// AdventOfCode 2020 Day 4
public class Day06 {
	private static final String Path = "day06-input.txt";

	public static void main(String[] args) {

		var me = new Day06();
		var part1 = me.Part1();
		var part2 = me.Part2();

		System.out.println("Part1: " + part1);
		System.out.println("Part2: " + part2);
	}

	// Data loaded from file and converted to list of Passports
	public List<SurveyGroup> data_;

	public Day06() {
		Load();
	}

	/*
--- Day 6: Custom Customs ---

As your flight approaches the regional airport where you'll switch to a much larger plane, customs declaration forms are
distributed to the passengers.

The form asks a series of 26 yes-or-no questions marked a through z. All you need to do is identify the questions for
which anyone in your group answers "yes". Since your group is just you, this doesn't take very long.

However, the person sitting next to you seems to be experiencing a language barrier and asks if you can help. For each
of the people in their group, you write down the questions for which they answer "yes", one per line. For example:

abcx
abcy
abcz

In this group, there are 6 questions to which anyone answered "yes": a, b, c, x, y, and z. (Duplicate answers to the
same question don't count extra; each question counts at most once.)

Another group asks for your help, then another, and eventually you've collected answers from every group on the plane
(your puzzle input). Each group's answers are separated by a blank line, and within each group, each person's answers
are on a single line. For example:

abc

a
b
c

ab
ac

a
a
a
a

b

This list represents answers from five groups:

    The first group contains one person who answered "yes" to 3 questions: a, b, and c.
    The second group contains three people; combined, they answered "yes" to 3 questions: a, b, and c.
    The third group contains two people; combined, they answered "yes" to 3 questions: a, b, and c.
    The fourth group contains four people; combined, they answered "yes" to only 1 question, a.
    The last group contains one person who answered "yes" to only 1 question, b.

In this example, the sum of these counts is 3 + 3 + 3 + 1 + 1 = 11.
*/
	private void Load() {
		ClassLoader classLoader = getClass().getClassLoader();
		BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(classLoader.getResourceAsStream(Path))));
		data_ = reader.lines().map(SurveyGroup::MakeSurveyGroup).distinct().collect(Collectors.toList());
	}


	/*
	This list represents answers from five groups:

		The first group contains one person who answered "yes" to 3 questions: a, b, and c.
		The second group contains three people; combined, they answered "yes" to 3 questions: a, b, and c.
		The third group contains two people; combined, they answered "yes" to 3 questions: a, b, and c.
		The fourth group contains four people; combined, they answered "yes" to only 1 question, a.
		The last group contains one person who answered "yes" to only 1 question, b.

	In this example, the sum of these counts is 3 + 3 + 3 + 1 + 1 = 11.
	 */
	public Integer GetAnyYesCount(SurveyGroup group) {
		Set<Character> yes = new HashSet<>();

		for (String answers : group.surveyAnswers_) {
			for (Character ch : answers.toCharArray()) {
				yes.add(ch);
			}
		}

		return yes.size();
	}

	/*
		--- Part Two ---

	As you finish the last group's customs declaration, you notice that you misread one word in the instructions:

	You don't need to identify the questions to which anyone answered "yes"; you need to identify the questions to which everyone answered "yes"!

	Using the same example as above:

	abc

	a
	b
	c

	ab
	ac

	a
	a
	a
	a

	b

	This list represents answers from five groups:

		In the first group, everyone (all 1 person) answered "yes" to 3 questions: a, b, and c.
		In the second group, there is no question to which everyone answered "yes".
		In the third group, everyone answered yes to only 1 question, a. Since some people did not answer "yes" to b or c, they don't count.
		In the fourth group, everyone answered yes to only 1 question, a.
		In the fifth group, everyone (all 1 person) answered "yes" to 1 question, b.

	In this example, the sum of these counts is 3 + 0 + 1 + 1 + 1 = 6.
	 */
	public Integer GetAllYesCount(SurveyGroup group) {
		Map<Character, Integer> yes = new HashMap<>();

		for (String line : group.surveyAnswers_) {
			for (Character ch : line.toCharArray()) {
				// Add or update value in hash
				yes.put(ch, yes.getOrDefault(ch, 0) + 1);
			}
		}

		// Count values in the hash that are the group size
		return Math.toIntExact(yes.values().stream().filter(s -> s == group.surveyAnswers_.size()).count());
	}

	/*
	For each group, count the number of questions to which anyone answered "yes". What is the sum of those counts?
	*/
	public Integer Part1() {
		var result = data_.stream().map(this::GetAnyYesCount).reduce(Integer::sum);
		assert(result.isPresent());
		return result.get();
	}

	/*
	For each group, count the number of questions to which everyone answered "yes". What is the sum of those counts?
	*/
	private Integer Part2() {
		var result = data_.stream().map(this::GetAllYesCount).reduce(Integer::sum);
		assert(result.isPresent());
		return result.get();
	}

}

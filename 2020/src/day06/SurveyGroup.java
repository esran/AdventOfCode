package day06;

import day04.Passport;

import java.util.ArrayList;
import java.util.List;

public class SurveyGroup {
	List<String> surveyAnswers_ = new ArrayList<>();

	// Whilst we're trying to make passports we might need to repeatedly
	// call so keep track of the current one being constructed.
	static SurveyGroup current;

	static {
		current = new SurveyGroup();
	}

	// Make a passport from a line of the input. Blank lines indicate the
	// end of a passport. Each line contains one or more key:value pairs
	public static SurveyGroup MakeSurveyGroup(String line) {
		SurveyGroup result = current;

		if (line.length() == 0) {
			// on a blank line we return what we've got and start a new passport
			current = new SurveyGroup();
		} else {
			// Add to the survey answers for this group
			current.surveyAnswers_.add(line);
		}

		return result;
	}
}

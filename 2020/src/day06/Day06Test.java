package day06;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class Day06Test {

	@Test
	void part1() {
		Day06 test = new Day06();

		SurveyGroup d1 = new SurveyGroup();
		d1.surveyAnswers_.add("abc");

		SurveyGroup d2 = new SurveyGroup();
		d2.surveyAnswers_.add("a");
		d2.surveyAnswers_.add("b");
		d2.surveyAnswers_.add("c");

		SurveyGroup d3 = new SurveyGroup();
		d3.surveyAnswers_.add("ab");
		d3.surveyAnswers_.add("ac");

		SurveyGroup d4 = new SurveyGroup();
		d4.surveyAnswers_.add("a");
		d4.surveyAnswers_.add("a");
		d4.surveyAnswers_.add("a");
		d4.surveyAnswers_.add("a");

		SurveyGroup d5 = new SurveyGroup();
		d5.surveyAnswers_.add("b");

		test.data_.add(d1);
		test.data_.add(d2);
		test.data_.add(d3);
		test.data_.add(d4);
		test.data_.add(d5);

		assertEquals(11, test.Part1());
	}
}
package jp.co.axiz.schoolManager.form;

import javax.validation.constraints.NotNull;

public class ScoreInsertForm {

	private Integer studentId;
	private Integer testId;
	private String studentName;
	private String testName;
	@NotNull
	//@Pattern(regexp = "[0-9]*")
	private Integer score;
	private Integer year;
	private Integer month;
	private Integer day;
	private String dayFormat;

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public String getDayFormat() {
		return dayFormat;
	}

	public void setDayFormat(String dayFormat) {
		this.dayFormat = dayFormat;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getTestId() {
		return testId;
	}

	public void setTestId(Integer testId) {
		this.testId = testId;
	}
}

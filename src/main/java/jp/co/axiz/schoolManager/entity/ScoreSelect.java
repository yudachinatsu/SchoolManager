package jp.co.axiz.schoolManager.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScoreSelect {
	//フィード
	private Integer id;
	private String studentName;
	private String testName;
	private Integer score;
	private Double average;
	private Integer rank;
	private String testDay;

	//コンストラクタ
	public ScoreSelect() {

	}

	public ScoreSelect(String studentName, String testName, Integer score, String testDay) {
		setStudentName(studentName);
		setTestName(testName);
		setScore(score);
		setTestDay(testDay);
	}

	public ScoreSelect(Integer id, String studentName, String testName, Integer score, Double average, Integer rank,
			String testDay) {
		setId(id);
		setStudentName(studentName);
		setTestName(testName);
		setScore(score);
		setTestDay(testDay);
		setAverage(average);
		setRank(rank);
	}

	public ScoreSelect(Integer id, String studentName, String testName, Integer score, Double average, Integer rank, Date testDay) {
		setId(id);
		setStudentName(studentName);
		setTestName(testName);
		setScore(score);
		setTestDay(testDay);
		setAverage(average);
		setRank(score);
	}

	//getter,setter
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

	public String getTestDay() {
		return testDay;
	}

	public void setTestDay(String testDay) {
		this.testDay = testDay;
	}

	public void setTestDay(Date testDay) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.testDay = dateFormat.format(testDay);
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Double getAverage() {
		return average;
	}

	public void setAverage(Double average) {
		this.average = average;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}

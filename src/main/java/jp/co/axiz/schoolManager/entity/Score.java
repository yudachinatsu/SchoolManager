package jp.co.axiz.schoolManager.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Score {

	//フィード
	private Integer id;
	private Integer studentId;
	private Integer testId;
	private Integer score;
	private String testDay;


	//コンストラクタ
	public Score() {

	}

	public Score(Integer id, Integer studentId, Integer testId, Integer score, String testDay) {
		setId(id);
		setStudentId(studentId);
		setTestId(testId);
		setScore(score);
		setTestDay(testDay);
	}

	public Score(Integer id, Integer studentId, Integer testId, Integer score, Date testDay) {
		setId(id);
		setStudentId(studentId);
		setTestId(testId);
		setScore(score);
		setTestDay(testDay);
	}

	//getter,setter

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

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}

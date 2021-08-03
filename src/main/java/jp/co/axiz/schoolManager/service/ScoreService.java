package jp.co.axiz.schoolManager.service;

import java.util.List;

import jp.co.axiz.schoolManager.entity.Score;

public interface ScoreService {
	public List<Score> selectScore(Integer studentId, Integer testId, String dayFormat);

	public void insertScore(Integer studentId, Integer testId, Integer score, String dayFormat);

	public void deleteScore(Integer scoreId);

	public String changeDayFormat(Integer year, Integer month, Integer day);

	public void updateScore(Integer studentId, Integer testId, Integer score, String dayFormat, Integer beforeId);

	boolean updateCheck(Integer scoreId);

	boolean Check(Integer testId, Integer studentId, String dateFormat);

	public Score search(Integer studentId, Integer testId, String dayFormat);

	boolean Check(Integer testId, Integer studentId, String dateFormat, Integer score);
}

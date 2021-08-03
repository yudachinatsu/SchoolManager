package jp.co.axiz.schoolManager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.axiz.schoolManager.dao.ScoreDao;
import jp.co.axiz.schoolManager.entity.Score;
import jp.co.axiz.schoolManager.service.ScoreService;

@Transactional
@Service
public class ScoreServiceImpl implements ScoreService {

	@Autowired
	ScoreDao dao;

	@Override
	public List<Score> selectScore(Integer studentId, Integer testId, String dayFormat) {
		// TODO 自動生成されたメソッド・スタブ
		return dao.selectScore(studentId, testId, dayFormat);
	}

	@Override
	public void insertScore(Integer studentId, Integer testId, Integer score, String dayFormat) {
		// TODO 自動生成されたメソッド・スタブ
		dao.insertScore(studentId, testId, score, dayFormat);
	}

	@Override
	public boolean Check(Integer testId, Integer studentId, String dateFormat) {
		// TODO 自動生成されたメソッド・スタブ
		return dao.Check(testId, studentId, dateFormat);
	}

	@Override
	public void deleteScore(Integer scoreId) {
		// TODO 自動生成されたメソッド・スタブ
		dao.deleteScore(scoreId);
	}

	@Override
	public String changeDayFormat(Integer year, Integer month, Integer day) {
		// TODO 自動生成されたメソッド・スタブ
		return dao.changeDayFormat(year, month, day);
	}

	@Override
	public void updateScore(Integer studentId, Integer testId, Integer score, String dayFormat, Integer beforeId) {
		// TODO 自動生成されたメソッド・スタブ
		dao.updateScore(studentId, testId, score, dayFormat, beforeId);
	}

	@Override
	public boolean updateCheck(Integer scoreId) {
		// TODO 自動生成されたメソッド・スタブ
		return dao.updateCheck(scoreId);
	}

	@Override
	public Score search(Integer studentId, Integer testId, String dayFormat) {
		// TODO 自動生成されたメソッド・スタブ
		return dao.search(studentId, testId, dayFormat);
	}

	@Override
	public boolean Check(Integer testId, Integer studentId, String dateFormat, Integer score) {
		// TODO 自動生成されたメソッド・スタブ
		return dao.Check(testId, studentId, dateFormat, score);
	}
}

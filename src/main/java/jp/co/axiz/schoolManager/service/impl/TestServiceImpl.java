package jp.co.axiz.schoolManager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.axiz.schoolManager.dao.TestDao;
import jp.co.axiz.schoolManager.entity.Test;
import jp.co.axiz.schoolManager.service.TestService;

@Transactional
@Service
public class TestServiceImpl implements TestService {
	@Autowired
	TestDao dao;

	@Override
	public List<Test> selectTests(Integer testId, String testName) {
		// TODO 自動生成されたメソッド・スタブ
		return dao.selectTests(testId, testName);
	}

	@Override
	public void insertTest(Integer testId, String testName) {
		// TODO 自動生成されたメソッド・スタブ
		dao.insertTest(testId, testName);
	}

	@Override
	public boolean updateCheck(Integer testId) {
		// TODO 自動生成されたメソッド・スタブ
		return dao.updateCheck(testId);
	}

	@Override
	public void updateTest(Integer testId, String testName, Integer beforeId) {
		// TODO 自動生成されたメソッド・スタブ
		dao.updateTest(testId, testName, beforeId);
	}

	@Override
	public void deleteTest(Integer testId) {
		// TODO 自動生成されたメソッド・スタブ
		dao.deleteTest(testId);
	}

	public Test getTestName(Integer testId) {
		return dao.getTestName(testId);
	}

	public boolean getTestNameInTrue(Integer testId) {
		return dao.getTestNameInTrue(testId);
	}

	public Test getTestId(String name) {
		return dao.getTestId(name);
	}

	@Override
	public void updateTestForCheckIsFalse(Integer testId, String testName, Integer beforeId) {
		// TODO 自動生成されたメソッド・スタブ
		dao.updateTestForCheckIsFalse(testId, testName, beforeId);
	}

	@Override
	public List<Test> getSessionTest() {
		// TODO 自動生成されたメソッド・スタブ
		return dao.getSessionTest();
	}

	@Override
	public boolean Check(Integer testId) {
		// TODO 自動生成されたメソッド・スタブ
		return dao.Check(testId);
	}
}

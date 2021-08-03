package jp.co.axiz.schoolManager.service;

import java.util.List;

import jp.co.axiz.schoolManager.entity.Test;

public interface TestService {
	public List<Test> selectTests(Integer testId, String testName);

	public void insertTest(Integer testId, String testName);

	public boolean updateCheck(Integer testId);

	public void updateTest(Integer testId, String testName, Integer beforeId);

	public void deleteTest(Integer testId);

	public Test getTestName(Integer testId);

	public Test getTestId(String name);

	public boolean getTestNameInTrue(Integer testId);

	void updateTestForCheckIsFalse(Integer testId, String testName, Integer beforeId);

	public List<Test> getSessionTest();

	public boolean Check(Integer testId);

}

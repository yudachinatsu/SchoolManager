package jp.co.axiz.schoolManager.dao;

import java.util.List;

import jp.co.axiz.schoolManager.entity.Test;

/*
 * テスト系DAO
 */
public interface TestDao {
	//担当者 : 小林
	public List<Test> selectTests(Integer testId, String testName);

	public void insertTest(Integer testId, String testName);

	public boolean updateCheck(Integer testId);

	public void updateTest(Integer testId, String testName, Integer beforeId);

	public void deleteTest(Integer testId);

	public Test getTestName(Integer testId);

	public boolean getTestNameInTrue(Integer testId);

	public Test getTestId(String name);

	void updateTestForCheckIsFalse(Integer testId, String testName, Integer beforeId);

	public List<Test> getSessionTest();

	public boolean Check(Integer testId);

}

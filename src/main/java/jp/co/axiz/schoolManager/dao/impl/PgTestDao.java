package jp.co.axiz.schoolManager.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.axiz.schoolManager.dao.TestDao;
import jp.co.axiz.schoolManager.entity.Test;

@Repository
public class PgTestDao implements TestDao {
	/*session保存用*/
	static final String TEST_SESSION = "SELECT * FROM test WHERE delete = false ORDER BY id";

	/* testのIDからテスト名取得 */
	static final String SELECT_NAME = "SELECT * FROM test WHERE delete = false AND id = :testId;";
	/* testの名前からID取得 */
	static final String SELECT_ID = "SELECT * FROM test WHERE delete = false AND name = :name;";
	/* testのIDからテスト名取得(テスト登録用 */
	static final String SELECT_NAME_TRUE_IN = "select test.id, test.name, test.delete from test join score on score.test_id = test.id "
			+ "where score.test_id = :testId AND score.delete = false;";

	/*＜テスト情報検索＞*/
	static final String SELECT_TEST_TESTID = "SELECT * FROM test WHERE delete = false AND id = :testId  ORDER BY  id;";
	static final String SELECT_TEST_NAME = "SELECT * FROM test WHERE delete = false AND name LIKE :name ORDER BY id;";//name部分一致
	static final String SELECT_TEST_TESTID_NAME = "SELECT * FROM test WHERE delete = false AND id = :testId AND name LIKE :name ORDER BY id;";//name部分一致
	static final String SELECT_TEST_ALL = "SELECT * FROM test WHERE delete = false AND id not in (0) ORDER BY id";

	/*<テスト情報登録>*/
	static final String INSERT_TEST = "INSERT INTO test VALUES (:testId,:testName,false);";

	/*テスト情報変更*/
	static final String UPDATE_TEST = "UPDATE test SET id = :testId,name = :testName WHERE id = :beforeId;";

	static final String UPDATE_TEST_FOR_CHECK_IS_FALSE = "insert into test values"
			+ "(:afterId,:afterName,"
			+ "(select delete from test where id = :beforeId));"
			+ "update score set test_id = :afterId where test_id = :beforeId;"
			+ "delete from test where id = :beforeId";

	static final String UPDATE_CHECK = "select test.id,test.name,test.delete from test join score on test.id = score.test_id"
			+ " where test.id = :testId AND score.delete = false AND test.delete = false group by test.id;";
	static final String CHECK = "select * from test where id = :testId";


	/*テスト情報削除*/
	static final String DELETE_TEST = "UPDATE test SET delete = true WHERE id = :testId;";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Test> getSessionTest(){
		String sql = TEST_SESSION;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		List<Test> list = jdbcTemplate.query(sql, msps, new BeanPropertyRowMapper<Test>(Test.class));
		return list.isEmpty() ? null : list;
	}

	//テストを検索する用
	public List<Test> selectTests(Integer testId, String testName) {
		String sql;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		if (testId == null) {
			if (testName == null || testName.isEmpty()) {
				//何も入っていない場合
				sql = SELECT_TEST_ALL;
			} else {
				//testNameのみの場合
				sql = SELECT_TEST_NAME;
				msps.addValue("name", "%" + testName + "%");
			}
		} else if (testName == null || testName.isEmpty()) {
			//testIdのみの場合
			sql = SELECT_TEST_TESTID;
			msps.addValue("testId", testId);
		} else {
			//両方入っている場合
			sql = SELECT_TEST_TESTID_NAME;
			msps.addValue("name", "%" + testName + "%");
			msps.addValue("testId", testId);
		}
		List<Test> list = jdbcTemplate.query(sql, msps, new BeanPropertyRowMapper<Test>(Test.class));
		return list.isEmpty() ? null : list;
	}

	//test追加用
	public void insertTest(Integer testId, String testName) {
		String sqlString = INSERT_TEST;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("testId", testId);
		msps.addValue("testName", testName);
		jdbcTemplate.update(sqlString, msps);
	}

	//testIdを変える際、削除する際、scoreのtestIdに外部参照制約がついており、変更できない可能性があるので、チェックする
	//testIdを変える際に使用
	//true→更新可能 false→更新不可
	public boolean updateCheck(Integer testId) {
		String sql = UPDATE_CHECK;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("testId", testId);
		List<Test> list = jdbcTemplate.query(sql, msps, new BeanPropertyRowMapper<Test>(Test.class));
		return list.isEmpty() ? true : false;
	}

	public boolean Check(Integer testId) {
		String sql = CHECK;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("testId", testId);
		List<Test> list = jdbcTemplate.query(sql, msps, new BeanPropertyRowMapper<Test>(Test.class));
		return list.isEmpty() ? true : false;
	}

	//test更新用
	public void updateTest(Integer testId, String testName, Integer beforeId) {
		String sql = UPDATE_TEST;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("testId", testId);
		msps.addValue("testName", testName);
		msps.addValue("beforeId", beforeId);
		jdbcTemplate.update(sql, msps);
	}

	@Override
	public void updateTestForCheckIsFalse(Integer testId, String testName, Integer beforeId) {
		String sql = UPDATE_TEST_FOR_CHECK_IS_FALSE;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("afterId", testId);
		msps.addValue("afterName", testName);
		msps.addValue("beforeId", beforeId);
		jdbcTemplate.update(sql, msps);
	}

	//test削除用
	public void deleteTest(Integer testId) {
		String sql = DELETE_TEST;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("testId", testId);
		jdbcTemplate.update(sql, msps);
	}

	//testIdからTest取得用
	public Test getTestName(Integer testId) {
		String sql = SELECT_NAME;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("testId", testId);
		List<Test> list = jdbcTemplate.query(sql, msps, new BeanPropertyRowMapper<Test>(Test.class));
		return list.isEmpty() ? null : list.get(0);
	}

	//testIdからTest取得用(登録時の確認用
	//true→更新可能 false→更新不可
	public boolean getTestNameInTrue(Integer testId) {
		String sql = SELECT_NAME_TRUE_IN;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("testId", testId);
		List<Test> list = jdbcTemplate.query(sql, msps, new BeanPropertyRowMapper<Test>(Test.class));
		return list.isEmpty() ? true : false;
	}

	//testNameからTest取得用
	@Override
	public Test getTestId(String name) {
		String sql = SELECT_ID;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("name", name);
		List<Test> list = jdbcTemplate.query(sql, msps, new BeanPropertyRowMapper<Test>(Test.class));
		return list.isEmpty() ? null : list.get(0);
	}

}

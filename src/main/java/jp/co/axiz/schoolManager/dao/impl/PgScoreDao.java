
package jp.co.axiz.schoolManager.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.axiz.schoolManager.dao.ScoreDao;
import jp.co.axiz.schoolManager.entity.Score;

@Repository
public class PgScoreDao implements ScoreDao {

	/*登録確認*/
	private static final String INSERT_SCORE_CHECK = "SELECT * FROM score WHERE :studentId = student_id AND :testId = test_id AND test_day = :testDay AND delete = false";
	//
	private static final String UPDATE_CHECK = "SELECT * FROM score WHERE :studentId = student_id AND :testId = test_id AND test_day = :testDay AND score =:score";

	/*登録*/
	private static final String INSERT_SCORE = "insert into score(student_id,test_id,score,test_day,delete) values "
			+ "(:student_id,:test_id,:score,:dateFormat,false)";

	/*<更新>*/
	private static final String UPDATE_SCORE_ID = "SELECT * FROM score WHERE id = :seisekiId;";

	private static final String UPDETE_SCORE_SEISEKI = "UPDATE score SET student_id = :studentId, test_id = :testId, score = :score ,test_day = :testDay WHERE id = :seisekiId";

	/*<削除>*/
	private static final String DELETE_SCORE = "UPDATE score SET delete = true WHERE id = :scoreId;";

	/*検索*/
	private static final String SELECT_SCORE = "SELECT score.id, student.name, test.name, score.score,score.test_day,"
			+ "score.test_id,score.student_id  FROM score join student on score.student_id = student.id join test on score.test_id = test.id "
			+ "where test.delete = false  AND student.delete = false AND score.delete = false ";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Score> selectScore(Integer studentId, Integer testId, String dayFormat) {
		String sql = SELECT_SCORE;

		MapSqlParameterSource msps = new MapSqlParameterSource();

		if (studentId != null && studentId > 0) {
			sql += " AND student_id = :studentId";
			msps.addValue("studentId", studentId);

		}

		if (testId != null && testId > 0) {
			sql += " AND test_id = :testId";
			msps.addValue("testId", testId);
		}

		if (!(dayFormat == null || dayFormat.isEmpty())) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date;
			try {
				date = format.parse(dayFormat);
			} catch (ParseException e) {
				// TODO 自動生成された catch ブロック
				date = new Date();
			}
			sql += " AND test_day = :testDay";
			msps.addValue("testDay", date);
		}
		sql += " order by score.id";
		List<Score> resultList = jdbcTemplate.query(sql, msps, new BeanPropertyRowMapper<Score>(Score.class));

		return resultList.isEmpty() ? null : resultList;
	}

	@Override
	public void updateScore(Integer studentId, Integer testId, Integer score, String dayFormat, Integer beforeId) {

		String sql = UPDETE_SCORE_SEISEKI;

		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("studentId", studentId);
		msps.addValue("testId", testId);
		msps.addValue("score", score);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = format.parse(dayFormat);
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			date = new Date();
		}
		msps.addValue("testDay", date);
		msps.addValue("seisekiId", beforeId);
		jdbcTemplate.update(sql, msps);
	}

	@Override
	public String changeDayFormat(Integer year, Integer month, Integer day) {
		boolean error = false;
		boolean check = false;
		if ((year == null || year == 0) || (month == null || month == 0) ||
				(day == null || day == 0)) {
			//yearは選択されなかった→nullをセット
			error = true;
		} else {
			if ((month == 4 || month == 6 || month == 9 || month == 11)) {
				if (day == 31) {
					//4,6,9,11月なのに31日を指定しているのでエラー
					error = true;
				}
			} else if (month == 2) {
				if (year % 4 == 0) {
					if ((year % 100) == 0) {
						if ((year % 400) == 0) {
							check = true; // うるう年
						}
					} else {
						check = true; // うるう年
					}
				}
				if (check) {
					if (day == 30 || day == 31) {
						//2月のうるう年に30，31日を指定しているエラー
						error = true;
					}
				} else {
					if (day == 29 || day == 30 || day == 31) {
						//2月のうるう年以外に、29,30,31日を指定しているエラー

						error = true;
					}
				}
			}

		}

		if (error) {
			//挿入失敗
			return null;
		} else {
			return year + "-" + month + "-" + day;
		}
	}

	//登録可能かを確認する
	@Override
	public boolean Check(Integer testId, Integer studentId, String dateFormat) {
		String sql = INSERT_SCORE_CHECK;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("studentId", studentId);
		msps.addValue("testId", testId);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = format.parse(dateFormat);
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			date = new Date();
		}
		msps.addValue("testDay", date);
		List<Score> list = jdbcTemplate.query(sql, msps, new BeanPropertyRowMapper<Score>(Score.class));
		return list.isEmpty() ? true : false;
	}

	//登録可能かを確認する
	@Override
	public boolean Check(Integer testId, Integer studentId, String dateFormat, Integer score) {
		String sql = UPDATE_CHECK;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("studentId", studentId);
		msps.addValue("testId", testId);
		msps.addValue("score", score);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = format.parse(dateFormat);
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			date = new Date();
		}
		msps.addValue("testDay", date);
		List<Score> list = jdbcTemplate.query(sql, msps, new BeanPropertyRowMapper<Score>(Score.class));
		return list.isEmpty() ? true : false;
	}

	@Override
	public boolean updateCheck(Integer scoreId) {
		String sql = UPDATE_SCORE_ID;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("seisekiId", scoreId);
		List<Score> list = jdbcTemplate.query(sql, msps, new BeanPropertyRowMapper<Score>(Score.class));
		return list.isEmpty() ? true : false;
	}

	@Override
	public void deleteScore(Integer scoreId) {
		String sql = DELETE_SCORE;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("scoreId", scoreId);
		jdbcTemplate.update(sql, msps);
	}

	@Override
	public void insertScore(Integer studentId, Integer testId, Integer score, String dayFormat) {
		// TODO 自動生成されたメソッド・スタブ
		String sql = INSERT_SCORE;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("student_id", studentId);
		msps.addValue("test_id", testId);
		msps.addValue("score", score);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = dateFormat.parse(dayFormat);
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			date = new Date();
		}
		msps.addValue("dateFormat", date);
		jdbcTemplate.update(sql, msps);
	}

	//
	@Override
	public Score search(Integer studentId, Integer testId, String dayFormat) {
		// TODO 自動生成されたメソッド・スタブ
		String sql = SELECT_SCORE;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("studentId", studentId);
		msps.addValue("testId", testId);
		msps.addValue("testDay", dayFormat);
		List<Score> list = jdbcTemplate.query(sql, msps, new BeanPropertyRowMapper<Score>(Score.class));
		return list.isEmpty() ? null : list.get(0);
	}

}

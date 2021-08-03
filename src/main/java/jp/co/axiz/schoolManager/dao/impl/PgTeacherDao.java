package jp.co.axiz.schoolManager.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.axiz.schoolManager.dao.TeacherDao;
import jp.co.axiz.schoolManager.entity.Teacher;

@Repository
public class PgTeacherDao implements TeacherDao {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	private static final String SELECT_BY_LOGIN_ID_AND_PASS = "SELECT id, name, login_id, password FROM teacher WHERE login_id = :loginId AND password = :password";

	@Override
	public List<Teacher> find(Teacher teacher) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public Teacher findByLoginIdAndPassword(String loginId, String password) {
		String sql = SELECT_BY_LOGIN_ID_AND_PASS;
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("loginId", loginId);
		param.addValue("password", password);

		List<Teacher> resultList = jdbcTemplate.query(sql, param,
				new BeanPropertyRowMapper<Teacher>(Teacher.class));

		return resultList.isEmpty() ? null : resultList.get(0);
	}

}

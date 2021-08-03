package jp.co.axiz.schoolManager.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import jp.co.axiz.schoolManager.dao.StudentDao;
import jp.co.axiz.schoolManager.entity.Student;

@Repository
public class PgStudentDao implements StudentDao {
	//セッション用
	static final String SESSION_STUDENT = "SELECT * FROM student WHERE delete = false ORDER BY id";

	/*生徒のIDから生徒名の取得*/
	static final String SELECT_NAME = "SELECT * FROM student WHERE delete = false AND id = :studentId";

	//生徒の名前からIDの取得
	static final String SELECT_ID = "SELECT * FROM student WHERE delete = false AND name = :name";

	/*＜生徒情報検索＞*/
	//生徒IDのみ入力された場合に使用するSQL
	static final String SELECT_STUDENT_STUDENTID = "SELECT * FROM student WHERE delete = false AND id = :studentId ORDER BY id";

	//生徒名のみ入力された場合に使用するSQL
	static final String SELECT_STUDENT_NAME = "SELECT * FROM student WHERE delete= false AND name LIKE :name ORDER BY id";

	//生徒ID、生徒名どちらも入力された場合に使用するSQL
	static final String SELECT_STUDENT_STUDENTID_NAME = "SELECT * FROM student where delete = false AND id = :studentId AND name LIKE :name ORDER BY id";

	//全件検索
	static final String SELECT_STUDENT_ALL = "SELECT * FROM student WHERE delete = false AND id not in (0) ORDER BY id";

	/*＜テスト情報登録＞*/
	static final String INSERT_STUDENT = "INSERT INTO student VALUES (:studentId,:studentName,false);";

	/*＜テスト情報変更＞*/

	//素直に更新可能な場合
	static final String UPDATE_STUDENT = "UPDATE student SET id = :studentId,name = :studentName WHERE id = :beforeId;";

	//使ってるやつを更新する場合
	static final String UPDATE_STUDENT_FOR_CHECK_IS_FALSE = "insert into student values(:afterId, "
			+ ":afterName, "
			+ "(select delete from student where id = :beforeId)); "
			+ "update score set student_id = :afterId where student_id = :beforeId; "
			+ "delete from student where id= :beforeId";

	//外部参照されているかの判定処理
	//されいる場合false,されていない場合trueを返す
	static final String UPDATE_CHECK = "select student.id,student.name,student.delete from student join score on student.id = score.student_id"
			+ " where student.id = :studentId AND score.delete = false group by student.id;";
	//static final String UPDATE_CHECK = "select * from student where id = :studentId";

	//idが該当した場合に生徒情報を返す
	static final String CHECK ="select * from student where id = :studentId";

	/*＜テスト情報削除＞*/
	static final String DELETE_STUDENT = "UPDATE student SET delete = true WHERE id = :studentId;";

	//生徒IDが一致したとき、生徒情報の取得
	private static final String SELECT_BY_LOGIN_ID = "SELECT id, name FROM student WHERE id = :id";

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	//セッション取得用
	@Override
	public List<Student> getSessionStudent() {
		String sql = SESSION_STUDENT;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		List<Student> list = jdbcTemplate.query(sql, msps, new BeanPropertyRowMapper<Student>(Student.class));
		return list.isEmpty() ? null : list;
	}

	//テストを検索する用
	@Override
	public List<Student> selectStudent(Integer studentId, String studentName) {
		String sql;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		if (studentId == null) {
			if (studentName == null || studentName.isEmpty()) {
				//何も入っていない場合
				sql = SELECT_STUDENT_ALL;
			} else {
				//studentNameのみの場合
				sql = SELECT_STUDENT_NAME;
				msps.addValue("name", "%" + studentName + "%");
			}
		} else if (studentName == null || studentName.isEmpty()) {
			//studentIdのみの場合
			sql = SELECT_STUDENT_STUDENTID;
			msps.addValue("studentId", studentId);
		} else {
			//両方入っている場合
			sql = SELECT_STUDENT_STUDENTID_NAME;
			msps.addValue("name", "%" + studentName + "%");
			msps.addValue("studentId", studentId);
		}
		List<Student> list = jdbcTemplate.query(sql, msps, new BeanPropertyRowMapper<Student>(Student.class));
		return list.isEmpty() ? null : list;
	}

	//student追加用
	@Override
	public void insertStudent(Integer studentId, String studentName) {
		String sqlString = INSERT_STUDENT;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("studentId", studentId);
		msps.addValue("studentName", studentName);
		jdbcTemplate.update(sqlString, msps);
	}

	//studentIdを変える際、削除する際、scoreのstudentIdに外部参照制約がついており、変更できない可能性があるので、チェックする
	//studentIdを変える際に使用
	//true→更新可能 false→更新不可
	@Override
	public boolean updateCheck(Integer studentId) {
		String sql = UPDATE_CHECK;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("studentId", studentId);
		List<Student> list = jdbcTemplate.query(sql, msps, new BeanPropertyRowMapper<Student>(Student.class));
		return list.isEmpty() ? true : false;
	}
	///
	public boolean Check(Integer studentId) {
		String sql = CHECK;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("studentId", studentId);
		List<Student> list = jdbcTemplate.query(sql, msps, new BeanPropertyRowMapper<Student>(Student.class));
		return list.isEmpty() ? true : false;
	}
	///

	//student更新用
	@Override
	public void updateStudent(Integer studentId, String studentName, Integer beforeId) {
		String sql = UPDATE_STUDENT;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("studentId", studentId);
		msps.addValue("studentName", studentName);
		msps.addValue("beforeId", beforeId);
		jdbcTemplate.update(sql, msps);
	}
	//外部参照されているstudetidを更新する際の処理
	@Override
	public void updateStudentForCheckIsFalse(Integer studentId, String studentName, Integer beforeId) {
		String sql = UPDATE_STUDENT_FOR_CHECK_IS_FALSE;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("afterId", studentId);
		msps.addValue("afterName", studentName);
		msps.addValue("beforeId", beforeId);
		jdbcTemplate.update(sql, msps);
	}

	//student削除用
	@Override
	public void deleteStudent(Integer studentId) {
		String sql = DELETE_STUDENT;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("studentId", studentId);
		jdbcTemplate.update(sql, msps);
	}

	//studentIdからStudent取得用
	@Override
	public Student getStudentName(Integer studentId) {
		String sql = SELECT_NAME;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("studentId", studentId);
		List<Student> list = jdbcTemplate.query(sql, msps, new BeanPropertyRowMapper<Student>(Student.class));
		return list.isEmpty() ? null : list.get(0);
	}

	//生徒IDが合致した際に生徒情報を返す
	@Override
	public Student findByLoginId(Integer id) {
		MapSqlParameterSource param = new MapSqlParameterSource();
		param.addValue("id", id);

		List<Student> resultList = jdbcTemplate.query(SELECT_BY_LOGIN_ID, param,
				new BeanPropertyRowMapper<Student>(Student.class));

		return resultList.isEmpty() ? null : resultList.get(0);
	}

	//nameからStudent取得用
	@Override
	public Student getStudentId(String name) {
		String sql = SELECT_ID;
		MapSqlParameterSource msps = new MapSqlParameterSource();
		msps.addValue("name", name);
		List<Student> list = jdbcTemplate.query(sql, msps, new BeanPropertyRowMapper<Student>(Student.class));
		return list.isEmpty() ? null : list.get(0);
	}

}

package jp.co.axiz.schoolManager.service;

import java.util.List;

import jp.co.axiz.schoolManager.entity.Student;

//変更者：伊藤
public interface StudentService {
	//検索
	public List<Student> selectStudent(Integer studentId, String studentName);

	//削除
	public void deleteStudent(Integer studentId);

	//更新
	public void updateStudent(Integer studentId, String studentName, Integer beforeId);

	//登録
	public void insertStudent(Integer studentId, String studentName);

	//更新、削除時の生徒が存在するかのチェック
	public boolean updateCheck(Integer studentId);

	public Student getStudentName(Integer studentId);

	public boolean existsUserByLoginId(Integer id);

	public Student findByLoginId(Integer id);

	public Student getStudentId(String name);

	void updateStudentForCheckIsFalse(Integer studentId, String studentName, Integer beforeId);

	public List<Student> getSessionStudent();

	public boolean Check(Integer studentId);
}

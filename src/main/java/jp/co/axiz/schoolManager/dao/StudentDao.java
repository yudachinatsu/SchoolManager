package jp.co.axiz.schoolManager.dao;

import java.util.List;

import jp.co.axiz.schoolManager.entity.Student;

//担当者 : 伊藤
public interface StudentDao {
	//検索
	public List<Student> selectStudent(Integer studentId, String studentName);

	//削除
	public void deleteStudent(Integer studentId);

	//更新
	public void updateStudent(Integer studentId, String studentName, Integer beforeId);

	//登録
	public void insertStudent(Integer studentId, String studentName);

	//更新、削除時更新可能かのチェック
	public boolean updateCheck(Integer studentId);

	//studentIdから生徒情報取得処理
	public Student getStudentName(Integer studentId);

	//生徒IDが合致した際に生徒情報を返す
	public Student findByLoginId(Integer id);

	//nameからStudent取得用
	public Student getStudentId(String name);

	//外部参照されているstudetidを更新する際の処理
	void updateStudentForCheckIsFalse(Integer studentId, String studentName, Integer beforeId);

	//セッション用
	public List<Student> getSessionStudent();

	//idが該当した場合に生徒情報を返す
	public boolean Check(Integer studentId);
}

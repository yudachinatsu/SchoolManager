package jp.co.axiz.schoolManager.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.axiz.schoolManager.dao.StudentDao;
import jp.co.axiz.schoolManager.entity.Student;
import jp.co.axiz.schoolManager.service.StudentService;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentDao studentDao;

	//検索
	@Override
	public List<Student> selectStudent(Integer studentId, String studentName) {
		return studentDao.selectStudent(studentId, studentName);
	}

	//削除
	@Override
	public void deleteStudent(Integer studentId) {
		studentDao.deleteStudent(studentId);
	}

	//更新
	@Override
	public void updateStudent(Integer studentId, String studentName, Integer beforeId) {
		studentDao.updateStudent(studentId, studentName, beforeId);
	}

	//登録
	@Override
	public void insertStudent(Integer studentId, String studentName) {
		studentDao.insertStudent(studentId, studentName);
	}

	//更新、削除時更新可能かのチェック
	@Override
	public boolean updateCheck(Integer studentId) {
		return studentDao.updateCheck(studentId);

	}

	@Override
	public Student getStudentName(Integer studentId) {
		return studentDao.getStudentName(studentId);
	}

	@Override
	public Student findByLoginId(Integer id) {
		return studentDao.findByLoginId(id);
	}

	@Override
	public boolean existsUserByLoginId(Integer id) {
		return findByLoginId(id) != null;

	}

	@Override
	public Student getStudentId(String name) {
		// TODO 自動生成されたメソッド・スタブ
		return studentDao.getStudentId(name);
	}

	@Override
	public void updateStudentForCheckIsFalse(Integer studentId, String studentName, Integer beforeId) {
		// TODO 自動生成されたメソッド・スタブ
		studentDao.updateStudentForCheckIsFalse(studentId, studentName, beforeId);
	}

	public List<Student> getSessionStudent() {
		return studentDao.getSessionStudent();
	}

	@Override
	public boolean Check(Integer studentId) {
		// TODO 自動生成されたメソッド・スタブ
		return studentDao.Check(studentId);
	}

}

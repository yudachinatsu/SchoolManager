package jp.co.axiz.schoolManager.dao;

import java.util.List;

import jp.co.axiz.schoolManager.entity.Teacher;

public interface TeacherDao {

	public List<Teacher> find(Teacher teacher);

	public Teacher findByLoginIdAndPassword(String loginId, String password);

}
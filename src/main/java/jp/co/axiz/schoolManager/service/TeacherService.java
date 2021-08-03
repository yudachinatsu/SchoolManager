package jp.co.axiz.schoolManager.service;

import jp.co.axiz.schoolManager.entity.Teacher;

public interface TeacherService {

	public Teacher authentication(String loginId, String password);

}

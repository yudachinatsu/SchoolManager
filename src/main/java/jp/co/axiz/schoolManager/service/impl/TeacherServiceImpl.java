package jp.co.axiz.schoolManager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.axiz.schoolManager.dao.TeacherDao;
import jp.co.axiz.schoolManager.entity.Teacher;
import jp.co.axiz.schoolManager.service.TeacherService;

@Transactional
@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherDao teacherDao;

	@Override
	public Teacher authentication(String loginId, String password) {
		return teacherDao.findByLoginIdAndPassword(loginId, password);
	}

}

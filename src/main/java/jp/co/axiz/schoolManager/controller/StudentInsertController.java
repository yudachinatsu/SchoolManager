package jp.co.axiz.schoolManager.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.axiz.schoolManager.entity.Student;
import jp.co.axiz.schoolManager.form.StudentInsertForm;
import jp.co.axiz.schoolManager.service.StudentService;
import jp.co.axiz.schoolManager.util.ParamUtil;

/*
 * 生徒登録画面周りコントローラー
 */
@Controller
public class StudentInsertController {

	@Autowired
	MessageSource messageSource;
	@Autowired
	HttpSession session;
	@Autowired
	StudentService studentService;

	@RequestMapping(value = "studentInsert", method = RequestMethod.GET)
	public String studentUpdate(@ModelAttribute("studentInsert") StudentInsertForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "timeout";
		}
		return "studentInsert";
	}

	@RequestMapping(value = "studentInsertConfirm", method = RequestMethod.POST)
	public String studentUpdateConfirm(@Validated @ModelAttribute("studentInsert") StudentInsertForm form,
			BindingResult bindingResult, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		if (bindingResult.hasErrors()) {
			return "studentInsert";
		}

		Student student = new Student(form.getStudentId(), form.getStudentName(), false);

		//IDが重複している場合
		if (studentService.existsUserByLoginId(form.getStudentId())) {
			String errMsg = messageSource.getMessage("id.duplicate.error", null, Locale.getDefault());
			model.addAttribute("errMsg", errMsg);
			return "studentInsert";
		}
		session.setAttribute("student", student);
		return "studentInsertConfirm";
	}

	@RequestMapping(value = "studentInsertResult", params = "insert", method = RequestMethod.POST)
	public String studentUpdateResult(@ModelAttribute("studentInsert") StudentInsertForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		//入力値を登録
		Student student = (Student) session.getAttribute("student");
		studentService.insertStudent(student.getId(), student.getName());

		//セッションの更新
		List<Student> studentInfo = studentService.getSessionStudent();
		session.setAttribute("studentInfo", studentInfo);

		return "studentInsertResult";
	}

	//戻る
	@RequestMapping(value = "studentInsertResult", params = "back", method = RequestMethod.POST)
	public String studentUpdateBack(@ModelAttribute("studentInsert") StudentInsertForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		Student student = (Student) session.getAttribute("student");
		form.setStudentId(student.getId());
		form.setStudentName(student.getName());
		return "studentInsert";
	}
}
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
import jp.co.axiz.schoolManager.form.StudentUpdateForm;
import jp.co.axiz.schoolManager.service.StudentService;
import jp.co.axiz.schoolManager.util.ParamUtil;

/*
 * 生徒更新画面周りコントローラー
 */

@Controller
public class StudentUpdateController {
	@Autowired
	MessageSource messageSource;
	@Autowired
	HttpSession session;
	@Autowired
	StudentService studentService;

	@RequestMapping(value = "/studentUpdate", method = RequestMethod.GET)
	public String studentUpdate(@ModelAttribute("studentUpdate") StudentUpdateForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		form.setStudentId(null);
		form.setStudentName(null);
		return "studentUpdate";

	}

	@RequestMapping(value = "/studentUpdateInput", method = RequestMethod.POST)
	public String studentUpdateInput(@Validated @ModelAttribute("studentUpdate") StudentUpdateForm form,
			BindingResult bindingResult, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		if (bindingResult.hasErrors()) {
			return "studentUpdate";
		}

		Integer studentId = form.getStudentId();

		Student ret = studentService.getStudentName(studentId);

		if (ret == null || studentId == 0) {
			String errMsg = messageSource.getMessage("student.none.error", null, Locale.getDefault());
			model.addAttribute("errMsg", errMsg);
			return "studentUpdate";

		} else {
			String studentName = ret.getName();

			form.setStudentId(ret.getId());
			form.setStudentName(ret.getName());
			session.setAttribute("beforeStudentId", studentId);
			session.setAttribute("beforeStudentName", studentName);
			return "studentUpdateInput";
		}
	}

	//検索から
	@RequestMapping(value = "/studentUpdateInputForSelect", method = RequestMethod.POST)
	public String studentUpdateInputForSelect(@ModelAttribute("studentUpdate") StudentUpdateForm form,
			Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}

		Student editData = (Student) session.getAttribute("editStudent");
		Integer studentId = editData.getId();

		Student ret = studentService.getStudentName(studentId);

		if (ret == null || studentId == 0) {
			String errMsg = messageSource.getMessage("student.none.error", null, Locale.getDefault());
			model.addAttribute("errMsg", errMsg);
			return "studentUpdate";

		} else {
			String studentName = ret.getName();

			form.setStudentId(ret.getId());
			form.setStudentName(ret.getName());
			session.setAttribute("beforeStudentId", studentId);
			session.setAttribute("beforeStudentName", studentName);
			return "studentUpdateInput";
		}
	}

	@RequestMapping(value = "/studentUpdateConfirm", params = "check", method = RequestMethod.POST)
	public String studentUpdateConfirm(@Validated @ModelAttribute("studentUpdate") StudentUpdateForm form,
			BindingResult bindingResult, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		Integer beforeId = (Integer) session.getAttribute("beforeStudentId");
		String beforeName = (String) session.getAttribute("beforeStudentName");
		Integer studentId = form.getStudentId();
		String studentName = form.getStudentName();
		if (bindingResult.hasErrors()) {
			return "studentUpdateInput";
		}

		String errMsg;
		if (!studentService.Check(studentId) && !studentId.equals(beforeId)) {
			//既に使われているので変更不可

			errMsg = messageSource.getMessage("student.update.error.dup", null, Locale.getDefault());
			model.addAttribute("errMsg", errMsg);
			return "studentUpdateInput";
		} else if (beforeId.equals(studentId) && beforeName.equals(studentName)) {
			//何も変更していない
			errMsg = messageSource.getMessage("student.notupdate.error", null, Locale.getDefault());
			model.addAttribute("errMsg", errMsg);

			return "studentUpdateInput";

		} else {
			form.setStudentId(studentId);
			form.setStudentName(studentName);

			return "studentUpdateConfirm";
		}

	}

	@RequestMapping(value = "/studentUpdateResult", params = "update", method = RequestMethod.POST)
	public String studentUpdateResult(@ModelAttribute("studentUpdate") StudentUpdateForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		Integer beforeId = (Integer) session.getAttribute("beforeStudentId");
		Integer studentId = form.getStudentId();
		String studentName = form.getStudentName();

		if (studentService.updateCheck(beforeId) || studentId.equals(beforeId)) {
			studentService.updateStudent(studentId, studentName, beforeId);
		} else {
			studentService.updateStudentForCheckIsFalse(studentId, studentName, beforeId);
		}

		List<Student> studentInfo = studentService.getSessionStudent();
		session.setAttribute("studentInfo", studentInfo);

		return "studentUpdateResult";
	}

	//戻るボタン押下時の画面遷移
	@RequestMapping(value = "/studentUpdateConfirm", params = "back", method = RequestMethod.POST)
	public String studentUpdateComfirmBack(@ModelAttribute("studentUpdate") StudentUpdateForm form, Model model) {

		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		Integer beforeId = (Integer) session.getAttribute("beforeStudentId");
		String beforeName = (String) session.getAttribute("beforeStudentName");

		form.setStudentId(beforeId);
		form.setStudentName(beforeName);
		return "studentUpdate";
	}

	//戻るボタン押下時の画面遷移
	@RequestMapping(value = "/studentUpdateResult", params = "back", method = RequestMethod.POST)
	public String studentUpdateBack(@ModelAttribute("studentUpdate") StudentUpdateForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		return "studentUpdateInput";
	}

}

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
import jp.co.axiz.schoolManager.form.StudentDeleteForm;
import jp.co.axiz.schoolManager.service.StudentService;
import jp.co.axiz.schoolManager.util.ParamUtil;

//担当：伊藤
@Controller
public class StudentDeleteController {

	/*
	 *メッセージ取得用
	 */
	@Autowired
	MessageSource messageSource;

	/*
	 * セッション情報
	 */
	@Autowired
	HttpSession session;

	@Autowired
	private StudentService studentService;

	//menu→生徒情報削除画面
	@RequestMapping("/studentDelete")
	public String studentDelete(@ModelAttribute("studentDelete") StudentDeleteForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		return "studentDelete";
	}

	//生徒削除画面→生徒情報削除確認画面
	@RequestMapping(value = "/studentDeleteConfirm", method = RequestMethod.POST)
	public String studentDeleteConfirm(@Validated @ModelAttribute("studentDelete") StudentDeleteForm form,
			BindingResult bindingResult, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		if (bindingResult.hasErrors()) {

			return "studentDelete";
		}
		//入力値取得
		Integer studentId;
		String studentName;

		Student editData = null;
		editData = (Student) session.getAttribute("editStudent");
		//検索から削除を選択された場合の入力値の取得
		if (editData != null) {
			studentId = editData.getId();
			studentName = editData.getName();
		} else {

			//入力値取得
			studentId = form.getId();
			studentName = form.getName();
		}

		//どちらも未入力の場合
		if (ParamUtil.isNullOrEmpty(studentName) && studentId == null) {
			String errMsg = messageSource.getMessage("date.duplicate.error", null, Locale.getDefault());
			model.addAttribute("errMsg", errMsg);

			return "studentDelete";
		}
		List<Student> student = studentService.selectStudent(studentId, studentName);
		if (student == null || student.get(0).getId() <= 0) {
			//対象生徒がいなかった場合
			String errMsg = messageSource.getMessage("student.none.error", null, Locale.getDefault());
			model.addAttribute("errMsg", errMsg);
			return "studentDelete";
		} else if (student.size() > 1) {
			//複数いる場合
			String errMsg = messageSource.getMessage("students.error", null, Locale.getDefault());
			model.addAttribute("errMsg", errMsg);
			return "studentDelete";
		} else {
			//該当生徒がいた場合
			if (studentService.updateCheck(student.get(0).getId())) {
				//削除可能生徒である場合
				//次画面用の情報をセット(セッションに変更)結果画面に遷移
				session.setAttribute("studentDelete", student.get(0));
				form.setId(student.get(0).getId());
				form.setName(student.get(0).getName());
				return "studentDeleteConfirm";

			} else {
				String errMsg = messageSource.getMessage("student.not.error", null, Locale.getDefault());
				model.addAttribute("errMsg", errMsg);
				return "studentDelete";
			}
		}
	}

	//削除結果画面遷移
	@RequestMapping(value = "/studentDeleteResult", params = "delete", method = RequestMethod.POST)
	public String studentDeleteResult(@ModelAttribute("studentDelete") StudentDeleteForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "timeout";
		}
		//確認時に入力した生徒ID、生徒名を取得
		Integer studentId = form.getId();

		//削除処理
		studentService.deleteStudent(studentId);

		//06281140変更：伊藤

		//studentの情報の入ったセッションの取得
		List<Student> studentInfo = studentService.getSessionStudent();
		session.setAttribute("studentInfo", studentInfo);
		return "studentDeleteResult";
	}

	//確認画面の戻るボタン押下時の画面遷移
	@RequestMapping(value = "/studentDeleteResult", params = "back", method = RequestMethod.POST)
	public String studentDeleteBack(@ModelAttribute("studentDelete") StudentDeleteForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "timeout";
		}
		return "studentDelete";
	}

}

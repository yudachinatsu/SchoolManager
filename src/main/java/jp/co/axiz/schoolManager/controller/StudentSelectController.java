package jp.co.axiz.schoolManager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.axiz.schoolManager.entity.Student;
import jp.co.axiz.schoolManager.form.StudentDeleteForm;
import jp.co.axiz.schoolManager.form.StudentSelectForm;
import jp.co.axiz.schoolManager.form.StudentUpdateForm;
import jp.co.axiz.schoolManager.service.StudentService;
import jp.co.axiz.schoolManager.util.ParamUtil;

/*
 * 生徒情報検索周辺のコントローラー
 */
@Controller
public class StudentSelectController {

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

	private final int PAGE = 10;


	//生徒情報検索画面表示
	@RequestMapping(value = "/studentSelect", method = RequestMethod.GET)
	public String studentSelect(@ModelAttribute("studentSelect") StudentSelectForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		return "studentSelect";
	}

	//生徒情報検索確認画面遷移
	@RequestMapping(value = "/studentSelectResult", method = RequestMethod.GET)
	public String studentSelectConfirm(@ModelAttribute("studentSelect") StudentSelectForm form,
			BindingResult bindingResult, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		if (bindingResult.hasErrors()) {
			return "studentSelect";
		}

		//入力値の取得 生徒ID、生徒名
		Integer studentId = form.getStudentId();
		String studentName = form.getStudentName();

		//検索処理
		List<Student> studentSelectList = studentService.selectStudent(studentId, studentName);

		if (studentSelectList == null) {
			String errMsg = messageSource.getMessage("student.none.error", null, Locale.getDefault());
			model.addAttribute("errMsg", errMsg);
			return "studentSelect";
		}

		//検索結果があった場合
		session.setAttribute("studentSelectList", studentSelectList);


		List<Student> studentViewData = new ArrayList<Student>();

		for(int listIndex = 0; listIndex <= PAGE - 1 && listIndex < studentSelectList.size(); listIndex++) {
			studentViewData.add(studentSelectList.get(listIndex));
		}

		session.setAttribute("studentViewData", studentViewData);
		session.setAttribute("nowPage", 1);

		int size = studentSelectList.size();

		int count = 0;

		for (int j = size; j > 0; j -= PAGE) {
			count++;
		}
		System.out.println(count);
		session.setAttribute("count", count);




		return "studentSelectResult";
	}

	/////////試作
	@RequestMapping(value = "/studentSelectUpdate", method = RequestMethod.POST)
	public String studentSelectUpdate(@RequestParam("id") Integer id,
			@ModelAttribute("studentSelect") StudentSelectForm form, Model model) {
		List<Student> editList = (List<Student>) session.getAttribute("studentSelectList");
		for (Student edit : editList) {

			if (edit.getId().equals(id)) {
				session.setAttribute("editStudent", edit);

				break;
			}
		}
		return "studentSelectUpdate";
	}



	@RequestMapping(value = "studentSelectUpdate", params="page_btn", method=RequestMethod.POST)
	public String studentSelectForPaging(@RequestParam("page_btn") Integer pageNumber, @ModelAttribute("studentSelect") StudentSelectForm form,
			Model model) {
		List<Student> studentSelectList = (List<Student>) session.getAttribute("studentSelectList");
		List<Student> studentViewData = new ArrayList<Student>();

		for(int listIndex = (pageNumber -1) * PAGE; listIndex <= (pageNumber * PAGE) - 1 && listIndex < studentSelectList.size(); listIndex++) {
			studentViewData.add(studentSelectList.get(listIndex));
		}

		session.setAttribute("studentViewData", studentViewData);
		session.setAttribute("nowPage", pageNumber);

		return "studentSelectResult";
	}





	//更新ボタン押下時の画面遷移
	@RequestMapping(value = "/studentSelectUpdate", params = "update", method = RequestMethod.POST)
	public String studentSelectUp(@ModelAttribute("studentUpdate") StudentUpdateForm form, Model model) {

			return "forward:/studentUpdateInputForSelect";
	}

	//更新ボタン押下時の画面遷移
	@RequestMapping(value = "/studentSelectUpdate", params = "delete", method = RequestMethod.POST)
	public String studentSelectDel(@ModelAttribute("studentDelete") StudentDeleteForm form, Model model) {
				return "forward:/studentDeleteConfirm";
	}

	//戻るボタン押下時の画面遷移
	@RequestMapping(value = "/studentSelectUpdate", params = "back", method = RequestMethod.POST)
	public String studentSelectBack(@ModelAttribute("studentSelect") StudentSelectForm form, Model model) {

		return "studentSelectResult";
	}

}

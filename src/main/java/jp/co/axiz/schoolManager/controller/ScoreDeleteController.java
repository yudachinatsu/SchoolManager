package jp.co.axiz.schoolManager.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.axiz.schoolManager.entity.Score;
import jp.co.axiz.schoolManager.form.ScoreDeleteForm;
import jp.co.axiz.schoolManager.service.ScoreService;
import jp.co.axiz.schoolManager.service.StudentService;
import jp.co.axiz.schoolManager.service.TestService;
import jp.co.axiz.schoolManager.util.ParamUtil;

/*
 * 成績削除画面周りコントローラー
 */
@Controller
public class ScoreDeleteController {
	@Autowired
	private ScoreService scoreService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private TestService testService;

	@Autowired
	private HttpSession session;

	//menu→scoreDelete画面に遷移
	@RequestMapping(value = "/scoreDelete", method = RequestMethod.GET)
	public String delete(@ModelAttribute("scoreDelete") ScoreDeleteForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		return "scoreDelete";
	}

	//scoreDelete→scoreDeleteConfirm遷移
	@RequestMapping(value = "/scoreDeleteConfirm", method = RequestMethod.POST)
	public String scoreDeleteConfirm(@Validated @ModelAttribute("scoreDelete") ScoreDeleteForm form,
			BindingResult bindingResult, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "timeout";
		}
		if (bindingResult.hasErrors()) {
			return "scoreDelete";
		}
		Score editData = null;
		Integer studentId;
		Integer testId;
		String dayFormat;
		editData = (Score) session.getAttribute("editData");

		//検索から削除を選択された場合の入力値の取得
		if (editData != null) {
			studentId = editData.getStudentId();
			testId = editData.getTestId();
			dayFormat = editData.getTestDay();
		} else {

			//入力値の所得
			studentId = form.getStudentId();
			testId = form.getTestId();
			Integer year = form.getYear();
			Integer month = form.getMonth();
			Integer day = form.getDay();

			//日付を変換
			dayFormat = scoreService.changeDayFormat(year, month, day);
		}

		//IDデータをもとに成績データ取得
		//日付がjspにない

		List<Score> deleteData = scoreService.selectScore(studentId, testId, dayFormat);

		if (deleteData == null || deleteData.isEmpty()) {
			model.addAttribute("errMsgScoreDelete", "対象のデータはありません");
			return "scoreDelete";
		} else if (deleteData.size() > 1) {
			model.addAttribute("errMsgScoreDelete", "正しい情報を入力してください");
			return "scoreDelete";
		} else {
			Score scoreDelete = deleteData.get(0);
			//session.setAttribute("scoreId",scoreDelete);

			String studentName = studentService.getStudentName(scoreDelete.getStudentId()).getName();
			String testName = testService.getTestName(scoreDelete.getTestId()).getName();
			form.setStudentId(scoreDelete.getStudentId());
			form.setStudentName(studentName);
			form.setTestId(scoreDelete.getTestId());
			form.setTestName(testName);
			form.setTestDay(scoreDelete.getTestDay());
			form.setScore(scoreDelete.getScore());
			session.setAttribute("deleteId", scoreDelete.getId());
			return "scoreDeleteConfirm";
		}
	}

	//scoreDelete→scoreDeleteResult画面に遷移
	@RequestMapping(value = "/scoreDeleteResult", params = "delete", method = RequestMethod.POST)
	public String scoreDeleteResult(@ModelAttribute("scoreDelete") ScoreDeleteForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "timeout";
		}
		Integer scoreId = (Integer) session.getAttribute("deleteId");
		scoreService.deleteScore(scoreId);

		return "scoreDeleteResult";
	}

	//戻るが推された場合
	@RequestMapping(value = "/scoreDeleteResult", params = "back", method = RequestMethod.POST)
	public String scoreDeleteBack(@ModelAttribute("scoreDelete") ScoreDeleteForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "timeout";
		}
		Integer studentId = studentService.getStudentId(form.getStudentName()).getId();
		Integer testId = testService.getTestId(form.getTestName()).getId();
		form.setStudentId(studentId);
		form.setTestId(testId);
		form.setYear(ParamUtil.getYear(form.getTestDay()));
		form.setMonth(ParamUtil.getMonth(form.getTestDay()));
		form.setDay(ParamUtil.getDay(form.getTestDay()));
		return "scoreDelete";
	}

}

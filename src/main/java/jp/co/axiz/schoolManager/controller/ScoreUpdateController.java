package jp.co.axiz.schoolManager.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

import jp.co.axiz.schoolManager.entity.Score;
import jp.co.axiz.schoolManager.form.ScoreUpdateForm;
import jp.co.axiz.schoolManager.service.ScoreService;
import jp.co.axiz.schoolManager.service.StudentService;
import jp.co.axiz.schoolManager.service.TestService;
import jp.co.axiz.schoolManager.util.ParamUtil;

/*
 * 成績更新画面周りコントローラー
 */

@Controller
public class ScoreUpdateController {
	@Autowired
	MessageSource messageSource;
	@Autowired
	HttpSession session;
	@Autowired
	ScoreService scoreService;
	@Autowired
	StudentService studentService;
	@Autowired
	TestService testService;

	@RequestMapping(value = "scoreUpdate", method = RequestMethod.GET)
	public String scoreUpdate(@ModelAttribute("scoreUpdate") ScoreUpdateForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		return "scoreUpdate";
	}

	@RequestMapping(value = "scoreUpdateInput", method = RequestMethod.POST)
	public String scoreUpdateInput(@Validated @ModelAttribute("scoreUpdate") ScoreUpdateForm form,
			BindingResult bindingResult, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}


		if (bindingResult.hasErrors()) {
			model.addAttribute("error", "生徒名またはテスト名を正しく入力してください");
			return "scoreUpdate";
		}

		Integer studentId;
		Integer testId;
		String dayFormat;


		//検索画面からの編集

		//入力値取得
		studentId = form.getStudentId();
		testId = form.getTestId();
		Integer year = form.getYear();
		Integer month = form.getMonth();
		Integer day = form.getDay();

		//日付を変換
		dayFormat = scoreService.changeDayFormat(year, month, day);

		if(!year.equals(0) && !month.equals(0) && !day.equals(0) && dayFormat == null) {
			model.addAttribute("error","存在しない日付です");
			return "scoreUpdate";
		}


		if ((studentId == null || studentId.equals(0))
				&& (testId == null || testId.equals(0))
				&& dayFormat == null) {
			model.addAttribute("error", "一つ以上検索条件を指定してください");
			return "scoreUpdate";
		}

		List<Score> ret = scoreService.selectScore(studentId, testId, dayFormat);

		if (ret == null || ret.isEmpty()) {
			System.out.println(ret);
			model.addAttribute("error", "存在しない成績データです");
			return "scoreUpdate";
		} else if (ret.size() >= 2) {
			model.addAttribute("error", "データが複数存在します");
			return "scoreUpdate";
		} else {
			session.setAttribute("updateScore", ret.get(0));
			//プルダウンに表示する処理
			form.setStudentId(ret.get(0).getStudentId());
			form.setTestId(ret.get(0).getTestId());
			form.setScore(ret.get(0).getScore());
			String testDay = ret.get(0).getTestDay();
			Integer formYear = ParamUtil.getYear(testDay);
			Integer formMonth = ParamUtil.getMonth(testDay);
			Integer formDay = ParamUtil.getDay(testDay);
			form.setYear(formYear);
			form.setMonth(formMonth);
			form.setDay(formDay);

			return "scoreUpdateInput";
		}
	}

	//検索から
	@RequestMapping(value = "scoreUpdateInputForSelect", method = RequestMethod.POST)
	public String scoreUpdateInputForSelect( @ModelAttribute("scoreUpdate") ScoreUpdateForm form,
			 Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		Score editData = null;
		editData = (Score)session.getAttribute("editData");


		Integer studentId;
		Integer testId;
		String dayFormat;


		//検索画面からの編集

			studentId =editData.getStudentId();
			testId = editData.getTestId();
			dayFormat = editData.getTestDay();


		if ((studentId == null || studentId.equals(0))
				&& (testId == null || testId.equals(0))
				&& dayFormat == null) {
			model.addAttribute("error", "一つ以上検索条件を指定してください");
			return "scoreUpdate";
		}

		List<Score> ret = scoreService.selectScore(studentId, testId, dayFormat);

		if (ret == null || ret.isEmpty()) {
			System.out.println(ret);
			model.addAttribute("error", "存在しない成績データです");
			return "scoreUpdate";
		} else if (ret.size() >= 2) {
			model.addAttribute("error", "データが複数存在します");
			return "scoreUpdate";
		} else {
			session.setAttribute("updateScore", ret.get(0));
			//プルダウンに表示する処理
			form.setStudentId(ret.get(0).getStudentId());
			form.setTestId(ret.get(0).getTestId());
			form.setScore(ret.get(0).getScore());
			String testDay = ret.get(0).getTestDay();
			Integer formYear = ParamUtil.getYear(testDay);
			Integer formMonth = ParamUtil.getMonth(testDay);
			Integer formDay = ParamUtil.getDay(testDay);
			form.setYear(formYear);
			form.setMonth(formMonth);
			form.setDay(formDay);

			return "scoreUpdateInput";
		}
	}


	@RequestMapping(value = "scoreUpdateConfirm", params = "check", method = RequestMethod.POST)
	public String scoreUpdateConfirm(@Validated @ModelAttribute("scoreUpdate") ScoreUpdateForm form,
			BindingResult bindingResult, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		if (bindingResult.hasErrors() || form.getStudentId().equals(0) || form.getTestId().equals(0)) {
			model.addAttribute("error", "変更項目を正しく入力してください");
			return "scoreUpdateInput";
		}

		//入力内容を取得
		Integer studentId = form.getStudentId();
		Integer testId = form.getTestId();
		String studentName = studentService.getStudentName(studentId).getName();
		String testName = testService.getTestName(testId).getName();
		Integer score = form.getScore();
		Integer year = form.getYear();
		Integer month = form.getMonth();
		Integer day = form.getDay();

		//日付を変換
		String testDay = scoreService.changeDayFormat(year, month, day);
		if (testDay == null) {
			model.addAttribute("error", "日付を正しく入力してください");
			return "scoreUpdateInput";
		}
		//更新前のデータ
		Score beforeData = (Score) session.getAttribute("updateScore");
		//更新予定の入力データ
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = format.parse(testDay);
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			date = new Date();
		}
		String str = new SimpleDateFormat("yyyy-MM-dd").format(date);

		//変更がない場合のエラー、変更情報が重複している場合のエラー

		if (beforeData.getStudentId().equals(studentId) &&
				beforeData.getTestId().equals(testId) &&
				beforeData.getScore().equals(score) &&
				beforeData.getTestDay().equals(str)) {
			model.addAttribute("error", "変更を加えてください");
			return "scoreUpdateInput";
			//} else if (!scoreService.Check(testId, studentId, testDay, score)) {
		} else if (scoreService.Check(testId, studentId, testDay) ||
				(beforeData.getStudentId().equals(studentId) &&
						beforeData.getTestId().equals(testId) &&
						beforeData.getTestDay().equals(str))) {
			form.setStudentId(studentId);
			form.setStudentName(studentName);
			form.setTestId(testId);
			form.setTestName(testName);
			form.setScore(score);
			form.setDayFormat(testDay);

			return "scoreUpdateConfirm";

		} else {
			model.addAttribute("error", "データが重複しています");
			return "scoreUpdateInput";
			//confirm表示用

		}
	}

	@RequestMapping(value = "scoreUpdateResult", params = "update", method = RequestMethod.POST)
	public String scoreUpdateResult(@ModelAttribute("scoreUpdate") ScoreUpdateForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "timeout";
		}
		Integer studentId = studentService.getStudentId(form.getStudentName()).getId();
		Integer testId = testService.getTestId(form.getTestName()).getId();

		Integer score = form.getScore();
		String testDay = form.getDayFormat();

		Score data = (Score) session.getAttribute("updateScore");
		Integer id = data.getId();
		//scoreUpdate
		scoreService.updateScore(studentId, testId, score, testDay, id);

		return "scoreUpdateResult";
	}

	//戻るボタン押下時の画面遷移
	@RequestMapping(value = "/scoreUpdateConfirm", params = "back", method = RequestMethod.POST)
	public String scoreUpdateComfirmBack(@ModelAttribute("scoreUpdate") ScoreUpdateForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "timeout";
		}
		return "scoreUpdate";
	}

	//戻るボタン押下時の画面遷移
	@RequestMapping(value = "/scoreUpdateResult", params = "back", method = RequestMethod.POST)
	public String scoreUpdateBack(@ModelAttribute("scoreUpdate") ScoreUpdateForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "timeout";
		}
		//form表示用
		Integer studentId = studentService.getStudentId(form.getStudentName()).getId();
		Integer testId = testService.getTestId(form.getTestName()).getId();
		form.setStudentId(studentId);
		form.setTestId(testId);
		form.setScore(form.getScore());
		form.setYear(ParamUtil.getYear(form.getDayFormat()));
		form.setMonth(ParamUtil.getMonth(form.getDayFormat()));
		form.setDay(ParamUtil.getDay(form.getDayFormat()));
		return "scoreUpdateInput";
	}
	//gitlab.com/AokiRyunosuke/schoolmanager.git
}

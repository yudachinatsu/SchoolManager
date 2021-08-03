package jp.co.axiz.schoolManager.controller;

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

import jp.co.axiz.schoolManager.form.ScoreInsertForm;
import jp.co.axiz.schoolManager.service.ScoreService;
import jp.co.axiz.schoolManager.service.StudentService;
import jp.co.axiz.schoolManager.service.TestService;
import jp.co.axiz.schoolManager.util.ParamUtil;

/*
 * 成績登録画面周りコントローラー
 */

@Controller
public class ScoreInsertController {
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

	@RequestMapping(value = "scoreInsert", method = RequestMethod.GET)
	public String studentUpdate(@ModelAttribute("scoreInsert") ScoreInsertForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		return "scoreInsert";
	}

	//scoreConfirm移行用
	@RequestMapping(value = "scoreInsertConfirm", method = RequestMethod.POST)
	public String studentUpdateConfirm(@Validated @ModelAttribute("scoreInsert") ScoreInsertForm form,
			BindingResult bindingResult, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "timeout";
		}
		Integer year = form.getYear();
		Integer month = form.getMonth();
		Integer day = form.getDay();
		Integer studentId = form.getStudentId();
		Integer testId = form.getTestId();
		String dayFormat = scoreService.changeDayFormat(year, month, day);
		if (bindingResult.hasErrors() || studentId.equals(0) || testId.equals(0)) {
			String errMsg = "";
			errMsg = messageSource.getMessage("date.duplicate.error", null, Locale.getDefault());

			model.addAttribute("errMsg", errMsg);
			return "scoreInsert";
		}
		if (dayFormat == null) {
			String errMsg = "日付が正しくありません";
			model.addAttribute("errMsg", errMsg);
			return "scoreInsert";
		}
		if (scoreService.Check(testId, studentId, dayFormat)) {
			form.setStudentName(studentService.getStudentName(studentId).getName());
			form.setTestName(testService.getTestName(testId).getName());
			form.setDayFormat(dayFormat);
			form.setDay(day);
			form.setMonth(month);
			form.setYear(year);
			return "scoreInsertConfirm";
		} else {
			model.addAttribute("errMsg", "このユーザのこの日のこのテストはすでに登録済みです、更新をしてください");
			return "scoreInsert";
		}

	}

	@RequestMapping(value = "scoreInsertResult", params = "insert", method = RequestMethod.POST)
	public String studentUpdateResult(@ModelAttribute("scoreInsert") ScoreInsertForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "timeout";
		}
		String dayFormat = form.getDayFormat();
		scoreService.insertScore(studentService.getStudentId(form.getStudentName()).getId(),
				testService.getTestId(form.getTestName()).getId(),
				form.getScore(),
				dayFormat);

		return "scoreInsertResult";
	}

	@RequestMapping(value = "scoreInsertResult", params = "back", method = RequestMethod.POST)
	public String studentUpdateBack(@ModelAttribute("scoreInsert") ScoreInsertForm form, Model model) {
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

		return "scoreInsert";
	}
}

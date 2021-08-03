package jp.co.axiz.schoolManager.controller;

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

import jp.co.axiz.schoolManager.entity.Test;
import jp.co.axiz.schoolManager.form.TestInsertForm;
import jp.co.axiz.schoolManager.service.TestService;
import jp.co.axiz.schoolManager.util.ParamUtil;

/*
 * テスト登録画面周りコントローラー
 */

@Controller
public class TestInsertController {
	@Autowired
	MessageSource messageSource;

	@Autowired
	HttpSession session;

	@Autowired
	TestService testService;

	/**
	 * testinsertに移動
	 */
	@RequestMapping(value = "/testInsert", method = RequestMethod.GET)
	public String testInsert(@ModelAttribute("testInsert") TestInsertForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		return "testInsert";
	}

	/**
	 * 確認画面に移動、
	 */
	@RequestMapping(value = "/testInsertConfirm", method = RequestMethod.POST)
	public String testInsertConfirm(@Validated @ModelAttribute("testInsert") TestInsertForm form,
			BindingResult bindingResult,
			Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		if (bindingResult.hasErrors()) {
			return "testInsert";
		}

		Integer testId = form.getTestId();
		String testName = form.getTestName();
		System.out.println("testService.getTestNameInTrue(testId):" + testService.getTestNameInTrue(testId));
		//if (testService.getTestNameInTrue(testId)) {
		if(testService.Check(testId)) {
			form.setTestId(testId);
			form.setTestName(testName);
			return "testInsertConfirm";

		} else {
			model.addAttribute("TestInsertMsg", "登録済みのテストIＤです");
			return "testInsert";
		}
	}

	/**
	 * 登録処理 (登録確認画面の登録ボタン押下)
	 */
	@RequestMapping(value = "/testInsertResult", params = "insert", method = RequestMethod.POST)
	public String testInsertResult(@ModelAttribute("testInsert") TestInsertForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "timeout";
		}
		session.getAttribute("loginUser");

		Integer testId = form.getTestId();
		String testName = form.getTestName();

		// 登録処理
		testService.insertTest(testId, testName);

		//セッションの更新処理の追加
		List<Test> testInfo = testService.getSessionTest();
		session.setAttribute("testInfo", testInfo);

		return "testInsertResult";
	}

	//戻るボタン用
	@RequestMapping(value = "/testInsertResult", params = "back", method = RequestMethod.POST)
	public String testDeleteBack(@ModelAttribute("testInsert") TestInsertForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "timeout";
		}
		return "testInsert";
	}

}

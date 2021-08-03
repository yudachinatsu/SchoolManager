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

import jp.co.axiz.schoolManager.entity.Test;
import jp.co.axiz.schoolManager.form.TestUpdateForm;
import jp.co.axiz.schoolManager.service.TestService;
import jp.co.axiz.schoolManager.util.ParamUtil;

/*
 * テスト更新画面周りコントローラー
 */

@Controller
public class TestUpdateController {

	//青木さんお願いします
	@Autowired
	MessageSource messageSource;
	@Autowired
	HttpSession session;
	@Autowired
	TestService service;

	@RequestMapping(value = "/testUpdate", method = RequestMethod.GET)
	public String testUpdate(@ModelAttribute("testUpdate") TestUpdateForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		return "testUpdate";
	}

	@RequestMapping(value = "/testUpdateInput", method = RequestMethod.POST)
	public String testUpdateInput(@Validated @ModelAttribute("testUpdate") TestUpdateForm form,
			BindingResult bindingResult, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		if (bindingResult.hasErrors()) {
			return "testUpdate";
		}
		Integer testId = form.getTestId();
		List<Test> ret = service.selectTests(testId, null);
		if (ret == null || testId <= 0) {
			String errMsg = messageSource.getMessage("test.none.error", null, Locale.getDefault());
			model.addAttribute("errMsg", errMsg);
			;
			return "testUpdate";
		} else {
			Test test = ret.get(0);
			session.setAttribute("updateTest", test);
			form.setTestId(test.getId());
			form.setTestName(test.getName());
			return "testUpdateInput";
		}
	}

	//検索から
	@RequestMapping(value = "/testUpdateInputForSelect", method = RequestMethod.POST)
	public String testUpdateInputForSelect(@ModelAttribute("testUpdate") TestUpdateForm form,
			Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		Test editTestData = (Test) session.getAttribute("editTest");

		Integer testId = editTestData.getId();
		List<Test> ret = service.selectTests(testId, null);
		if (ret == null || testId <= 0) {
			String errMsg = messageSource.getMessage("test.none.error", null, Locale.getDefault());
			model.addAttribute("errMsg", errMsg);
			return "testUpdate";
		} else {
			Test test = ret.get(0);
			session.setAttribute("updateTest", test);
			form.setTestId(test.getId());
			form.setTestName(test.getName());
			return "testUpdateInput";
		}
	}

	@RequestMapping(value = "/testUpdateConfirm", params = "check", method = RequestMethod.POST)
	public String testUpdateConfirm(@Validated @ModelAttribute("testUpdate") TestUpdateForm form,
			BindingResult bindingResult, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		if (bindingResult.hasErrors()) {
			return "testUpdateInput";
		}
		Integer testId = form.getTestId();
		String testName = form.getTestName();
		Test before = (Test) session.getAttribute("updateTest");
		//変更可能かどうかチェックも行う
		//ここから!

		if (!service.Check(testId) && !testId.equals(before.getId())) {
			//既に使用しているID
			String errMsg = messageSource.getMessage("test.update.error.dup", null, Locale.getDefault());
			model.addAttribute("errMsg", errMsg);
			return "testUpdateInput";
		} else if (testId.equals(before.getId()) && testName.equals(before.getName())) {
			//何も更新していない
			String errMsg = messageSource.getMessage("test.notupdate.error", null, Locale.getDefault());
			model.addAttribute("errMsg", errMsg);
			return "testUpdateInput";
		} else {
			form.setTestId(testId);
			form.setTestName(testName);
			return "testUpdateConfirm";
		}

		//test情報を更新
		//正しく更新できたらResultへ
	}

	@RequestMapping(value = "/testUpdateResult", params = "update", method = RequestMethod.POST)
	public String testUpdateResult(@ModelAttribute("testUpdate") TestUpdateForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		Integer testId = form.getTestId();
		String testName = form.getTestName();
		Test before = (Test) session.getAttribute("updateTest");

		if (service.updateCheck(before.getId()) || testId.equals(before.getId())) {
			service.updateTest(testId, testName, before.getId());
		} else {
			service.updateTestForCheckIsFalse(testId, testName, before.getId());
		}

		//セッションの更新処理の追加
		List<Test> testInfo = service.getSessionTest();
		session.setAttribute("testInfo", testInfo);
		return "testUpdateResult";
	}

	//戻るボタン押下時の画面遷移
	@RequestMapping(value = "/testUpdateConfirm", params = "back", method = RequestMethod.POST)
	public String testUpdateComfirmBack(@ModelAttribute("testUpdate") TestUpdateForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/index";
		}
		return "testUpdate";
	}

	//戻るボタン押下時の画面遷移
	@RequestMapping(value = "/testUpdateResult", params = "back", method = RequestMethod.POST)
	public String testUpdateBack(@ModelAttribute("testUpdate") TestUpdateForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		return "testUpdateInput";
	}

}

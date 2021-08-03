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
import jp.co.axiz.schoolManager.form.TestDeleteForm;
import jp.co.axiz.schoolManager.service.TestService;
import jp.co.axiz.schoolManager.util.ParamUtil;

//担当：伊藤
@Controller
public class TestDeleteController {
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
	private TestService testService;

	//テスト情報削除画面表示
	@RequestMapping("/testDelete")
	public String testDelete(@ModelAttribute("testDelete") TestDeleteForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		return "testDelete";
	}

	//テスト情報確認画面遷移
	@RequestMapping(value = "/testDeleteConfirm", method = RequestMethod.POST)
	public String testDeleteConfirm(@Validated @ModelAttribute("testDelete") TestDeleteForm form,
			BindingResult bindingResult, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		if (bindingResult.hasErrors()) {
			return "testDelete";
		}
		Integer testId;
		String testName;
		Test editTest = null;
		editTest = (Test)session.getAttribute("editTest");

		if(editTest!=null) {
			testId= editTest.getId();
			testName= editTest.getName();
		}else {

		//入力値取得
		testId = form.getTestId();
		testName = form.getTestName();
		}
		//どちらも未入力の場合
		if (ParamUtil.isNullOrEmpty(testName) && testId == null) {
			String errMsg = messageSource.getMessage("date.duplicate.error", null, Locale.getDefault());
			model.addAttribute("errMsg", errMsg);

			return "testDelete";
		}

		//変更可能なテストである場合
		List<Test> test = testService.selectTests(testId, testName);

		//該当するテストがいない場合
		if (test == null || test.get(0).getId() <= 0) {
			String errMsg = messageSource.getMessage("testnot.error", null, Locale.getDefault());
			model.addAttribute("errMsg", errMsg);
			return "testDelete";
		} else if (test.size() > 1) {
			String errMsg = messageSource.getMessage("students.error", null, Locale.getDefault());
			model.addAttribute("errMsg", errMsg);
			return "testDelete";
		}
		//いた場合
		else {
			if (testService.updateCheck(test.get(0).getId())) {
				//DBに保存されている生徒の検索(selectTest内に名前検索、IDと名前検索の記述あり)
				session.setAttribute("testDelete", test.get(0));
				form.setTestId(test.get(0).getId());
				form.setTestName(test.get(0).getName());
				return "testDeleteConfirm";

			} else {
				//変更不可の場合
				String errMsg = messageSource.getMessage("not.change.error", null, Locale.getDefault());
				model.addAttribute("errMsg", errMsg);
				return "testDelete";
			}
		}
	}

	//テスト情報削除結果画面遷移
	@RequestMapping(value = "/testDeleteResult", params = "delete", method = RequestMethod.POST)
	public String testDeleteResult(@ModelAttribute("testDelete") TestDeleteForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		//確認時に入力したテストID、テスト名を取得
		Integer testId = form.getTestId();

		//削除処理
		testService.deleteTest(testId);

		//06281417追記：伊藤
		//セッションの更新処理の追加
		List<Test> testInfo = testService.getSessionTest();
		session.setAttribute("testInfo", testInfo);

		return "testDeleteResult";
	}

	//戻るボタン押下時の画面遷移
	@RequestMapping(value = "/testDeleteResult", params = "back", method = RequestMethod.POST)
	public String testDeleteBack(@ModelAttribute("testDelete") TestDeleteForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		return "testDelete";
	}

}

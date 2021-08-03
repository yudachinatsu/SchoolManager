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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.axiz.schoolManager.entity.Test;
import jp.co.axiz.schoolManager.form.TestDeleteForm;
import jp.co.axiz.schoolManager.form.TestSelectForm;
import jp.co.axiz.schoolManager.form.TestUpdateForm;
import jp.co.axiz.schoolManager.service.TestService;
import jp.co.axiz.schoolManager.util.ParamUtil;

/*
 * テスト検索画面周りコントローラー
 */

@Controller
public class TestSelectController {
	@Autowired
	HttpSession session;

	@Autowired
	private TestService testService;

	@Autowired
	private MessageSource messageSource;

	private final int PAGE = 10;

	@RequestMapping(value = "/testSelect", method = RequestMethod.GET)
	public String testselect(@ModelAttribute("testSelect") TestSelectForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		return "testSelect";
	}

	@RequestMapping(value = "/testSelectResult", method = RequestMethod.GET)
	public String testSetectResult(@Validated @ModelAttribute("testSelect") TestSelectForm form,
			BindingResult bindingResult, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		Integer testId = form.getTestId();
		String testName = form.getTestName();

		if (bindingResult.hasErrors()) {
			return "testSelect";
		}

		List<Test> testList = testService.selectTests(testId, testName);

		if (testList == null || testList.isEmpty()) {
			String errMsg = messageSource.getMessage("not.error", null, Locale.getDefault());
			model.addAttribute("errMsg", errMsg);
			return "testSelect";
		} else {
			session.setAttribute("testList", testList);

			List<Test> testViewData = new ArrayList<Test>();

			for (int listIndex = 0; listIndex <= PAGE - 1 && listIndex < testList.size(); listIndex++) {
				testViewData.add(testList.get(listIndex));
			}

			//ページング機能追加
			session.setAttribute("testViewData", testViewData);
			session.setAttribute("nowPage", 1);

			int size = testList.size();

			int count = 0;

			for (int j = size; j > 0; j -= PAGE) {
				count++;
			}
			System.out.println(count);
			session.setAttribute("count", count);

			return "testSelectResult";
		}
	}

	@RequestMapping(value = "/testSelectUpdate", method = RequestMethod.POST)
	public String testSelectUpdate(@RequestParam("id") Integer id, @ModelAttribute("testSelect") TestSelectForm form,
			Model model) {
		List<Test> editList = (List<Test>) session.getAttribute("testList");
		for (Test edit : editList) {

			if (edit.getId().equals(id)) {
				session.setAttribute("editTest", edit);

				break;
			}
		}
		return "testSelectUpdate";
	}

	@RequestMapping(value = "testSelectUpdate", params = "page_btn", method = RequestMethod.POST)
	public String testSelectForPaging(@RequestParam("page_btn") Integer pageNumber,
			@ModelAttribute("testSelect") TestSelectForm form,
			Model model) {
		List<Test> testList = (List<Test>) session.getAttribute("testList");
		List<Test> testViewData = new ArrayList<Test>();

		for (int listIndex = (pageNumber - 1) * PAGE; listIndex <= (pageNumber * PAGE) - 1
				&& listIndex < testList.size(); listIndex++) {
			testViewData.add(testList.get(listIndex));
		}

		session.setAttribute("testViewData", testViewData);
		session.setAttribute("nowPage", pageNumber);

		return "testSelectResult";
	}

	//更新ボタン押下時の画面遷移
	@RequestMapping(value = "/testSelectUpdate", params = "update", method = RequestMethod.POST)
	public String testSelectUp(@ModelAttribute("testUpdate") TestUpdateForm form, Model model) {

		return "forward:/testUpdateInputForSelect";
	}

	//更新ボタン押下時の画面遷移
	@RequestMapping(value = "/testSelectUpdate", params = "delete", method = RequestMethod.POST)
	public String testSelectDel(@ModelAttribute("testDelete") TestDeleteForm form, Model model) {

		return "forward:/testDeleteConfirm";
	}

	//戻るボタン押下時の画面遷移
	@RequestMapping(value = "/testSelectUpdate", params = "back", method = RequestMethod.POST)
	public String testSelectBack(@ModelAttribute("testSelect") TestSelectForm form, Model model) {

		return "testSelectResult";
	}

}

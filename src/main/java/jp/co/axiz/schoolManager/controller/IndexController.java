package jp.co.axiz.schoolManager.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
/*
 * トップ画面周りコントローラー
 */
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.axiz.schoolManager.entity.DatePulldown;
import jp.co.axiz.schoolManager.entity.Student;
import jp.co.axiz.schoolManager.entity.Teacher;
import jp.co.axiz.schoolManager.entity.Test;
import jp.co.axiz.schoolManager.util.ParamUtil;

@Controller
public class IndexController {
	//担当：小林さん、伊藤

	//セッション情報
	@Autowired
	HttpSession session;

	/*
	 * トップ画面表示
	 */
	@RequestMapping("/index")
	public String index(Model model) {
		return "index";
	}

	/*
	 * メニュー画面表示
	 */
	@RequestMapping("/menu")
	public String menu(Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		//06281533変更：伊藤
		//全てのセッション破棄からセッション情報を先に保存➡invalidate後、再度セッションに保存するよう変更

		/*＜セッションからログインユーザー、テスト、生徒の情報の取得＞ */
		Teacher loginUser = (Teacher) session.getAttribute("loginUser");

		//未検査キャストの警告抑制の為(警告未表示アノテーション)
		@SuppressWarnings("unchecked")
		List<Student> studentInfo = (List<Student>) session.getAttribute("studentInfo");

		//未検査キャストの警告抑制の為(警告未表示アノテーション)
		@SuppressWarnings("unchecked")
		List<Test> testInfo = (List<Test>) session.getAttribute("testInfo");

		//未検査キャストの警告抑制の為(警告未表示アノテーション)
		@SuppressWarnings("unchecked")
		List<DatePulldown> years = (List<DatePulldown>) session.getAttribute("years");

		//未検査キャストの警告抑制の為(警告未表示アノテーション)
		@SuppressWarnings("unchecked")
		List<DatePulldown> monthes = (List<DatePulldown>) session.getAttribute("monthes");
		;

		//未検査キャストの警告抑制の為(警告未表示アノテーション)
		@SuppressWarnings("unchecked")
		List<DatePulldown> days = (List<DatePulldown>) session.getAttribute("days");

		//セッションの破棄
		session.invalidate();

		//再度ログインユーザー、テスト、生徒情報をセッションに保存
		session.setAttribute("loginUser", loginUser);
		session.setAttribute("studentInfo", studentInfo);
		session.setAttribute("testInfo", testInfo);
		session.setAttribute("years", years);
		session.setAttribute("monthes", monthes);
		session.setAttribute("days", days);
		return "menu";
	}
}

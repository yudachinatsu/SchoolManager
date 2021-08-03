package jp.co.axiz.schoolManager.controller;

import java.util.ArrayList;
import java.util.Calendar;
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

import jp.co.axiz.schoolManager.entity.DatePulldown;
import jp.co.axiz.schoolManager.entity.Student;
import jp.co.axiz.schoolManager.entity.Teacher;
import jp.co.axiz.schoolManager.entity.Test;
import jp.co.axiz.schoolManager.form.LoginForm;
import jp.co.axiz.schoolManager.service.StudentService;
import jp.co.axiz.schoolManager.service.TeacherService;
import jp.co.axiz.schoolManager.service.TestService;
import jp.co.axiz.schoolManager.util.ParamUtil;

@Controller
public class AuthController {
	@Autowired
	MessageSource messageSource;

	@Autowired
	HttpSession session;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private TestService testService;
	@Autowired
	private StudentService studentService;

	//ログイン画面遷移
	@RequestMapping("/login")
	public String login(@ModelAttribute("loginForm") LoginForm form, Model model) {
		if (ParamUtil.ifLogin(session)) {
			return "menu";
		} else {
			return "login";
		}
	}

	@RequestMapping("/timeout")
	public String timeout(Model model) {
		return "timeout";
	}

	//ログイン処理
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@Validated @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult,
			Model model) {

		String errMsg = messageSource.getMessage("login.error", null, Locale.getDefault());

		if (bindingResult.hasErrors()) {
			return "login";
		}

		Teacher loginUser = teacherService.authentication(form.getLoginId(), form.getPassword());

		if (loginUser == null) {
			// ログイン失敗
			model.addAttribute("errMsg", errMsg);
			return "login";
		} else {
			// ログイン成功

			// test一覧を取得
			List<Test> testInfo = testService.getSessionTest();
			//student一覧を取得
			List<Student> studentInfo = studentService.getSessionStudent();

			//年、月、日の型を生成
			List<DatePulldown> years = new ArrayList<DatePulldown>();
			List<DatePulldown> monthes = new ArrayList<DatePulldown>();
			List<DatePulldown> days = new ArrayList<DatePulldown>();
			DatePulldown date = new DatePulldown(0, "-");
			years.add(date);
			monthes.add(date);
			days.add(date);

			Calendar calendar = Calendar.getInstance();
			//現在の年
			int year = calendar.get(Calendar.YEAR);

			//2018年から現在まで取得
			for (int i = 2018; i <= year; i++) {
				date = new DatePulldown(i, i + "年");
				years.add(date);
			}

			//1月から12月まで取得
			for (int i = 1; i <= 12; i++) {

				date = new DatePulldown(i, i + "月");
				monthes.add(date);
			}
			//1日から31日まで取得
			for (int i = 1; i <= 31; i++) {

				date = new DatePulldown(i, i + "日");
				days.add(date);
			}

			session.setAttribute("loginUser", loginUser);
			session.setAttribute("testInfo", testInfo);
			session.setAttribute("studentInfo", studentInfo);
			session.setAttribute("years", years);
			session.setAttribute("monthes", monthes);
			session.setAttribute("days", days);

			 session.setMaxInactiveInterval(1200);
			return "menu";
		}
	}

	//ログアウト
	@RequestMapping(value = "/logout")
	public String logout(Model model) {
		session.invalidate();
		return "logout";
	}
}

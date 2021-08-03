package jp.co.axiz.schoolManager.util;

import javax.servlet.http.HttpSession;

import jp.co.axiz.schoolManager.entity.Teacher;

public class ParamUtil {
	//NullorEmptyを返却
	public static boolean isNullOrEmpty(String str) {
		if (str == null || str.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	//数値に変更可能かどうかを判定
	public static boolean isNumber(String str) {
		try {
			Integer.parseInt(str);
		} catch (NumberFormatException ex) {
			return false;
		}

		return true;
	}

	//数値に変換(できない場合はNull)
	public static Integer checkAndParseInt(String str) {
		if (isNumber(str)) {
			return Integer.parseInt(str);
		} else {
			return null;
		}
	}

	//Stringから年を取得
	public static Integer getYear(String dayFormat) {
		return checkAndParseInt(dayFormat.substring(0, 4));
	}

	//Stringから月を取得
	public static Integer getMonth(String dayFormat) {
		//最初の取得場所を検索
		int first = dayFormat.indexOf("-");
		first += 1;
		int second = dayFormat.indexOf("-", first);
		return checkAndParseInt(dayFormat.substring(first, second));
	}

	//Stringから日を取得
	public static Integer getDay(String dayFormat) {
		//最初の取得場所を検索
		int first = dayFormat.indexOf("-");
		first += 1;
		int second = dayFormat.indexOf("-", first);
		return checkAndParseInt(dayFormat.substring(second + 1));
	}

	//ログインしているか判定
	//false→未ログイン true→ログイン済み
	public static boolean ifLogin(HttpSession session) {
		Teacher loginUser = null;
		loginUser = (Teacher) session.getAttribute("loginUser");
		if (loginUser == null) {
			return false;
		} else {
			return true;
		}

	}
}

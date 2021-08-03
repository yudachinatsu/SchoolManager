package jp.co.axiz.schoolManager.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.axiz.schoolManager.entity.Score;
import jp.co.axiz.schoolManager.entity.ScoreSelect;
import jp.co.axiz.schoolManager.entity.Student;
import jp.co.axiz.schoolManager.entity.Test;
import jp.co.axiz.schoolManager.form.ScoreDeleteForm;
import jp.co.axiz.schoolManager.form.ScoreSelectForm;
import jp.co.axiz.schoolManager.form.ScoreUpdateForm;
import jp.co.axiz.schoolManager.service.ScoreService;
import jp.co.axiz.schoolManager.service.StudentService;
import jp.co.axiz.schoolManager.service.TestService;
import jp.co.axiz.schoolManager.util.ParamUtil;

/*
 * 成績検索画面周りコントローラー
 */

@Controller
public class ScoreSelectController {

	@Autowired
	HttpSession session;

	@Autowired
	private ScoreService scoreService;
	@Autowired
	private StudentService studentService;
	@Autowired
	private TestService testService;

	private final int PAGE = 10;

	@RequestMapping(value = "/scoreSelect", method = RequestMethod.GET)
	public String select(@ModelAttribute("scoreSelect") ScoreSelectForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {
			return "redirect:/timeout";
		}
		return "scoreSelect";
	}

	@RequestMapping(value = "/scoreSelectResult", method = RequestMethod.GET)
	public String scoreSelectResult(@ModelAttribute("scoreSelect") ScoreSelectForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {

			return "redirect:/timeout";

		}
		Integer studentId = form.getStudentId();
		Integer testId = form.getTestId();
		Integer year = form.getYear();
		Integer month = form.getMonth();
		Integer day = form.getDay();
		String dateFormat = scoreService.changeDayFormat(year, month, day);
		List<Score> resultList = scoreService.selectScore(studentId, testId, dateFormat);
		if (resultList == null || resultList.isEmpty()) {
			//String errMsg = メッセージリソース("select.error", null,Locale.getDefault());
			model.addAttribute("errMsg", "対象のデータは存在しませんでした");
			return "scoreSelect";
		} else {
			String[] dateList = new String[resultList.size()];
			int[] testIdList = new int[resultList.size()];
			int[] scoreList = new int[resultList.size()];
			int[] rankList = new int[resultList.size()];
			boolean[] scoreCheck = new boolean[resultList.size()];
			double[] averageList = new double[resultList.size()];

			int i = 0;
			//配列に点数を入れる。
			for (Score score : resultList) {
				scoreList[i] = score.getScore();
				rankList[i] = 1;
				averageList[i] = score.getScore();
				scoreCheck[i] = false;
				dateList[i] = score.getTestDay();
				testIdList[i] = score.getTestId();
				i++;
			}

			for (i = 0; i < resultList.size(); i++) {
				if (!scoreCheck[i]) {
					//まだ編集してないやつがあったとき
					scoreCheck[i] = true;
					List<Integer> numberIndex = new ArrayList<>();
					numberIndex.add(i);
					for (int j = i + 1; j < resultList.size(); j++) {
						//同じ日で同じテストの場合
						if (!scoreCheck[j] && dateList[i].equals(dateList[j]) && testIdList[i] == testIdList[j]) {
							scoreCheck[j] = true;
							numberIndex.add(j);
						}
					}
					int sum = 0;
					for (int j = 0; j < numberIndex.size(); j++) {
						sum += scoreList[numberIndex.get(j)];
						for (int k = j + 1; k < numberIndex.size(); k++) {
							if (scoreList[numberIndex.get(j)] < scoreList[numberIndex.get(k)]) {
								rankList[numberIndex.get(j)]++;
							} else if (scoreList[numberIndex.get(j)] > scoreList[numberIndex.get(k)]) {
								rankList[numberIndex.get(k)]++;
							}
						}
					}
					double average = (double) sum / (double) numberIndex.size();
					double roundDbl = Math.round(average * 100.0) / 100.0;
					for (int j = 0; j < numberIndex.size(); j++) {
						averageList[numberIndex.get(j)] = roundDbl;
					}
				}
			}
			ArrayList<ScoreSelect> viewList = new ArrayList<ScoreSelect>();
			for (int index = 0; index < resultList.size(); index++) {

				String studentName = studentService.getStudentName(resultList.get(index).getStudentId()).getName();
				String testName = testService.getTestName(resultList.get(index).getTestId()).getName();
				Integer id = resultList.get(index).getId();
				ScoreSelect s = new ScoreSelect(id, studentName, testName,
						resultList.get(index).getScore(), averageList[index], rankList[index],
						resultList.get(index).getTestDay());
				viewList.add(s);
			}
			session.setAttribute("viewList", viewList);
			List<ScoreSelect> viewData = new ArrayList<ScoreSelect>();

			for (int listIndex = 0; listIndex <= PAGE - 1 && listIndex < viewList.size(); listIndex++) {
				viewData.add(viewList.get(listIndex));
			}

			session.setAttribute("viewData", viewData);
			session.setAttribute("nowPage", 1);

			int size = viewList.size();

			int count = 0;

			for (int j = size; j > 0; j -= PAGE) {
				count++;
			}
			session.setAttribute("count", count);

			return "scoreSelectResult";
		}

	}

	@RequestMapping(value = "/scoreSelectUpdate", params = "id", method = RequestMethod.POST)
	public String scoreSelectUpdate(@RequestParam("id") Integer id, @ModelAttribute("scoreSelect") ScoreSelectForm form,
			Model model) {
		if (!ParamUtil.ifLogin(session)) {

			return "redirect:/timeout";

		}
		List<ScoreSelect> editList = (List<ScoreSelect>) session.getAttribute("viewList");
		for (ScoreSelect edit : editList) {

			if (edit.getId().equals(id)) {
				session.setAttribute("edit", edit);

				break;
			}
		}
		return "scoreSelectUpdate";
	}

	@RequestMapping(value = "/scoreSelectUpdate", params = "page_btn", method = RequestMethod.POST)
	public String scoreSelectForPaging(@RequestParam("page_btn") Integer pageNumber,
			@ModelAttribute("scoreSelect") ScoreSelectForm form,
			Model model) {
		if (!ParamUtil.ifLogin(session)) {

			return "redirect:/timeout";

		}
		List<ScoreSelect> viewList = (List<ScoreSelect>) session.getAttribute("viewList");
		List<ScoreSelect> viewData = new ArrayList<ScoreSelect>();

		for (int listIndex = (pageNumber - 1) * PAGE; listIndex <= (pageNumber * PAGE) - 1
				&& listIndex < viewList.size(); listIndex++) {
			viewData.add(viewList.get(listIndex));
		}

		session.setAttribute("viewData", viewData);
		session.setAttribute("nowPage", pageNumber);

		return "scoreSelectResult";
	}

	//更新ボタン押下時の画面遷移
	@RequestMapping(value = "/scoreSelectUpdate", params = "update", method = RequestMethod.POST)
	public String scoreSelectUp(@ModelAttribute("scoreUpdate") ScoreUpdateForm form, Model model) {
		ScoreSelect edit = (ScoreSelect) session.getAttribute("edit");
		if (!ParamUtil.ifLogin(session)) {

			return "redirect:/timeout";

		}
		//studentNameからstudentId取得
		Student student = studentService.getStudentId(edit.getStudentName());
		Integer studentId = student.getId();

		Test test = testService.getTestId(edit.getTestName());
		Integer testId = test.getId();

		Score editData = new Score(edit.getId(), studentId, testId, edit.getScore(), edit.getTestDay());
		session.setAttribute("editData", editData);

		return "forward:/scoreUpdateInputForSelect";
	}

	//更新ボタン押下時の画面遷移
	@RequestMapping(value = "/scoreSelectUpdate", params = "delete", method = RequestMethod.POST)
	public String scoreSelectDel(@ModelAttribute("scoreDelete") ScoreDeleteForm form, Model model) {
		ScoreSelect edit = (ScoreSelect) session.getAttribute("edit");
		if (!ParamUtil.ifLogin(session)) {

			return "redirect:/timeout";

		}
		//studentNameからstudentId取得
		Student student = studentService.getStudentId(edit.getStudentName());
		Integer studentId = student.getId();

		Test test = testService.getTestId(edit.getTestName());
		Integer testId = test.getId();

		Score editData = new Score(edit.getId(), studentId, testId, edit.getScore(), edit.getTestDay());
		session.setAttribute("editData", editData);

		return "forward:/scoreDeleteConfirm";
	}

	//戻るボタン押下時の画面遷移
	@RequestMapping(value = "/scoreSelectUpdate", params = "back", method = RequestMethod.POST)
	public String scoreSelectBack(@ModelAttribute("scoreSelect") ScoreSelectForm form, Model model) {
		if (!ParamUtil.ifLogin(session)) {

			return "redirect:/timeout";

		}
		return "scoreSelectResult";
	}

	//CSV書き出し
	/*@RequestMapping(value = "/scoreSelectUpdate", params = "csvOutput", method = RequestMethod.POST)
	public String kariInsatu(@ModelAttribute("scoreSelect") ScoreSelectForm form, Model model) {
		List<ScoreSelect> viewList = (List<ScoreSelect>) session.getAttribute("viewList");
		String text = "";
		for (ScoreSelect data : viewList) {
			text += data.getId() + "," + data.getStudentName() + "," + data.getTestName() + "," + data.getScore() + ","
					+
					data.getAverage() + "," + data.getRank() + "," + data.getTestDay() + "\n";
		}

		//String downloadUrl = "/selectData.csv";

		// デフォルトのファイル名をURLから取得します。
		URL url;

		//url = new URL(downloadUrl);
		//String fileName = Paths.get(url.getPath()).getFileName().toString();

		// 保存ダイアログの表示
		JFileChooser filechooser = new JFileChooser();
		// デフォルトのファイル名を指定
		//filechooser.setSelectedFile(new File(fileName));
		// 保存ダイアログを表示
		if (filechooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
			// 「保存」以外を押されたら終了
			return "scoreSelectResult";
		}
		// 保存先を設定
		File file = filechooser.getSelectedFile();
		try {
			PrintWriter p_writer = new PrintWriter(
					new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "Shift-JIS")));

			p_writer.print(text);
			p_writer.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		}

		return "scoreSelectResult";
	}*/
}

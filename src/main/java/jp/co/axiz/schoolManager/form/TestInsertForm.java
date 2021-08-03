package jp.co.axiz.schoolManager.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TestInsertForm {
	@NotNull
	//@Size(min = 1, max = 9999)
	private Integer testId;
	@NotBlank
	private String testName;

	public Integer getTestId() {
		return testId;
	}

	public void setTestId(Integer testId) {
		this.testId = testId;
	}

	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

}

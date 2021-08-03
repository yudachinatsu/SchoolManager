package jp.co.axiz.schoolManager.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class StudentUpdateForm {
	@NotNull
	private Integer studentId;
	@NotBlank
	private String studentName;

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

}

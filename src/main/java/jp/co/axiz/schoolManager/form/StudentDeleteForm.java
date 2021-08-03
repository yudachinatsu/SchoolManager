package jp.co.axiz.schoolManager.form;

public class StudentDeleteForm {
	//idとnameという名前に変更 :青木
	private Integer id;
	//private String studentString から private String studentName; に変更：伊藤
	private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}

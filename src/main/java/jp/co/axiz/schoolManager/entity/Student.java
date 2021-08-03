package jp.co.axiz.schoolManager.entity;

public class Student {

	//フィード
	private Integer id;
	private String name;
	private Boolean delete;

	//コンストラクタ
	public Student() {

	}

	public Student(Integer id, String name, Boolean delete) {
		setId(id);
		setName(name);
		setDelete(delete);
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

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

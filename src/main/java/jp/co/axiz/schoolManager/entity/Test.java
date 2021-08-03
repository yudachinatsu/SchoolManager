package jp.co.axiz.schoolManager.entity;

public class Test {
	//フィード
	private Integer id;
	private String name;
	private Boolean delete;

	//コンストラクタ
	public Test() {

	}

	public Test(Integer testId, String testName, Boolean delete) {
		setId(id);
		setName(name);
		setDelete(delete);
	}

	//getter,setter

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

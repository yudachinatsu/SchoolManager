package jp.co.axiz.schoolManager.entity;

public class Teacher {

	private Integer id;
	private String name;
	private String loginId;
	private String password;

	public Teacher() {

	}

	public Teacher(Integer _id, String _name, String _loginId, String _password) {
		setId(_id);
		setName(_name);
		setLoginId(_loginId);
		setPassword(_password);
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

	public String getLoginId() {
		return loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

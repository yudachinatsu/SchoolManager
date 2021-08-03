package jp.co.axiz.schoolManager.entity;

public class DatePulldown {

	private Integer Number;
	private String Text;

	public DatePulldown(Integer Number, String Text) {
		setNumber(Number);
		setText(Text);
	}

	public Integer getNumber() {
		return Number;
	}

	public void setNumber(Integer number) {
		Number = number;
	}

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}

}

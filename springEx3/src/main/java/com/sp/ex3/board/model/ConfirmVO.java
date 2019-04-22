package com.sp.ex3.board.model;

public class ConfirmVO {

	private int no;
	private String password;

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "ConfirmVO [no=" + no + ", password=" + password + "]";
	}

}

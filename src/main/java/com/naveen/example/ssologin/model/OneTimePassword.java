package com.naveen.example.ssologin.model;

public class OneTimePassword {
	private int oneTimePassword;
	private String target;
	private String userName;
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getOneTimePassword() {
		return oneTimePassword;
	}

	public void setOneTimePassword(int oneTimePassword) {
		this.oneTimePassword = oneTimePassword;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}

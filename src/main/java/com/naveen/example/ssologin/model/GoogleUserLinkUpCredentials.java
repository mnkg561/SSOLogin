package com.naveen.example.ssologin.model;

public class GoogleUserLinkUpCredentials {
	private String userId;
	private String userPassword;
	private String destination;
	private String googleSubjectId;
	private String googleEmailid;
	
	
	public String getGoogleSubjectId() {
		return googleSubjectId;
	}
	public void setGoogleSubjectId(String googleSubjectId) {
		this.googleSubjectId = googleSubjectId;
	}
	public String getGoogleEmailid() {
		return googleEmailid;
	}
	public void setGoogleEmailid(String googleEmailid) {
		this.googleEmailid = googleEmailid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
}

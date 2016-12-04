package com.naveen.example.ssologin.model;

import org.springframework.stereotype.Component;

@Component
public class NewPolicy {

	private String path;

	private String contextRoot;
	
	public String getContextRoot() {
		return contextRoot;
	}
	public void setContextRoot(String contextRoot) {
		this.contextRoot = contextRoot;
	}
	
	private boolean thisProtected;
	public boolean getThisProtected() {
		return thisProtected;
	}
	public void setThisProtected(boolean thisProtected) {
		this.thisProtected = thisProtected;
	}
	private String protectedBy;
	private String groupName;
	private int sessionLevel;
	private boolean mfaEnabled;
	public boolean isMfaEnabled() {
		return mfaEnabled;
	}
	public void setMfaEnabled(boolean mfaEnabled) {
		this.mfaEnabled = mfaEnabled;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	public String getProtectedBy() {
		return protectedBy;
	}
	public void setProtectedBy(String protectedBy) {
		this.protectedBy = protectedBy;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public int getSessionLevel() {
		return sessionLevel;
	}
	public void setSessionLevel(int sessionLevel) {
		this.sessionLevel = sessionLevel;
	}
}

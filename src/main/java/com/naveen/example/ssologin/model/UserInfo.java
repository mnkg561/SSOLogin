package com.naveen.example.ssologin.model;

import java.io.Serializable;

public class UserInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String distinguishedName;
	private String cn;
	private String givenName;
	private String memberOf;
	private String sAMAccountName;
	private String mobile;
	private String objectSid;
	private String userPrincipalName;
	
	public String getUserPrincipalName() {
		return userPrincipalName;
	}
	public void setUserPrincipalName(String userPrincipalName) {
		this.userPrincipalName = userPrincipalName;
	}
	public String getsAMAccountName() {
		return sAMAccountName;
	}
	public void setsAMAccountName(String sAMAccountName) {
		this.sAMAccountName = sAMAccountName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getObjectSid() {
		return objectSid;
	}
	public void setObjectSid(String objectSid) {
		this.objectSid = objectSid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getDistinguishedName() {
		return distinguishedName;
	}
	public void setDistinguishedName(String distinguishedName) {
		this.distinguishedName = distinguishedName;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public String getMemberOf() {
		return memberOf;
	}
	public void setMemberOf(String memberOf) {
		this.memberOf = memberOf;
	}
	

}

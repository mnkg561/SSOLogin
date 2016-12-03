package com.naveen.example.ssologin.model;

import java.util.Enumeration;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;

public class UserAttributeMapper implements AttributesMapper<UserInfo> {

	@Override
	public UserInfo mapFromAttributes(Attributes attributes) throws NamingException {

		UserInfo userInfo;

		if (attributes == null) {
			return null;
		}
		userInfo = new UserInfo();
		userInfo.setCn(attributes.get("cn").get().toString());
		userInfo.setDistinguishedName(attributes.get("distinguishedName").get().toString());
		userInfo.setGivenName(attributes.get("givenName").get().toString());
		userInfo.setsAMAccountName(attributes.get("sAMAccountName").get().toString());
		userInfo.setObjectSid(attributes.get("objectSid").get().toString());
		userInfo.setUserPrincipalName(attributes.get("userPrincipalName").get().toString());

		if (attributes.get("memberOf") != null) {
			@SuppressWarnings("unchecked")
			Enumeration<String> enumeration = (Enumeration<String>) attributes.get("memberOf").getAll();
			StringBuilder sb = new StringBuilder();
			while (enumeration.hasMoreElements()) {
				sb.append(enumeration.nextElement()).append(";");
			}
			userInfo.setMemberOf(sb.toString());
		}

		if (attributes.get("mobile") != null) {
			userInfo.setMobile(attributes.get("mobile").get().toString());
		}

		return userInfo;

	}

}

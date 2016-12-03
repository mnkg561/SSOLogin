package com.naveen.example.ssologin.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.naveen.example.ssologin.model.UserInfo;
import com.naveen.example.ssologin.utility.EncryptionDecryptionUtility;

@Repository
public class FormFreeCookieDaoImpl {
	@Autowired
	EncryptionDecryptionUtility encryptionDecryptionUtility;

	private static final String SECRET = "27e799e85ebf430695cd915a29065805";
	private static final String AMPERSAND_OPERATOR = "&";

	public String generateFormFreeCookie(UserInfo userInfo, String target) {
		long unixPresentTime = System.currentTimeMillis();
		String[] targetTokens = target.split("/");
		String vip = targetTokens[2];
		String timeStamp = "" + unixPresentTime;
		StringBuilder constructBaseString = new StringBuilder();
		constructBaseString.append("givenName=" + userInfo.getGivenName()).append(AMPERSAND_OPERATOR)
				.append("sAMAccountName=" + userInfo.getsAMAccountName()).append(AMPERSAND_OPERATOR)
				.append("target=" + target).append(AMPERSAND_OPERATOR).append("createdTime=" + timeStamp)
				.append(AMPERSAND_OPERATOR).append("webagent_host=" + vip).append(AMPERSAND_OPERATOR)
				.append("distinguishedName=" + userInfo.getDistinguishedName()).append(AMPERSAND_OPERATOR)
				.append("userPrincipalName=" + userInfo.getUserPrincipalName()).append(AMPERSAND_OPERATOR)
				.append("cn=" + userInfo.getCn());
		String formFreeCookie = EncryptionDecryptionUtility.encrypt(constructBaseString.toString(), SECRET);
		return formFreeCookie;
	}

}

package com.naveen.example.ssologin.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.naveen.example.ssologin.data.AuthorizationDaoImpl;
import com.naveen.example.ssologin.data.NewPolicyDaoImpl;
import com.naveen.example.ssologin.data.UserSearchDaoImpl;
import com.naveen.example.ssologin.model.AuthorizationDecision;
import com.naveen.example.ssologin.model.NewPolicy;
import com.naveen.example.ssologin.model.UserInfo;

@Controller
public class AuthorizationController {

	@Autowired
	NewPolicyDaoImpl newPolicyDaoImpl;

	@Autowired
	AuthorizationDaoImpl authorizationDaoImpl;

	@Autowired
	UserSearchDaoImpl userSearchDaoImpl;

	private final static String SESSIONNAME = "NAVEENSESSIONID";
	final static Logger logger = Logger.getLogger(AuthorizationController.class);

	@RequestMapping(value = "/isAuthorized", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody AuthorizationDecision isAuthorized(@CookieValue("NAVEENSESSIONID")String sessionID, String path, HttpServletRequest request,
			HttpServletResponse response) {
		// Includes 4 steps
		// 1. Retrieve the policy for this path
		// 2. Validate SMSESSION and retrieve CN or userID
		// 3. Retrieve LDAP Attributes for this userID
		// 4. Compare policy groups and user groups and provide decision

		// Step 1
		//String path = "/MyApp1/sample/protected/page1";
		logger.info("Inside SSO policy server isAuthorized API module");
		logger.info("Retrieving policy information for requested target resource");
		NewPolicy protectedPolicy = newPolicyDaoImpl.retrievePolicy(path);
		
		boolean mfaEnabled = protectedPolicy.isMfaEnabled();
		String mfaValidation = "notdone";

		// Step 2
		logger.info("Validating session cookie");
		Map<String, String> sessionObject = authorizationDaoImpl.validateAndCreateSession(sessionID, SESSIONNAME, mfaEnabled, mfaValidation);

		if (sessionObject != null && sessionObject.get("validation").equalsIgnoreCase("success")) {
			logger.info("Session cookie validation is successful");
			
			mfaValidation = sessionObject.get("mfaValidation");
			
			Cookie cookie = new Cookie(SESSIONNAME, sessionObject.get("sessionID"));
			cookie.setDomain(".naveen.com");
			cookie.setMaxAge(600);
			cookie.setPath("/");
			response.addCookie(cookie);

			logger.info("Updated session is added back to browser");
		}
		// Step 3
		logger.info("Retrieving user attributes from ldap");
		UserInfo userInfo = userSearchDaoImpl.ldapSearch(sessionObject.get("userId"));
		Cookie cookie = new Cookie("userId", sessionObject.get("userId"));
		cookie.setDomain(".naveen.com");
		cookie.setMaxAge(6000);
		cookie.setPath("/");
		response.addCookie(cookie);


		// Step 4
		logger.info("Validating user authorization based on policy and mfaEnabled tags");
		AuthorizationDecision authorizationDecision = authorizationDaoImpl.isAuthorized(protectedPolicy, userInfo, mfaEnabled, mfaValidation);
		return authorizationDecision;
	}
}

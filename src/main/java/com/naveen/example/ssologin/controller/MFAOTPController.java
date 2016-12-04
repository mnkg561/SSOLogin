package com.naveen.example.ssologin.controller;

import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.naveen.example.ssologin.data.AuthorizationDaoImpl;
import com.naveen.example.ssologin.data.NewPolicyDao;
import com.naveen.example.ssologin.data.SalesforceConnectorDao;
import com.naveen.example.ssologin.model.OneTimePassword;

@Controller
@RequestMapping(value = "/openotp")
public class MFAOTPController {
	private final static String SESSIONNAME = "NAVEENSESSIONID";
	@Autowired
	AuthorizationDaoImpl authorizationDaoImpl;

	@Autowired
	NewPolicyDao newPolicyDaoImpl;

	@Autowired
	SalesforceConnectorDao salesforceConnectorDaoImpl;
	final static Logger logger = Logger.getLogger(MFAOTPController.class);

	/*
	 * This method is to send OTP to user mobile number and also to display OTP
	 * page
	 * 
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String getOTPPage(Model model, @CookieValue("userId") String userName,
			@RequestParam("target") String target) {
		Random randomNumber = new Random();
		int otp = (int) (100000 + randomNumber.nextFloat() * 900000);
		logger.debug("Inserting OTp into database");
		boolean result = newPolicyDaoImpl.insertOTP(userName, otp);
		if (result) {
			logger.info("Calling Salesforce to send an OTP " + otp);
			salesforceConnectorDaoImpl.sendMessage(otp);
		}
		OneTimePassword oneTimePassword = new OneTimePassword();
		oneTimePassword.setUserName(userName);
		oneTimePassword.setTarget(target);
		model.addAttribute("oneTimePassword", oneTimePassword);
		logger.info("Redirected to One Time Passwor page");
		return "onetimepasswordPage";
	}

	/*
	 * This method is to validate OTP against OTP_Store and create a new session
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String validateOTP(@CookieValue(value = "NAVEENSESSIONID", required = false) String sessionID,
			@CookieValue(value = "formFreeCredCookie", required = false) String formCookie,
			@ModelAttribute("oneTimePassword") OneTimePassword oneTimePassword, HttpServletRequest request,
			HttpServletResponse response) {
		String sessionName = SESSIONNAME;
		if (sessionID == null) {
			logger.info("Session cookie is not available and may be social networking login");
			sessionID = formCookie;
			sessionName = "formFreeCredCookie";
		}
		String redirectUrl = "redirect:" + request.getRequestURL()+"?target="+oneTimePassword.getTarget();
		int otp = oneTimePassword.getOneTimePassword();
		String userName = oneTimePassword.getUserName();
		logger.info("Valdate the OTP entered against OTP Store, OTP= " + otp);
		boolean validation = newPolicyDaoImpl.validateOTP(userName, otp);
		String mfaValidation = "notdone";
		if (validation) {
			logger.info("One Time password validation is successful");
			mfaValidation = "success";

			Map<String, String> sessionObject = authorizationDaoImpl.validateAndCreateSession(sessionID, sessionName,
					true, mfaValidation);

			if (sessionObject != null && sessionObject.get("validation").equalsIgnoreCase("success")) {

				mfaValidation = sessionObject.get("mfaValidation");

				Cookie cookie = new Cookie(SESSIONNAME, sessionObject.get("sessionID"));
				cookie.setDomain(".naveen.com");
				cookie.setMaxAge(600);
				cookie.setPath("/");
				response.addCookie(cookie);

				logger.info("Updated session is added back to browser");
				logger.info(" MFA Validation is successful and redirecting to target page");
				redirectUrl = "redirect:" + oneTimePassword.getTarget();
			}
		}
		return redirectUrl;

	}
}

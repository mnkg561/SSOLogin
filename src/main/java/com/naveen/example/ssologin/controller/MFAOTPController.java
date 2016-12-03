package com.naveen.example.ssologin.controller;

import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	@RequestMapping(method = RequestMethod.GET)
	public String getOTPPage(Model model, @CookieValue("userId") String userName,
			@RequestParam("target") String target) {
		Random randomNumber = new Random();
		int otp = (int) (100000 + randomNumber.nextFloat() * 900000);
		boolean result = newPolicyDaoImpl.insertOTP(userName, otp);
		if(result){
			salesforceConnectorDaoImpl.sendMessage(otp);
		}
		OneTimePassword oneTimePassword = new OneTimePassword();
		oneTimePassword.setUserName(userName);
		oneTimePassword.setTarget(target);
		model.addAttribute("oneTimePassword", oneTimePassword);
		return "onetimepasswordPage";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String validateOTP(@CookieValue(value="NAVEENSESSIONID", required=false) String sessionID, @CookieValue(value="formFreeCredCookie", required=false) String formCookie,
			@ModelAttribute("oneTimePassword") OneTimePassword oneTimePassword, HttpServletRequest request,
			HttpServletResponse response) {
		String sessionName = SESSIONNAME;
		if(sessionID == null){
			sessionID = formCookie;
			sessionName = "formFreeCredCookie";
		}
		String redirectUrl = "redirect:" + request.getRequestURL();
		int otp = oneTimePassword.getOneTimePassword();
		String userName = oneTimePassword.getUserName();
		boolean validation = newPolicyDaoImpl.validateOTP(userName, otp);
		String mfaValidation = "notdone";
		if (validation) {
			mfaValidation="success";
			Map<String, String> sessionObject = authorizationDaoImpl.validateAndCreateSession(sessionID, sessionName,
					true, mfaValidation);

			if (sessionObject != null && sessionObject.get("validation").equalsIgnoreCase("success")) {
				System.out.println(" SUCCESSFUL ");

				mfaValidation = sessionObject.get("mfaValidation");

				Cookie cookie = new Cookie(SESSIONNAME, sessionObject.get("sessionID"));
				cookie.setDomain(".naveen.com");
				cookie.setMaxAge(600);
				cookie.setPath("/");
				response.addCookie(cookie);

				System.out.println("Updated session is added back to browser");
				System.out.println(" MFA Validation is successful ");
				redirectUrl = "redirect:" + oneTimePassword.getTarget();
			}
		}
		return redirectUrl;

	}
}

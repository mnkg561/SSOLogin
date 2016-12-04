package com.naveen.example.ssologin.controller;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naveen.example.ssologin.data.FormFreeCookieDaoImpl;
import com.naveen.example.ssologin.data.NewPolicyDao;
import com.naveen.example.ssologin.data.UserSearchDaoImpl;
import com.naveen.example.ssologin.model.GoogleUserLinkUpCredentials;
import com.naveen.example.ssologin.model.UserCredentials;
import com.naveen.example.ssologin.model.UserInfo;

@Controller
@RequestMapping(value = "/linkme")
public class SocialNetworkLinkupController {

	@Autowired
	UserSearchDaoImpl userSearchDaoImpl;

	@Autowired
	FormFreeCookieDaoImpl formFreeCookieDaoImpl;

	@Autowired
	NewPolicyDao newPolicyDaoImpl;

	final static Logger logger = Logger.getLogger(SocialNetworkLinkupController.class);

	/*
	 * This method is to redirect the user's browser to login link up page and
	 * this is only one time activity in user's life cycle
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String getLoginPage(Model model,
			@ModelAttribute("googleUserCredentials") GoogleUserLinkUpCredentials googleUserCredentials) {

		logger.info("User's google email " + googleUserCredentials.getGoogleEmailid());
		model.addAttribute("googleUserCredentials", googleUserCredentials);
		logger.info("Redirecting to link up login form");
		return "linkupLoginForm";
	}

	/*
	 * This method retrieves the model attribute and inserts google user into
	 * database
	 * 
	 */

	@RequestMapping(method = RequestMethod.POST)
	public String linkUserAccounts(Model model,
			@ModelAttribute("googleUserCredentials") GoogleUserLinkUpCredentials googleUserCredentials,
			HttpServletRequest request, HttpServletResponse resp) throws ServletException, InterruptedException {
		try {
			logger.info("Validate user credentials");
			UserInfo userInfo = userSearchDaoImpl.ldapSearch(googleUserCredentials.getUserId());
			logger.info("Inserting Google user details along with user intranet user id");
			boolean result = newPolicyDaoImpl.insertGoogleUser(googleUserCredentials.getGoogleSubjectId(),
					googleUserCredentials.getGoogleEmailid(), googleUserCredentials.getUserId());
			String cookieString = formFreeCookieDaoImpl.generateFormFreeCookie(userInfo,
					googleUserCredentials.getDestination());
			Cookie cookie = new Cookie("formFreeCredCookie", cookieString);
			cookie.setDomain(".naveen.com");
			cookie.setMaxAge(300);
			cookie.setPath("/");
			resp.addCookie(cookie);
			logger.info("Redirecting user to target url");
			return "redirect:" + googleUserCredentials.getDestination();
		} catch (NullPointerException exc) {
			return "redirect:" + request.getRequestURL() + "?target=" + googleUserCredentials.getDestination();
		}

	}

}

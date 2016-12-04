package com.naveen.example.ssologin.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/logout")
public class LogoutController {

	private final static String SESSIONNAME = "NAVEENSESSIONID";
	private final static String FORMFREECREDCOOKIE = "formFreeCredCookie";
	private final static String USERCOOKIENAME = "userId";
	final static Logger logger = Logger.getLogger(LogoutController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String logoutAllTheSessions(Model model, HttpServletRequest request, HttpServletResponse response) {
		logger.info("Inside logout controller");
		Cookie sessionCookie = new Cookie(SESSIONNAME, "LOGGEDOUT");
		sessionCookie.setDomain(".naveen.com");
		sessionCookie.setMaxAge(0);
		sessionCookie.setPath("/");
		response.addCookie(sessionCookie);

		Cookie formCookie = new Cookie(FORMFREECREDCOOKIE, "LOGGEDOUT");
		formCookie.setDomain(".naveen.com");
		formCookie.setMaxAge(0);
		formCookie.setPath("/");
		response.addCookie(formCookie);

		Cookie userCookie = new Cookie(USERCOOKIENAME, "LOGGEDOUT");
		userCookie.setDomain(".naveen.com");
		userCookie.setMaxAge(0);
		userCookie.setPath("/");
		response.addCookie(userCookie);

		logger.info("All the cookies are deleted and redirecting to logout page");
		return "logoutPage";
	}

}

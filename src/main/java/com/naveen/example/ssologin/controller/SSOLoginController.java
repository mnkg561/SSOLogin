package com.naveen.example.ssologin.controller;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.naveen.example.ssologin.data.FormFreeCookieDaoImpl;
import com.naveen.example.ssologin.data.UserSearchDaoImpl;
import com.naveen.example.ssologin.model.UserCredentials;
import com.naveen.example.ssologin.model.UserInfo;

@Controller
public class SSOLoginController {

	@Autowired
	UserSearchDaoImpl userSearchDaoImpl;

	@Autowired
	FormFreeCookieDaoImpl formFreeCookieDaoImpl;

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model, @RequestParam("target") String target) {
		UserCredentials userCredentials = new UserCredentials();
		userCredentials.setTarget(target);
		model.addAttribute("userCredentials", userCredentials);
		return "loginForm";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String getUserInfo(Model model, @ModelAttribute("userCredentials") UserCredentials userCredentials,
			HttpServletRequest request, HttpServletResponse resp) throws ServletException, InterruptedException {
		try {
			UserInfo userInfo = userSearchDaoImpl.ldapSearch(userCredentials.getUserName());

			String cookieString = formFreeCookieDaoImpl.generateFormFreeCookie(userInfo, userCredentials.getTarget());
			Cookie cookie = new Cookie("formFreeCredCookie", cookieString);
			cookie.setDomain(".naveen.com");
			cookie.setMaxAge(300);
			cookie.setPath("/");
			resp.addCookie(cookie);
			return "redirect:" + userCredentials.getTarget();
		} catch (NullPointerException exc) {
			return "redirect:" + request.getRequestURL()+"?target="+userCredentials.getTarget();
		}

	}
}

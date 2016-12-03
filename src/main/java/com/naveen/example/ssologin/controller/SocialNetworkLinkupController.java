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

	@RequestMapping(method = RequestMethod.GET)
	public String getLoginPage(Model model, @ModelAttribute("googleUserCredentials") GoogleUserLinkUpCredentials googleUserCredentials) {
		System.out.println(" Inside link accounts controller");
		System.out.println("email " + googleUserCredentials.getGoogleEmailid());
		model.addAttribute("googleUserCredentials", googleUserCredentials);
		return "linkupLoginForm";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String linkUserAccounts(Model model, @ModelAttribute("googleUserCredentials") GoogleUserLinkUpCredentials googleUserCredentials,
			HttpServletRequest request, HttpServletResponse resp) throws ServletException, InterruptedException {
		try {
			UserInfo userInfo = userSearchDaoImpl.ldapSearch(googleUserCredentials.getUserId());
			System.out.println("im here "+googleUserCredentials.getGoogleSubjectId()+" "+googleUserCredentials.getGoogleEmailid()+ " "+googleUserCredentials.getDestination());
			boolean result = newPolicyDaoImpl.insertGoogleUser(googleUserCredentials.getGoogleSubjectId(),
					googleUserCredentials.getGoogleEmailid(), googleUserCredentials.getUserId());
			String cookieString = formFreeCookieDaoImpl.generateFormFreeCookie(userInfo, googleUserCredentials.getDestination());
			Cookie cookie = new Cookie("formFreeCredCookie", cookieString);
			cookie.setDomain(".naveen.com");
			cookie.setMaxAge(300);
			cookie.setPath("/");
			resp.addCookie(cookie);
			return "redirect:" + googleUserCredentials.getDestination();
		} catch (NullPointerException exc) {
			return "redirect:" + request.getRequestURL() + "?target=" + googleUserCredentials.getDestination();
		}

	}

}

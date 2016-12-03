package com.naveen.example.ssologin.controller;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naveen.example.ssologin.data.FormFreeCookieDaoImpl;
import com.naveen.example.ssologin.data.GoogleAccessTokenDao;
import com.naveen.example.ssologin.data.NewPolicyDao;
import com.naveen.example.ssologin.data.UserSearchDaoImpl;
import com.naveen.example.ssologin.model.GoogleOAuthToken;
import com.naveen.example.ssologin.model.GoogleUserLinkUpCredentials;
import com.naveen.example.ssologin.model.UserCredentials;
import com.naveen.example.ssologin.model.UserInfo;

@Controller
public class RedirectUrlController {

	@Autowired
	GoogleAccessTokenDao googleAccessTokenDaoImpl;

	@Autowired
	GoogleOAuthToken googleOAuthToken;

	@Autowired
	NewPolicyDao newPolicyDaoImpl;

	@Autowired
	UserSearchDaoImpl userSearchDaoImpl;

	@Autowired
	FormFreeCookieDaoImpl formFreeCookieDaoImpl;

	@RequestMapping(value = "/google/redirectUrl", method = RequestMethod.GET)
	public String authorizationCodeHandler(Model model, RedirectAttributes redirectAttributes, @RequestParam("code") String code,
			@RequestParam("state") String target, HttpServletRequest request, HttpServletResponse response) {
		String ssoLoginUrl = "http://ssologin.naveen.com:8080/SSOLogin/login";
		String targetURL = "redirect:" + ssoLoginUrl + "?target=" + target;
		googleOAuthToken = googleAccessTokenDaoImpl.exchangeCodeForAccessToken(code);
		System.out.println("Google ID Token " + googleOAuthToken.getId_token());
		String[] tokens = googleOAuthToken.getId_token().split("\\.");
		String userJsonBase64EncodedString = tokens[1];
		String userJsonString = new String(Base64.decodeBase64(userJsonBase64EncodedString.getBytes()));
		System.out.println("UserInfo from Google Id Token " + userJsonString);
		JSONParser parser = new JSONParser();
		JSONObject jsonObject;
		try {
			jsonObject = (JSONObject) parser.parse(userJsonString);
			String googleSubjectId = (String) jsonObject.get("sub");
			String googleEmailId = (String) jsonObject.get("email");
			System.out.println(" Successfully decoded " + googleSubjectId + " " + googleEmailId);
			Map<String, String> googleUser = newPolicyDaoImpl.searchGoogleUser(googleSubjectId);
			if (googleUser.get("isUserExist").equalsIgnoreCase("true")) {
				UserInfo userInfo = userSearchDaoImpl.ldapSearch(googleUser.get("userName"));

				String cookieString = formFreeCookieDaoImpl.generateFormFreeCookie(userInfo, target);
				Cookie formCookie = new Cookie("formFreeCredCookie", cookieString);
				formCookie.setDomain(".naveen.com");
				formCookie.setMaxAge(300);
				formCookie.setPath("/");
				response.addCookie(formCookie);
				
				Cookie userCookie = new Cookie("userId", googleUser.get("userName"));
				userCookie.setDomain(".naveen.com");
				userCookie.setMaxAge(600);
				userCookie.setPath("/");
				response.addCookie(userCookie);
				
				targetURL = "redirect:/openotp?target=" + target;
			} else {
				GoogleUserLinkUpCredentials googleUserCredentials = new GoogleUserLinkUpCredentials();
				googleUserCredentials.setDestination(target);;
				googleUserCredentials.setGoogleSubjectId(googleSubjectId);
				googleUserCredentials.setGoogleEmailid(googleEmailId);
				//model.addAttribute("userCredentials2", userCredentials);
				//return "linkupLoginForm";
				redirectAttributes.addFlashAttribute("googleUserCredentials", googleUserCredentials);
				return "redirect:/linkme?target="+target;
			}

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return targetURL;
	}

	
}

package com.naveen.example.ssologin.controller;

import org.apache.http.entity.ContentType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.naveen.example.ssologin.model.OAuthToken;

@Controller
@RequestMapping("/unauthorized")
public class UnauthorizedController {
	
	@RequestMapping(method = RequestMethod.GET)
	public String getUnAuthorizedPage() {
		return "unauthorizedPage";
	}
}
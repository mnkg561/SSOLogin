package com.naveen.example.ssologin.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/unauthorized")
public class UnauthorizedController {
	final static Logger logger = Logger.getLogger(UnauthorizedController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String getUnAuthorizedPage() {
		logger.info("redirecting to Unauthorized page");
		return "unauthorizedPage";
	}
}
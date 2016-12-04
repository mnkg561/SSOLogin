package com.naveen.example.ssologin.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naveen.example.ssologin.data.NewPolicyDao;
import com.naveen.example.ssologin.model.NewPolicy;

@Controller
@RequestMapping(value = "/create/policy")
public class PolicyCreatorController {
	final static Logger logger = Logger.getLogger(PolicyCreatorController.class);

	@Autowired
	NewPolicyDao newPolicyDaoImpl;

	/*
	 * This method is to redirect to create policy page
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String createNewPolicyPage(Model model) throws Exception {

		model.addAttribute("newPolicy", new NewPolicy());
		logger.debug("I'm inside Polciy creation controller");
		logger.info("Redirecting to create policy page");
		return "createPolicy";
	}

	/*
	 * This method is retrieve the model attribute from create policy page and
	 * insert into policy_store
	 */
	@RequestMapping(method = RequestMethod.POST)
	public String createNewPolicy( @ModelAttribute("newPolicy") NewPolicy newPolicy, Model model) {
		System.out.println("NewPolicy Variables " + newPolicy);
		Boolean result = newPolicyDaoImpl.createNewPolicy(newPolicy);
		if (result)
			logger.info("New Policy has been created successfully");
		return "createPolicy";
	}
}

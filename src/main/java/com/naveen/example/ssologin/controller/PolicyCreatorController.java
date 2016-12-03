package com.naveen.example.ssologin.controller;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naveen.example.ssologin.data.NewPolicyDao;
import com.naveen.example.ssologin.model.NewPolicy;

@Controller
@RequestMapping(value="/create/policy")
public class PolicyCreatorController {
	@Autowired
	NewPolicyDao newPolicyDaoImpl;
	Log logger = LogFactory.getLog(PolicyCreatorController.class.getName());
	@RequestMapping(method=RequestMethod.GET)
	public String createNewPolicyPage(Model model) throws Exception{
		
		model.addAttribute("newPolicy", new NewPolicy());
		logger.info("I'm inside");
		System.out.println("I got the request");
		return "createPolicy";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String createNewPolicy(@Valid @ModelAttribute("newPolicy") NewPolicy newPolicy, Model model){
		System.out.println("NewPolicy Variables "+newPolicy);
		Boolean result = newPolicyDaoImpl.createNewPolicy(newPolicy);
		if(result)
			System.out.println("New Policy has been created successfully");
		return "createPolicy";
	}
}

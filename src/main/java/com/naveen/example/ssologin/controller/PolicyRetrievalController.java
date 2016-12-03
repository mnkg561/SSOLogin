package com.naveen.example.ssologin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.naveen.example.ssologin.data.NewPolicyDao;
import com.naveen.example.ssologin.model.NewPolicy;

@Controller
public class PolicyRetrievalController {
	@Autowired
	NewPolicyDao newPolicyDaoImpl;

	@SuppressWarnings("null")
	@RequestMapping(value = "/isProtected", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody NewPolicy retrievePolicy(String path) {
		System.out.println("Path in the request " + path);

		NewPolicy policy = newPolicyDaoImpl.retrievePolicy(path);

		return policy;
	}

}

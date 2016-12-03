package com.naveen.example.ssologin.data;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.naveen.example.ssologin.model.NewPolicy;

@Service
public interface NewPolicyDao {

	public Boolean createNewPolicy(NewPolicy newPolicy);

	public NewPolicy retrievePolicy(String path);

	public boolean insertOTP(String userName, int otp);

	public boolean validateOTP(String userName, int otp);
	
	public Map<String, String> searchGoogleUser(String googleSubjectId);
	
	public boolean insertGoogleUser(String googleSubjectId, String googleEmailId, String userName);

}

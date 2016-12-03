package com.naveen.example.ssologin.data;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.naveen.example.ssologin.model.NewPolicy;

@Repository
public class NewPolicyDaoImpl implements NewPolicyDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Boolean createNewPolicy(NewPolicy newPolicy) {
		Boolean result = false;
		String sql = "insert into policy_store(path, thisProtected, groupName, sessionLevel, protectedBy, contextRoot, mfaEnabled) values (?, ?, ?,?, ?, ?,?)";
		int numberOfRows = jdbcTemplate.update(sql,
				new Object[] { newPolicy.getPath(), newPolicy.getThisProtected(), newPolicy.getGroupName(),
						newPolicy.getSessionLevel(), newPolicy.getProtectedBy(), newPolicy.getContextRoot(),
						newPolicy.isMfaEnabled() });
		if (numberOfRows == 1) {
			result = true;
		}
		return result;
	}

	@Override
	public NewPolicy retrievePolicy(String path) {
		NewPolicy protectedPolicy = new NewPolicy();
		protectedPolicy.setThisProtected(false);
		String[] stringTokens = path.split("/");
		String contextRoot = "/" + stringTokens[1];
		String sql = "select * from policy_store where contextRoot = ?";
		List<NewPolicy> policyList = (List<NewPolicy>) jdbcTemplate.query(sql, new Object[] { contextRoot },
				new BeanPropertyRowMapper<NewPolicy>(NewPolicy.class));
		Iterator<NewPolicy> iterator = policyList.iterator();
		while (iterator.hasNext()) {
			NewPolicy policy = iterator.next();
			String registeredPath = policy.getPath();
			if (policy.getThisProtected()) {
				if (registeredPath.contains("*")) {
					String[] tokens = registeredPath.split("*");
					System.out.println("token[] " + tokens[0]);
					if (path.contains(tokens[0])) {
						protectedPolicy = policy;
						break;
					}
				} else {
					if (path.equalsIgnoreCase(registeredPath)) {
						protectedPolicy = policy;
						break;
					}
				}
			}

		}
		return protectedPolicy;
	}

	@Override
	public boolean insertOTP(String userName, int otp) {
		boolean result = false;
		String sql = "insert into otp_store(userName, otp) values (?, ?)";
		int numberOfRows = jdbcTemplate.update(sql, new Object[] { userName, otp });
		if (numberOfRows == 1) {
			result = true;
		}
		return result;
	}

	@Override
	public boolean validateOTP(String userName, int otp) {
		boolean result = false;
		String sql = "select count(userName) from otp_store where otp = ?";
		int numberOfRows = jdbcTemplate.queryForInt(sql, new Object[] { otp });
		if (numberOfRows == 1) {
			result = true;
		}
		return result;
	}

	@Override
	public Map<String, String> searchGoogleUser(String googleSubjectId) {
		Map<String, String> googleUser = new LinkedHashMap<String, String>();
		googleUser.put("isUserExist", "false");
		// Search user_store table to see if user is existed with
		// googleSubjectId
		// If exist, return userName
		try {
			String sql = "select userName from user_store where googleSubjectId=?";
			String userName = jdbcTemplate.queryForObject(sql, new Object[] { googleSubjectId }, String.class);
			googleUser.put("isUserExist", "true");
			googleUser.put("userName", userName);
		} catch (EmptyResultDataAccessException exception) {
			System.out.println(" Empty result: that means no user existed");
		}

		return googleUser;
	}

	@Override
	public boolean insertGoogleUser(String googleSubjectId, String googleEmailId, String userName) {
		boolean result = false;
		String sql = "insert into user_store(userName, googleSubjectId, googleEmailId) values (?,?, ?)";
		int numberOfRows = jdbcTemplate.update(sql, new Object[] { userName, googleSubjectId, googleEmailId });
		if (numberOfRows == 1) {
			result = true;
		}
		return result;
	}
}

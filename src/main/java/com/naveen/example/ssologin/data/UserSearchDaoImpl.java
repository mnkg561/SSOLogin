package com.naveen.example.ssologin.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Repository;

import com.naveen.example.ssologin.model.UserAttributeMapper;
import com.naveen.example.ssologin.model.UserInfo;

@Repository
public class UserSearchDaoImpl {

	@Autowired
	LdapTemplate ldapTemplate;

	public UserInfo ldapSearch(String userName) {
		UserInfo userInfo;
		LdapQuery query = LdapQueryBuilder.query().base("ou=Global Users").where("objectclass").is("person").and("sAMAccountName")
				.is(userName);

		List<UserInfo> results = ldapTemplate.search(query, new UserAttributeMapper());

		if (results != null && !results.isEmpty()) {
			userInfo = results.get(0);
			System.out.println("Results " + results.get(0).getDistinguishedName());
		} else {
			userInfo = null;
		}
		return userInfo;
	}

}

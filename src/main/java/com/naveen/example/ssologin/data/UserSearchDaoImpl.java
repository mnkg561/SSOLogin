package com.naveen.example.ssologin.data;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ldap.CommunicationException;
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
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	final static Logger logger = Logger.getLogger(UserSearchDaoImpl.class);

	public UserInfo ldapSearch(String userName) {
		logger.info("Validating user credentials and retrieving attributesfrom LDAP");
		UserInfo userInfo;
		LdapQuery query = LdapQueryBuilder.query().base("ou=Global Users").where("objectclass").is("person")
				.and("sAMAccountName").is(userName);
		try {
			List<UserInfo> results = ldapTemplate.search(query, new UserAttributeMapper());

			if (results != null && !results.isEmpty()) {
				userInfo = results.get(0);
				logger.info("Results " + results.get(0).getDistinguishedName());
			} else {
				userInfo = null;
			}
		} catch (CommunicationException exc) {
			String sql = "select * from user_store where sAMAccountName = ?";
			userInfo = (UserInfo)jdbcTemplate.queryForObject(sql, new Object[]{userName}, new BeanPropertyRowMapper(UserInfo.class));
		}
		return userInfo;
	}

}

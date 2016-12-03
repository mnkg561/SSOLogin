package com.naveen.example.ssologin.data;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.naveen.example.ssologin.model.GoogleOAuthToken;
@Service
public class GooleAccessTokenDaoImpl implements GoogleAccessTokenDao {

	private static final String CLIENTID = "349455219287-nsba8isi6qnspmttjalgiet1vvprus0h.apps.googleusercontent.com";
	private static final String CLIENTSECRET = "Xj4QCIHz2cx3bQripbLFRm9R";
	private static final String GRANTTYPE = "authorization_code";
	private static final String REDIRECTURL = "http://ssologin.naveen.com:8080/SSOLogin/google/redirectUrl";

	@Override
	public GoogleOAuthToken exchangeCodeForAccessToken(String code) {
		System.out.println("Exchange Authorization Code for Access Token");
		String endPointURL = "https://www.googleapis.com/oauth2/v4/token";
		MultiValueMap<String, String> requestObject = new LinkedMultiValueMap<String, String>();
		requestObject.add("client_id", CLIENTID);
		requestObject.add("client_secret", CLIENTSECRET);
		requestObject.add("grant_type", GRANTTYPE);
		requestObject.add("code", code);
		requestObject.add("redirect_uri", REDIRECTURL);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> e = new HttpEntity<MultiValueMap<String, String>>(requestObject,
				headers);
		RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		GoogleOAuthToken googleOAuthToken = restTemplate.postForObject(endPointURL, e, GoogleOAuthToken.class);
		return googleOAuthToken;
	}

}

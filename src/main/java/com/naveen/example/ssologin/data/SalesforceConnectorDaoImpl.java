package com.naveen.example.ssologin.data;

import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.naveen.example.ssologin.model.OAuthToken;
import com.naveen.example.ssologin.model.SalesforceResponse;

@Service
public class SalesforceConnectorDaoImpl implements SalesforceConnectorDao {
	@Autowired
	OAuthToken oauthToken;
	
	@Autowired
	SalesforceResponse salesforceResponse;
/*
	@Override
	public boolean sendMessage(int otp) {
		
		// Step1.. Call exact target to retrieve access token
		// Step 2.. call exact target with access token to send message
		System.out.println(" Calling Salesforce to get an access token");
		boolean result=false;
		String oauthURL = "https://auth.exacttargetapis.com/v1/requestToken";
		//String endPointURL = "http://ssologin.naveen.com:8080/SSOLogin/unauthorized";
		JSONObject json = new JSONObject();
		json.put("clientId", "300qn3es8fvqsnr80qxjqb0f");
		json.put("clientSecret", "KR0x4clqLLbWZqR46fSv8oVQ");
		
			MultiValueMap<String, String> requestObject = new LinkedMultiValueMap<String, String>();
		requestObject.add("clientId", "300qn3es8fvqsnr80qxjqb0f");
		requestObject.add("clientSecret", "KR0x4clqLLbWZqR46fSv8oVQ");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity e = new HttpEntity(json, headers);
		RestTemplate restTemplate = new RestTemplate(
				new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault()));
		oauthToken = restTemplate.postForObject(oauthURL, e, OAuthToken.class);
		System.out.println(" OAuth token received " + oauthToken.getAccessToken());
		if(oauthToken.getAccessToken()!= null && !oauthToken.getAccessToken().isEmpty()){
			String smsAPIURL = "https://www.exacttargetapis.com/sms/v1/messageContact/MTo3ODow/send";
			json = new JSONObject();
			String[] mobileNumbers = new String[]{"14802806660"};
			json.put("mobileNumbers", mobileNumbers);
			json.put("Subscribe", true);
			json.put("ReSubscribe", true);
			json.put("keyword", "MGAlerts");
			json.put("Override", true);
			json.put("messageText", "please enter the following OTP code "+otp);
			System.out.println("JSON String "+json.toString());
			headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Authorization", "BEARER "+oauthToken.getAccessToken());
			e = new HttpEntity(json, headers);
			restTemplate = new RestTemplate(
					new HttpComponentsClientHttpRequestFactory());
			salesforceResponse = restTemplate.postForObject(smsAPIURL, e, SalesforceResponse.class);
			if(salesforceResponse.getTokenId()!= null) result = true;
		}
		return result;
	}
*/
	@Override
	public boolean sendMessage(int otp){
		System.out.println(" Sending SMS to user's registered mobile number");
		boolean result=false;
		String smsURL = "http://extws.moneygram.com/simple-sms/service/SalesforceService1607";
		String mobileNumber = "14802806660";
		String smsText = "Naveen's SSO App: Your one time password is "+otp;
		String xmlInput = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns0:SendSimpleSMSNotificationRequest xmlns:ns0=\"http://www.moneygram.com/SalesforceService1607\"><ns0:requestIdentifier>405613e5-3734-4a60-ae03-1c82b9a</ns0:requestIdentifier><ns0:messageAPIKey>MTo3ODow</ns0:messageAPIKey><ns0:NotificationRequest><ns0:sourceSystem>MGO</ns0:sourceSystem><ns0:notificationIdentifier/><ns0:createdDate>2016-11-11T12:37:14.971-06:00</ns0:createdDate><ns0:phoneNumber>"+mobileNumber+"</ns0:phoneNumber><ns0:smsContent><ns0:smsMessage>"+smsText+"</ns0:smsMessage></ns0:smsContent></ns0:NotificationRequest></ns0:SendSimpleSMSNotificationRequest>";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_XML);
		HttpEntity e = new HttpEntity(xmlInput, headers);
		RestTemplate restTemplate = new RestTemplate(
				new HttpComponentsClientHttpRequestFactory());
		HttpEntity<String> resp = restTemplate.postForEntity(smsURL, e, String.class);
		
		return true;

	}
}

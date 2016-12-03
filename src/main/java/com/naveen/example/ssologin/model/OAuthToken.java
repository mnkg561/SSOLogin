package com.naveen.example.ssologin.model;

import org.springframework.stereotype.Component;

@Component
public class OAuthToken {
	private String accessToken;
	private int expiresIn;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

	@Override
	public String toString() {
		return "OAuthToken [accessToken=" + accessToken + ", expiresIn=" + expiresIn + "]";
	}
}

package com.naveen.example.ssologin.data;

import com.naveen.example.ssologin.model.GoogleOAuthToken;

public interface GoogleAccessTokenDao {
	public GoogleOAuthToken exchangeCodeForAccessToken(String code);
}

package com.cuadra.microservices.oauth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.cuadra.microservices.commons.model.entity.User;
import com.cuadra.microservices.oauth.services.api.UserService;

@Component
public class AdditionalInfoToken implements TokenEnhancer {
	
	private UserService userService;
	
	@Autowired
	public AdditionalInfoToken(UserService userService) {
		this.userService = userService;
	}

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		User user = userService.findByUsername(authentication.getName());
		
		Map<String, Object> info = new HashMap<>();
		info.put("name", user.getName());
		info.put("lastName", user.getLastName());
		info.put("email", user.getEmail());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		return accessToken;
	}
	
	

}

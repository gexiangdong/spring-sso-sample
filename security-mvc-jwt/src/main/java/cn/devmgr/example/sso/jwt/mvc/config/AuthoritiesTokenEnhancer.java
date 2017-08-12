package cn.devmgr.example.sso.jwt.mvc.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

public class AuthoritiesTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		System.out.println("\r\n-------------\r\n\r\nAuthoritiesTokenEnhancer\r\n" + accessToken + "\r\n\r\n" + authentication + "\r\n");
        final Map<String, Object> additionalInfo = new HashMap<>();

//        additionalInfo.put("userId", su.getId());
//        additionalInfo.put("name", su.getName());
//        additionalInfo.put("email", su.getEmail());
//        additionalInfo.put("phone", su.getPhone());
//        additionalInfo.put("headImage", su.getHeadImage());
//        additionalInfo.put("authorities", authentication.getAuthorities());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
        
        return accessToken;

	}

}

package com.dev.security.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    //후처리.. 여기서 됨
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest = " + userRequest);
        // userRequest = org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest@4bf908a0
        // 구글로 부터 받은 userRequest 데이터
        System.out.println("userRequest.getAccessToken() = " + userRequest.getAccessToken());
        System.out.println("userRequest.getClientRegistration() = " + userRequest.getClientRegistration());
        //userRequest.getClientRegistration() 
        //= ClientRegistration{registrationId='google', clientId='23776500576-864st1iggg5774dmuc82shpglsli5d0p.apps.googleusercontent.com', clientSecret='i7MXE2YEaFYKuQfR3_EvqvS7', clientAuthenticationMethod=org.springframework.security.oauth2.core.ClientAuthenticationMethod@4fcef9d3, authorizationGrantType=org.springframework.security.oauth2.core.AuthorizationGrantType@5da5e9f3, redirectUri='{baseUrl}/{action}/oauth2/code/{registrationId}', scopes=[email, profile], providerDetails=org.springframework.security.oauth2.client.registration.ClientRegistration$ProviderDetails@65b1070, clientName='Google'}
        System.out.println("super.loadUser(userRequest) = " + super.loadUser(userRequest));

        System.out.println("-----------------------------------");
        //회원가입을 강제로 진행해볼 예정
        return super.loadUser(userRequest);
    }
}

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
        //userRequest
        //구글로그인 버튼 --> 구글로그인 창 --> 로그인 완료 --> code 리턴!
        //OAuth-client라이브러리가 받아주고 그걸로 --> AccessToken 요청!
        //--> AccessToken 받음. 여기가 바로 userRequest입니다.
        //--> userRequest정보로 Profile을 받아야지? --> loadUser함수 --> 회원 프로필

        OAuth2User oauth2User = super.loadUser(userRequest);

        return super.loadUser(userRequest);
        //ClientRegistration : 우리 서버의 기본 정보, 클라이언트 아이디, secret 등
        // --> registrationId로 어떤 OAuth로 로그인 했는지 확인 가능
        //AccessToken :
        //Attributes :
    }
}

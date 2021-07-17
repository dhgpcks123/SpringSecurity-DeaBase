package com.dev.security.config;

import com.dev.security.config.oauth.PrincipalOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity //스프링 시큐리티 필터가 스프링 필터 체인에 등록
//@EnableGlobalMethodSecurity(securedEnabled = true) // secured 어노테이션 활성화!
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) //preAuthorize 어노테이션 활성화 // PostAuthorized도 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    //해당 메서드의 리턴되는 오브젝트를 IoC로 등록해준다.
    @Bean
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); //
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
//        There was an unexpected error (type=Forbidden, status=403). 403은 권한이 없다는 말!
                .and()
                .formLogin().loginPage("/loginForm")
                .loginProcessingUrl("/login") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다. // password체크도..
                // 컨트롤러에 /login 안 만들어줘도 된다.
                .defaultSuccessUrl("/")
        .and()
                .oauth2Login()
                .loginPage("/loginForm") // 구글 로그인이 완료된 두의 후처리 필요!
        //구글 로그인이 완료된 뒤의 후처리가 필요함
        // 1. 코드 받기 ( 인증 )
        // 2. 액세스 토큰 ( 권한 ) -사용자 정보에 접근할 권한
        // 3. 사용자 프로필 정보를 가져오고
            // 4-1. 정보를 토대로 회원가입을 자동 시킴
            // 4-2. (이메일, 전화번호, 이름, 아이디) 쇼핑몰 -> (집 주소), 백화점몰 -> (등급)

            //Tip. 코드x, 액세스 토큰 + 사용자 프로필 정보 O
            .userInfoEndpoint()
            .userService(principalOauth2UserService)
        ;

    }


}

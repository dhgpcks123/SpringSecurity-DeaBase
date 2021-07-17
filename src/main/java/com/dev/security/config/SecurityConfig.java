package com.dev.security.config;

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
        ;

    }


}

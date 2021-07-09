package com.dev.security.auth;

import com.dev.security.model.User;
import com.dev.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessingUrl("/login");
// /login 요청이 오면 자동으로 userDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수 실행 (규칙)
@Service
public class PrincipalDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    // 시큐리티 session ( Authentication(내부 UserDetails) )
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //String username 이거 input에서 넘기는 username 값이 넘어와.. 이름 맞춰줘!
        System.out.println("username = " + username);
        User userEntity = userRepository.findByUsername(username);
        if(userEntity != null){
            return new PrincipalDetails(userEntity);
        }
        return null;
    }
}

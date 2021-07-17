package com.dev.security.auth;

// 시큐리티가 /login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료되면 (시큐리티 전용) session을 만들어준다. ( Security ContextHolder <- 세션정보 저장 )
// 이 세션에 들어갈 수 있는 오브젝트 정해져있음. => Authentication 타입 객체

// Authentication 안에 user 정보가 있어야 됨!
// user오브젝트 타입도! 정해져 있음 => UserDetails 타입 객체

// Security Session => Authentication => UserDetails(PrincipalDetails

import com.dev.security.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalDetails implements UserDetails {

    private User user; // 콤포지션

    public PrincipalDetails(User user) {
        this.user = user;
    }

    // 해당 User의 권한을 리턴하는 곳!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        user.getRole();
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠겼니?
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 기간이 지났니~?
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 니 계정이 활성화 되어 있니?
    @Override
    public boolean isEnabled() {
        // 우리 사이트!! 1년 동안 회원이 로그인을 안 하면 -> 휴먼 계정으로 하기로 함
        // user.getLoginDate();
        // 현재시간 - 로그인시간 => 1년 초과하면 return false;
        // 그러면 휴먼계정이다~ 페이지로 이동 시켜주고?
        return true;
    }
}

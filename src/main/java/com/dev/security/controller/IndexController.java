package com.dev.security.controller;

import com.dev.security.auth.PrincipalDetails;
import com.dev.security.model.User;
import com.dev.security.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;
    //이것도 더 좋은 방식으로 사용할 것!

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication,
                                          @AuthenticationPrincipal PrincipalDetails userDetails) {
        //@AuthenticationPrincipal을 통해 세션에 접근할 수 있따.
        //얘는 UserDetails타입을 가지고 있다.
        System.out.println("/test/login==============");
        System.out.println("authentication.get = " + authentication.getPrincipal());
        //==클래스 Casting 에러 발생//
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        //==//
        System.out.println("principalDetails = " + principalDetails.getUser());

//        System.out.println("userDetails = " + userDetails.getUsername());
        System.out.println("userDetails = " + userDetails.getUser());

        //getUser를 찾는 두 가지 방법...
        return "세선 저장정보 확인하기";
    }

    //구글 로그인 시에는 CastException 발생하지 않음. 반대로 그냥 로그인 시 CastException 발생함.
    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOAuthLogin(Authentication authentication,
                                          @AuthenticationPrincipal OAuth2User oauth) {

        System.out.println("/test/login==============");
        System.out.println("authentication.get = " + authentication.getPrincipal());
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        System.out.println("principalDetails = " + oAuth2User.getAttributes());


        System.out.println("oauth.getAttributes() = " + oauth.getAttributes());

        return "OAuth2 세션 정보 확인";
    }

    @GetMapping({"","/"})
    public String index(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        System.out.println("principalDetails = " + principalDetails);
        // security 통해서 얻은 정보는 해당 어노테이션을 통해서 정보를 받을 수 있다...
        // 그럼 baseContoller는?

        // 머스테치 기본폴더 src/main/resources/
        // 뷰리졸버 설정 : templates (prefix), .mustache (suffix) 설정 생략가능
        // prefix, suffix설정은 WebMvcConfig에서 해줬음.
        return "index";
        // src/main/resources/templates/index.mustache
    }

    @GetMapping("/user")
    public @ResponseBody String user() {
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    //SecuriyConfig 파일 config설정하니까 작동 함 - 낚아채기 안 함
    @GetMapping("/loginForm")
    public String loginForm() {
        return "loginForm";
    }
    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user) {
        System.out.println("user = " + user);
        //user = User(id=0, username=ssar, password=1234, email=ssar@nate.com, role=null, createDate=null)
        user.setRole("ROLE_USER");

        // 회원가입은 잘 됨! 비밀번호 : 1234 -> 시큐리티 로그인 안 됨;;
        // 왜냐하면 패스워드가 암호화되지 않았기 때문이다.
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user);

        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN")
    //이 메서드 실행될 때 권한 체크
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }


    @PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
    //이 메서드가 실행되기 직전에 실행된다 .
    /*
        예)
    @Secured({"ROLE_USER","ROLE_ADMIN"}) => OR 조건, AND 조건 불가능
    @PreAuthorize("hasRole('ROLE_USER') and hasRole('ROLE_ADMIN')") => and 조건, or 조건 모두 가능
    @PostAuthorize도 있다. 잘 안 쓸 듯;

    보통 global로 쓰면서, 개개별로 @Secured를 쓸 듯
     */
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "데이터 정보";
    }

}

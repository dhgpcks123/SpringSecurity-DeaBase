package com.dev.security.repository;

import com.dev.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// CRUD 함수를 JpaRepository가 들고 있음!
//@Repository 어노테이션이 없어도 IoC가 됨. JpaRepository상속해서!
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    //select * from user where email = ?
//    public User findByEmail(String email);
}

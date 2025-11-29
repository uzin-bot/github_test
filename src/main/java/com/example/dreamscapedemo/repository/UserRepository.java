package com.example.dreamscapedemo.repository;

import com.example.dreamscapedemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUserNickName(String userNickName);

    boolean existsByEmail(String email);
}

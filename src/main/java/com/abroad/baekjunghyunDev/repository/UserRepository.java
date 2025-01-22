package com.abroad.baekjunghyunDev.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abroad.baekjunghyunDev.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	boolean existsByEmail(String email);
	boolean existsByName(String name);
	Optional<User> findByEmail(String email);
}

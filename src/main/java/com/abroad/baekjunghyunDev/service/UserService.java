package com.abroad.baekjunghyunDev.service;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.abroad.baekjunghyunDev.model.RoleType;
import com.abroad.baekjunghyunDev.model.User;
import com.abroad.baekjunghyunDev.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@(.+)$";
	
	public int 회원가입(User user) {
		
		if (!StringUtils.hasText(user.getEmail()) || !Pattern.matches(EMAIL_PATTERN, user.getEmail())) {
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다: " + user.getEmail());
        }
		if(userRepository.existsByEmail(user.getEmail())) {
			throw new IllegalArgumentException("이미 사용중인 Email 입니다: " + user.getEmail());
		}
		if(userRepository.existsByName(user.getName())) {
			throw new IllegalArgumentException("이미 사용중인 Name 입니다: " + user.getName());
		}
		
	    System.out.println(user.getPassword());
		user.setPassword(encoder.encode(user.getPassword()));
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return  200;
	}
	
	public boolean findUser(String email) {
        boolean result = userRepository.existsByEmail(email);
        return result;
	}
}

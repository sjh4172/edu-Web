package com.abroad.baekjunghyunDev.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abroad.baekjunghyunDev.model.Site;
import com.abroad.baekjunghyunDev.model.User;
import com.abroad.baekjunghyunDev.repository.SiteRepository;
import com.abroad.baekjunghyunDev.repository.UserRepository;
import com.abroad.baekjunghyunDev.service.SchemaService;
import com.abroad.baekjunghyunDev.service.SiteService;


@Service
public class PrincipalDetailService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	@Autowired
	SiteService siteService;
	@Autowired
	SiteRepository siteRepository; 
	@Autowired
	SchemaService schemaService;
	
	// 스프링이 로그인 요청을 가로챌때, username, password 를 받아서 username 이 DB에 있는지 확인 (password 자동 처리)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Site siteValue = siteRepository.findBySiteId(0).
				orElseThrow(()->{
					return new IllegalArgumentException("사이트 찾기 실패");
				});

        schemaService.changeSchema(siteValue.getSite());
        
		User principal = userRepository.findByEmail(email)
				.orElseThrow(()->{
					System.out.println("해당 사용자를 찾을 수 없습니다: " + email);
					return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다: " + email);
				});
		System.out.println("해당 사용자를 찾았습니다: " + email);
		return new PrincipalDetail(principal);	// 시큐리티 세션에 유저 정보가 저장이 됨
	}
	
}

package com.abroad.baekjunghyunDev.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.abroad.baekjunghyunDev.model.User;

import lombok.Getter;

// 스프링시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를 
// 스프링시큐리티의 고유한 세션 저장소에 저장을 해준다
@Getter
public class PrincipalDetail implements UserDetails{
	private User user;	// 콤포지션

	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getName();
	}

	// 계정이 만료되지 않았는지 리턴 (true = 만료 안됨)
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	// 계정이 잠겨있지 않은지 리턴 (true = 잠겨있지 않음)
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	// 비밀번호가 만료되지 않았는지 리턴 (true = 만료 안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	// 계정이 활성화 되어 있는지 리턴 (true = 활성화)
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	// 계정이 가지고있는 권한 목록을 리턴
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> colletors = new ArrayList<>();
//		colletors.add(new GrantedAuthority() {
//			@Override
//			public String getAuthority() {
//				// TODO Auto-generated method stub
//				return "ROLE_"+user.getRole();		// 스프링에서 ROLE을 받을때는 앞에 ROLE_ 추가 필요 ex) ROLE_USER, ROLE_ADMIN
//			}
//		});
		
		// 위와 동일 (람다식)
		colletors.add(()->{return "ROLE_"+user.getRole();});
		
		return colletors;
	}
}

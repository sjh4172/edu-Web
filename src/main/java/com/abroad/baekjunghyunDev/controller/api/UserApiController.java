package com.abroad.baekjunghyunDev.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abroad.baekjunghyunDev.config.auth.PrincipalDetail;
import com.abroad.baekjunghyunDev.dto.ResponseDto;
import com.abroad.baekjunghyunDev.model.User;
import com.abroad.baekjunghyunDev.service.SchemaService;
import com.abroad.baekjunghyunDev.service.UserService;

@RestController
public class UserApiController {
	@Autowired
	UserService userService;
	@Autowired
	SchemaService schemaService;
	
	@PostMapping("/v1/{site}/signupProc")
	public ResponseDto<Integer> save(@PathVariable String site, @RequestBody User user) {

        schemaService.changeSchema(site);

		// 실제로 DB에 insert 하고 return 해주면 됨
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 	// 회원가입 결과 Return
	}
	
	@GetMapping("/v1/{site}/me")
	public ResponseDto<User> me(@PathVariable String site, @AuthenticationPrincipal PrincipalDetail principal){
		boolean result = false;
        schemaService.changeSchema(site);
        
        // 해당 Site의 User가 맞는지 확인
        if(principal != null) {
        	result = userService.findUser(principal.getUser().getEmail());
        }
        
        // User 정보 확인 결과 반환
		if (principal != null && principal.getUser() != null && result != false) {
	        return new ResponseDto<User>(HttpStatus.OK.value(), principal.getUser());
	    } else {
	        return new ResponseDto<User>(HttpStatus.OK.value(), null);
	    }
	}
	
//	@GetMapping("/v1/me")
//	public void test(@AuthenticationPrincipal PrincipalDetail principal) {
//		if (principal != null && principal.getUser() != null) {
//			System.out.println(principal.getUser().getEmail());
//	    } else {
//	    	System.out.println("user 없음");
//	    }
//	}
}

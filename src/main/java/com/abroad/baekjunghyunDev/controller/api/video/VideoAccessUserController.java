package com.abroad.baekjunghyunDev.controller.api.video;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abroad.baekjunghyunDev.config.auth.PrincipalDetail;
import com.abroad.baekjunghyunDev.dto.AccessRequestDto;
import com.abroad.baekjunghyunDev.dto.ResponseDto;
import com.abroad.baekjunghyunDev.model.Access;
import com.abroad.baekjunghyunDev.model.User;
import com.abroad.baekjunghyunDev.service.AccessService;
import com.abroad.baekjunghyunDev.service.SchemaService;

@RestController
public class VideoAccessUserController {
	@Autowired
	AccessService accessService;
	@Autowired
	SchemaService schemaService;
	
	@GetMapping("/v1/{site}/video/{videoId}/access")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ResponseDto<?>> getUserList(@PathVariable String site, @PathVariable Long videoId, @AuthenticationPrincipal PrincipalDetail principal){
		if(schemaService.changeSchemaPrincipal(site, principal.getUser()) != false) {
			List<User> allowedUsers = accessService.getAllowedUsers(videoId);
			List<User> deniedUsers = accessService.getDeniedUsers(videoId);
			
			ResponseDto<Map<String, Object>> response = ResponseDto.<Map<String, Object>>builder()
	                .status(HttpStatus.OK.value())
	                .data(Map.of("allowedUsers", allowedUsers, "deniedUsers", deniedUsers))
	                .build();
	
	        return ResponseEntity.ok(response);
		}
		else {
			return ResponseEntity.ok(null);
		}
	}
	
	@PostMapping("/v1/{site}/video/{videoId}/access")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseDto<String> modifyAccess(@PathVariable String site, @PathVariable int videoId, @RequestBody AccessRequestDto request, @AuthenticationPrincipal PrincipalDetail principal) {
		if(schemaService.changeSchemaPrincipal(site, principal.getUser()) != false) {
	        accessService.modifyAccess(request, videoId);
	        return new ResponseDto<String>(HttpStatus.OK.value(), "권한 수정 완료");
		}
		else {
			return new ResponseDto<String>(HttpStatus.BAD_REQUEST.value(), "권한 수정 실패");
		}
    }
	
	
	
}

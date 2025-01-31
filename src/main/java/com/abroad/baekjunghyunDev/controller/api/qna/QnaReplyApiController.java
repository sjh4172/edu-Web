package com.abroad.baekjunghyunDev.controller.api.qna;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abroad.baekjunghyunDev.config.auth.PrincipalDetail;
import com.abroad.baekjunghyunDev.dto.ResponseDto;
import com.abroad.baekjunghyunDev.model.qna.Reply;
import com.abroad.baekjunghyunDev.service.SchemaService;
import com.abroad.baekjunghyunDev.service.qna.QnaReplyService;

@RestController
public class QnaReplyApiController {
	
	@Autowired
	QnaReplyService replyService;
	@Autowired
	SchemaService schemaService;
	
	@PostMapping("/v1/{site}/qna/{qnaId}/comment")
	public ResponseDto<Reply> replySave(@PathVariable String site, @PathVariable int qnaId, @RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail principal){
		if(schemaService.changeSchemaPrincipal(site, principal.getUser()) != false) {
			Reply newReply = replyService.댓글저장(principal.getUser(), qnaId, reply);
			return new ResponseDto<Reply>(HttpStatus.OK.value(), newReply); 	// 회원가입 결과 Return
		}
		else {
			return new ResponseDto<Reply>(HttpStatus.UNAUTHORIZED.value(), null); 	// 회원가입 결과 Return
		}
	}

	@GetMapping({"/v1/{site}/qna/{qnaId}/comment"})
	public ResponseDto<Page<Reply>> finByBoardIdReply(@PathVariable String site, @PathVariable int qnaId, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		schemaService.changeSchema(site);
		Page<Reply> replies = replyService.댓글목록(qnaId, pageable);
		return new ResponseDto<Page<Reply>>(HttpStatus.OK.value(), replies);
	}

	@PatchMapping("/v1/{site}/qna/{qnaId}/comment/{qnaReplyId}")
	public ResponseDto<Reply> replyUpdate(@PathVariable String site, @PathVariable int qnaReplyId, @RequestBody Reply reply, @AuthenticationPrincipal PrincipalDetail principal){
		if((schemaService.changeSchemaPrincipal(site, principal.getUser()) != false) &&
				(replyService.회원확인(qnaReplyId, principal.getUser()) != false)) {
			Reply newReply = replyService.댓글수정(qnaReplyId, reply);
			return new ResponseDto<Reply>(HttpStatus.OK.value(), newReply);
		}
		else {
			return new ResponseDto<Reply>(HttpStatus.UNAUTHORIZED.value(), null);
		}
	}
	
	@DeleteMapping("/v1/{site}/qna/{qnaId}/comment/{qnaReplyId}")
	public ResponseDto<Integer> replyDelete(@PathVariable String site, @PathVariable int qnaReplyId, @AuthenticationPrincipal PrincipalDetail principal){
		if((schemaService.changeSchemaPrincipal(site, principal.getUser()) != false) &&
				(replyService.회원확인(qnaReplyId, principal.getUser()) != false)) {
			replyService.댓글삭제(qnaReplyId);
			return new ResponseDto<Integer>(HttpStatus.OK.value(), qnaReplyId);
		}
		else {
			return new ResponseDto<Integer>(HttpStatus.UNAUTHORIZED.value(), 0);
		}
	}
}

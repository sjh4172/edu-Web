package com.abroad.baekjunghyunDev.controller.api.video;

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
import com.abroad.baekjunghyunDev.model.video.VideoReply;
import com.abroad.baekjunghyunDev.service.qna.QnaReplyService;
import com.abroad.baekjunghyunDev.service.video.VideoReplyService;

@RestController
public class VideoReplyApiController {

	@Autowired
	VideoReplyService videoReplyService;
	 
	@PostMapping("/v1/video/{videoId}/comment")
	public ResponseDto<VideoReply> videoReplySave(@PathVariable int videoId, @RequestBody VideoReply videoReply, @AuthenticationPrincipal PrincipalDetail principal){
		VideoReply newReply = videoReplyService.댓글저장(principal.getUser(), videoId, videoReply);
		return new ResponseDto<VideoReply>(HttpStatus.OK.value(), newReply);
	}

	@GetMapping({"/v1/video/{videoId}/comment"})
	public ResponseDto<Page<VideoReply>> finByVideodIdVideoReply(@PathVariable int videoId, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
		Page<VideoReply> videoReplies = videoReplyService.댓글목록(videoId, pageable);
	    
		return new ResponseDto<Page<VideoReply>>(HttpStatus.OK.value(), videoReplies);
	}

	@PatchMapping("/v1/video/{videoId}/comment/{videoReplyId}")
	public ResponseDto<VideoReply> vidoeReplyUpdate(@PathVariable int videoReplyId, @RequestBody VideoReply videoReply){
		VideoReply newReply = videoReplyService.댓글수정(videoReplyId, videoReply);
		return new ResponseDto<VideoReply>(HttpStatus.OK.value(), newReply);
	}
	
	@DeleteMapping("/v1/video/{videoId}/comment/{videoReplyId}")
	public ResponseDto<Integer> videoReplyDelete(@PathVariable int videoReplyId){
		videoReplyService.댓글삭제(videoReplyId);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), videoReplyId);
	}
}

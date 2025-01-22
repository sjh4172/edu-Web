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
import com.abroad.baekjunghyunDev.model.qna.Board;
import com.abroad.baekjunghyunDev.model.qna.Reply;
import com.abroad.baekjunghyunDev.model.video.Video;
import com.abroad.baekjunghyunDev.service.video.VideoService;

@RestController
public class VideoApiController {
	@Autowired
	VideoService videoService;

	@PostMapping("/v1/video")
	public ResponseDto<Video> save(@RequestBody Video video, @AuthenticationPrincipal PrincipalDetail principal){
		Video newVideo = videoService.비디오쓰기(video, principal.getUser());
		return new ResponseDto<Video>(HttpStatus.OK.value(), newVideo); 	
	}

	@GetMapping("/v1/video/{id}")
	public ResponseDto<Video> findById(@PathVariable int id) {
		Video video = videoService.비디오상세보기(id);
		return new ResponseDto<Video>(HttpStatus.OK.value(), video);
	}
	
	@GetMapping({"/v1/video"})
	public ResponseDto<Page<Video>> finByVideos(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<Video> videos = videoService.비디오목록(pageable);
		return new ResponseDto<Page<Video>>(HttpStatus.OK.value(), videos);
	}
	 
	@PatchMapping("/v1/video/{id}")
	public ResponseDto<Video> updateById(@PathVariable int id, @RequestBody Video video){
		Video newVideo = videoService.비디오수정(id, video);
		return new ResponseDto<Video>(HttpStatus.OK.value(), newVideo); 	// 회원가입 결과 Return;
	}
	
	@DeleteMapping("/v1/video/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id){
		videoService.비디오삭제(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), id);
	}
}

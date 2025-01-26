package com.abroad.baekjunghyunDev.service.video;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abroad.baekjunghyunDev.model.User;
import com.abroad.baekjunghyunDev.model.qna.Board;
import com.abroad.baekjunghyunDev.model.qna.Reply;
import com.abroad.baekjunghyunDev.model.video.Video;
import com.abroad.baekjunghyunDev.repository.video.VideoRepository;

@Service
public class VideoService {
	@Autowired
	VideoRepository videoRepository;

	@Transactional(readOnly = true) 
	public Page<Video> 비디오목록(Pageable pageable){
		return videoRepository.findAll(pageable);
	}
	
	@Transactional
	public Video 비디오쓰기(Video video, User user) {
		video.setUser(user);
		video.setPrivate(false);
		videoRepository.save(video);
		
		return video;
	}
	
	@Transactional
	public Video 비디오상세보기(int id) {
		return videoRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("비디오 상세보기 실패: 아이디를 찾을수 없습니다.");
			});
	}
	
	@Transactional
	public Video 비디오수정(int id, Video video) {
		Video findVideo = videoRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("비디오 수정 실패: 아이디를 찾을수 없습니다.");
				});
		findVideo.setContent(video.getContent());
		findVideo.setTitle(video.getTitle());
		findVideo.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		findVideo.setPrivate(video.isPrivate());
		
		return video;
	}
	
	@Transactional
	public void 비디오삭제(int id) {
		videoRepository.deleteById(id);
	}
	

	@Transactional
	public boolean 회원확인(int videoId, User user) {
		Video findVideo = videoRepository.findById(videoId)
				.orElseThrow(() -> {
					return new IllegalArgumentException("회원확인 실패: 회원을 찾을수 없습니다.");
				});
		
		if(findVideo.getUser().getEmail().equals(user.getEmail())) {
			return true;
		}
		else {
			return false;
		}
	}
}

package com.abroad.baekjunghyunDev.service.video;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abroad.baekjunghyunDev.model.Access;
import com.abroad.baekjunghyunDev.model.RoleType;
import com.abroad.baekjunghyunDev.model.User;
import com.abroad.baekjunghyunDev.model.qna.Board;
import com.abroad.baekjunghyunDev.model.qna.Reply;
import com.abroad.baekjunghyunDev.model.video.Video;
import com.abroad.baekjunghyunDev.repository.AccessRepository;
import com.abroad.baekjunghyunDev.repository.UserRepository;
import com.abroad.baekjunghyunDev.repository.video.VideoRepository;

@Service
public class VideoService {
	@Autowired
	VideoRepository videoRepository;
	@Autowired
	AccessRepository accessRepository;
	@Autowired
	UserRepository userRepository;
	
	@Transactional(readOnly = true) 
	public Page<Video> 비디오목록(Pageable pageable){
		return videoRepository.findAll(pageable);
	}
	
	@Transactional
	public Video 비디오쓰기(Video video, User user) {
		video.setUser(user);
		video.setPrivate(false);
		videoRepository.save(video);
		
		// video 를 생성한 사용자는 시청 가능하도록 설정
		Access access = Access.builder()
                .user(user)
                .video(video)
                .build(); 
		accessRepository.save(access);
		
		return video;
	}
	
	@Transactional
	public Video S3비디오쓰기(Video video, User user) {
		video.setUser(user);
		video.setPrivate(false);
		video.setUrl("null");
		
		videoRepository.save(video);
		
		// video 를 생성한 사용자는 시청 가능하도록 설정
		Access access = Access.builder()
                .user(user)
                .video(video)
                .build(); 
		accessRepository.save(access);
		
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
					return new IllegalArgumentException("video를 찾을수 없습니다.");
				});
		
		if(findVideo.getUser().getEmail().equals(user.getEmail())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean 시청권한확인(User user, int videoId) {
		Video findVideo = videoRepository.findById(videoId)
				.orElseThrow(() -> {
					return new IllegalArgumentException("video를 찾을수 없습니다.");
				});
		if(user.getRole() != RoleType.ADMIN) {
			return accessRepository.existsByUserAndVideo(user, findVideo);
		}
		else {
			return true;
		}
	}

}

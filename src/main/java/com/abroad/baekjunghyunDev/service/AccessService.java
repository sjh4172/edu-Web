package com.abroad.baekjunghyunDev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abroad.baekjunghyunDev.dto.AccessRequestDto;
import com.abroad.baekjunghyunDev.model.Access;
import com.abroad.baekjunghyunDev.model.RoleType;
import com.abroad.baekjunghyunDev.model.User;
import com.abroad.baekjunghyunDev.model.video.Video;
import com.abroad.baekjunghyunDev.repository.AccessRepository;
import com.abroad.baekjunghyunDev.repository.UserRepository;
import com.abroad.baekjunghyunDev.repository.video.VideoRepository;

import jakarta.transaction.Transactional;

@Service
public class AccessService {
	@Autowired
	AccessRepository accessRepository;
	@Autowired
	VideoRepository videoRepository;
	@Autowired
	UserRepository userRepository;
	
	
	public List<User> getAllowedUsers(Long videoId){
		return accessRepository.findAllowedUsers(videoId);
	}
	public List<User> getDeniedUsers(Long videoId){
		return accessRepository.findDeniedUsers(videoId);
	}
	
	@Transactional
    public void modifyAccess(AccessRequestDto request, int videoId) { 
        Video video = videoRepository.findById(videoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 비디오가 존재하지 않습니다."));

        // 권한 부여
        for (int userId : request.getGrantUserIds()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
            if(user.getRole() != RoleType.ADMIN) {
	            if (!accessRepository.existsByUserAndVideo(user, video)) {
	                Access access = Access.builder()
	                        .user(user)
	                        .video(video)
	                        .build(); 
	                accessRepository.save(access);
	            }
            }
        }

        // 권한 취소
        for (int userId : request.getRevokeUserIds()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
            if(user.getRole() != RoleType.ADMIN) {
            	accessRepository.deleteByUserAndVideo(user, video);
            }
        }
    }
}

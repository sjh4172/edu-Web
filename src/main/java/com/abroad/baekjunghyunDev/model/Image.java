package com.abroad.baekjunghyunDev.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import com.abroad.baekjunghyunDev.model.video.Video;
import com.abroad.baekjunghyunDev.model.video.VideoReply;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity	// MySql 테이블 자동 생성 
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	private String originName;	// 이미지의 원래 이름
	
	private String storedName;	// s3	에 저장될 이름
	
	private String accessUrl;	// s3 내부 이미지에 접근할 URL
	
	public Image(String originName) {
        this.originName = originName;
        this.storedName = getFileName(originName);
        this.accessUrl = "";
    }

    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }

    // 이미지 파일의 확장자를 추출하는 메소드
    public String extractExtension(String originName) {
        int index = originName.lastIndexOf('.');

        return originName.substring(index, originName.length());
    }

    // 이미지 파일의 이름을 저장하기 위한 이름으로 변환하는 메소드
    public String getFileName(String originName) {
        return UUID.randomUUID() + "." + extractExtension(originName);
    }
}

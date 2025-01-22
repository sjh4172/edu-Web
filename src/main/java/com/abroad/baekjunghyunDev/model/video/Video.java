package com.abroad.baekjunghyunDev.model.video;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.abroad.baekjunghyunDev.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity	// MySql 테이블 자동 생성 
public class Video {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;

	@Column(nullable = false, length = 200)
	private String url;
	
	@Lob
	private String content;
	
	@ColumnDefault("false")
	@JsonProperty("isPrivate")
	private boolean isPrivate;
	
	@ManyToOne(fetch = FetchType.EAGER)	// Many = Board / One = User 
	@JoinColumn(name="userId")
	private User user;
	
	@OneToMany(mappedBy = "video", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)	// 연관관계의 주인이 아님
	@JsonIgnoreProperties({"video"})	// 무한 참조 방지
	@OrderBy("id desc")
	@JsonIgnore
	private List<VideoReply> replys;
	
	@CreationTimestamp
	private Timestamp createdAt;
	
	public boolean isPrivate() {
	    return this.isPrivate;
	}
	
}

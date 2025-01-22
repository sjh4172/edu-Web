package com.abroad.baekjunghyunDev.model.video;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.abroad.baekjunghyunDev.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class VideoReply {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 200)
	private String content;
	
	@ManyToOne	// Mane = Reply / One = Board
	@JoinColumn(name = "videoId")
	@JsonIgnore
	private Video video ;
	
	@ManyToOne	// Many = Reply / One = User
	@JoinColumn(name = "userId")
	private User user;
	
	@CreationTimestamp
	private Timestamp createdAt;
}

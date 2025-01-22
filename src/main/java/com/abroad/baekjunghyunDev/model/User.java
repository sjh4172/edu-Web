package com.abroad.baekjunghyunDev.model;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class User {
	@Id	// primary
	@GeneratedValue(strategy = GenerationType.IDENTITY)	// 넘버링 자동
	private int id;
	
	@Column(nullable = false, length=30, unique = true)
	private String name;

	@Column(nullable = false, length=100)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;

	@Column(nullable = false, length=50)
	private String email;

	@Enumerated(EnumType.STRING)	// DB에 해당 타입이 String 이라고 알려줌
	private RoleType role; 
	
	@CreationTimestamp
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private Timestamp createdAt;
	
}

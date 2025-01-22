package com.abroad.baekjunghyunDev.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abroad.baekjunghyunDev.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
	
}
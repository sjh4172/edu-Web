package com.abroad.baekjunghyunDev.repository.video;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.abroad.baekjunghyunDev.model.video.Video;

public interface VideoRepository extends JpaRepository<Video, Integer>{
    @Query("SELECT MAX(v.id) FROM Video v")
    Integer findMaxId();
}

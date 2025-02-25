package com.abroad.baekjunghyunDev.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abroad.baekjunghyunDev.model.Access;
import com.abroad.baekjunghyunDev.model.User;
import com.abroad.baekjunghyunDev.model.video.Video;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface AccessRepository extends JpaRepository<Access, Integer> {

    // 시청 가능 유저 조회
    @Query("""
        SELECT u
        FROM User u
        JOIN Access a ON u.id = a.user.id
        WHERE a.video.id = :videoId
        AND u.role != 'ADMIN'
    """)
    List<User> findAllowedUsers(@Param("videoId") Long videoId);

    // 시청 불가능 유저 조회
    @Query("""
        SELECT u
        FROM User u
        WHERE u.id NOT IN (
            SELECT a.user.id
            FROM Access a
            WHERE a.video.id = :videoId
        )
        AND u.role != 'ADMIN'
    """)
    List<User> findDeniedUsers(@Param("videoId") Long videoId);
    
    // 시청 권한 확인
    boolean existsByUserAndVideo(User user, Video video);
    
    // 시청 권한 삭제
    void deleteByUserAndVideo(User user, Video video);
    
    // 특정 비디오에 대한 모든 접근 권한리스트 조회
    List<Access> findAllByVideo(Video video);

}

package com.abroad.baekjunghyunDev.repository.qna;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.abroad.baekjunghyunDev.model.qna.Reply;

public interface QnaReplyRepository extends JpaRepository<Reply, Integer>{
	public Page<Reply> findByBoardId(int boardId, Pageable pageable);
}

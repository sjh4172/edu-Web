package com.abroad.baekjunghyunDev.service.qna;

import java.security.Principal;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.abroad.baekjunghyunDev.model.User;
import com.abroad.baekjunghyunDev.model.qna.Board;
import com.abroad.baekjunghyunDev.repository.qna.QnaRepository;


@Service
public class QnaService {

	@Autowired
	private QnaRepository boardRepository;
	
	@Transactional(readOnly = true)
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}
	
	public Board 글쓰기(Board board, User user) {
		
		board.setUser(user);
		board.setPrivate(board.isPrivate());
		
		boardRepository.save(board);
		
		return board;
	}
	
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
		.orElseThrow(()->{
			return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을수 없습니다.");
		});
	}
	
	@Transactional
	public Board 글수정하기(int id, Board board, User user) {
		Board findBoard = boardRepository.findById(id)
					.orElseThrow(() -> {
						return new IllegalArgumentException("글 수정하기 실패: 아이디를 찾을수 없습니다.");
					});
		findBoard.setTitle(board.getTitle());
		findBoard.setContent(board.getContent());
		findBoard.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		findBoard.setPrivate(board.isPrivate());
			
		return board; 
	}
	
	@Transactional
	public void 글삭제하기(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public boolean 회원확인(int boardId, User user) {
		Board findBoard = boardRepository.findById(boardId)
				.orElseThrow(() -> {
					return new IllegalArgumentException("회원확인 실패: 회원을 찾을수 없습니다.");
				});
		
		if(findBoard.getUser().getEmail().equals(user.getEmail())) {
			return true;
		}
		else {
			return false;
		}
	}
}

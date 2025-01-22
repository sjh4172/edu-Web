package com.abroad.baekjunghyunDev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.abroad.baekjunghyunDev.service.qna.QnaReplyService;

@Controller
public class ReplyController {
	@Autowired
	QnaReplyService replyService;

//	@GetMapping({"/api/board/{boardId}/reply"})
//	public String finByBoardIdReply(@PathVariable int boardId, Model model, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
//		model.addAttribute("replys", replyService.댓글목록(boardId, pageable));	
//		return "board/boards";
//	}
}

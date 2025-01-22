package com.abroad.baekjunghyunDev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.abroad.baekjunghyunDev.service.qna.QnaService;

@Controller
public class BoardController {

	@Autowired 
	private QnaService boardService;
	
	/*
	 * @GetMapping({"/boards", "/"}) public String AllBoard(Model
	 * model, @PageableDefault(size = 10, sort = "id", direction =
	 * Sort.Direction.DESC) Pageable pageable) { model.addAttribute("boards",
	 * boardService.글목록(pageable)); return "board/boards"; }
	 */
	
	@GetMapping("/board/saveForm")
	public String saveFrom() {
		return "board/saveForm";
	}
	
	/*
	 * @GetMapping("/board/{id}") public String findById(@PathVariable int id, Model
	 * model) { model.addAttribute("board", boardService.글상세보기(id)); return
	 * "board/detail"; }
	 */
//	
//	@GetMapping("/board/{id}/updateForm")
//	public String updateForm(@PathVariable int id, Model model) {
//		model.addAttribute("board", boardService.글상세보기(id));
//		return "board/updateForm";
//	}
}

package com.douzone.mysite.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.KwdVo;
import com.douzone.mysite.vo.PageVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String indexGet(Model model,PageVo pager,KwdVo kwd, String move) {
		Map<String, Object> map = boardService.getContentsList(pager,kwd,move);
		model.addAllAttributes(map);
		
		return "board/list";
	}
	
	@RequestMapping(value="/",method=RequestMethod.POST)
	public String indexPost(Model model,PageVo pager,KwdVo kwd) {
		Map<String, Object> map = boardService.getContentsList(pager,kwd,"");
		model.addAllAttributes(map);
		System.out.println(pager.getOffset());
		return "board/list";
	}
	
	
}

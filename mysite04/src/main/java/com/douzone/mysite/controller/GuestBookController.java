package com.douzone.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestBookVo;

@Controller
@RequestMapping("/guestbook")
public class GuestBookController {
	@Autowired
	private GuestbookService guestbookservice;
	
	@RequestMapping(value={"/list","/"},method=RequestMethod.GET)
	public String list(Model model) {
		List<GuestBookVo> list = guestbookservice.getMessageList();
		model.addAttribute("list",list);
		return "guestbook/list";
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String add(GuestBookVo vo) {
		guestbookservice.addMessage(vo);
		return "redirect:/guestbook/";
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.GET)
	public String delete(Model model,@RequestParam(value="no",required=false) Long no) {
		model.addAttribute("no",no);
		return "guestbook/delete";
	}
	
	@RequestMapping(value="/delete",method=RequestMethod.POST)
	public String delete(GuestBookVo vo) {
		guestbookservice.deleteMessage(vo);
		return "redirect:/guestbook/";
	}
	
}

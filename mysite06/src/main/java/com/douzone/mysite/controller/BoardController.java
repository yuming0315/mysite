package com.douzone.mysite.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.security.AuthUser;
import com.douzone.mysite.service.BoardService;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.KwdVo;
import com.douzone.mysite.vo.PageVo;
import com.douzone.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String indexGet(HttpServletRequest request, Model model, PageVo pager, KwdVo kwd, String move) {
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			pager = (PageVo) flashMap.get("pager");
		}

		Map<String, Object> map = boardService.getContentsList(pager, kwd, move);
		model.addAllAttributes(map);

		return "board/list";
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String indexPost(Model model, PageVo pager, KwdVo kwd) {
		Map<String, Object> map = boardService.getContentsList(pager, kwd, "");
		model.addAllAttributes(map);
		return "board/list";
	}

	@RequestMapping(value = "/view", method = RequestMethod.GET)
	public String viewGet(HttpServletResponse response,HttpServletRequest request, Model model, PageVo pager, BoardVo vo) {
		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if (flashMap != null) {
			vo = (BoardVo) flashMap.get("vo");
			pager = (PageVo) flashMap.get("pager");
		}

		BoardVo data = boardService.getContents(vo.getNo());
		model.addAttribute("vo", data);
		model.addAttribute("pager", pager);

		Cookie[] cookies = request.getCookies();

		Boolean notCookie = true;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("visit")) {// visit라는 이름의 쿠키일때
				notCookie = false;
				if (!cookie.getValue().contains(vo.getNo() + ":c")) {
					// 조회 한 적이 없다면! 쿠키에 값 추가하고 조회수+1
					cookie.setMaxAge(60 * 60 * 2);
					cookie.setValue(cookie.getValue() + vo.getNo() + ":c");
					response.addCookie(cookie);// 값을 변경한 쿠키를 다시 추가해줘야함
					boardService.updateHit(vo.getNo());
				}
			}
		}

		if (notCookie) {// visit 쿠키가 없으면!
			Cookie newCookie = new Cookie("visit", vo.getNo() + ":c");
			newCookie.setMaxAge(60 * 60 * 24);
			response.addCookie(newCookie);
			boardService.updateHit(vo.getNo());
		}
		
		return "board/view";
	}

	@Auth(role="ADMIN")
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String writeGet(Model model, PageVo pager) {
		model.addAttribute("pager", pager);
		return "board/write";
	}

	@Auth
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String writePost(RedirectAttributes redirect, PageVo pager, BoardVo vo, @AuthUser UserVo authUser) {
		vo.setUser_no(authUser.getNo());
		boardService.addContents(vo);
		redirect.addFlashAttribute("pager", pager);

		return "redirect:/board/";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public String modifyGet(Model model, PageVo pager, BoardVo vo) {
		BoardVo data = boardService.getContents(vo.getNo());
		model.addAttribute("vo", data);
		model.addAttribute("pager", pager);
		return "board/modify";
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modifyPost(RedirectAttributes redirect, PageVo pager, BoardVo vo) {
		boardService.updateContents(vo);
		redirect.addFlashAttribute("vo", vo);
		redirect.addFlashAttribute("pager", pager);
		return "redirect:/board/view";
	}

	@RequestMapping(value = "/reply", method = RequestMethod.GET)
	public String replyGet(Model model, PageVo pager, BoardVo vo) {
		model.addAttribute("vo", vo);
		model.addAttribute("pager", pager);
		return "board/reply";
	}

	@Auth
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String replyPost(RedirectAttributes redirect, PageVo pager, BoardVo vo, @AuthUser UserVo authUser) {
		vo.setUser_no(authUser.getNo());
		boardService.replyContents(vo);

		redirect.addFlashAttribute("pager", pager);

		return "redirect:/board/";
	}

	@Auth
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deletePost(RedirectAttributes redirect, 
			PageVo pager, BoardVo vo, 
			@AuthUser UserVo authUser) {
		vo.setUser_no(authUser.getNo());
		boardService.deleteContents(vo);

		redirect.addFlashAttribute("pager", pager);

		return "redirect:/board/";
	}
}

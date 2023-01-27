package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.UserVo;
import com.douzone.web.mvc.Action;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BoardVo vo = new BoardVo();
		HttpSession session = request.getSession(true);
		vo.setUser_no(((UserVo) session.getAttribute("authUser")).getNo());
		vo.setTitle(request.getParameter("title"));
		vo.setContent(request.getParameter("content"));
		vo.setNo(Long.parseLong(request.getParameter("no")));
		
		new BoardDao().update(vo);
		
		response.sendRedirect("/mysite02/board?a=view&no="+vo.getNo()+"&page="+request.getParameter("page")+
				"&offset="+request.getParameter("offset"));
	}

}

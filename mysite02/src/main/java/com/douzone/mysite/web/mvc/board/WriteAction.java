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
import com.douzone.web.util.WebUtil;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			WebUtil.redirect(request.getContextPath(), request, response);
			return;
		}
		
		BoardVo vo = new BoardVo();
		vo.setUser_no(authUser.getNo());
		vo.setTitle(request.getParameter("title"));
		vo.setContent(request.getParameter("content"));
		
		new BoardDao().insert(vo);
		
		response.sendRedirect("/mysite02/board?page="+request.getParameter("page")+"&offset="+request.getParameter("offset"));
	}

}

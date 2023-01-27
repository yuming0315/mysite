package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.web.mvc.Action;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		new BoardDao().deleteNo(Long.parseLong(request.getParameter("no")));

		response.sendRedirect("/mysite02/board?&page="+request.getParameter("page")+
				"&offset="+request.getParameter("offset"));
	}

}

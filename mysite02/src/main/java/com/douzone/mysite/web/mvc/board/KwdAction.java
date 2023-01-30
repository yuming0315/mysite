package com.douzone.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.web.mvc.Action;

public class KwdAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String kwd = request.getParameter("kwd");

		if (kwd != null && !"".equals(kwd)) {
			List<BoardVo> list = new BoardDao().findKwd(request.getParameter("kwdOption"),kwd,
					request.getParameter("offset"));

			request.setAttribute("search", list);
		}

		response.sendRedirect("/mysite02/board?page="+request.getParameter("page")+
				"&offset="+request.getParameter("offset"));
	}

}

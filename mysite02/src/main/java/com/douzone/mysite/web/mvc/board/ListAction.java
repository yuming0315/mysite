package com.douzone.mysite.web.mvc.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PageVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Object obj = request.getAttribute("page");
		PageVo page =(PageVo) (obj==null ? new PageVo() : obj) ;

		page.setPage(request.getParameter("page"));
		page.setOffset(request.getParameter("offset"));
		
		page.setPages(new BoardDao().boardCount());
		page.addPage(request.getParameter("move"));
		
		List<BoardVo> list = new BoardDao().findAll(page);
		request.setAttribute("list", list);
		request.setAttribute("page", page);
		
		WebUtil.forward("board/list", request, response);
	}

}

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
		Object obj = request.getAttribute("pager");
		PageVo pager =(PageVo) (obj==null ? new PageVo() : obj) ;

//		obj = request.getAttribute("search");
		System.out.println(obj);
		
		pager.setPage(request.getParameter("page"));
		pager.setOffset(request.getParameter("offset"));
		
		pager.setPages(new BoardDao().boardCount());
		pager.addPage(request.getParameter("move"));
		
		List<BoardVo> list = new BoardDao().findAll(pager);
		request.setAttribute("list", list);
		request.setAttribute("pager", pager);
		
		WebUtil.forward("board/list", request, response);
	}

}

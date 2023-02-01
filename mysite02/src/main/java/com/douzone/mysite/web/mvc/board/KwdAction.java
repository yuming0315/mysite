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

public class KwdAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String kwd = request.getParameter("kwd");
		List<BoardVo> list=null;
		PageVo pager =new PageVo();
		if (kwd != null && !"".equals(kwd)) {
			 list = new BoardDao().findKwd(request.getParameter("kwdOption"),kwd,
					request.getParameter("offset"));

			request.setAttribute("list", list);
			pager.setPages(Long.valueOf(list.size()));
		}
		pager.setPage(request.getParameter("page"));
		pager.setOffset(request.getParameter("offset"));
		
		request.setAttribute("kwd", request.getParameter("kwd"));
		request.setAttribute("kwdOption", request.getParameter("kwdOption"));
		request.setAttribute("pager", pager);
		WebUtil.forward("board/list", request, response);
	}

}

package com.douzone.mysite.web.mvc.gb;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.GuestBookDao;
import com.douzone.mysite.vo.GuestBookVo;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class GuestBookDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GuestBookVo vo = new GuestBookVo();
		System.out.println(request.getParameter("no")+" "+request.getParameter("password"));
		vo.setNo(Long.parseLong(request.getParameter("no")));
		vo.setPassword(request.getParameter("password"));

		new GuestBookDao().delete(vo);
		
		WebUtil.redirect(request.getContextPath()+"/guestbook?a=list", request, response);
	}

}

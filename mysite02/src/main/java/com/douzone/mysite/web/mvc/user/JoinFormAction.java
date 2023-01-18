package com.douzone.mysite.web.mvc.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class JoinFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebUtil.forward("user/joinform", request, response);
	}

}

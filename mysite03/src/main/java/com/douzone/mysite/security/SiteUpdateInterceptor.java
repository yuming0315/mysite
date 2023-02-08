package com.douzone.mysite.security;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.douzone.mysite.vo.SiteVo;

public class SiteUpdateInterceptor implements HandlerInterceptor {
	@Autowired
	ServletContext context;
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
		
		String title = request.getParameter("title");
		SiteVo vo = (SiteVo)context.getAttribute("site");
		vo.setTitle(title);
	}
}

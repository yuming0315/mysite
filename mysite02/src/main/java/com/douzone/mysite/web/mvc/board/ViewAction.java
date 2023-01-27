package com.douzone.mysite.web.mvc.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.douzone.mysite.dao.BoardDao;
import com.douzone.web.mvc.Action;
import com.douzone.web.util.WebUtil;

public class ViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String number = request.getParameter("no");
		Long no = Long.parseLong(number);
		request.setAttribute("vo", new BoardDao().searchNo(no));

		Cookie[] cookies = request.getCookies();
		
		Boolean notCookie = true;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("visit")) {//visit라는 이름의 쿠키일때
				notCookie = false;
				if (!cookie.getValue().contains(number+":c")) {
				//조회 한 적이 없다면! 쿠키에 값 추가하고 조회수+1
					cookie.setValue(cookie.getValue()+number+":c");
					response.addCookie(cookie);//값을 변경한 쿠키를 다시 추가해줘야함
					new BoardDao().updateHit(no);
				}
			}
		}
		
		if(notCookie){//visit 쿠키가 없으면!
			Cookie newCookie = new Cookie("visit", number+":c");
			newCookie.setMaxAge(60 * 60 * 24);
			response.addCookie(newCookie);
			new BoardDao().updateHit(no);
		}

		WebUtil.forward("board/view", request, response);
	}

}

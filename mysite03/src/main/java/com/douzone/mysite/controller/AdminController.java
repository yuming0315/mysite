package com.douzone.mysite.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.security.Auth;
import com.douzone.mysite.service.FileuploadService;
import com.douzone.mysite.service.SiteService;
import com.douzone.mysite.vo.SiteVo;

@Auth(role="ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private ServletContext servletContext;
	
	@Autowired
	private SiteService siteService;
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private FileuploadService fileuploadService;
	
	@RequestMapping("")
	public String main(Model model) {
		SiteVo site = applicationContext.getBean(SiteVo.class);
		
		model.addAttribute("site",site);
		return "admin/main";
	}
	
	@RequestMapping("/main/update")
	public String update(SiteVo vo, @RequestParam("file") MultipartFile multipartFile) {
		String url = fileuploadService.restore(multipartFile);
		vo.setProfile(url);
		
		SiteVo site = applicationContext.getBean(SiteVo.class);

		siteService.updateSite(vo);
//		servletContext.setAttribute("siteVo", vo);
//		site.setTitle(vo.getTitle());
//		site.setWelcome(vo.getWelcome());
//		site.setProfile(vo.getProfile());
//		site.setDescription(vo.getDescription());
		BeanUtils.copyProperties(vo, site);
		
		return "redirect:/admin";
	}
	
	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}

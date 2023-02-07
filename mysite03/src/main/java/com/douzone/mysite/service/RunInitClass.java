package com.douzone.mysite.service;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

@Service
public class RunInitClass implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private SiteService siteService;

	@Autowired
	ServletContext context;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		String title = siteService.getSite().getTitle();
		context.setAttribute("title", title);
	}

}

package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestBookRepository;
import com.douzone.mysite.vo.GuestBookVo;

@Service
public class GuestbookService {
	@Autowired
	private GuestBookRepository guestbookrepository;
	
	public List<GuestBookVo> getMessageList() {
		return guestbookrepository.findAll();
	}
	
	public boolean deleteMessage(GuestBookVo vo) {
		return guestbookrepository.deleteByNoAndPassword(vo);
	}
	
	public void addMessage(GuestBookVo vo) {
		guestbookrepository.insert(vo);
	}

	public List<GuestBookVo> findSno(Long startNo) {
		return guestbookrepository.findSno(startNo);
	}
}

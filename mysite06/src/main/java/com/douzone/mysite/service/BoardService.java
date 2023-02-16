package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.BoardRepository;
import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.KwdVo;
import com.douzone.mysite.vo.PageVo;

@Service
public class BoardService {
	@Autowired
	private BoardRepository boardRepository;

	public void addContents(BoardVo vo) {
		boardRepository.addContents(vo);
	}
	
	public BoardVo getContents(Long no) {
		return boardRepository.getContents(no);
	}
	
	public void updateHit(Long no) {
		boardRepository.updateHit(no);
	}
	
	public void updateContents(BoardVo vo) {
		boardRepository.updateContents(vo);
	}
	
	public void deleteContents(BoardVo vo) {
		boardRepository.deleteContents(vo);
	}
	
	public Map<String, Object> getContentsList(PageVo pager, KwdVo kwd,String move) {
		List<BoardVo> list = boardRepository.findAllByPageAndKeyWord(pager,kwd,move);
		
		Map<String,Object> map = new HashMap<>();
		map.put("list", list);
		map.put("pager", pager);
		map.put("kwdVo", kwd);
		
		return map;
	}
	
	public void replyContents(BoardVo vo) {
		boardRepository.replyContents(vo); 
	}
}

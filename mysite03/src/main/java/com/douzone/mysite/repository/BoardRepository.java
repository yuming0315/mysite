package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.KwdVo;
import com.douzone.mysite.vo.PageVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	
	
	public List<BoardVo> findAllByPageAndKeyWord(PageVo pager, KwdVo kwd,String move) {
		Map<String, Object> map = new HashMap<>();
		map.put("pager", pager);
		map.put("kwdVo", kwd);
		pager.setPages(getTotalCount(kwd));
		pager.addPage(move);
		return sqlSession.selectList("board.findByPageAndKeyWord",map);
	}
	
	public Long getTotalCount(KwdVo kwd) {
		return sqlSession.selectOne("board.getTotalCount",kwd);
	}
}

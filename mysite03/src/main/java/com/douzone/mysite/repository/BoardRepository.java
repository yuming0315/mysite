package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	@Autowired
	private SqlSession sqlSession;
	
	
	public List<BoardVo> findAllByPageAndKeyWord(int page, String keyword) {
		Map<String, Object> map = new HashMap<>();
		map.put("page", page);
		map.put("keyword", keyword);
		map.put(keyword, map)
		
		return sqlSession.selectList("board.findAllByPageAndKeyWord",map);
	}
	public int getTotalCount(String keyword) {
		return sqlSession.selectOne("board.getTotalCount",keyword);
	}
}

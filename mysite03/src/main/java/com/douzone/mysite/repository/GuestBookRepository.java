package com.douzone.mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GuestBookVo;

@Repository
public class GuestBookRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public List<GuestBookVo> findAll() {
		return sqlSession.selectList("guestbook.findAll");
	}

	public void insert(GuestBookVo vo) {
		sqlSession.insert("guestbook.insert",vo);
	}
	
	public boolean deleteByNoAndPassword(GuestBookVo vo) {
		Map<String, Object> map = 
				Map.of("no",vo.getNo(),"password",vo.getPassword());
		return sqlSession.delete("guestbook.deleteByNoAndPassword",map)>0;
	}

	public List<GuestBookVo> findSno(Long startNo) {
		return sqlSession.selectList("guestbook.findSno",startNo);
	}
	
}

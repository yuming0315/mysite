package com.douzone.mysite.repository;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.UserVo;

@Repository
public class UserRepository {
//	@Autowired
//	private DataSource dataSource;
	//하나당 autowired 하나씩
	@Autowired
	private SqlSession sqlSession;
	
	public void insert(UserVo vo) {
		sqlSession.insert("user.insert",vo);
	}
	
//	public UserVo findByEmailAndPassword(UserVo vo) {
//		return sqlSession.selectOne("user.findByEmailAndPassword", vo);
//	}
	//위의 부분 map 버전
	public UserVo findByEmailAndPassword(UserVo vo) {
		Map<String, Object> map = new HashMap<>();
		map.put("e", vo.getEmail());
		map.put("p", vo.getPassword());
		return sqlSession.selectOne("user.findByEmailAndPassword", map);
	}
	
	public UserVo findByNo(Long no) {
		return sqlSession.selectOne("user.findByNo",no);
	}
	
	public void update(UserVo vo) {
		sqlSession.update("user.update",vo);
	}
	
}

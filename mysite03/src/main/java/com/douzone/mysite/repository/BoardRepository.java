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
		pager.setMaxPages(getTotalCount(kwd));
		pager.addPage(move);
		return sqlSession.selectList("board.findByPageAndKeyWord",map);
	}
	
	public Long getTotalCount(KwdVo kwd) {
		return sqlSession.selectOne("board.getTotalCount",kwd);
	}

	public BoardVo getContents(Long no) {
		return sqlSession.selectOne("board.getContents",no);
	}

	public void addContents(BoardVo vo) {
		sqlSession.insert("board.addContents",vo);
	}

	public void updateContents(BoardVo vo) {
		sqlSession.update("board.updateContents",vo);
	}
	
	public void replyContents(BoardVo vo) {
		//부모의 정보 싹 가져오기
		BoardVo parent = getContents(vo.getNo());
		//부모의 o_no +1L 값 이상인 값들 모두 +1
		sqlSession.update("board.updateOno",parent.getO_no());
		//reply추가
		vo.setG_no(parent.getG_no());
		vo.setDepth(parent.getDepth()+1L);
		vo.setO_no(parent.getO_no()+1L);
		sqlSession.insert("board.insertReply",vo);
	}

	public void deleteContents(BoardVo vo) {
		sqlSession.delete("board.deleteContents",vo);
	}

	public void updateHit(Long no) {
		sqlSession.update("board.updateHit",no);
	}
}

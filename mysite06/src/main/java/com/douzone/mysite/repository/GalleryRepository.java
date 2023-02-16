package com.douzone.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.vo.GalleryVo;

@Repository
public class GalleryRepository {
	@Autowired
	private SqlSession sqlSession;

	public List<GalleryVo> getImages() {
		return sqlSession.selectList("gallery.getImages");
	}

	public void removeImage(Long no) {
		sqlSession.delete("gallery.removeImage",no);
	}

	public void addImage(GalleryVo vo) {
		sqlSession.insert("gallery.addImage",vo);
	}	
}

package com.douzone.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.douzone.mysite.vo.BoardVo;

public class BoardDao {
	public Boolean insert(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql ="insert into board values(no,?,?,?,default,sysdate(),?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, vo.getUser_no());
			pstmt.setString(2,vo.getTitle());
			pstmt.setString(3, vo.getContent());
			//그룹넘버가 없다면 새로운 글
			if(vo.getG_no()==0L) {
				System.out.println("here");
				pstmt.setString(4, "max(g_no)+1");
				pstmt.setLong(5, 0L);
				pstmt.setLong(6, 0L);
			}else {//그룹넘버가 있으나 o_no가 어떻게 될 지 모름 o_no가 글을 누를 때 있을테니까 그거가지고 처리하기
				System.out.println("????");
				pstmt.setLong(4, vo.getG_no());
				pstmt.setLong(5, vo.getO_no() );
				pstmt.setLong(6, vo.getDepth());
			}
			
			int count = pstmt.executeUpdate();
			result = count == 1;
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");			
			String url = "jdbc:mariadb://192.168.10.113:3307/webdb?charset=utf8";
			conn = DriverManager.getConnection(url,"webdb","webdb");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
}

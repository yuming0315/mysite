package com.douzone.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.GuestBookVo;

public class GuestBookDao {
	public List<GuestBookVo> findAll() {
		List<GuestBookVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			String sql = "select no, name, message,reg_date from guestbook";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			//5.결과 처리
			while(rs.next()) {
				GuestBookVo vo = new GuestBookVo();
				vo.setNo(rs.getLong(1));
				vo.setName(rs.getString(2));
				vo.setMessage(rs.getString(3));
				vo.setReg_date(rs.getTimestamp(4));
				result.add(vo);
			}
			
		} catch (SQLException e) {
			System.out.println("error: "+e);
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				
				if(pstmt!=null) {
					pstmt.close();
				}
				
				if(conn!=null){
					conn.close();	
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public Boolean insert(GuestBookVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql ="insert into guestbook values(no,?,password(?),?,sysdate())";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getMessage());
			
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
	
	public Boolean delete(GuestBookVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "delete from guestbook where no = ? and password = password(?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, vo.getNo());
			pstmt.setString(2, vo.getPassword());
			
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

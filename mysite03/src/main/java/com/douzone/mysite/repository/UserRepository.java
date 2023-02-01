package com.douzone.mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.mysite.exception.UserRepositoryException;
import com.douzone.mysite.vo.UserVo;

@Repository
public class UserRepository {
	@Autowired
	private DataSource dataSource;
	//하나당 autowired 하나씩
	@Autowired
	private SqlSession sqlSession;
	
	public void insert(UserVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql ="insert into user values(no,?,?,password(?),?,now())";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
			pstmt.executeUpdate();
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
	}
	
	public UserVo findByEmailAndPassword(UserVo vo) {
		return sqlSession.selectOne("user.findByEmailAndPassword", vo);
	}
	
	public UserVo findByNo(Long no) {
		UserVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			
			String sql = "select no,name,email,gender from user where no = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, no);
			
			rs = pstmt.executeQuery();
			
			//5.결과 처리
			if(rs.next()) {
				result=new UserVo();
				result.setNo(rs.getLong(1));
				result.setName(rs.getString(2));
				result.setEmail(rs.getString(3));
				result.setGender(rs.getString(4));
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
	
	public void update(UserVo vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		if(!"".equals(vo.getName())&&vo.getName()!=null) {
			try {
				conn = getConnection();
				String sql ="update user set name = ? , gender = ? " 
							+("".equals(vo.getPassword())||vo.getPassword()==null ? "" : ", password = password( ? )" )
							+" where no = ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getGender());
				
				if("".equals(vo.getPassword())||vo.getPassword()==null) {
					pstmt.setLong(3, vo.getNo());
				}
				else {
					pstmt.setString(3, vo.getPassword());
					pstmt.setLong(4, vo.getNo());
				}
				
				pstmt.executeUpdate();
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
		}
		
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

package com.douzone.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.douzone.mysite.vo.BoardVo;
import com.douzone.mysite.vo.PageVo;

public class BoardDao {
	public Long boardCount() {
		Long result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();

			String sql = "select count(*) from board";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();

			if(rs.next()) {
				result = rs.getLong(1);
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	public Boolean insert(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "insert into board select null,?,?,?,0,sysdate(),ifnull(max(g_no),0)+1,?,? from board as a";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getUser_no());
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getContent());
			pstmt.setLong(4, 0L);
			pstmt.setLong(5, 0L);

			int count = pstmt.executeUpdate();
			result = count == 1;
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public Boolean replyInsert(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "insert into board select null,?,?,?,0,sysdate(),?,?,? from board as a";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getUser_no());
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getContent());
			pstmt.setLong(4, vo.getG_no());
			pstmt.setLong(5, vo.getO_no());
			pstmt.setLong(6, vo.getDepth());

			int count = pstmt.executeUpdate();
			result = count == 1;
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public BoardVo searchNo(Long no) {
		BoardVo vo = new BoardVo();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();

			String sql = "select no,user_no,title,contents from board where no=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo.setNo(rs.getLong(1));
				vo.setUser_no(rs.getLong(2));
				vo.setTitle(rs.getString(3));
				vo.setContent(rs.getString(4));	
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		return vo;
	}
	
	
	public List<BoardVo> findAll(PageVo page) {//다불러올 필요 없이 글 리스트 출력에 필요한 데이터만
		List<BoardVo> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();

			String sql = "select a.no,a.user_no,a.title,a.hit,a.reg_date,b.name from board a join user b on b.no=a.user_no"
					+ " order by g_no desc, o_no asc limit ?,?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, (page.getPage()-1L) * page.getOffset());
			pstmt.setLong(2, page.getOffset());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardVo vo = new BoardVo();
				vo.setNo(rs.getLong(1));
				vo.setUser_no(rs.getLong(2));
				vo.setTitle(rs.getString(3));
				vo.setHit(rs.getLong(4));
				vo.setRegDate(rs.getString(5));
				vo.setName(rs.getString(6));

				result.add(vo);
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}

				if (pstmt != null) {
					pstmt.close();
				}

				if (conn != null) {
					conn.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String url = "jdbc:mariadb://192.168.10.113:3307/webdb?charset=utf8";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}

}

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
	public Long newBoardNo(BoardVo vo) {
		Long result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();

			String sql = "select max(no) from board where user_no=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setLong(1, vo.getUser_no());
			
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
		Boolean result = true;
		Connection conn = null;
		PreparedStatement pstmt = null;
		PreparedStatement upstmt = null;

		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			//부모글의 g_no o_no depth 가져옴 => o_no+1 depth+1
			//부모글의 깊이보다 깊은것의 o_no 모두 +1 해줘야함
			//no를 넣어줘야 부모글을 읽어옴
			BoardVo parent = searchNo(vo.getNo());
			String usql = "update board set o_no=o_no+1 where g_no=? and depth>?";
			upstmt = conn.prepareStatement(usql);
			upstmt.setLong(1, parent.getG_no());
			upstmt.setLong(2, parent.getDepth());
			
			upstmt.executeUpdate();
			
			String sql = "insert into board values(null,?,?,?,0,sysdate(),?,?,?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getUser_no());
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getContent());
			pstmt.setLong(4, parent.getG_no());
			pstmt.setLong(5, parent.getO_no()+1L);
			pstmt.setLong(6, parent.getDepth()+1L);

			pstmt.executeUpdate();
			
			conn.commit();
		} catch (SQLException e) {
			if (conn != null) {
				result=false;
			}
			System.out.println("error:" + e);
		} finally {
			try {
				if(!result) {
					conn.rollback();
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
	public BoardVo searchNo(Long no) {
		BoardVo vo = new BoardVo();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();

			String sql = "select no,user_no,title,contents,g_no,o_no,depth from board where no=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				vo.setNo(rs.getLong(1));
				vo.setUser_no(rs.getLong(2));
				vo.setTitle(rs.getString(3));
				vo.setContent(rs.getString(4));	
				vo.setG_no(rs.getLong(5));
				vo.setO_no(rs.getLong(6));
				vo.setDepth(rs.getLong(7));
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

			String sql = "select a.no,a.user_no,a.title,a.hit,a.reg_date,b.name, a.g_no, a.o_no, a.depth from board a join user b on b.no=a.user_no"
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
				vo.setG_no(rs.getLong(7));
				vo.setO_no(rs.getLong(8));
				vo.setDepth(rs.getLong(9));
				
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
	public Boolean update(BoardVo vo) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "update board set user_no=?,title=?,contents=? where no=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, vo.getUser_no());
			pstmt.setString(2, vo.getTitle());
			pstmt.setString(3, vo.getContent());
			pstmt.setLong(4, vo.getNo());

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
	
	public Boolean updateHit(Long no) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "update board set hit=hit+1 where no=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

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
	
	public Boolean deleteNo(Long no) {
		Boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "delete from board where no=?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setLong(1, no);

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

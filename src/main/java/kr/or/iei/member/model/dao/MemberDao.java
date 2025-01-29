package kr.or.iei.member.model.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;

import kr.or.iei.member.model.vo.User;
import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.notice.model.vo.NoticeComment;

public class MemberDao {

	public int insertMember(Connection conn, User member) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "insert into tbl_user(user_no, user_id, user_pw,user_email, nickname, phone)  values (to_char(sysdate, 'yymmdd') || lpad(seq_user.nextval, 5, '0'),?,?,?,?,?)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getUserId());
			pstmt.setString(2, member.getUserPw());
			pstmt.setString(3, member.getUserEmail());
			pstmt.setString(4, member.getNickname());
			pstmt.setString(5, member.getPhone());
			
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int idDuplChk(Connection conn, String userId) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = "select count(*) as cnt from tbl_user where user_id = ?";
		int cnt = 0;
		

		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				//select 결과를 int로 꺼내온다
				cnt = rset.getInt("cnt");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return cnt;
	}

	public User userLogin(Connection conn, String loginId, String loginPw) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		User u = null;
		String query = "select * from tbl_user where user_id =? and user_pw=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, loginId);
			pstmt.setString(2, loginPw);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				u = new User();
				u.setUserNo(rset.getString("user_no"));
				u.setUserId(rset.getString("user_id"));
				u.setUserPw(rset.getString("user_pw"));
				u.setNickname(rset.getString("nickname"));
				u.setUserEmail(rset.getString("user_email"));
				u.setPhone(rset.getString("phone"));
				u.setUserDate(rset.getString("user_date"));
				u.setUserGrade(rset.getInt("grade"));
				u.setUserPoint(rset.getInt("point"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return u;
	}

	public int updateMember(Connection conn, User updUser) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "update tbl_user set nickname=?, user_email=?,phone=? where user_no=?";
		
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, updUser.getNickname());
			pstmt.setString(2, updUser.getUserEmail());
			pstmt.setString(3, updUser.getPhone());
			pstmt.setString(4, updUser.getUserNo());
			
			result=pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int deleteMember(Connection conn, String userNo) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "delete from tbl_user where user_no = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int updateMemberPw(Connection conn, String userNo, String newUserPw) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "update tbl_user set user_pw =? where user_no =?";
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, newUserPw);
			pstmt.setString(2, userNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public ArrayList<User> selectAllUser(Connection conn) {
		PreparedStatement pstmt = null;
		ArrayList<User> list = new ArrayList<User>();
		ResultSet rset = null;
		String query = "select * from tbl_user where grade != '100' order by user_no";
		
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				User u = new User();
				u.setUserNo(rset.getString("user_no"));
				u.setUserId(rset.getString("user_id"));
				u.setNickname(rset.getString("nickname"));
				u.setPhone(rset.getString("phone"));
				u.setUserEmail(rset.getString("user_email"));
				u.setUserGrade(rset.getInt("grade"));
				u.setUserDate(rset.getString("user_date"));
				u.setUserPoint(rset.getInt("point"));
				
				list.add(u);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return list;
	}

	public int updChgLevel(Connection conn, String userNo, String userGrade) {
		PreparedStatement pstmt = null;
		int result = 0;
		int point=0;
		String query = "update tbl_user set grade = ?, point=? where user_no = ?";
		if(userGrade.equals("1")) {
			point=199;
		}else if(userGrade.equals("2")) {
			point=299;
		}else if(userGrade.equals("3")) {
			point=399;
		}else if(userGrade.equals("4")) {
			point=499;
		}else {
			point=500; //한 회원이 갑자기 대량의 포인트를 얻기는 거의 불가능하니 관리자 권한으로 등급상승, 강등시 포인트 조정(추후 여유 될시 기능 따로 구현예정)
		}
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userGrade);
			pstmt.setInt(2, point);
			pstmt.setString(3, userNo);
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} finally {
			JDBCTemplate.close(pstmt);
			
		}
		
		return result;
	}

	public String sechInfoId(Connection conn, String srchEmail) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String query="select user_id from tbl_user where user_email=?";
		String userId=null;
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1,srchEmail);
			rset=pstmt.executeQuery();
			if(rset.next()) {
				userId=rset.getString("user_id");
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return userId;
	}

	public int addBann(Connection conn, String userId, String userEmail, String phone) {
		PreparedStatement pstmt=null;
		String query="insert into tbl_bann values(?,?,?)";
		int result=0;
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userId);
			pstmt.setString(2, userEmail);
			pstmt.setString(3, phone);
			result=pstmt.executeUpdate();
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			
		}
		return result;
	}

	public int selectBannUser(String userId, Connection conn) {
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String query="select * from tbl_bann where user_id=?";
		int result=0;
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userId);
			rset=pstmt.executeQuery();
			
			if(rset.next()) {
				result=1;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public int chkEmail(String userEmail, Connection conn) {
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String query= "select * from tbl_bann where user_email=?";
		int result=0;
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userEmail);
			rset=pstmt.executeQuery();
			
			if(rset.next()) {
				result=1;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int chkPhone(String userPhone, Connection conn) {
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String query= "select * from tbl_bann where phone=?";
		int result=0;
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userPhone);
			rset=pstmt.executeQuery();
			
			if(rset.next()) {
				result=1;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public int selectUserGrade(Connection conn, String userNo) {
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		int result=0;
		String query="select grade from tbl_user where user_no=?";
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userNo);
			rset=pstmt.executeQuery();
			if(rset.next()) {
				result=rset.getInt("grade");
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public String selectUser(Connection conn, String userEmail, String userId) {
		// TODO Auto-generated method stub
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		String query="select user_email from tbl_user where user_id=? and user_email=?";
		String email=null;
		
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userId);
			pstmt.setString(2, userEmail);
			rset=pstmt.executeQuery();
			
			if(rset.next()) {
				email=rset.getString("user_email");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return email;
	}

	public int replaceMemberPw(Connection conn, String userId, String newUserPw) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "update tbl_user set user_pw =? where user_id =?";
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, newUserPw);
			pstmt.setString(2, userId);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public ArrayList<Notice> myNotices(Connection conn, String userNo) {
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		
		ArrayList<Notice> list = new ArrayList<>();
		String query = "SELECT post_id, board_title, created_date FROM tbl_notice WHERE user_no = ? ORDER BY created_date DESC";
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userNo);
			rset=pstmt.executeQuery();
			while (rset.next()) {
				Notice notice = new Notice();
                notice.setPostId(rset.getString("post_id"));
                notice.setBoardTitle(rset.getString("board_title"));
                notice.setCreatedDate(rset.getString("created_date"));
                list.add(notice);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return list;
	}

	public ArrayList<NoticeComment> myComment(Connection conn, String userNo) {
		
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		
		ArrayList<NoticeComment> list = new ArrayList<>();
		String query = "SELECT post_id, comment_id, comments, comm_time FROM tbl_comment WHERE user_no = ? ORDER BY comm_time DESC";
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, userNo);
			rset=pstmt.executeQuery();
			while (rset.next()) {
				NoticeComment comment = new NoticeComment();
				comment.setPostId(rset.getString("post_id"));
				comment.setCommentId(rset.getString("comment_id"));
				comment.setComments(rset.getString("comments"));
				comment.setCommTime(rset.getString("comm_time"));
                list.add(comment);
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return list;
	}

	public ArrayList<User> selectMemberList(Connection conn, int start, int end) {
		ArrayList<User> list = new ArrayList<>();
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String sql = "SELECT * FROM (SELECT ROWNUM rnum, m.* FROM (SELECT * FROM tbl_user WHERE grade != 100 ORDER BY user_date DESC) m WHERE ROWNUM <= ?) WHERE rnum >= ?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, end);
            pstmt.setInt(2, start);
            rset = pstmt.executeQuery();

            while (rset.next()) {
                User user = new User();
                user.setUserNo(rset.getString("user_no")); // VARCHAR2(11) 이므로 String으로 처리
                user.setUserId(rset.getString("user_id"));
                user.setUserPw(rset.getString("user_pw"));
                user.setUserEmail(rset.getString("user_email"));
                user.setNickname(rset.getString("nickname"));
                user.setPhone(rset.getString("phone"));
                user.setUserDate(rset.getString("user_date")); // DATE 타입으로 처리
                user.setUserGrade(rset.getInt("grade")); // 컬럼 이름 변경
                user.setUserPoint(rset.getInt("point")); // 컬럼 이름 변경
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(rset);
            JDBCTemplate.close(pstmt);
        }

        return list;
    }

	public int selectMemberCount(Connection conn) {
		int totCnt = 0; // 전체 회원 수를 저장할 변수
        PreparedStatement pstmt = null;
        ResultSet rset = null;
        String sql = "SELECT COUNT(*) FROM tbl_user WHERE grade != 100"; // 전체 회원 수를 조회하는 SQL 쿼리

        try {
            pstmt = conn.prepareStatement(sql); // PreparedStatement 객체 생성
            rset = pstmt.executeQuery(); // 쿼리 실행

            if (rset.next()) { // 결과가 존재하면
                totCnt = rset.getInt(1); // 첫 번째 컬럼(전체 수)을 가져옴
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
        } finally {
            JDBCTemplate.close(rset); // ResultSet 객체 닫기
            JDBCTemplate.close(pstmt); // PreparedStatement 객체 닫기
        }

        return totCnt; // 전체 회원 수 반환
	}

	

	
}

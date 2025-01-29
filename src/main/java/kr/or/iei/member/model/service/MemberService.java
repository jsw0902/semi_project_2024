package kr.or.iei.member.model.service;

import java.sql.Connection
;
import java.util.ArrayList;
import java.util.StringTokenizer;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.member.model.dao.MemberDao;
import kr.or.iei.member.model.vo.MemberPageData;
import kr.or.iei.member.model.vo.User;
import kr.or.iei.notice.model.vo.Notice;
import kr.or.iei.notice.model.vo.NoticeComment;

public class MemberService {
	MemberDao dao;
	
	public MemberService() {
		dao=new MemberDao();
	}

	public int insertMember(User member) {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		int result = 0;
		result = dao.insertMember(conn, member);
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		return result;
	}

	public int idDuplChk(String userId) {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		int result= 0;
		result = dao.idDuplChk(conn,userId);
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	public User userLogin(String loginId, String loginPw) {
		Connection conn = JDBCTemplate.getConnection();
		User member = dao.userLogin(conn,loginId,loginPw);
		JDBCTemplate.close(conn);
		return member;
	}

	public int updateMember(User updUser) {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		int result = dao.updateMember(conn, updUser);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		return result;
	}

	public int deleteMember(String userNo) {
		// TODO Auto-generated method stub
		Connection conn = JDBCTemplate.getConnection();
		int result = dao.deleteMember(conn, userNo);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}
	
	public int updateMemberPw(String userNo, String newUserPw) {
		Connection conn = JDBCTemplate.getConnection();
		int result = dao.updateMemberPw(conn, userNo, newUserPw);
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	public ArrayList<User> selectAllUser() {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<User> list = dao.selectAllUser(conn);
		JDBCTemplate.close(conn);
		return list;
		
	}

	public int updChgLevel(String memberNo, String memberLevel) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = dao.updChgLevel(conn,memberNo,memberLevel);
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int updChgAllLevel(String memberNoArrStr, String memberLevelArrStr) {
		//memberNoArrStr : 2410230054/2410230055/2410230056
		Connection conn = JDBCTemplate.getConnection();
		
		StringTokenizer st1 = new StringTokenizer(memberNoArrStr, "/");
		StringTokenizer st2 = new StringTokenizer(memberLevelArrStr, "/");
		
		
		
		boolean resultChk = true;
		while(st1.hasMoreTokens()) {
			String userNo = st1.nextToken();
			String userLevel = st2.nextToken();
			
			int result = dao.updChgLevel(conn, userNo, userLevel);
			
			if(result < 1) {
				resultChk = false;
				break;
			}	
		}
		if(resultChk) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		if(resultChk) {
			return 1;
		}else {
			return 0;			
		}
	}

	public String srchInfoId(String srchEmail) {
	Connection conn=JDBCTemplate.getConnection();
	
	String userId=dao.sechInfoId(conn,srchEmail);
	
	
	JDBCTemplate.close(conn);
	return userId;
			
	}

	public int addBann(String bannNo, String userId, String userEmail, String phone) {
		Connection conn=JDBCTemplate.getConnection();
		int result=dao.addBann(conn,userId,userEmail,phone);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
		return result;
	}

	public int deleteAllUser(String userNoArr, String userIdArr, String userEmailArr, String phoneArr) {
		Connection conn=JDBCTemplate.getConnection();
		StringTokenizer st1 = new StringTokenizer(userNoArr, "/");
		StringTokenizer st2 = new StringTokenizer(userIdArr, "/");
		StringTokenizer st3 = new StringTokenizer(userEmailArr, "/");
		StringTokenizer st4 = new StringTokenizer(phoneArr, "/");
		 int result=0;
		
		
		while(st1.hasMoreTokens()) {
			String userNo = st1.nextToken();
			String userId = st2.nextToken();
			String userEmail = st3.nextToken();
			String phone = st4.nextToken();
			result =dao.addBann(conn,userId,userEmail,phone);
			
			if(result < 1) {
				result=-1;
				JDBCTemplate.rollback(conn);
				return result;
			}else {
				result=dao.deleteMember(conn, userNo);
				if(result<0) {
					result=0;
					JDBCTemplate.rollback(conn);
					return result;
				}
			}
		}
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		return result;
	}

	public int bannUser(String bannNo) {
		Connection conn=JDBCTemplate.getConnection();
		int result=dao.deleteMember(conn, bannNo);
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		
		return result;
	}
	//밴 유저 아이디
	public int selectBannUserId(String userId) {
		Connection conn=JDBCTemplate.getConnection();
		int result=0;
		result=dao.selectBannUser(userId,conn);
		
		JDBCTemplate.close(conn);
		return result;
	}
	//밴유저 이메일 검사
	public int chkEmail(String userEmail) {
		Connection conn=JDBCTemplate.getConnection();
		int result=0;
		result=dao.chkEmail(userEmail,conn);
		
		JDBCTemplate.close(conn);
		return result;
	}
	//벤유저 전화번호
	public int chkPhone(String userPhone) {
		Connection conn=JDBCTemplate.getConnection();
		int result=0;
		result=dao.chkPhone(userPhone,conn);
		
		JDBCTemplate.close(conn);
		return result;
	}

	public String selectUser(String userId, String userEmail) {
		// TODO Auto-generated method stub
		Connection conn=JDBCTemplate.getConnection();
		String email=null;
		email=dao.selectUser(conn, userEmail,userId);
		
		JDBCTemplate.close(conn);
		return email;
	}
	
	public int replaceMemberPw(String userId, String newUserPw) {
		Connection conn = JDBCTemplate.getConnection();
		int result = dao.replaceMemberPw(conn, userId, newUserPw);
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
		return result;
	}
	

	public ArrayList<Notice> myNotices(String userNo) {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<Notice> list = dao.myNotices(conn, userNo);
		JDBCTemplate.close(conn);
		return list;
	}

	public ArrayList<NoticeComment> myComment(String userNo) {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<NoticeComment> list = dao.myComment(conn, userNo);
		JDBCTemplate.close(conn);
		return list;
	}

	public MemberPageData selectMemberList(int reqPage) {
		Connection conn = JDBCTemplate.getConnection();

        // 한 페이지에 보여질 회원 수
        int viewMemberCnt = 10;

        // 현재 페이지에 보여질 회원의 시작과 끝 번호 계산
        int end = reqPage * viewMemberCnt;
        int start = end - viewMemberCnt + 1;

        // 회원 목록 조회
        ArrayList<User> list = dao.selectMemberList(conn, start, end);

        // 전체 회원 수 조회
        int totCnt = dao.selectMemberCount(conn);

        // 전체 페이지 수 계산
        int totPage = (totCnt % viewMemberCnt > 0) ? (totCnt / viewMemberCnt) + 1 : (totCnt / viewMemberCnt);

        // 페이지 네비게이션 사이즈 설정
        int pageNaviSize = 5;

        // 페이지 네비게이션 시작 번호 계산
        int pageNo = ((reqPage - 1) / pageNaviSize) * pageNaviSize + 1;

        // 페이지 네비게이션 HTML 생성
        StringBuilder pageNaviBuilder = new StringBuilder();
        pageNaviBuilder.append("<ul class='pagination circle-style'>");

        // 이전 버튼
        if (pageNo != 1) {
            pageNaviBuilder.append("<li>");
            pageNaviBuilder.append("<a class='page-item' href='/member/adminPage?reqPage=")
                           .append(pageNo - 1)
                           .append("'>");
            pageNaviBuilder.append("<span class='material-icons'><svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" fill=\"currentColor\" class=\"bi bi-arrow-left-circle\" viewBox=\"0 0 16 16\">\r\n"
            		+ "  <path fill-rule=\"evenodd\" d=\"M1 8a7 7 0 1 0 14 0A7 7 0 0 0 1 8m15 0A8 8 0 1 1 0 8a8 8 0 0 1 16 0m-4.5-.5a.5.5 0 0 1 0 1H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5z\"/>\r\n"
            		+ "</svg></span>");
            pageNaviBuilder.append("</a></li>");
        }

        // 페이지 번호 버튼
        for (int i = 0; i < pageNaviSize; i++) {
            if (pageNo > totPage) {
                break;
            }
            pageNaviBuilder.append("<li>");
            if (reqPage == pageNo) {
                pageNaviBuilder.append("<a class='page-item active-page' href='/member/adminPage?reqPage=")
                               .append(pageNo)
                               .append("'>")
                               .append(pageNo)
                               .append("</a>");
            } else {
                pageNaviBuilder.append("<a class='page-item' href='/member/adminPage?reqPage=")
                               .append(pageNo)
                               .append("'>")
                               .append(pageNo)
                               .append("</a>");
            }
            pageNo++;
        }

        // 다음 버튼
        if (pageNo <= totPage) {
            pageNaviBuilder.append("<li>");
            pageNaviBuilder.append("<a class='page-item' href='/member/adminPage?reqPage=")
                           .append(pageNo)
                           .append("'>");
            pageNaviBuilder.append("<span class='material-icons'><svg xmlns=\"http://www.w3.org/2000/svg\" width=\"24\" height=\"24\" fill=\"currentColor\" class=\"bi bi-arrow-right-circle\" viewBox=\"0 0 16 16\">\r\n"
            		+ "  <path fill-rule=\"evenodd\" d=\"M1 8a7 7 0 1 0 14 0A7 7 0 0 0 1 8m15 0A8 8 0 1 1 0 8a8 8 0 0 1 16 0M4.5 7.5a.5.5 0 0 0 0 1h5.793l-2.147 2.146a.5.5 0 0 0 .708.708l3-3a.5.5 0 0 0 0-.708l-3-3a.5.5 0 1 0-.708.708L10.293 7.5z\"/>\r\n"
            		+ "</svg></span>");
            pageNaviBuilder.append("</a></li>");
        }

        pageNaviBuilder.append("</ul>");

        String pageNavi = pageNaviBuilder.toString();

        MemberPageData pd = new MemberPageData(list, pageNavi);

        JDBCTemplate.close(conn);
        return pd;
    }
	

	
}

package kr.or.iei.notice.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.notice.model.service.NoticeService;

/**
 * Servlet implementation class NoticeScrapAddServlet
 */
@WebServlet("/scrap/add")
public class NoticeScrapAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeScrapAddServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String userNo = request.getParameter("userNo");
	     String postId = request.getParameter("postId");
	     String boardName = request.getParameter("boardName");
	     
	     NoticeService noticeService = new NoticeService();
	     boolean isScrapped = noticeService.isScrapped(userNo, postId);
	     
	     if (isScrapped) {
	            // 이미 스크랩한 경우 처리
	            request.setAttribute("title", "알림");
	            request.setAttribute("msg", "이미 스크랩한 게시글입니다.");
	            request.setAttribute("icon", "warning");
	            request.setAttribute("loc", "/notice/view?postId=" + postId);
	            request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
	            return;
	     }
	     
	     
	     int result = noticeService.noticeAddScrap(userNo, boardName ,postId);
	     
	     if (result > 0) {
	            // 스크랩 추가 성공 시
	            request.setAttribute("title", "성공");
	            request.setAttribute("msg", "스크랩 추가에 성공하였습니다.");
	            request.setAttribute("icon", "success");
	            request.setAttribute("loc", "/notice/view?postId=" + postId);
	     } else {
	            // 스크랩 추가 실패 시
	            request.setAttribute("title", "실패");
	            request.setAttribute("msg", "스크랩 추가에 실패하였습니다.");
	            request.setAttribute("icon", "error");
	            request.setAttribute("loc", "/");
	        }
	        request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

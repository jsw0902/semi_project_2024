package kr.or.iei.notice.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.notice.model.service.NoticeService;
import kr.or.iei.notice.model.vo.NoticeScrap;

/**
 * Servlet implementation class NoticeScrapSrchListServlet
 */
@WebServlet("/member/myScrap")
public class NoticeScrapSrchListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeScrapSrchListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userNo = request.getParameter("userNo");
	    String postId = request.getParameter("postId");
	    // 서비스 호출하여 스크랩 목록 가져오기
        NoticeService noticeService = new NoticeService();
        ArrayList<NoticeScrap> scrapList = noticeService.getMyScrapList(userNo);

        // 요청 객체에 스크랩 목록 저장
        request.setAttribute("scrapList", scrapList);

        // 스크랩 목록 JSP로 포워딩
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/member/myScrapList.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

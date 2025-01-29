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
 * Servlet implementation class NoticeScrapdelServlet
 */
@WebServlet("/scrap/remove")
public class NoticeScrapdelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeScrapdelServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 // 요청 파라미터에서 scrapId 가져오기
        String scrapId = request.getParameter("scrapId");
        String userNo = request.getParameter("userNo");

        // NoticeService를 통해 스크랩 삭제
        NoticeService noticeService = new NoticeService();
        int result = noticeService.removeScrap(scrapId);

        // 삭제 결과에 따라 메시지 설정
        if (result > 0) {
            // 삭제 성공
            request.setAttribute("title", "성공");
            request.setAttribute("msg", "스크랩이 해제되었습니다.");
            request.setAttribute("icon", "success");
            request.setAttribute("loc", "/member/myScrap?userNo=" + userNo); // 스크랩 목록 페이지로 이동
        } else {
            // 삭제 실패
            request.setAttribute("title", "실패");
            request.setAttribute("msg", "스크랩 해제에 실패하였습니다.");
            request.setAttribute("icon", "error");
            request.setAttribute("loc", "/member/myScrap?userNo=" + userNo); // 스크랩 목록 페이지로 이동
        }

        // 메시지 JSP로 포워딩
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
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

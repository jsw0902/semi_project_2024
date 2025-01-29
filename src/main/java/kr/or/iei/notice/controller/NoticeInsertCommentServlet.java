package kr.or.iei.notice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.aside.model.vo.Product;
import kr.or.iei.common.vo.DataFetcher;
import kr.or.iei.member.model.vo.User;
import kr.or.iei.news.model.vo.NewsItem;
import kr.or.iei.notice.model.service.NoticeService;
import kr.or.iei.notice.model.vo.NoticeComment;
import kr.or.iei.search.word.service.SearchService;
import kr.or.iei.search.word.vo.Word;
import kr.or.iei.weather.model.vo.Weather;

/**
 * Servlet implementation class NoticeInsertCommentServlet
 */
@WebServlet("/notice/insertComment")
public class NoticeInsertCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeInsertCommentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//api,검색 세션관리 - 복붙(?) 시작
    	HttpSession session = request.getSession();
        // 검색어 랭킹 데이터 가져오기
        SearchService srchService = new SearchService();
        ArrayList<Word> wordList = srchService.selectAllWord();
        session.setAttribute("wordList", wordList); // wordList 세션에 저장

        // 네이버 쇼핑/뉴스 데이터 가져오기
        List<Product> productItems = (List<Product>) session.getAttribute("productItems");
        List<NewsItem> newsItems = (List<NewsItem>) session.getAttribute("newsItems");

        if (productItems == null || newsItems == null) {
            Map<String, Object> result = DataFetcher.fetchNaverData();
            productItems = (List<Product>) result.get("productItems");
            newsItems = (List<NewsItem>) result.get("newsItems");

            session.setAttribute("productList", productItems);
            session.setAttribute("newsList", newsItems);
        }

        // 날씨 데이터 가져오기
        Weather weather = (Weather) session.getAttribute("weather");
        if (weather == null) {
            weather = DataFetcher.fetchWeatherData();
            if (weather != null) {
                session.setAttribute("weather", weather); // 날씨 데이터 세션에 저장
            }
        }

        //세션관리 - 복붙(?) 끝
        
        //로그인 세션 체크
        User loginMember = (User) session.getAttribute("loginMember");
        if(loginMember == null) {
        	//다시 로그인 요청
        	RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
        	request.setAttribute("title", "로그인 기간 만료");
			request.setAttribute("msg", "다시 로그인 해주세요.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/index");
			
			view.forward(request, response);
			return;
        }else {        	
        	//1.인코딩
        	
        	//2.값 추출
        	String replyTo=request.getParameter("replyTo");
        	String commentRef = request.getParameter("postId");
        	String commentWriter = request.getParameter("commentWriter");
        	String commentContent = request.getParameter("commentContent");
        	
        	
    		if (commentContent == null || commentContent.trim().isEmpty()) {
    			// 이전 페이지(뷰) URL 가져오기
    			request.setAttribute("title", "오류");
    			request.setAttribute("msg", "내용입력해주세요");
    			request.setAttribute("icon", "error");
    			request.setAttribute("loc", "/notice/view?postId="+ commentRef);
    			request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
    			return;
    		}
        	
        	//3.로직
        	NoticeComment comment = new NoticeComment();
        	comment.setPostId(commentRef);
        	comment.setCommentWriter(commentWriter);
        	comment.setComments(commentContent);
        	comment.setReplyTo(replyTo);
        	NoticeService service = new NoticeService();
        	int result = service.insertComment(comment);
        	
        	//4.결과 처리
        	if(result>0) {
        		request.setAttribute("title", "성공");
        		request.setAttribute("msg", "댓글이 작성되었습니다.");
        		request.setAttribute("icon", "success");
        		request.setAttribute("loc", "/notice/view?postId="+ commentRef+"&commentChk=chk");
        	}else {
        		request.setAttribute("title", "실패");
        		request.setAttribute("msg", "댓글 작성 중,오류가 발생하였습니다.");
        		request.setAttribute("icon", "error");
        		request.setAttribute("loc", "/notice/view?noticeNo="+commentRef);
        	}
        	request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
        }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

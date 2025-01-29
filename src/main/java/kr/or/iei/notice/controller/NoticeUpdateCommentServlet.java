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
 * Servlet implementation class NoticeUpdateCommentServlet
 */
@WebServlet("/notice/updateComment")
public class NoticeUpdateCommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeUpdateCommentServlet() {
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
        	String commentId = request.getParameter("commentId");
        	String postId =  request.getParameter("postId");
        	String comments = request.getParameter("commentContent");
        	
        	NoticeComment comment = new NoticeComment();
        	comment.setCommentId(commentId);
        	comment.setPostId(postId);
        	comment.setComments(comments);
        	
        	NoticeService service= new NoticeService();
        	int result = service.updateComment(comment);
        	
        	if(result>0) {
        		request.setAttribute("title", "알림");
        		request.setAttribute("msg", "댓글 수정이 완료되었습니다.");
        		request.setAttribute("icon", "success");
        		request.setAttribute("loc", "/notice/view?postId="+postId+"&commentChk=chk");
        		
        	}else {
        		request.setAttribute("title", "알림");
        		request.setAttribute("msg", "댓글 수정중 오류가 발생");
        		request.setAttribute("icon", "error");
        		request.setAttribute("loc", "/notice/view?postId="+postId+"&commentChk=chk");
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

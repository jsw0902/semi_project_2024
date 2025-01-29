package kr.or.iei.notice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import kr.or.iei.search.word.service.SearchService;
import kr.or.iei.search.word.vo.Word;
import kr.or.iei.weather.model.vo.Weather;

/**
 * Servlet implementation class NoticeAnnounceUpdateServlet
 */
@WebServlet("/NoticeAnnounceUpdate")
public class NoticeAnnounceUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeAnnounceUpdateServlet() {
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
        	//로그인 세션이 있음
        	String boardId = request.getParameter("boardId"); //게시글 종류 코드
        	String boardName = request.getParameter("boardName"); //게시글 종류 명칭(공지사항, FAQ...)
        	
        	
        	
        	// 서비스 객체 호출하여 공지 리스트 조회
        	NoticeService service = new NoticeService();
        	
        	// 클라이언트로부터 받은 selectedPostIds 값들
        	String[] postIds = request.getParameterValues("selectedPostIds");
        	
        	System.out.println(postIds);
        	
        	if (postIds != null) {
        		// 각 postId에 대해 처리
        		List<String> postIdList = new ArrayList<>();
        		for (String postId : postIds) {
        		    try {
        		        // 문자열이 콤마로 구분된 경우 이를 분리
        		        String[] splitPostIds = postId.split(",");
        		        postIdList.addAll(Arrays.asList(splitPostIds)); // 분리된 값을 리스트에 추가
        		        for(String s : postIdList) {
        		        	System.out.println(s);
        		        }
        		    } catch (NumberFormatException e) {
        		        e.printStackTrace(); // 예외 처리
        		    }
        		}
        		
        		// 공지사항 업데이트 로직 호출
        		boolean result = service.updateAnounce(postIdList);
        		if (result) {
        			request.setAttribute("boardName", boardName);
        			request.setAttribute("boardId", boardId);
        			
        			request.getRequestDispatcher("/notice/list?sort=latest").forward(request, response);
        		} else {
        			// 실패 시 처리 (예: 에러 메시지 처리)
        			response.getWriter().write("공지사항 업데이트 실패");
        		}
        	} else {

        	}
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

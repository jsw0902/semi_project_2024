package kr.or.iei.member.controller;

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
import kr.or.iei.member.model.service.MemberService;
import kr.or.iei.member.model.vo.MemberPageData;
import kr.or.iei.member.model.vo.User;
import kr.or.iei.news.model.vo.NewsItem;
import kr.or.iei.search.word.service.SearchService;
import kr.or.iei.search.word.vo.Word;
import kr.or.iei.weather.model.vo.Weather;

/**
 * Servlet implementation class MemberAdminPageServlet
 */
@WebServlet("/member/adminPage")
public class MemberAdminPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberAdminPageServlet() {
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
		if(loginMember != null) {
			//현재 로그인된 회원 레벨이 1이 아닐 때 (관리자가 아닐 때)
			if(100 != loginMember.getUserGrade()) {
				request.setAttribute("title", "알림");
				request.setAttribute("msg", "해당 메뉴에 대한 접속 권한이 없습니다.");
				request.setAttribute("icon", "error");
				request.setAttribute("loc", "/member/mypage");
				
				request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
				
				return;
			}
		}else {
			//다시 로그인 요청
        	request.setAttribute("title", "로그인 기간 만료");
			request.setAttribute("msg", "다시 로그인 해주세요.");
			request.setAttribute("icon", "error");
			request.setAttribute("loc", "/index");
			
			request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
			return;
		}
		// 1. 인코딩 - 필터 사용 중이라면 생략 가능

        
        MemberService service = new MemberService();
        
        String reqPageStr = request.getParameter("reqPage");
        int reqPage = 1;
        if (reqPageStr != null && !reqPageStr.isEmpty()) {
            try {
                reqPage = Integer.parseInt(reqPageStr);
            } catch (NumberFormatException e) {
                reqPage = 1; // 기본값
            }
        }

        // 3. 로직 - 페이징 적용된 최신 가입순 회원 조회
        MemberPageData pd = service.selectMemberList(reqPage);

        // 4. 결과 처리
        request.setAttribute("memberList", pd.getList());
        request.setAttribute("pageNavi", pd.getPageNavi());
        request.setAttribute("currentPage", reqPage);
        // 정렬 관련 속성 제거
        // request.setAttribute("sort", sort); // 삭제

        request.getRequestDispatcher("/WEB-INF/views/member/adminPage.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

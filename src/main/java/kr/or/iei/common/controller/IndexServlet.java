package kr.or.iei.common.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.or.iei.aside.model.vo.Product;
import kr.or.iei.common.vo.DataFetcher;
import kr.or.iei.news.model.vo.NewsItem;
import kr.or.iei.search.word.service.SearchService;
import kr.or.iei.search.word.vo.Word;
import kr.or.iei.weather.model.vo.Weather;

/**
 * Servlet implementation class IndexServlet
 */

public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	//세션관리 - 복붙(?) 시작
    	HttpSession session = request.getSession();
        // 검색어 랭킹 데이터 가져오기
    	ServletContext context = request.getServletContext();
        SearchService srchService = new SearchService();
        ArrayList<Word> list = srchService.selectAllWord();
        context.setAttribute("wordList", list); // wordList 세션에 저장

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

        // 쇼핑 및 뉴스 데이터 JSP 전달 (필요하면)
        request.setAttribute("productList", productItems);
        request.setAttribute("newsList", newsItems);
        //세션관리 - 복붙(?) 끝

        // 인덱스 페이지로 포워딩
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
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

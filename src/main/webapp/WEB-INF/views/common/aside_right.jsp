<%@page import="kr.or.iei.search.word.vo.Word"%>
<%@page import="kr.or.iei.aside.model.vo.Product"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
ServletContext context = request.getServletContext();
ArrayList<Word> wordList = (ArrayList<Word>) context.getAttribute("wordList");
%>

<aside class="right-sidebar">
	<div class="search-rank">
		<div class="rank-text">
			<span>인기 검색어</span>
		</div>
		<div class="rank-bottom"></div>
		<div class="rank-wrapper">
			<ul class="rank-box">
				<%for(int i=0; i<wordList.size(); i++) {%>
				<li class="rank-item"><a href="/search?search=<%=wordList.get(i).getSrchWord() %>">
				<span class="keyword-rank-title"><%=wordList.get(i).getSrchRank() %>. </span> <span><%=wordList.get(i).getSrchWord() %></span>
				</a></li>
				<%} %>
			</ul>
		</div>
	</div>
	<div class="adv-box">
		<a href="https://maplestory.nexon.com/Home/Main">
			<img src="resources/image/god.gif" class="god">
		</a>
	</div>
	<div class="remote-control">
        <a href="#" id="nextBoard" class="remote-btn">다음 게시판</a>
        <a href="#" id="prevBoard" class="remote-btn">이전 게시판</a>
        <a href="#" id="scrollTop" class="remote-btn">위로 가기</a>
        <a href="#" id="scrollBottom" class="remote-btn">아래로 가기</a>
    </div>
</aside>

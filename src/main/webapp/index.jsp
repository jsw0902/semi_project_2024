<%@page import="kr.or.iei.news.model.vo.NewsItem"%>
<%@page import="kr.or.iei.search.word.vo.Word"%>
<%@page import="kr.or.iei.aside.model.vo.Product"%>
<%@page import="java.util.ArrayList"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
   ArrayList<NewsItem> newsList = (ArrayList<NewsItem>)request.getAttribute("newsList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
.section {
	margin-top: 20px;
}

.list-header {
	text-align: right;
	margin-bottom: 10px;
}

.list-header>a:hover {
	text-decoration: underline;
}

.selected-row {
	color: red;
}
</style>
</head>
<body>
	<jsp:include page="/WEB-INF/views/common/header.jsp" />
	<main class="main">
		<jsp:include page="/WEB-INF/views/common/aside_left.jsp" />
		<div class="content">
			<div class="pop-box">
				<section class="pop-section typePopular">
					<div class="page-title">
						<div class="page-title-position">
							<a class="page-title-a"
								href="/notice/list?reqPage=1&boardId=0&boardName=인기게시판"> <span>인기
									게시물</span>
							</a>
							<div class="list-header">
								<a href="/notice/list?reqPage=1&boardId=0&boardName=인기게시판">더보기</a>
							</div>
						</div>
						<div class="list-content">
							<table class="tbl hover">
								<tr class="th">
									<th style="width: 50%">제목</th>
									<th style="width: 10%">글쓴이</th>
									<th style="width: 10%">날짜</th>
									<th style="width: 10%">조회수</th>
									<th style="width: 10%">추천</th>
								</tr>
							</table>
						</div>
					</div>
					<div class="pop-bottom-line"></div>
				</section>
			</div>
			<c:forEach var="notice" items="${noticeTypeList }">
				<section class="section typeGeneral type${notice.boardId }">
					<div class="page-title">
						<div class="page-title-position">
							<a class="page-title-a"
								href="/notice/list?reqPage=1&boardId=${notice.boardId }&boardName=${notice.boardName}&sort=latest">
								<span>${notice.boardName}</span>
							</a>
							<div class="list-header">
								<a
									href="/notice/list?reqPage=1&boardId=${notice.boardId }&boardName=${notice.boardName}&sort=latest">더보기</a>
							</div>
						</div>
						<div class="list-content">
							<table class="tbl hover">
								<tr class="th">
									<th style="width: 50%">제목</th>
									<th style="width: 20%">작성자</th>
									<th style="width: 20%">작성일</th>
								</tr>
							</table>
						</div>
					</div>
					<div class="bottom-line"></div>
				</section>
			</c:forEach>
			<div class="last-bottom-line"></div>
			<div class="newsContainer">
				<%for(int i=0; i<newsList.size(); i++) {%>
				<div class="newsTitle">
					<a href="<%=newsList.get(i).getLink()%>"><%=newsList.get(i).getTitle() %></a>
				</div>
				<div class="newsDescription"><%=newsList.get(i).getDescription() %></div>
				<br>
				<%} %>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/common/aside_right.jsp" />
	</main>
	<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
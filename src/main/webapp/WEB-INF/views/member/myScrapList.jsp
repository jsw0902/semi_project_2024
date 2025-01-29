<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
</head>
<body>
	<div class="wrap">
				<jsp:include page="/WEB-INF/views/common/header.jsp" />
				<main class="main">
					<jsp:include page="/WEB-INF/views/common/aside_left.jsp" />
					<div class="content">
						<section class="section-list">
							<div class="page-title">내 글 전체 조회</div>
							<div class="lists-content">
									<table class="tbl hover">
										<tr class="list-text-comm">
											<th style="width: 15%;">번호</th>
							                <th style="width: 10%;">게시판명</th>
							                <th style="width: 50%;">글 제목</th>
							                <th style="width: 10%;">스크랩 등록일</th>
							                <th style="width: 15%;">스크랩 삭제</th>
										</tr>
										<c:forEach var="scrapList" items="${scrapList}">
							                <tr>
							                    <td style="text-align: center;">${scrapList.scrapId}</td>
							                    <td>${scrapList.boardName}</td>
							                    <td style="text-align: center;"><a href="/notice/view?postId=${scrapList.postId}">${scrapList.boardTitle}</a></td>
							                    <td>${scrapList.scrapDate}</td>
							                   	<td style="text-align: center;">
							                   		<a href="/scrap/remove?scrapId=${scrapList.scrapId}&userNo=${scrapList.userNo}">
							                   			<svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" fill="currentColor" class="bi bi-x-circle" viewBox="0 0 16 16">
														  <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14m0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16"/>
														  <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708"/>
														</svg>
														스크랩해제
							                   		</a>
							                   	</td>
							                </tr>
							            </c:forEach>
									</table>
									<div>
							</div>
							<div class="user-btn-position">
				    			<div id="pageNavi">${pageNavi}</div>
							</div>
						</div>
				</section>
			</div>
			<jsp:include page="/WEB-INF/views/common/aside_right.jsp" />
			</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>
	<h1>내 스크랩 조회</h1>
    <table border="1">
        <thead>
            <tr>
                <th>번호</th>
                <th>게시판명</th>
                <th>글 제목</th>
                <th>스크랩 등록일</th>
                <th>스크랩 삭제</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="scrapList" items="${scrapList}">
                <tr>
                    <td>${scrapList.postId}</td>
                    <td>${scrapList.boardName}</td>
                    <td>${scrapList.boardTitle}</td>
                    <td>${scrapList.scrapDate}</td>
                   	<td>
                   		<a href="/scrap/remove?scrapId=${scrapList.scrapId}&userNo=${scrapList.userNo}">스크랩 해제</a>
                   	</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
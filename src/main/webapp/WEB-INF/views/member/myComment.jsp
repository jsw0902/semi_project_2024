<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="wrap">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<main class="main">
			<jsp:include page="/WEB-INF/views/common/aside_left.jsp" />
			<div class="content">
				<section class="section-list">
					<div class="page-title">내 댓글 전체 조회</div>
					<div class="lists-content">
							<table class="tbl hover">
								<tr class="list-text-comm">
									<th style="width: 10%;">번호</th>
                					<th style="width: 50%;">내용</th>
                					<th style="width: 20%;">작성일</th>
								</tr>
								<c:forEach var="comment" items="${myComment}">
					                <tr>
					                    <td>${comment.commentId}</td>
					                    <td style="text-align: center;"><a href="/notice/view?postId=${comment.postId }">${comment.comments}</a></td>
					                    <td>${comment.commTime}</td>
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
</body>
</html>
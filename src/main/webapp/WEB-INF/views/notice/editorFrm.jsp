<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.notice-write-container{
		display: flex;
		justify-content: center;
		align-items: center;
	}
	.notice-write-wrap{
		width : 1000px;
	}
	.notice-write-wrap .input-item>input[type=text]{
		border : none;
		padding:0;
	}
	.notice-write-wrap .input-item>textarea{
		height:300px;
	}
	.input-item {
		background-color: white;
	}
	.input-item input{
		width:100%;
	}
	.btn-primary {
		width:100px;
		height:50px;
		display:flex;
		justify-content:center;
		background-color: green;
 		color:white;
 		border: 1px solid black;
 		border-radius: 20px;
 		align-items: center;
 	}
</style>

</head>
<body>

	
	<div class="wrap">
		<jsp:include page="/WEB-INF/views/common/header.jsp"/>
		<main class="content notice-write-container">
			<section class="section notice-write-wrap">
				<div class="page-title">${boardName} 작성</div>
				<form action="/notice/write" method="post" enctype="multipart/form-data">
					<input type="hidden" name="boardId" value="${boardId}">
					<input type="hidden" name="boardName" value="${boardName}">
					<input type="hidden" name="boardWriter" value="${loginMember.userNo }"> <%--현재 로그인 사용자 --%>
					<table class="tbl">
						<tr>
							<th style="width:20%;">제목</th>
							<td style="width:80%;">
								<div class="input-item">
									<input type="text" name="boardTitle">
								</div>
							</td>
						</tr>
						<tr>
							<th style="width:20%;">작성자</th>
							<td style="width:30%;">
								${loginMember.userId }
							</td>
							<th style="width:20%;">첨부파일</th>
							<td style="width:30%;">
								<input type="file" name="uploadFile">
							</td>
						</tr>
						<tr>
							<td colspan="4">
								<div class="input-item">
									<textarea id="boardContent" name="boardContent" escapeXml="false" style="background-color: white;"></textarea>
								</div>
							</td>
						</tr>
						<tr>
							<td colspan="3"></td>
							<td colspan="1" style="text-align: right;display: flex;justify-content: flex-end;">
								<button type="submit" class="btn-primary lg">작성하기</button>
							</td>
						</tr>
					</table>
				</form>
			</section>
		</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp"></jsp:include>
	</div>
		
<%-- summerNote 사용을 위한 라이브러리 --%>
<script src="/resources/summernote/summernote-lite.js"></script>
	<script src="/resources/summernote/lang/summernote-ko-KR.js"></script>
	<link rel="stylesheet" href="/resources/summernote/summernote-lite.css">
	
	<script>
		
		
		$('#boardContent').summernote({
			height: 500,
			width: 1000,
			lang: "ko-KR",
			disableResize: true,
			disableResizeEditor: true,
			resize: true,
		});
	
	</script>
	
	
	
</body>
</html> 
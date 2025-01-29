<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
* {
	box-sizing: border-box;
}

html, body {
	min-height: 100%;
}

.notice-view-wrap {
	margin: 0 auto;
	align-items: flex-start;
}

.container1 * {
	align-items: flex-start;
}

.noticeContent {
	min-height: 300px;
}

.comment-content textarea[name=commentContent] {
	width: 100px;
	height: 20px;
	margin-right: 10px;
}

.comment-content {
	max-width: 100%;
	overflow: hidden;
}

.comment-content li {
	float: left;
}

.inputCommentBox {
	margin-bottom: 20px;
}

.comment-content button {
	font-size: 20px;
}

.commentBox ul {
	margin-bottom: 15px;
	border-bottom: solid 1px black;
}

.btn-primary2 {
	display: flex;
	justify-content: center;
	width: 80px;
	height: 50px;
	border: 1px solid black;
	background-color: green;
	color: white;
	border-radius: 20px;
	align-items: center;
}

.btn-primary3 {
	display: flex;
	justify-content: center;
	width: 80px;
	height: 30px;
	border: 1px solid black;
	background-color: green;
	color: white;
	border-radius: 20px;
	align-items: center;
}

.btn-secondary2 {
	display: flex;
	justify-content: center;
	width: 80px;
	height: 50px;
	border: 1px solid black;
	background-color: red;
	color: white;
	border-radius: 20px;
	align-items: center;
}

.row {
	border-bottom: 1px solid black;
	display: flex;
	justify-content: space-between;
	align-items: center;
	width: 100%;
	box-sizing: border-box;
}

.row a {
	display: inline-block;
	margin-right: 5px;
	text-decoration: none;
	color: black;
}

.mod-button {
	display: flex;
	padding-bottom: 5px;
	border-top: 2px solid rgba(0, 0, 0, 0.5);
	justify-content: space-between;
	margin-bottom: 10px;
}

.mod-button2 {
	display: flex;
}

.input-item {
	width: 70%;
	height: 150%;
}

.input-item textarea {
	width: 100%;
	height: 100%;
	resize: none;
	word-wrap: break-word;
}

.view-content {
	max-width: 100%;
	background-color: white;
	border-bottom: 2px solid rgba(0, 0, 0, 0.5);
}

.view-content>* {
	max-width: 100%;
	box-sizing: border-box;
}

.material-icons {
	width: 100%;
	font-size: 28px;
	font-weight: bold;
}

.comment-info a {
	color: black;
}

.comment-info a:hover {
	text-decoration: underline;
}

.posting-comment {
	padding: 0px;
	border: 1px solid black;
}

.view-title {
	font-weight: bold;
	padding: 30px;
	text-align: center;
	font-family: ns-b;
	font-size: 40px;
	color: black;
}

.title {
	font-size: 18px;
}
</style>
</head>
<body>
	<div class="wrap">
		<jsp:include page="/WEB-INF/views/common/header.jsp" />
		<main class="main">
			<jsp:include page="/WEB-INF/views/common/aside_left.jsp" />
			<div class="content">
				<section class="section notice-view-wrap" style="width: 95%">
					<div class="container1" style="background-color: white;">
						<div class="view-title"
							style="background-color: gray; color: white;">${notice.boardName}</div>
						<div class="view-content">
							<div class="viewtbl notice-view">

								<div class="title"
									style="width: 100%; background-color: #E0E0E0;">${notice.boardTitle}</div>
							</div>

							<div class="row g-0">
								<div style="width: 50%;">${notice.nickname}</div>
								<div style="width: 50%; background-color: white;">작성일:
									${notice.createdDate} | 추천수: ${notice.likes} | 조회수:
									${notice.readCount} | 댓글: ${fn:length(notice.commentList)}</div>
							</div>

							<div class="row g-0">
								<div style="width: 15%; background-color: #E0E0E0;">첨부파일</div>
								<div class="color" style="width: 85%">
									<c:forEach var="file" items="${notice.fileList}">
										<a
											href="javascript:fileDown('${file.fileName}', '${file.filePath}')"
											style="color: black">${file.fileName}. </a>
									</c:forEach>
								</div>
							</div>


							<div class="contents"
								style="width: 100%; background-color: white;">
								<div class="noticeContent">${notice.boardContent}</div>
							</div>


							<c:if test="${not empty loginMember}">
								<div class="mod-button">
									<button type="button" class="btn-primary2"
										onclick="recommend(${loginMember.userNo}, ${notice.postId}, ${notice.boardWriter})">추천하기</button>
									<div class="mod-button2">
										<button type="button" class="btn-primary2" onclick="scrapPost('${loginMember.userNo}', '${notice.postId}', '${notice.boardName}')">스크랩</button>
										<c:if
											test="${loginMember.userNo eq notice.boardWriter or loginMember.userGrade eq '100'}">
											<c:if test="${loginMember.userNo eq notice.boardWriter}">
												<a href='/notice/updateFrm?postId=${notice.postId}'
													class="btn-primary2">수정</a>
											</c:if>
											<button class="btn-secondary2"
												onclick="deleteNotice('${notice.postId}')">삭제</button>
										</c:if>
									</div>
								</div>
							</c:if>

						</div>
						<div class="material-icons" style="width: 100%">댓글</div>
						<c:if test="${not empty loginMember}">
							<div class="inputCommentBox">
								<form name="insertComment" action="/notice/insertComment">
									<input type="hidden" name="postId" value="${notice.postId}">
									<input type="hidden" name="boardId" value="${notice.boardId}">
									<input type="hidden" name="commentWriter"
										value="${loginMember.userNo}">

									<ul>
										<li>
											<div class="input-item">
												<textarea id="inputField" name="commentContent"
													placeholder="최대 500byte"></textarea>
											</div>
										</li>
										<li>
											<button type="submit" class="btn-primary3">등록</button>
										</li>
									</ul>
								</form>
							</div>
						</c:if>
						<br>
						<div class="commentBox" style="background-color: white">
							<c:forEach var="comment" items="${notice.commentList}">
								<ul class="posting-comment">

									<li>
										<p class="comment-info" style="background-color: #E0E0E0">
											<span>${comment.commenterName}</span> <span>${comment.commTime}</span>
											<c:if
												test="${not empty loginMember and loginMember.userNo eq comment.commentWriter or loginMember.userGrade eq '100'}">
												<c:if
													test="${not empty loginMember and loginMember.userNo eq comment.commentWriter}">
													<a href='javascript:void(0)'
														onclick="mdfComment(this,'${comment.commentId}');">수정</a>
												</c:if>
												<a href='javascript:void(0)'
													onclick="delComment('${comment.commentId}');">삭제</a>
											</c:if>
										</p>
										<p class="comment-content">${comment.comments}</p>
										<div class="input-item" style="display: none;">
											<textarea name="commentContent">${comment.comments}</textarea>
										</div>
									</li>
								</ul>
							</c:forEach>
						</div>


					</div>
				</section>
			</div>
			<jsp:include page="/WEB-INF/views/common/aside_right.jsp" />
		</main>
		<jsp:include page="/WEB-INF/views/common/footer.jsp" />
	</div>
	<script>
		function fileDown(fileName, filePath) {
			location.href = '/notice/fileDown?fileName=' + fileName + '&filePath=' + filePath;
		}
		
		
		
		
		function deleteNotice(postId) { // 삭제 버튼 클릭 시 호출
			swal({
				title: "삭제",
				text: "게시글을 삭제하시겠습니까?",
				icon: "warning",
				buttons: {
					cancel: {
						text: "취소",
						value: false,
						visible: true,
						closeModal: true
					},
					confirm: {
						text: "삭제",
						value: true,
						visible: true,
						closeModal: true
					}
				}
			}).then(function(isConfirm) {
				if (isConfirm) {
					let postId = '${notice.postId}';
					location.href = '/notice/delete?postId=' + postId;
				}
			});
			
			
		}

		function delComment(commentId) { // 삭제 버튼 클릭 시 호출
			swal({
				title: "삭제",
				text: "댓글을 삭제하시겠습니까?",
				icon: "warning",
				buttons: {
					cancel: {
						text: "취소",
						value: false,
						visible: true,
						closeModal: true
					},
					confirm: {
						text: "삭제",
						value: true,
						visible: true,
						closeModal: true
					}
				}
			}).then(function(isConfirm) {
				if (isConfirm) {
					let postId = '${notice.postId}';
					location.href = '/notice/deleteComment?postId=' + postId + '&commentId=' + commentId;
				}
			});
		}
		
		function mdfComment(obj, commentId) { // 수정 기능 활성화
			let commentContentLi = $(obj).parents('li');
			$(commentContentLi).find('div.input-item').show();
			$(commentContentLi).find('p.comment-content').hide();

			$(obj).text('수정완료');
			$(obj).attr('onclick', 'mdfCommentComplete(this,"'+commentId+'")');
			
			$(obj).next().text('취소');
			$(obj).next().attr('onclick', 'mdfCommentCancel(this,"'+commentId+'")');
		}
		
		function mdfCommentComplete(obj, commentId) { // 수정 완료
			let form = $('<form>');
			form.attr('action', '/notice/updateComment');
			form.attr('method', 'post');
			
			let postId = '${notice.postId}';
			form.append($('<input type="hidden" name="postId">').val(postId));
			form.append($('<input type="hidden" name="commentId">').val(commentId));

			let commentContent = $(obj).parents('li').find('textarea[name="commentContent"]').val();
			form.append($('<input type="hidden" name="commentContent">').val(commentContent));
			
			$('body').append(form);
			form.submit();
		}
		
		function mdfCommentCancel(obj, commentId) { // 수정 취소
			let commentContentLi = $(obj).parents('li');
			$(commentContentLi).find('div.input-item').hide();
			$(commentContentLi).find('p.comment-content').show();
			
			$(obj).text('삭제');
			$(obj).attr('onclick', 'delComment("'+commentId+'")');
			
			$(obj).prev().text('수정');
			$(obj).prev().attr('onclick', 'mdfComment(this,"'+commentId+'")');
		}
		
		function recommend(userNo, postId, boardWriter) {
		    if (userNo && postId && boardWriter) {
		        location.href = '/notice/recommend?postId=' + postId + "&userNo=" + userNo+"&boardWriter="+boardWriter;
		        console.log(postId, userNo, boardWriter);
		    } else {
		        console.error('userNo 또는 postId, boardWriter 가 정의되지 않았습니다.');
		        
		    }
		}
		
		//입력 수 제한
 		document.querySelector("#inputField").addEventListener("input", function () {
		    const inputField = this; // 이벤트 대상
		    const maxBytes = 500; // 최대 바이트 수
		    const inputValue = inputField.value;
		    let currentBytes = 0; // 현재 바이트 계산 변수
		    let truncatedValue = ""; // 잘라낸 결과값 저장
		
		    for (let i = 0; i < inputValue.length; i++) {
		        const char = inputValue.charAt(i);
		        const charBytes = new Blob([char]).size; // 문자 하나의 바이트 수 계산
		
		        if (currentBytes + charBytes > maxBytes) {
		            break; // 최대 바이트 초과 시 루프 종료
		        }
		        truncatedValue += char; // 허용 범위 내 문자 추가
		        currentBytes += charBytes; // 누적 바이트 계산
		    }
		
		    // 초과 부분 자르고 입력값 업데이트
		    if (inputValue !== truncatedValue) {
		        inputField.value = truncatedValue;
		        alert("입력은 최대 500바이트까지만 가능합니다."); // 알림 메시지
		    }
		});
 		
 		function scrapPost(userNo, postId, boardName) {
 		    if (userNo && postId) {
 		        location.href = '/scrap/add?postId=' + postId + '&userNo=' + userNo + '&boardName=' + boardName;
 		    } else {
 		        swal("로그인이 필요합니다.", "로그인 후 이용해주세요.", "warning");
 		    }
 		}
	</script>
</body>
</html>

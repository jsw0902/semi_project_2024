// 현재 boardId와 reqPage를 URL에서 추출하는 함수
function getCurrentParams() {
	const urlParams = new URLSearchParams(window.location.search);
	const reqPage = parseInt(urlParams.get('reqPage')) || 1; // 기본값 1
	const boardId = parseInt(urlParams.get('boardId')) || 1; // 기본값 1
	return { reqPage, boardId };
}

// URL의 쿼리 파라미터를 업데이트하거나 추가하는 함수
function updateQueryStringParameter(uri, key, value) {
	const url = new URL(uri, window.location.origin);
	url.searchParams.set(key, value);
	return url.toString();
}

// 이전 게시판으로 이동
$('#prevBoard').click(function(e) {
	e.preventDefault();
	const { reqPage, boardId } = getCurrentParams();
	if (boardId > 1) {
		const prevBoardId = boardId - 1;
		const newUrl = `/notice/list?reqPage=1&boardId=${prevBoardId}&sort=latest`;
		window.location.href = newUrl;
	} else {
		swal({
			title: "알림",
			text: "현재 첫 번째 게시판입니다.",
			icon: "info",
			button: "확인"
		});
	}
});

// 다음 게시판으로 이동
$('#nextBoard').click(function(e) {
	e.preventDefault();
	const { reqPage, boardId } = getCurrentParams();
	const nextBoardId = boardId + 1;
	const maxBoardId = 8; // 실제 최대 게시판 ID로 변경하세요

	if (nextBoardId <= maxBoardId) {
		const newUrl = `/notice/list?reqPage=1&boardId=${nextBoardId}&sort=latest`;
		window.location.href = newUrl;
	} else {
		swal({
			title: "알림",
			text: "현재 마지막 게시판입니다.",
			icon: "info",
			button: "확인"
		});
	}
});

// 위로 가기
$('#scrollTop').click(function(e) {
	e.preventDefault();
	$('html, body').animate({ scrollTop: 0 }, 'slow');
});

// 아래로 가기
$('#scrollBottom').click(function(e) {
	e.preventDefault();
	$('html, body').animate({ scrollTop: $(document).height() }, 'slow');
});



//일반 게시글
function noticeList() {
    $.ajax({
        url: "/notice/index", // 일반 게시물 API 호출
        type: "GET",
        dataType: "json",
        success: function(res) {
            console.log("Notice List:", res);
			
			// 기존 데이터 초기화 (헤더는 유지)
			$('.section.typeGeneral .tbl tr:not(.th)').remove();
			
            $(res).each(function(index, item) {
                let html = '';
				if (item.noticeYn === 'Y') {
					html += "<tr class='nomal selected-row'>"; // When noticeYn is 'Y'
				} else {
					html += "<tr class='nomal'>"; // When noticeYn is 'N'
				}
                html += "<input type='hidden' value='" + item.boardId + "'>";
                html += "<td><a href='/notice/view?postId=" + item.postId + "'>" + item.boardTitle + "</a></td>";
                html += "<td>" + item.nickname + "</td>";
                html += "<td>" + item.createdDate + "</td>";
                html += "</tr>";

                // 각 게시판에 해당하는 테이블에 데이터 추가
                $('.section.type' + item.boardId).find('.tbl').append(html);
            });
        },
        error: function() {
            console.log("일반 게시글 로딩 실패");
        }
    });
}

function popularNoticeList() {
    $.ajax({
        url: "/notice/popular", // 인기 게시물 API 호출
        type: "GET",
        dataType: "json",
        success: function(res) {
            console.log("Popular Notice List:", res);
			$('.section.typePopular .tbl tr:not(.th)').remove();

            $(res).each(function(index, item) {
				let html = '';
				html += "<tr class='nomal'>"; // 인기 게시물
				html += "<input type='hidden' value='" + item.boardId + "'>";
				html += "<td><a href='/notice/view?postId=" + item.postId + "'>" + item.boardTitle + "</a></td>";
				html += "<td>" + item.nickname + "</td>";
				html += "<td>" + item.createdAt + "</td>";
				html += "<td>" + item.views + "</td>"; // 조회수 추가
				html += "<td>" + item.likes + "</td>"; // 추천수 추가
				html += "</tr>";
                // 인기 게시물 테이블에 데이터 추가
                $('.pop-section.typePopular').find('.tbl').append(html);
            });
        },
        error: function() {
            console.log("인기 게시글 로딩 실패");
        }
    });
}
// 페이지 로드 시 및 주기적으로 데이터를 갱신
$(function() {
    noticeList(); // 일반 게시물 데이터 로드
    popularNoticeList(); // 인기 게시물 데이터 로드
});

// 매 10분마다 데이터 갱신
setInterval(function() {
    popularNoticeList();
    noticeList();
}, 1000 * 60 * 10); // 10분마다 실행

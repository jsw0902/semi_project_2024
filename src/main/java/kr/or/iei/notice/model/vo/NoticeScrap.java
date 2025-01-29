package kr.or.iei.notice.model.vo;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoticeScrap {
	private String scrapId;
	private String userNo;
	private String boardName;
	private String postId;
	private String scrapDate;
	private String boardTitle; // 추가된 필드
}

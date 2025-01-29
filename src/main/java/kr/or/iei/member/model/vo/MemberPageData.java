package kr.or.iei.member.model.vo;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class MemberPageData {
	 private ArrayList<User> list;    // 현재 페이지에 표시될 회원 목록
	 private String pageNavi; 
}

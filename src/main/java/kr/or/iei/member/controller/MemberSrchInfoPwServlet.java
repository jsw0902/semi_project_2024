package kr.or.iei.member.controller;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.or.iei.member.model.service.MemberService;

/**
 * Servlet implementation class MemberSrchInfoPw
 */
@WebServlet("/member/srchInfoPw")
public class MemberSrchInfoPwServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberSrchInfoPwServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userEmail=request.getParameter("userEmail");
		String userId=request.getParameter("userId");
		MemberService service=new MemberService();
		int control=0;
		String email=service.selectUser(userId,userEmail);
		
		if(email!=null) {
			String upper="ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			String lower="abcdefghijklmnopqrstuvwxyz";
			String digit="0123456789";				
			String special="!@#$";
			String allStr=upper+lower+digit+special;
			
			SecureRandom random=new SecureRandom();//난수발생
			StringBuilder ranPw=new StringBuilder();//임시비밀번호
			
			//각각 최소 1개 이상은 포함되도록
			ranPw.append(upper.charAt(random.nextInt(upper.length())));
			ranPw.append(lower.charAt(random.nextInt(lower.length())));
			ranPw.append(digit.charAt(random.nextInt(digit.length())));
			ranPw.append(special.charAt(random.nextInt(special.length())));
			for(int i=0;i<6;i++) {
				ranPw.append(allStr.charAt(random.nextInt(allStr.length())));//나머지는 전체 문자열에서
			}
			//임시비밀번호 10자리는 완성했으나 첫 4글자 형태가 고정이므로 무작위로 섞어줌
			char []allChars=ranPw.toString().toCharArray();
			for(int i=0;i<allChars.length;i++) {
				int ranIdx=random.nextInt(allChars.length);
				//현재 i번쨰 인뎃그 요소와 난수번쨰 있는 요소를 바꿈
				char temp=allChars[i];
				allChars[i]=allChars[ranIdx];
				allChars[ranIdx]=temp;
			}
			
			String newPw=new String(allChars);
			//유djqepdlxm
			
			int result=service.replaceMemberPw(userId,newPw);
			if(result>0) {
				Properties prop=new Properties();
				prop.put("mail.smtp.host","smtp.naver.com");
				prop.put("mail.smtp.port","465");
				prop.put("mail.smtp.auth","true");
				prop.put("mail.smtp.ssl.enable", "true");
				prop.put("mail.smtp.ssl.trust", "smtp.naver.com");
				
				Session session=Session.getDefaultInstance(prop,new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication("pkj9089@naver.com","asaasasa7810");
					}
				});
				MimeMessage msg=new MimeMessage(session);
				try {
					msg.setSentDate(new Date());
					msg.setFrom(new InternetAddress("pkj9089@naver.com","kh정보교육원"));
					InternetAddress to = new InternetAddress(email);
					msg.addRecipient(Message.RecipientType.TO, to);
					msg.setSubject("임시 비밀번호 발급");
					msg.setContent("임시 비밀번호는 [<span style='color:red;font-weight:bold;'>"+newPw+"</span>]입니다","text/html; charset=utf-8");
					Transport.send(msg);
				}catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					}
				control=1;
				}else {
					//DB업데이트가 정상적으로 이루어지지 못했을 떄
					control=0;
				}
		}else{
			control=-1;
		}
		response.getWriter().print(control);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

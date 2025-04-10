package com.legacydiary.util;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.stereotype.Component;

import com.mysql.cj.log.Log;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SendMailService {
	
	private String username;
	private String password;
	
	public void sendMail(String emailAddr, String activationCode) throws FileNotFoundException, IOException, AddressException, MessagingException {
		
		String subject = "legacydiary.com에서 보내는 회원가입 이메일 인증번호입니다";
		String message = "회원가입을 환영합니다. 인증번호를 입력하시고 회원가입을 완료하세요" + "인증 번호 : " + activationCode;
		
		Properties props = new Properties();
		
		// -- 
		props.put("mail.smtp.host", "smtp.naver.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.starttls.required", "true");
		props.put("mail.smtp.ssl.protocols", "TLSv1.2");
		props.put("mail.smtp.auth", "true");
//		props.put("mail.smtp.ssl.trust", "smtp.naver.com"); // could 머시기 오류 떳을때 추가?
		
		getAccount();
		
		// 세션 생성
		Session mailSession = Session.getInstance(props, new Authenticator() {
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		
		System.out.println(mailSession.toString());
		log.info("mailSession : {} " , mailSession);
		
		if (mailSession != null) {
			
			MimeMessage mime = new MimeMessage(mailSession);
			mime.setFrom(new InternetAddress("alkali7355@naver.com")); // 보내는 사람의 메일 주소
			mime.addRecipient(RecipientType.TO, new InternetAddress(emailAddr)); // 받는 사람의 메일 주소
			
			mime.setSubject(subject);
//			mime.setText(message);
			String html = "<h1>회원가입을 환영합니다.</h1>";
			html += "<h2>회원가입을 환영합니다. 인증번호를 입력하시고 회원가입을 완료하세요</h2>";
			html += "<h3>인증 코드 : </h3>";
			html += "<h3>" + activationCode + "</h3>";
			mime.setText(html, "utf-8", "html");
			Transport.send(mime);
		}
		
		
	}
	
	private void getAccount() throws FileNotFoundException, IOException {
		Properties account = new Properties();
		account.load(new FileReader("C:\\lecture\\spring\\legacy-diary\\src\\main\\webapp\\WEB-INF\\config.properties"));
		this.username = (String)account.get("username");
		this.password = (String)account.get("password");
		
//		System.out.println("username : " + this.username);
//		System.out.println("password : " + this.password);
		
	}
	
}

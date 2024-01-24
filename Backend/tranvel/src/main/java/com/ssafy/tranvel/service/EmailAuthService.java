package com.ssafy.tranvel.service;

import com.ssafy.tranvel.repository.EmailAuthDao;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmailAuthService {

    private final EmailAuthDao emailAuthDao;
    private final JavaMailSender emailSender;
    private String verificationCode;

    public String createVerificationCode() {
        Random random = new Random();
        StringBuffer tmpCode = new StringBuffer();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        Set<Character> usedChars = new HashSet<>();
        // 4자리의 랜덤 코드 생성
        while (tmpCode.length() < 4) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            // 중복 검사
            if (!usedChars.contains(randomChar)) {
                tmpCode.append(randomChar);
                usedChars.add(randomChar);
            }
        }
        return tmpCode.toString();
    }

    public MimeMessage createEmailForm(String email) throws MessagingException, UnsupportedEncodingException {

        String setFrom = "gumissafy00@gmail.com";
        String toEmail = email;
        String title = "[Tranvel] 회원가입 인증을 완료해주세요.";

        MimeMessage message = emailSender.createMimeMessage();
        message.addRecipients(MimeMessage.RecipientType.TO, toEmail);

        String msgOfEmail = "안녕하세요, Tranvel 입니다."
                + "<br>"
                + "이메일 인증을 완료하시려면 아래의 인증 코드를 입력해주세요."
                + "<br>"
                + "인증 번호 : <strong>"
                + createVerificationCode()
                + "</strong>"
                + "<br>";

        message.setFrom();
        message.setText(msgOfEmail, "utf-8", "html");

        return message;



    }

}

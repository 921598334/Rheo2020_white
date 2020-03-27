package com.Rheo.Rheo2020.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServer {

    @Autowired
    JavaMailSender javaMailSender;


    public void sendMail(String to,String title,String content){

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("921598334@qq.com");
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(content);

        javaMailSender.send(simpleMailMessage);
    }

}

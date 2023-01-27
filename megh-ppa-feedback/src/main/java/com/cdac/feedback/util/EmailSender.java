package com.cdac.feedback.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Configuration
public class EmailSender {
	
	
	
@Value("${spring.mail.username}")
private String fromAddress;

	@Autowired
    private JavaMailSender javaMailSender;
   
    public void sendEmail(String receipient, String subject, String bodytext) throws MessagingException, IOException {

       
    	MimeMessage msg= javaMailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(msg, true);
    	
    	helper.setTo(receipient);

    	helper.setFrom(fromAddress);
    	
    	helper.setSubject(subject);
    	helper.setText(bodytext,true);
       
        javaMailSender.send(msg);

    }


	
}

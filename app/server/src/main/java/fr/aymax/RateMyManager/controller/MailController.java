package fr.aymax.RateMyManager.controller;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("mail")
public class MailController 
{
	@Autowired
    private JavaMailSender sender;

    @PostMapping("/send")
    String send(@RequestBody Map<String, Object> mailInfo) 
    {
        try {
            sendEmail(mailInfo.get("to").toString());
            return "Email Sent!";
        } catch(Exception e) {
            return "Error in sending email: "+e;
        }
    }

    private void sendEmail(String to) throws Exception 
    {
		System.out.println(to);
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        
        helper.setFrom("test@localhost");
        helper.setTo(to);
        helper.setText("How are you?");
        helper.setSubject("Hi");
        
        sender.send(message);
    }
}

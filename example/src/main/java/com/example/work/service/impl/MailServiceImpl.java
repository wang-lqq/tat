package com.example.work.service.impl;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.work.service.MailService;
/**
 * 邮件服务实现类
 * @author SSX_IT08112
 *
 */
@Service
public class MailServiceImpl implements MailService{
	
    @Autowired
    private JavaMailSenderImpl mailSender;
    
    @Autowired
	private TemplateEngine templateEngine;
    
    @Value("${spring.mail.username}")  
    private String username; 
	
	@Override
	public void sendMail(String to, String subject, String template, Map<String, Object> data) throws MessagingException {
		MimeMessage mimeMessage =mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setFrom(username);
		mimeMessageHelper.setTo(to);
		mimeMessageHelper.setSubject(subject);
		Context ctx = new Context();
		ctx.setVariable("data", data);
		String emailText = templateEngine.process(template, ctx);
		mimeMessageHelper.setText(emailText, true);
		mailSender.send(mimeMessage);
	}
	
	@Override
	public void sendMail(String[] to, String subject, String template, Map<String, Object> data) throws MessagingException {
		MimeMessage mimeMessage =mailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setFrom(username);
		mimeMessageHelper.setTo(to);
		mimeMessageHelper.setSubject(subject);
		Context ctx = new Context();
		ctx.setVariable("data", data);
		String emailText = templateEngine.process(template, ctx);
		mimeMessageHelper.setText(emailText, true);
		mailSender.send(mimeMessage);
	}
}

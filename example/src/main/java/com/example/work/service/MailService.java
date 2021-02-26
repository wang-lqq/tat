package com.example.work.service;

import java.util.Map;

import javax.mail.MessagingException;

/**
 * 邮件服务
 * @author SSX_IT08112
 *
 */
public interface MailService {
	void sendMail(String to, String subject, String template, Map<String, Object> data) throws MessagingException ;

	void sendMail(String[] to, String subject, String template, Map<String, Object> data) throws MessagingException;
}

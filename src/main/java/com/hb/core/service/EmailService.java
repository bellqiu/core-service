package com.hb.core.service;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hb.core.entity.HTML;
import com.hb.core.util.Constants;
import com.honeybuy.shop.web.cache.HtmlServiceCacheWrapper;
import com.honeybuy.shop.web.cache.SettingServiceCacheWrapper;

import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class EmailService {
	
	private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
	
	@Autowired
	HtmlServiceCacheWrapper htmService;
	
	@Autowired
	SettingServiceCacheWrapper settingService;
	
	public void sendRecoverMail(String toEmail, String newPassword) {
		String recoverPwdTemplate = getTemplateFromDB(Constants.HTML_MAIL_RECOVER_PASSWORD_TEMPLATE);
		String recoverSubject = settingService.getStringValue(Constants.SETTING_RECOVER_PASSWORD_SUBJECT, Constants.DEFAULT_RECOVERY_MAIL_TITLE);
		if(recoverPwdTemplate == null) {
			recoverPwdTemplate = Constants.DEFAULT_RECOVERY_MAIL_CONTENT + newPassword;
		}
		
		Map<String, Object> variable = new HashMap<String, Object>();
		variable.put("email", toEmail);
		variable.put("password", newPassword);
		
		sendMail(recoverPwdTemplate, recoverSubject, variable, toEmail);
	}
	
	public void sendRegisterMail(String toEmail, String password) {
		String registerTemplate = getTemplateFromDB(Constants.HTML_MAIL_REGISTER_TEMPLATE);
		String recoverSubject = settingService.getStringValue(Constants.SETTING_REGISTER_SUBJECT, Constants.DEFAULT_REGISTER_MAIL_TITLE);
		if(registerTemplate == null) {
			registerTemplate = Constants.DEFAULT_REGISTER_MAIL_CONTENT;
		}
		
		Map<String, Object> variable = new HashMap<String, Object>();
		variable.put("email", toEmail);
		variable.put("password", password);
		
		sendMail(registerTemplate, recoverSubject, variable, toEmail);
	}
	
	private String getTemplateFromDB(String htmlKey) {
		HTML html = htmService.getHTML(htmlKey);
		if(html != null) {
			return html.getContent();
		}
		return null;
	}
	
	public void sendMail(String templateString, String subject, Map<String,Object> variable, String sendTo){
    	String mailContent = parseMailContent(templateString, variable);
    	logger.info("send mail to :" + sendTo);
    	if (mailContent != null) {
    		HtmlEmail email = new HtmlEmail();
    	    try {
    	    	String hostname = settingService.getStringValue(Constants.SETTING_MAIL_HOST_NAME, Constants.DEFAULT_MAIL_HOST_NAME);
    			String mailAccount = settingService.getStringValue(Constants.SETTING_MAIL_ACCOUNT, Constants.DEFAULT_MAIL_FROM_ACCOUNT);
    			String mailPassword = settingService.getStringValue(Constants.SETTING_MAIL_PASSWORD, Constants.DEFAULT_MAIL_FROM_PASSWORD);
    			String mailFrom = settingService.getStringValue(Constants.SETTING_MAIL_FROM, Constants.DEFAULT_MAIL_FROM_ACCOUNT);
    	    	email.setHostName(hostname);
    	    	email.setAuthentication(mailAccount, mailPassword);
    	    	email.setFrom(mailFrom);
    	        email.setSubject(subject);
    	        email.setHtmlMsg(mailContent);
    	        email.setTLS(true);
    	        email.addTo(sendTo);
    	        email.setCharset(Constants.DEFAULT_MAIL_CHARSET);
    	        email.send();
    	    } catch (EmailException e) {
    	        logger.error(e.getMessage(), e);
    	    }
        } else {
            logger.error("cannot parse email template");
        }
    }
	
	private String parseMailContent(String templeteString,
			Map<String, Object> variables) {
		try {
			Template tpl = new Template("mail", new StringReader(templeteString), new Configuration());
			StringWriter writer = new StringWriter();
			tpl.process(variables, writer);
			return writer.toString();
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
			return null;
		}
	}

}
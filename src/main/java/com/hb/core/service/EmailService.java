package com.hb.core.service;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.cxf.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.hb.core.entity.Currency;
import com.hb.core.entity.HTML;
import com.hb.core.exception.CoreServiceException;
import com.hb.core.shared.dto.OrderDetailDTO;
import com.hb.core.util.Constants;
import com.honeybuy.shop.web.cache.CurrencyServiceCacheWrapper;
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
	
	@Autowired
	private CurrencyServiceCacheWrapper currencyService;
	
	public void sendRecoveryMail(String toEmail, String newPassword) {
		String email = getThrirdPartyEmail(toEmail);
		String recoverPwdTemplate = getTemplateFromDB(Constants.HTML_MAIL_RECOVER_PASSWORD_TEMPLATE);
		String recoverSubject = settingService.getStringValue(Constants.SETTING_RECOVER_PASSWORD_SUBJECT, Constants.DEFAULT_RECOVERY_MAIL_TITLE);
		if(recoverPwdTemplate == null) {
			recoverPwdTemplate = Constants.DEFAULT_RECOVERY_MAIL_CONTENT + newPassword;
		}
		
		Map<String, Object> variable = new HashMap<String, Object>();
		variable.put("email", toEmail);
		variable.put("password", newPassword);
		
		sendMail(recoverPwdTemplate, recoverSubject, variable, email);
	}
	
	public void sendRegisterMail(String toEmail, String password) {
		String email = getThrirdPartyEmail(toEmail);
		String registerTemplate = getTemplateFromDB(Constants.HTML_MAIL_REGISTER_TEMPLATE);
		String registerSubject = settingService.getStringValue(Constants.SETTING_REGISTER_SUBJECT, Constants.DEFAULT_REGISTER_MAIL_TITLE);
		if(registerTemplate == null) {
			registerTemplate = Constants.DEFAULT_REGISTER_MAIL_CONTENT;
		}
		
		Map<String, Object> variable = new HashMap<String, Object>();
		variable.put("email", toEmail);
		variable.put("password", password);
		
		
		sendMail(registerTemplate, registerSubject, variable, email);
	}
	
	public void sendPayOrderMail(OrderDetailDTO order) {
		String email = getThrirdPartyEmail(order.getUseremail());
		String payOrderTemplate = getTemplateFromDB(Constants.HTML_MAIL_TO_PAY_ORDER_TEMPLATE);
		String payOrderSubject = settingService.getStringValue(Constants.SETTING_TO_PAY_ORDER_SUBJECT, Constants.DEFAULT_TO_PAY_ORDER_MAIL_TITLE);
		if(payOrderTemplate == null) {
			payOrderTemplate = Constants.DEFAULT_TO_PAY_ORDER_MAIL_CONTENT;
		}
		
		Map<String, Object> variable = new HashMap<String, Object>();
		variable.put("order", order);
		Currency currency = currencyService.getCurrencyByCode(order.getCurrency());
		if(currency == null) {
			currency = currencyService.getDefaultSettingCurrency();
		}
		variable.put("currency", currency);
		
		sendMail(payOrderTemplate, payOrderSubject, variable, email);
	}
	
	public void sendReceiveOrderPaymentMail(OrderDetailDTO order) {
		String email = getThrirdPartyEmail(order.getUseremail());
		String receiveOrderPaymentTemplate = getTemplateFromDB(Constants.HTML_MAIL_RECEIVE_ORDER_PAYMENT_TEMPLATE);
		
		String receiveOrderPaymentSubject = settingService.getStringValue(Constants.SETTING_RECEIVE_ORDER_PAYMENT_SUBJECT, Constants.DEFAULT_RECEIVE_ORDER_PAYMENT_TITLE);
		if(receiveOrderPaymentTemplate == null) {
			receiveOrderPaymentTemplate = Constants.DEFAULT_RECEIVE_ORDER_PAYMENT_CONTENT;
		}
		
		Map<String, Object> variable = new HashMap<String, Object>();
		variable.put("order", order);
		Currency currency = currencyService.getCurrencyByCode(order.getCurrency());
		if(currency == null) {
			currency = currencyService.getDefaultSettingCurrency();
		}
		variable.put("currency", currency);
		
		sendMail(receiveOrderPaymentTemplate, receiveOrderPaymentSubject, variable, email);
	}
	
	public void sendShippingOrderMail(OrderDetailDTO order) {
		String email = getThrirdPartyEmail(order.getUseremail());
		String shipOrderTemplate = getTemplateFromDB(Constants.HTML_MAIL_SHIPPING_ORDER_TEMPLATE);
		
		String shipOrderSubject = settingService.getStringValue(Constants.SETTING_SHIPPING_ORDER_SUBJECT, Constants.DEFAULT_SHIPPING_ORDER_TITLE);
		if(shipOrderTemplate == null) {
			shipOrderTemplate = Constants.DEFAULT_SHIPPING_ORDER_CONTENT;
		}
		
		Map<String, Object> variable = new HashMap<String, Object>();
		variable.put("order", order);
		
		sendMail(shipOrderTemplate, shipOrderSubject, variable, email);
	}
	
	public void sendSubmitSupport(String email, Map<String, Object> content) {
		String submitSupportTemplate = getTemplateFromDB(Constants.HTML_MAIL_SUBMIT_SUPPORT_TEMPLATE);
		
		if(submitSupportTemplate == null) {
			submitSupportTemplate = Constants.DEFAULT_SUBMIT_SUPPORT_CONTENT;
		}
		
		String supportSubject;
		if(content.containsKey("subject")) {
			supportSubject = "[Support] " + content.get("subject").toString();
			content.remove("subject");
		} else {
			supportSubject = "[Support]Empty Support Subject";
		}
		String supportMail = settingService.getStringValue(Constants.SETTING_MAIL_SUPPORT, Constants.DEFAULT_MAIL_SUPPORT);
		
		sendMailWithFromAndTo(submitSupportTemplate, supportSubject, content, email, supportMail);
	}
	
	private String getTemplateFromDB(String htmlKey) {
		HTML html = htmService.getHTML(htmlKey);
		if(html != null) {
			return html.getContent();
		}
		return null;
	}
	
	private void sendMail(String templateString, String subject, Map<String,Object> variable, String sendTo){
		if(StringUtils.isEmpty(sendTo)) {
			throw new CoreServiceException("Send to email is empty");
		}
    	String mailContent = parseMailContent(templateString, variable);
    	logger.info("Send mail to: " + sendTo);
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
    	        logger.info("Send mail successfully to " + sendTo);
    	    } catch (EmailException e) {
    	        logger.error(e.getMessage(), e);
    	    }
        } else {
            logger.error("cannot parse email template");
        }
    }
	
	private void sendMailWithFromAndTo(String templateString, String subject, Map<String,Object> variable, String from, String to){
		if(StringUtils.isEmpty(to)) {
			throw new CoreServiceException("Send to email is empty");
		}
    	String mailContent = parseMailContent(templateString, variable);
    	logger.info("Send mail from {}, to {}", new Object[]{from, to});
    	if (mailContent != null) {
    		HtmlEmail email = new HtmlEmail();
    	    try {
    	    	String hostname = settingService.getStringValue(Constants.SETTING_MAIL_HOST_NAME, Constants.DEFAULT_MAIL_HOST_NAME);
    			String mailAccount = settingService.getStringValue(Constants.SETTING_MAIL_ACCOUNT, Constants.DEFAULT_MAIL_FROM_ACCOUNT);
    			String mailPassword = settingService.getStringValue(Constants.SETTING_MAIL_PASSWORD, Constants.DEFAULT_MAIL_FROM_PASSWORD);
    			String mailFrom = settingService.getStringValue(Constants.SETTING_MAIL_FROM, Constants.DEFAULT_MAIL_FROM_ACCOUNT);
    	    	email.setHostName(hostname);
    	    	email.setAuthentication(mailAccount, mailPassword);
    	    	email.setFrom(mailFrom, from);
    	        email.setSubject(subject);
    	        email.setHtmlMsg(mailContent);
    	        email.setTLS(true);
    	        email.addTo(to);
    	        email.setCharset(Constants.DEFAULT_MAIL_CHARSET);
    	        email.send();
    	    } catch (EmailException e) {
    	        logger.error(e.getMessage(), e);
    	    }
        } else {
            logger.error("cannot parse email template");
        }
    }
	
	public boolean sendEdmMail(String subject, String mailContent, String emailHost, String mailAccount, String mailPassword, String mailFrom, String mailAlias, List<String> emailList, long period) {
		if(current < total) {
			return false;
		}
		ScheduledExecutorService scheduledThreadPool = Executors.newSingleThreadScheduledExecutor();
		try {
			if(StringUtils.isEmpty(mailAlias)) {
				mailAlias = mailFrom;
			}
			EdmCommand command = new EdmCommand(scheduledThreadPool, subject, mailContent, emailHost, mailAccount, mailPassword, mailFrom, mailAlias, emailList);
			scheduledThreadPool.scheduleAtFixedRate(command, 0L, period, TimeUnit.MILLISECONDS);
			return true;
		} catch(CoreServiceException e) {
			return false;
		}
	}
	
	public void sendEdmMail(String subject, String mailContent, String emailHost, String mailAccount, String mailPassword, String mailFrom, String mailAlias, String to){
		if(StringUtils.isEmpty(to)) {
			throw new CoreServiceException("Send to email is empty");
		}
    	logger.debug("Send mail from {}, to {}", new Object[]{mailFrom, to});
    	if (mailContent != null) {
    		HtmlEmail email = new HtmlEmail();
    	    try {
    	    	email.setHostName(emailHost);
    	    	email.setAuthentication(mailAccount, mailPassword);
    	    	email.setFrom(mailFrom, mailAlias);
    	        email.setSubject(subject);
    	        email.setHtmlMsg(mailContent);
    	        email.setTLS(true);
    	        email.addTo(to);
    	        email.setCharset(Constants.DEFAULT_MAIL_CHARSET);
    	        email.send();
    	    } catch (EmailException e) {
    	        logger.error(e.getMessage(), e);
    	    }
        } else {
            logger.error("Mail content is null");
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
	
	public String getThrirdPartyEmail(String email) {
		if(email == null) {
			return null;
		}
		int slashIndex = email.indexOf('/');
		if(slashIndex != -1) {
			return email.substring(0, slashIndex);
		}
		return email;
	}
	
	public StringBuffer replaceString(StringBuffer sb, String str, String replacement) {
		if(str != null && replacement != null) {
			int start = sb.indexOf(str);
			sb.replace(start, start + str.length() + 1, replacement);
		}
		return sb;
	}
	
	public boolean checkEdmTask(Model model) {
		if(current < total) {
			model.addAttribute("processingMessage", "Edm Task is running. Process: " + current + " / " + total);
			return true;
		}
		return false;
	}
	
	private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-\\+]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);
	
	public static boolean validateEmail(String email) {
		if(email == null) {
			return false;
		}
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	static int total = 0;
	
	static int current = 0;
	class EdmCommand implements Runnable {
		
		ScheduledExecutorService scheduledThreadPool;
		
		
		private List<String> list;
		int executorCount = 0;
		private int size;
		
		private String subject;
		private String mailContent;
		private String emailHost;
		private String mailAccount;
		private String mailPassword;
		private String mailFrom;
		private String mailAlias;
		
		public EdmCommand(ScheduledExecutorService scheduledThreadPool, String subject, String mailContent, String emailHost, String mailAccount, String mailPassword, String mailFrom, String mailAlias, List<String> list) {
			if(current != total) {
				throw new CoreServiceException("Edm task is running");
			}
			this.scheduledThreadPool = scheduledThreadPool;
			this.list = list;
			this.size = list.size();
			total = this.size;
			current = 0;
			
			this.subject = subject;
			this.mailContent = mailContent;
			this.emailHost = emailHost;
			this.mailAccount = mailAccount;
			this.mailPassword = mailPassword;
			this.mailFrom = mailFrom;
			this.mailAlias = mailAlias;
		}

		@Override
		public void run() {
			if(executorCount < size) {
				String mail = list.get(executorCount).trim();
				System.out.println(Thread.currentThread().getName() + ": [" + mail + "]");
				if(validateEmail(mail)) {
					sendEdmMail(subject, mailContent, emailHost, mailAccount, mailPassword, mailFrom, mailAlias, mail);
				}
				current = ++executorCount;
				 
			}
			if(executorCount >= size) {
				executorCount = 0;
				size = 0;
				scheduledThreadPool.shutdown();
			}
			
		}
		
	}
}


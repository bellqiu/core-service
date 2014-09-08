package com.honeybuy.shop.web.dto;

import org.codehaus.jackson.annotate.JsonAutoDetect;

@JsonAutoDetect
public class MailEntity {
	
	private String mailHost;
	
	private String mailAccount;
	
	private String mailPassword;
	
	private String mailFrom;
	
	private String mailAlias;
	
	private String mailTo;
	
	private String subject;
	
	private String content;
	
	private String tooken;
	
	public MailEntity() {
	}

	public MailEntity(String mailHost, String mailAccount, String mailPassword,
			String mailFrom, String mailAlias, String mailTo, String subject,
			String content, String tooken) {
		super();
		this.mailHost = mailHost;
		this.mailAccount = mailAccount;
		this.mailPassword = mailPassword;
		this.mailFrom = mailFrom;
		this.mailAlias = mailAlias;
		this.mailTo = mailTo;
		this.subject = subject;
		this.content = content;
		this.tooken = tooken;
	}

	public String getMailHost() {
		return mailHost;
	}

	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}

	public String getMailAccount() {
		return mailAccount;
	}

	public void setMailAccount(String mailAccount) {
		this.mailAccount = mailAccount;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getMailAlias() {
		return mailAlias;
	}

	public void setMailAlias(String mailAlias) {
		this.mailAlias = mailAlias;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTooken() {
		return tooken;
	}

	public void setTooken(String tooken) {
		this.tooken = tooken;
	}
	

}

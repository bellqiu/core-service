package com.hb.core.util;

public interface Constants {
	
	public final static float FLOAT_COMPARE = 0.0001f;
	
	public final static String SPECIAL_CHAR_REPLACEMENT = "-";
	
	public final static String TRACKING_COOKE_ID_NAME = "hb_cart_trackingId";
	public final static String CURRENCY_COOKE_ID_NAME = "___hbCurrencyCode";
	
	
	public final static String LOGINUSER_SESSION_ATTR = "currentUser";
	
	
	public static final String DEFAULT_MAIL_CHARSET = "UTF-8";
    public static final String DEFAULT_MAIL_HOST_NAME = "smtpout.secureserver.net";
    public static final String DEFAULT_MAIL_FROM_ACCOUNT = "no-reply@honeybuy.com";
    public static final String DEFAULT_MAIL_FROM_PASSWORD = "20091125!@#A";
    
    public static final String DEFAULT_REGISTER_MAIL_TITLE = "Your account, in Honey-Buy, is created!";
	public static final String DEFAULT_REGISTER_MAIL_CONTENT = "Congratulations! You r the registered user of Honey-Buy!";
	public static final String DEFAULT_RECOVERY_MAIL_TITLE = "Your account, in Honey-Buy, is recoveried!";
	public static final String DEFAULT_RECOVERY_MAIL_CONTENT = "Congratulations! Your new password is ";
	
	public static final String SETTING_MAIL_HOST_NAME = "MAIL_HOST_NAME";
	public static final String SETTING_MAIL_ACCOUNT = "MAIL_ACCOUNT";
	public static final String SETTING_MAIL_PASSWORD = "MAIL_PASSWORD";
	public static final String SETTING_MAIL_FROM = "MAIL_FROM";
	public static final String SETTING_RECOVER_PASSWORD_SUBJECT = "MAIL_RECOVER_PASSWORD_SUBJECT";
	public static final String SETTING_REGISTER_SUBJECT = "MAIL_REGISTER_SUBJECT";
	
	public static final String HTML_MAIL_RECOVER_PASSWORD_TEMPLATE = "MAIL_RECOVER_PASSWORD_TEMPLATE";
	public static final String HTML_MAIL_REGISTER_TEMPLATE = "MAIL_REGISTER_TEMPLATE";
	
	public static final String FACEBOOK_TYPE = "facebook";
	public static final String FACEBOOK_VALIDAT_URL_PREFIX = "https://graph.facebook.com/100002077999840?method=get&pretty=0&sdk=joey";
	public static final String SETTING_FACEBOOK_API_KEY = "FACEBOOK_API_KEY";
	public static final String SETTING_FACEBOOK_SECRET_KEY = "FACEBOOK_SECRET_KEY";
	public static final String DEFAULT_FACEBOOK_API_KEY = "277233412302753";
	public static final String DEFAULT_FACEBOOK_SECRET_KEY = "92efa5dd2807209cb37b375777b21eaf";
	
	
}

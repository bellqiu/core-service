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
}

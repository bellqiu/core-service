package com.hb.core.util;

public interface Constants {
	
	public final static float FLOAT_COMPARE = 0.0001f;
	
	public final static String SPECIAL_CHAR_REPLACEMENT = "-";
	public final static String SPACE_CHAR = " ";
	
	public final static String TRACKING_COOKE_ID_NAME = "hb_cart_trackingId";
	public final static String CURRENCY_COOKE_ID_NAME = "___hbCurrencyCode";
	
	
	public final static String LOGINUSER_SESSION_ATTR = "currentUser";
	
	
	public static final String DEFAULT_MAIL_CHARSET = "UTF-8";
    public static final String DEFAULT_MAIL_HOST_NAME = "smtpout.secureserver.net";
    public static final String DEFAULT_MAIL_FROM_ACCOUNT = "no-reply@honeybuy.com";
    public static final String DEFAULT_MAIL_FROM_PASSWORD = "20091125!@#A";
    
    public static final String DEFAULT_REGISTER_MAIL_TITLE = "Your account, in Honey-Buy, is created!";
	public static final String DEFAULT_REGISTER_MAIL_CONTENT = "Congratulations! You r the registered user of Honey-Buy!";
	public static final String DEFAULT_RECOVERY_MAIL_TITLE = "Your account, in Honey-Buy, is recovered!";
	public static final String DEFAULT_RECOVERY_MAIL_CONTENT = "Congratulations! Your new password is ${password} ";
	public static final String DEFAULT_PAY_ORDER_MAIL_TITLE = "Your order, in Honey-Buy, is paid!";
	public static final String DEFAULT_PAY_ORDER_MAIL_CONTENT = "Congratulations! Your order is paid. It will be delivered to you soon.";
	public static final String DEFAULT_RECEIVE_ORDER_PAYMENT_TITLE = "Your order payment, in Honey-Buy, is received!";
	public static final String DEFAULT_RECEIVE_ORDER_PAYMENT_CONTENT = "Congratulations! Your order payment is received. It will be delivered to you soon.";
	
	public static final String SETTING_MAIL_HOST_NAME = "MAIL_HOST_NAME";
	public static final String SETTING_MAIL_ACCOUNT = "MAIL_ACCOUNT";
	public static final String SETTING_MAIL_PASSWORD = "MAIL_PASSWORD";
	public static final String SETTING_MAIL_FROM = "MAIL_FROM";
	public static final String SETTING_RECOVER_PASSWORD_SUBJECT = "MAIL_RECOVER_PASSWORD_SUBJECT";
	public static final String SETTING_REGISTER_SUBJECT = "MAIL_REGISTER_SUBJECT";
	public static final String SETTING_PAY_ORDER_SUBJECT = "MAIL_PAY_ORDER_SUBJECT";
	public static final String SETTING_RECEIVE_ORDER_PAYMENT_SUBJECT = "MAIL_RECEIVE_ORDER_PAYMENT_SUBJECT";
	
	public static final String HTML_MAIL_RECOVER_PASSWORD_TEMPLATE = "MAIL_RECOVER_PASSWORD_TEMPLATE";
	public static final String HTML_MAIL_REGISTER_TEMPLATE = "MAIL_REGISTER_TEMPLATE";
	public static final String HTML_MAIL_PAY_ORDER_TEMPLATE = "MAIL_PAY_ORDER_TEMPLATE";
	public static final String HTML_MAIL_RECEIVE_ORDER_PAYMENT_TEMPLATE = "MAIL_RECEIVE_ORDER_PAYMENT_TEMPLATE";
	
	public static final String FACEBOOK_TYPE = "facebook";
	public static final String FACEBOOK_VALIDAT_URL_PREFIX = "https://graph.facebook.com/100002077999840?method=get&pretty=0&sdk=joey";
	public static final String SETTING_FACEBOOK_API_KEY = "FACEBOOK_API_KEY";
	public static final String SETTING_FACEBOOK_SECRET_KEY = "FACEBOOK_SECRET_KEY";
	public static final String DEFAULT_FACEBOOK_API_KEY = "277233412302753";
	public static final String DEFAULT_FACEBOOK_SECRET_KEY = "92efa5dd2807209cb37b375777b21eaf";
	
	public static final String SHIPPING_PRICE_NORMAL_CONF_KEY = "SHIPPING_PRICE_NORMAL_CONF";
	public static final String SHIPPING_PRICE_EXPEDITED_CONF_KEY = "SHIPPING_PRICE_EXPEDITED_CONF";
	public static final String FREE_SHIPPING_PRICE_NORMAL_CONF_KEY = "FREE_SHIPPING_PRICE_NORMAL_CONF";
	public static final String FREE_SHIPPING_PRICE_EXPEDITED_CONF_KEY = "FREE_SHIPPING_PRICE_EXPEDITED_CONF";
	
	public static final String SHIPPING_PRICE_NORMAL_CONF_DEFAULT = "10";
	public static final String SHIPPING_PRICE_EXPEDITED_CONF_DEFAULT = "20";
	public static final String FREE_SHIPPING_PRICE_NORMAL_CONF_DEFAULT = "100";
	public static final String FREE_SHIPPING_PRICE_EXPEDITED_CONF_DEFAULT = "200";
	
	
	public static final String PAYMENT_METHOD_LIST = "PAYMENT_METHOD_LIST";
	
	
	
}

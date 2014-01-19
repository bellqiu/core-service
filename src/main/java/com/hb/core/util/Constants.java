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
    public static final String DEFAULT_MAIL_FROM_ACCOUNT = "no-reply@dentalsupplies.com";
    public static final String DEFAULT_MAIL_FROM_PASSWORD = "20091125!@#A";
    
    public static final String DEFAULT_REGISTER_MAIL_TITLE = "Your account, in Dental-Supplies, is created!";
	public static final String DEFAULT_REGISTER_MAIL_CONTENT = "Congratulations! You are the registered user of Dental-Supplies!";
	public static final String DEFAULT_RECOVERY_MAIL_TITLE = "Your account, in dentalsupplies360.com, is recovered!";
	public static final String DEFAULT_RECOVERY_MAIL_CONTENT = "Congratulations! Your new password is ${password} ";
	public static final String DEFAULT_TO_PAY_ORDER_MAIL_TITLE = "Your order, in Dental-Supplies, is to be paid!";
	public static final String DEFAULT_TO_PAY_ORDER_MAIL_CONTENT = "Thanks for your shopping. Your order is to be paid.";
	public static final String DEFAULT_RECEIVE_ORDER_PAYMENT_TITLE = "Your order payment, in Dental-Supplies, is received!";
	public static final String DEFAULT_RECEIVE_ORDER_PAYMENT_CONTENT = "Congratulations! Your order payment is received. It will be delivered to you soon.";
	
	public static final String SETTING_MAIL_HOST_NAME = "MAIL_HOST_NAME";
	public static final String SETTING_MAIL_ACCOUNT = "MAIL_ACCOUNT";
	public static final String SETTING_MAIL_PASSWORD = "MAIL_PASSWORD";
	public static final String SETTING_MAIL_FROM = "MAIL_FROM";
	public static final String SETTING_RECOVER_PASSWORD_SUBJECT = "MAIL_RECOVER_PASSWORD_SUBJECT";
	public static final String SETTING_REGISTER_SUBJECT = "MAIL_REGISTER_SUBJECT";
	public static final String SETTING_TO_PAY_ORDER_SUBJECT = "MAIL_TO_PAY_ORDER_SUBJECT";
	public static final String SETTING_RECEIVE_ORDER_PAYMENT_SUBJECT = "MAIL_RECEIVE_ORDER_PAYMENT_SUBJECT";
	
	public static final String HTML_MAIL_RECOVER_PASSWORD_TEMPLATE = "MAIL_RECOVER_PASSWORD_TEMPLATE";
	public static final String HTML_MAIL_REGISTER_TEMPLATE = "MAIL_REGISTER_TEMPLATE";
	public static final String HTML_MAIL_TO_PAY_ORDER_TEMPLATE = "MAIL_TO_PAY_ORDER_TEMPLATE";
	public static final String HTML_MAIL_RECEIVE_ORDER_PAYMENT_TEMPLATE = "MAIL_RECEIVE_ORDER_PAYMENT_TEMPLATE";
	
	public static final String FACEBOOK_TYPE = "facebook";
	public static final String FACEBOOK_VALIDAT_URL = "https://graph.facebook.com/%s?method=get&pretty=0&sdk=joey&access_token=%s&appsecret_proof=%s";
	public static final String GOOGLE_TYPE = "google";
	public static final String GOOGLE_VALIDAT_URL = "https://www.googleapis.com/oauth2/v1/tokeninfo?access_token=%s";
	public static final String TWITTER_TYPE = "twitter";
	public static final String TWITTER_VALIDAT_URL_PREFIX = "https://api.twitter.com/1.1/account/verify_credentials.json";
	public static final String LINKIN_TYPE = "linkedIn";
	public static final String LINKIN_VALIDAT_URL = "https://api.linkedin.com/v1/people/~/email-address?format=json&oauth_token=%s";
	
	public static final String TOKEN_SESSION_KEY = "TOKEN_KEY";
	
	public static final String SETTING_FACEBOOK_API_KEY = "FACEBOOK_API_KEY";
	public static final String SETTING_FACEBOOK_SECRET_KEY = "FACEBOOK_SECRET_KEY";
	public static final String SETTING_GOOGLE_API_KEY = "GOOGLE_API_KEY";
	public static final String SETTING_GOOGLE_SECRET_KEY = "GOOGLE_SECRET_KEY";
	public static final String SETTING_TWITTER_API_KEY = "TWITTER_API_KEY";
	public static final String SETTING_TWITTER_SECRET_KEY = "TWITTER_SECRET_KEY";
	public static final String SETTING_LINKIN_API_KEY = "LINKIN_API_KEY";
	public static final String SETTING_LINKIN_SECRET_KEY = "LINKIN_SECRET_KEY";
	
	public static final String DEFAULT_FACEBOOK_API_KEY = "277233412302753";
	public static final String DEFAULT_FACEBOOK_SECRET_KEY = "92efa5dd2807209cb37b375777b21eaf";
	public static final String DEFAULT_GOOGLE_API_KEY = "573965665808.apps.googleusercontent.com";
	public static final String DEFAULT_GOOGLE_SECRET_KEY = "WuEeML3Ufrp9gQa0El2aRE08";
	public static final String DEFAULT_TWITTER_API_KEY = "o9xDiUcnFxmenjR1KVizvA";
	public static final String DEFAULT_TWITTER_SECRET_KEY = "qNdgmqkWVZsIF2v0Zm8ncNtFHeDise4S9YFHfc0MOTw";
	public static final String DEFAULT_LINKIN_API_KEY = "7530oj8n5n1fan";
	public static final String DEFAULT_LINKIN_SECRET_KEY = "Oy2IJP6FBEbjBbgQ";
	
	public static final String SHIPPING_PRICE_NORMAL_CONF_KEY = "SHIPPING_PRICE_NORMAL_CONF";
	public static final String SHIPPING_PRICE_EXPEDITED_CONF_KEY = "SHIPPING_PRICE_EXPEDITED_CONF";
	public static final String FREE_SHIPPING_PRICE_NORMAL_CONF_KEY = "FREE_SHIPPING_PRICE_NORMAL_CONF";
	public static final String FREE_SHIPPING_PRICE_EXPEDITED_CONF_KEY = "FREE_SHIPPING_PRICE_EXPEDITED_CONF";
	
	public static final String SHIPPING_PRICE_NORMAL_CONF_DEFAULT = "10";
	public static final String SHIPPING_PRICE_EXPEDITED_CONF_DEFAULT = "20";
	public static final String FREE_SHIPPING_PRICE_NORMAL_CONF_DEFAULT = "100";
	public static final String FREE_SHIPPING_PRICE_EXPEDITED_CONF_DEFAULT = "200";
	
	
	public static final String PAYMENT_METHOD_LIST = "PAYMENT_METHOD_LIST";
	
	
	public static final String PAYPAL_NOTIFY_URI = "PAYPAL_NOTIFY_URI";
	
	public static final String PAYPAL_NOTIFY_URI_DEFAULT = "/sp/notify/paypal";
	
	
	public static final String PAYPAL_ACCOUNT = "PAYPAL_ACCOUNT";
	public static final String PAYPAL_ACCOUNT_DEFAULT = "zhuwanshan@outlook.com";
	
	
	
}

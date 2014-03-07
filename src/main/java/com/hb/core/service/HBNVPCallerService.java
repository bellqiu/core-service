/**
 * 
 */
package com.hb.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paypal.sdk.exceptions.PayPalException;
import com.paypal.sdk.profiles.APIProfile;
import com.paypal.sdk.profiles.ProfileFactory;
import com.paypal.sdk.services.NVPCallerServices;

/**
 * @author Spark Zhu, wan-shan.zhu@hp.com
 *
 */

@Service
public class HBNVPCallerService extends NVPCallerServices{
	/**
	 * 
	 */
	@Autowired
	private SettingService settingService;
	
	public HBNVPCallerService() {
		super();
	}
	
	public void login() throws PayPalException{
		//profile = ProfileFactory.createSignatureAPIProfile();
		//profile.setAPIUsername(DEFAULT_USER_NAME);
		//profile.setAPIPassword(DEFAULT_PASSWORD);
		//profile.setSignature(DEFAULT_SIGNATURE);
		//profile.setEnvironment(testEnv);
		//session.setAttribute("environment", testEnv);
		APIProfile apiProfile =  ProfileFactory.createSignatureAPIProfile();
		String apiUsername = settingService.getStringValue("PAYPAL_API_USERNAME", "test1_api1.dentalsupplies360.com");
		String apiPassword = settingService.getStringValue("PAYPAL_API_PWD", "X8N84SV65CQAF96F");
		String apiSignature = settingService.getStringValue("PAYPAL_API_SIGNATURE", "AiPC9BjkCyDFQXbSkoZcgqH3hpacAxseHUtI5BR8N3ds6.pFK3LDU6Cz");
		apiProfile.setAPIPassword(apiPassword);
		apiProfile.setAPIUsername(apiUsername);
		apiProfile.setSignature(apiSignature);
		apiProfile.setEnvironment("live");
		
		setAPIProfile(apiProfile);
		
	}

	public SettingService getSettingService() {
		return settingService;
	}

	public void setSettingService(SettingService settingService) {
		this.settingService = settingService;
	}
	
	
}

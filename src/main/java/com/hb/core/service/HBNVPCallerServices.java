/**
 * 
 */
package com.hb.core.service;

import javax.annotation.PostConstruct;

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
public class HBNVPCallerServices extends NVPCallerServices{
	/**
	 * 
	 */
	@Autowired
	private SettingService settingService;
	
	public HBNVPCallerServices() {
		super();
	}
	
	@PostConstruct
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
		String apiSignature = settingService.getStringValue("PAYPAL_API_USERNAME", "test1_api1.dentalsupplies360.com");
		apiProfile.setAPIPassword(apiPassword);
		apiProfile.setAPIUsername(apiUsername);
		apiProfile.setSignature(apiSignature);
		apiProfile.setEnvironment("sandbox");
		
		setAPIProfile(apiProfile);
		
		System.out.println("###############@PostConstruct");
	}

	public SettingService getSettingService() {
		return settingService;
	}

	public void setSettingService(SettingService settingService) {
		this.settingService = settingService;
	}
	
	
}

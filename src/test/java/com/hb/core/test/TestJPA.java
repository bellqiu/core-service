package com.hb.core.test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;

import com.hb.core.service.OrderService;


//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration({"classpath:applicationContext-test.xml"})
public class TestJPA {
	
	@Autowired
	private OrderService orderService;
	
	
	@PersistenceContext
	private EntityManager em;
	
/*	@Test
	public void test(){
		Order order = new Order();
		order.setBillingAddress("billingAddress");
		order.setCouponCode("couponCode");
		order.setCouponCutOff(0.1f);
		order.setCurrency("USD");
		order.setCustomerMsg("customerMsg");
		order.setDeliveryPrice(0.2f);
		order.setUser(em.find(User.class, 1l));
		
		for (int i = 0; i < 2; i++) {
			
			OrderItem item = new OrderItem();
			item.setProduct(em.find(Product.class, Long.valueOf(6 + 1)));
			
			SelectedOpts selectedOpts = new SelectedOpts();
			selectedOpts.setValue("test" + i);
			
			item.getSelectedOpts().add(selectedOpts);
			
			order.getItems().add(item);
		}
		
		orderService.saveOrUpdate(order);
	}
	*/
	
	private static String hmacSHA256(String data, String key) throws Exception {
	    SecretKeySpec secretKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
	    Mac mac = Mac.getInstance("HmacSHA256");
	    mac.init(secretKey);
	    byte[] hmacData = mac.doFinal(data.getBytes("UTF-8"));
	    return new String(Base64.encodeBase64URLSafe(hmacData), "UTF-8");
	}
	
	public static String encode(String key, String data) throws Exception {
		  Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		  SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(), "HmacSHA256");
		  sha256_HMAC.init(secret_key);

		  return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes()));
		}

		public static void main(String[] args) {
			System.out.println(Integer.valueOf("000000031"));
		}
	
}

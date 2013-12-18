package com.honeybuy.shop.util;

import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class EncodingUtils {
	public static String hmac256(String key, String data) throws Exception {
		  Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
		  SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(), "HmacSHA256");
		  sha256_HMAC.init(secret_key);

		  return Hex.encodeHexString(sha256_HMAC.doFinal(data.getBytes()));
	}
	
	public static void main(String [] args) throws Exception {
		  // System.out.println(encode("277233412302753", "92efa5dd2807209cb37b375777b21eaf").toUpperCase());
		String secretKey = "92efa5dd2807209cb37b375777b21eaf";
		System.out.println(hmac256(secretKey,"CAAD8JG8PZA6EBABPZCfXfNO5kbEUuFhuZBRlXz0ZBA4dz0itf4hfMLTzWhsGmdySXBWMavVDO5AwZB61XMh4Fx8mfHkWygJR2qGta2i1sQHhOeUZAo2XqmxYm4cR1BSov331DV7aiwFeLf6eCnDhLDeXE2hIRo3JZBSCZCMdANBy6CtZAUb2D9VltZBZArkvx8J0jkZD"));
		String token = "CAAD8JG8PZA6EBAK6eUz2nZCYdoFywCZANARA4vNhHOhk2yNR9xWBWv3YM8q0VOqp2ZBtXDDgMJ0gdagRd9TTZA1hrDBUWoeDlCizwh8D97TeEZCNBHyZCCg5Ha4M9HZBypk0OcqxxrUCZBD7YUq9ly3koZCBm9bOvi6EAjMNZA0th7jJERx1EmS35V33CwdrZCEuExus82UX2OKOnAZDZD";
		System.out.println(hmac256(secretKey, token));
		UUID uuid = UUID.randomUUID();
		String randomString = new String(Base64.encodeBase64URLSafe(uuid.toString().getBytes()));
		System.out.println(randomString);
		System.out.println(randomString.substring(0,8));
	
		}
}

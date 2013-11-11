package com.honeybuy.shop.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.w3c.tidy.Tidy;

public class HtmlTidy {
	
	public static String getTidyHtmlString(String html) {
		if(html == null) {
			return null;
		}
		Tidy tidy = new Tidy();
		tidy.setShowWarnings(false);
		tidy.setMakeClean(true);
		tidy.setPrintBodyOnly(true);
		OutputStream os = new ByteArrayOutputStream();
		tidy.parseDOM(new ByteArrayInputStream(html.getBytes()), os);
		return os.toString();
	}

}

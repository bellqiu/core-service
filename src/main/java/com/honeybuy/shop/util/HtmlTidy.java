package com.honeybuy.shop.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
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
		Document parseDOM = tidy.parseDOM(new ByteArrayInputStream(html.getBytes()), os);
		Transformer transformer;
		StringBuffer sb = new StringBuffer();
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
			NodeList styleTags = parseDOM.getElementsByTagName("style");
			if(styleTags != null && styleTags.getLength() > 0) {
				for(int i = 0, length = styleTags.getLength(); i < length; i ++) {
					StringWriter writer = new StringWriter();
					transformer.transform(new DOMSource(styleTags.item(i)), new StreamResult(writer));
					String str = writer.toString();
					sb.append(str.substring(str.indexOf("<style"), str.length()));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		sb.append(os.toString());
		return sb.toString();
	}

}

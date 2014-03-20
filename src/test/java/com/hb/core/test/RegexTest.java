package com.hb.core.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hb.core.service.EmailService;
import com.hb.core.util.Constants;
import com.honeybuy.shop.util.HtmlTidy;
import com.honeybuy.shop.util.RegexUtils;

public class RegexTest {

	/**
	 * @param args
	 * @throws  
	 * @throws TransformerConfigurationException 
	 */
	public static void main1(String[] args) throws Exception {
		String str = "slfj lsdf-sldjfls-***-flsdjfsdf";
		System.out.println(RegexUtils.replaceSpecialChar(str, "-"));
		
		str = "<style>.slls_test{asf}</style>sdfsdf<b>sdfsdf</b><style>lsjldfsjl</style><h1>jsld";
		Tidy tidy = new Tidy();
		tidy.setShowWarnings(false);
		tidy.setMakeClean(true);
		tidy.setPrintBodyOnly(true);
		//tidy.setXHTML(true);
		tidy.setJoinStyles(false);
		/*tidy.setXHTML(false);
		tidy.setQuiet(false);
	    tidy.setTidyMark(false);*/
		OutputStream os = new ByteArrayOutputStream();
		Document parseDOM = tidy.parseDOM(new ByteArrayInputStream(str.getBytes()), os);
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		StringWriter writer = new StringWriter();
		StreamResult outputTarget = new StreamResult(writer);
		transformer.transform(new DOMSource(parseDOM.getElementsByTagName("style").item(0)), outputTarget);
		System.out.println(writer.toString());
		//tidy.parse(new ByteArrayInputStream(str.getBytes()), os);
		//tidy.get
		System.out.println("============");
		System.out.println(os.toString());
		
		System.out.println("============");
		System.out.println(HtmlTidy.getTidyHtmlString(str));
		
		

	}
	
	public static void main(String args []) throws IOException {
		/*URL url = new URL("https://graph.facebook.com/100002077999840?access_token=CAAD8JG8PZA6EBAK6eUz2nZCYdoFywCZANARA4vNhHOhk2yNR9xWBWv3YM8q0VOqp2ZBtXDDgMJ0gdagRd9TTZA1hrDBUWoeDlCizwh8D97TeEZCNBHyZCCg5Ha4M9HZBypk0OcqxxrUCZBD7YUq9ly3koZCBm9bOvi6EAjMNZA0th7jJERx1EmS35V33CwdrZCEuExus82UX2OKOnAZDZD&method=get&pretty=0&sdk=joey&appsecret_proof=1871e7c1a2c8164af48b0fefacdef9b9f62865c0018aad67cb84c73aea722c18");
		//InputStream openStream = url.openStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		Map value = mapper.readValue(url, Map.class);
		System.out.println(value.getClass());
		System.out.println(value);*/
		
		String str = "<html><table></table></html>";
		str = "<body><div></div></body>";
		//str = "<h1>aaa</h1>";
		System.out.println(HtmlTidy.getTidyHtmlString(str));
		System.out.println("End");
		str = "0sjldSA lajlfA lsjf ";
		str = RegexUtils.replaceSpecialChar(str, Constants.HYPHEN_CHAR);
		System.out.println(str);
		System.out.println(RegexUtils.upperFirstLetterEachWord(str));
		
		EmailService emailServie = new EmailService();
		System.out.println(emailServie.getThrirdPartyEmail("qiu@hp.com/facebook"));
		System.out.println(emailServie.getThrirdPartyEmail("qiu-Normal@hp.com"));
		
		System.out.println(RegexUtils.replaceSpace("ajldf   sljdl js sldfj ", " "));
		
		String value = "ass/lsjdf /lsjs";
		String v = value.replace("/", "--slash--");
		System.out.println(v);
		v = v.replace("--slash--", "/");
		System.out.println(v);
		
		double dv = 1.1111;
		NumberFormat numberFormat = new DecimalFormat("#,###,###.##");
		System.out.println(Double.parseDouble(numberFormat.format(dv)));
		dv = 1.1151;
		System.out.println(Double.parseDouble(numberFormat.format(dv)));
		dv = 1.1161;
		System.out.println(Double.parseDouble(numberFormat.format(dv)));
		
		System.out.println(Math.floor(1.1));
		System.out.println(Math.floor(1.5));
		System.out.println(Math.floor(1.6));
		
	}

}

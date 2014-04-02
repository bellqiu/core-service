package com.honeybuy.shop.web;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping("")
public class FreedomPageController {
	private static final Logger logger = LoggerFactory.getLogger(FreedomPageController.class);
	
	@RequestMapping("/page/{pageName}")
	public String freedomPage(@PathVariable("pageName") String pageName, Model model) {
		logger.debug("Access freedom Page {}", pageName);
		model.addAttribute("freedompage", pageName);
		
		return "freedomPage";
	}
	
	@RequestMapping("/blogger/{pageName}.pdf")
	@ResponseBody
	@Produces("application/pdf")
	public byte[] pdfPage(@PathVariable("pageName") String pageName, Model model) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream in = FreedomPageController.class.getResourceAsStream("/t1.pdf");
		BufferedInputStream inBuffer = new BufferedInputStream(in);
		int readSize = 0;
		byte[] buffer = new byte[4096];
		while((readSize = inBuffer.read(buffer)) != -1) {
			baos.write(buffer, 0, readSize);
		}
		in.close();
		return baos.toByteArray();
	}
}

package com.hb.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

public class FileUtils {
	
	public static String retriveFileExtendedName(String filename){
		String extendedName = "";
		
		if(!StringUtils.isEmpty(filename) && filename.lastIndexOf('.') > 0){
			extendedName = filename.substring(filename.lastIndexOf('.')+1);
		}
		
		return extendedName;
	}
	
	public static String retriveFileNameNoExtended(String filename){
		String extendedName = filename;
		
		if(!StringUtils.isEmpty(filename) && filename.lastIndexOf('.') > 0){
			extendedName = filename.substring(0, filename.lastIndexOf('.'));
		}
		
		return extendedName;
	}
	
	public static void saveFileToPath(byte[] binary, String filename) throws IOException{
		File file  = new File(filename);
		
		if(!file.exists()){
			file.createNewFile();
		}
		
		FileOutputStream fileOutputStream = new FileOutputStream(filename);
		
		IOUtils.write(binary, fileOutputStream);
		
	}
	
	
	public static void main(String[] args) {
		System.out.println(retriveFileExtendedName("11.dd"));
		System.out.println(retriveFileNameNoExtended("11.dd"));
	}

}

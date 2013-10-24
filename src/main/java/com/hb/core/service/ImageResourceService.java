package com.hb.core.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.StringUtils;

import com.hb.core.entity.Image;
import com.hb.core.exception.CoreServiceException;

@Transactional
@Service
public class ImageResourceService {

	@Autowired
	private SettingService settingService;

	@PersistenceContext
	private EntityManager em;

	public static String IMAGE_DIRECTORY = "IMAGE_DIRECTORY_KEY";
	public static String FILE_FILTER_KEY = "IMAGE_FILE_FILTER_KEY";
	public static String CONVERTER_PATH_KEY = "CONVERTER_PATH_KEY";
	
	
	public static String LARGER_SIZE = "IMG_LARGER_SIZE_KEY";
	public static String LOGO_SIZE = "IMG_LOGO_SIZE_KEY";
	public static String ICON_SIZE = "IMG_ICON_SIZE_KEY";
	public static String THUMBNAIL_SIZE = "IMG_THUMBNAIL_SIZE_KEY";
	public static String SMALL_SIZE = "IMG_SMALL_SIZE_KEY";
	
	public static String IMG_HOST = "IMG_HOST_KEY";
	
	public static String CONVERT_PATTERN = "${src}  -quality 80 ( -clone 0  -set option:distort:viewport  \"%[fx:min(w,h)]x%[fx:min(w,h)]+%[fx:max((w-h)/2,0)]+%[fx:max((h-w)/2,0)]\" -filter point -distort SRT 0 )  ( -clone 1 -trim -thumbnail ${largsize} )  ( -clone 2 -trim -thumbnail ${thumbnailsize} )  ( -clone 2 -trim -thumbnail ${logosize} )   ( -clone 2 -trim -thumbnail ${smailsize} ) ( -clone 2 -trim -thumbnail ${iconsize} ) ${output}";

	public String retriveFileExtendedName(String filename) {
		String extendedName = "";

		if (!StringUtils.isEmpty(filename) && filename.lastIndexOf('.') > 0) {
			extendedName = filename.substring(filename.lastIndexOf('.') + 1);
		}

		return extendedName;
	}

	public String retriveFileNameNoExtended(String filename) {
		String extendedName = filename;

		if (!StringUtils.isEmpty(filename) && filename.lastIndexOf('.') > 0) {
			extendedName = filename.substring(0, filename.lastIndexOf('.'));
		}

		return extendedName;
	}

	public void saveFileToPath(byte[] binary, String filename)
			throws IOException {
		File file = new File(filename);

		if (!file.exists()) {
			file.createNewFile();
		}

		FileOutputStream fileOutputStream = new FileOutputStream(filename);

		IOUtils.write(binary, fileOutputStream);

		fileOutputStream.close();
	}

	public Image newImage(byte[] binary, String filename) {

		Image image = new Image();

		String extendName = retriveFileExtendedName(filename);
		String cleanName = retriveFileNameNoExtended(filename);

		image.setName(cleanName);
		image.setCreateDate(new Date());
		image.setUpdateDate(new Date());
		image.setAltTitle(cleanName);

		String fileNameFilter = settingService.getStringValue(FILE_FILTER_KEY);

		if (null != fileNameFilter) {
			String fileNames[] = fileNameFilter.split(",");
			if (!Arrays.asList(fileNames).contains(extendName)) {
				throw new CoreServiceException("Invalid File format");
			}
		}

		String fileDirectory = settingService.getStringValue(IMAGE_DIRECTORY,
				System.getProperty("java.io.tmpdir"));

		String newFileName = UUID.randomUUID().toString();

		try {

			String realFilePath = fileDirectory + File.separator + newFileName
					+ "." + extendName;
			saveFileToPath(binary, realFilePath);
			cropImage(realFilePath);
			image.setNoChangeUrl(newFileName+ "." + extendName);

		} catch (IOException e) {
			throw new CoreServiceException(e);
		}

		image.setNoChangeUrl(newFileName + "-0." + extendName);
		image.setLargerUrl(newFileName + "-2." + extendName);
		image.setThumbnailUrl(newFileName + "-3." + extendName);
		image.setSmallUrl(newFileName + "-4." + extendName);
		image.setLogoUrl(newFileName + "-5." + extendName);
		image.setIconUrl(newFileName + "-6." + extendName);
		image = em.merge(image);

		return image;
	}

	private void cropImage(String realFilePath) throws IOException {
		
	/*	String convertCmd = "convert ${src}  -quality 80 ( -clone 0 -trim -thumbnail ${largsize} )  ( -clone 0 -trim -thumbnail ${normalsize} ) " +
				" ( -clone 0 -trim -thumbnail ${thumbnailsize} )  ( -clone 0 -trim -thumbnail ${logosize} )   " +
				"( -clone 0 -trim -thumbnail ${smailsize} ) ( -clone 0 -trim -thumbnail ${iconsize} ) ${output}";*/
		
		String converter = settingService.getStringValue(CONVERTER_PATH_KEY, "convert") + " " + CONVERT_PATTERN;
		
		Properties properties = new Properties();
		
		properties.setProperty("src", realFilePath);
		properties.setProperty("output", realFilePath);
		
		properties.setProperty("largsize", settingService.getStringValue(LARGER_SIZE, "300x400"));
		properties.setProperty("thumbnailsize", settingService.getStringValue(THUMBNAIL_SIZE, "120x160"));
		properties.setProperty("smailsize", settingService.getStringValue(SMALL_SIZE, "120x90"));
		properties.setProperty("logosize", settingService.getStringValue(LOGO_SIZE, "60x80"));
		properties.setProperty("iconsize", settingService.getStringValue(ICON_SIZE, "40x60"));
		
		PropertyPlaceholderHelper placeholderHelper = new PropertyPlaceholderHelper("${", "}");
		
		converter = placeholderHelper.replacePlaceholders(converter, properties);
		
		Process process = Runtime.getRuntime().exec(converter);
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		
		String line = bufferedReader.readLine();
		

		bufferedReader.close();
		
		if(null != line){
			throw  new CoreServiceException(line);
		}
		
	}
	
}

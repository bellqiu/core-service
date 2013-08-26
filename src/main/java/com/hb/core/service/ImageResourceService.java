package com.hb.core.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.core.entity.Image;
import com.hb.core.exception.CoreServiceException;
import com.hb.core.util.FileUtils;

@Transactional
@Service
public class ImageResourceService {
	
	@Autowired
	private SettingService settingService;
	
	@PersistenceContext
	private EntityManager em;

	public static String TEMP_FILE_PATH_KEY = "TEMP_FILE_PATH_KEY";
	public static String FILE_FILTER_KEY = "IMAGE_FILE_FILTER_KEY";
	public static String CONVERTER_PATH_KEY = "CONVERTER_PATH_KEY";
	public static String NORMAL_ = "CONVERTER_PATH_KEY";
	
	
	public Image newImage(byte[] binary, String filename){
		
		Image image = new Image();
		
		String extendName = FileUtils.retriveFileExtendedName(filename);
		String cleanName = FileUtils.retriveFileNameNoExtended(filename);
		
		image.setName(cleanName);
		image.setCreateDate(new Date());
		image.setUpdateDate(new Date());
		image.setAltTitle(cleanName);
		
		String fileNameFilter = settingService.getStringValue(FILE_FILTER_KEY);
		
		if(null != fileNameFilter){
			String fileNames[] = fileNameFilter.split(",");
			if(!Arrays.asList(fileNames).contains(extendName)){
				throw new CoreServiceException("Invalid File format");
			}
		}
		
		String tempFilePath = settingService.getStringValue(TEMP_FILE_PATH_KEY, System.getProperty("java.io.tmpdir"));
		
		String newFileName = UUID.randomUUID().toString();
		
		try {
			FileUtils.saveFileToPath(binary, tempFilePath + File.separator + newFileName+"."+extendName);
		} catch (IOException e) {
			throw new CoreServiceException(e);
		}
		
		image.setNoChangeUrl(newFileName+"."+extendName);
		
		image = em.merge(image);
		
		return image;
	}
	
}

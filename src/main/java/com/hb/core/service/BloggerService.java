package com.hb.core.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.StringUtils;

import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.hb.core.entity.Blogger;
import com.hb.core.entity.Image;
import com.hb.core.exception.CoreServiceException;

@Transactional
@Service
public class BloggerService {
	
	private static final Logger logger = LoggerFactory.getLogger(BloggerService.class);

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

	public Blogger newBlogger(byte[] binary, String filename) {

		Blogger blogger = new Blogger();
		String extendName = retriveFileExtendedName(filename);
		String cleanName = retriveFileNameNoExtended(filename);
		blogger.setName(filename);
		blogger.setDescription(cleanName);
		blogger.setCreateDate(new Date());
		blogger.setUpdateDate(new Date());
		if("pdf".equalsIgnoreCase(extendName)) {
			blogger.setType(Blogger.Type.PDF);
		} else if("html".equalsIgnoreCase(extendName)) {
			blogger.setType(Blogger.Type.HTML);
		} else if("jsp".equalsIgnoreCase(extendName)) {
			blogger.setType(Blogger.Type.JSP);
		} else if("txt".equalsIgnoreCase(extendName)) {
			blogger.setType(Blogger.Type.TXT);
		} else {
			throw new CoreServiceException("Invalid File format");
		}
		
		return blogger;
		/*Image image = new Image();

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

		return image;*/
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
		
		logger.warn("Convert String: {}", new Object[]{converter});
		
		Process process = Runtime.getRuntime().exec(converter);
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
		
		String line = bufferedReader.readLine();
		

		bufferedReader.close();
		
		if(null != line){
			//throw  new CoreServiceException(line);
			logger.warn(line);
		}
		
	}
	
	public void destory(Blogger blogger){
		blogger =  em.merge(blogger);
		em.remove(blogger);
	}
	
	public ExtDirectStoreReadResult<Blogger> queryResult(int start, int max,
			String sort, String direction, Map<String, String> filters) {
		StringBuffer ql = new StringBuffer("");
		if(!filters.isEmpty()){
			ql.append(" where ");
			Iterator<String> item = filters.keySet().iterator();
			while(item.hasNext()){
				String param = item.next();
				if("type".equalsIgnoreCase(param)){
					ql.append(param +" = :" + param + " ");
				} else {
					ql.append(param +" like :"+param +" ");
				}
				if(item.hasNext()){
					ql.append(" and ");
				}
			}
		}
		
		ql.append(" order by " + sort + " " + direction);
		
		StringBuffer queryStringPrefix = new StringBuffer("select h from Blogger as h ");
		StringBuffer CountStringPrefix = new StringBuffer("select count(h.id) from Blogger as h ");
		
		TypedQuery<Blogger> query = em.createQuery( queryStringPrefix.append(ql).toString(), Blogger.class);
		TypedQuery<Long> count = em.createQuery( CountStringPrefix.append(ql).toString(), Long.class);
		
		query.setFirstResult(start);
		query.setMaxResults(max);
		for (Map.Entry<String, String> paramEntry : filters.entrySet()) {
			if("type".equals(paramEntry.getKey())){
				query.setParameter(paramEntry.getKey(), Blogger.Type.valueOf(paramEntry.getValue()));
				count.setParameter(paramEntry.getKey(), Blogger.Type.valueOf(paramEntry.getValue()));
			}else{
				query.setParameter(paramEntry.getKey(), "%" + paramEntry.getValue() + "%");
				count.setParameter(paramEntry.getKey(), "%" + paramEntry.getValue() + "%");
			}
		}
		return new ExtDirectStoreReadResult<Blogger>(count.getSingleResult().intValue(),query.getResultList());
	}
	
	public Blogger saveOrUpdate(Blogger blogger){
		Blogger existingBlogger = getBloggerByName(blogger.getName());
		if(null != existingBlogger && (existingBlogger.getId() != blogger.getId() || blogger.getId() < 1)) {
			throw new CoreServiceException("HTML name already exist");
		}
		blogger = em.merge(blogger);
		return blogger;
	}

	private Blogger getBloggerByName(String name) {
		Blogger blogger = null;
		
		try {
			TypedQuery<Blogger> query = em.createNamedQuery("QueryBloggerByName", Blogger.class);
			query.setParameter("name", name);
			blogger = query.getSingleResult();
		} catch(NoResultException e){
			return null;
		} catch (Exception e) {
			throw new CoreServiceException(e);
		}
		return blogger;
	}
	
}

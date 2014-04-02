package com.hb.core.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

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
import org.springframework.util.StringUtils;

import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadResult;

import com.hb.core.entity.Blogger;
import com.hb.core.entity.Component;
import com.hb.core.exception.CoreServiceException;
import com.hb.core.util.Constants;
import com.honeybuy.shop.util.RegexUtils;

@Transactional
@Service
public class BloggerService {
	
	private static final Logger logger = LoggerFactory.getLogger(BloggerService.class);

	@Autowired
	private SettingService settingService;

	@PersistenceContext
	private EntityManager em;

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
		checkExistingBlogger(blogger, filename);
		String savedFileName = blogger.getName();
		if(getBloggerByName(savedFileName) != null) {
			throw new CoreServiceException("Blogger File [ + " + savedFileName + "] is existing");
		}
		
		String fileDirectory = settingService.getStringValue(Constants.SETTING_BLOGGER_DIR,
				System.getProperty("java.io.tmpdir"));
		String realFilePath = fileDirectory + File.separator + savedFileName;
		try {
			saveFileToPath(binary, realFilePath);
		} catch (IOException e) {
			throw new CoreServiceException(e);
		}
		
		blogger.setSize(binary.length);
		blogger.setCreateDate(new Date());
		blogger.setUpdateDate(new Date());
		
		blogger = em.merge(blogger);
		return blogger;
	}

	public void destory(Blogger blogger){
		Blogger b = null;
		if(blogger == null || blogger.getId() < 1 || (b = getBloggerById(blogger.getId())) == null) {
			throw new CoreServiceException("Blogger does not exist");
		}
		b.setUpdateDate(new Date());
		b.setStatus(Component.Status.DELETED);
		b =  em.merge(b);
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
	
	public Blogger update(Blogger blogger){
		Blogger existingBlogger = null;
		if(blogger == null || blogger.getId() < 1 || (existingBlogger = getBloggerById(blogger.getId())) == null) {
			throw new CoreServiceException("Blogger does not exist");
		}
		String name = blogger.getName();
		String oldName = existingBlogger.getName();
		if(!oldName.equalsIgnoreCase(name)) {
			existingBlogger = checkExistingBlogger(existingBlogger, name);
			String fileDirectory = settingService.getStringValue(Constants.SETTING_BLOGGER_DIR,
					System.getProperty("java.io.tmpdir"));
			renameBloggerFile(fileDirectory + File.separator, oldName, existingBlogger.getName());
		}
		existingBlogger.setDescription(blogger.getDescription());
		existingBlogger.setUpdateDate(new Date());
		existingBlogger = em.merge(existingBlogger);
		return existingBlogger;
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
	
	private Blogger getBloggerById(long id) {
		return em.find(Blogger.class, id);
	}
	
	private Blogger checkExistingBlogger(Blogger blogger, String filename) {
		String extendName = retriveFileExtendedName(filename);
		String fileExtensions = settingService.getStringValue(Constants.SETTING_BLOGGER_FILE_EXTENTIONS);
		if (null != fileExtensions) {
			String fileNames[] = fileExtensions.split(",");
			if (!Arrays.asList(fileNames).contains(extendName)) {
				throw new CoreServiceException("Invalid File format");
			}
		}
		if("pdf".equalsIgnoreCase(extendName)) {
			blogger.setType(Blogger.Type.PDF);
		} else if("html".equalsIgnoreCase(extendName)) {
			blogger.setType(Blogger.Type.HTML);
		} else if("jsp".equalsIgnoreCase(extendName)) {
			blogger.setType(Blogger.Type.JSP);
		} else if("txt".equalsIgnoreCase(extendName)) {
			blogger.setType(Blogger.Type.TXT);
		} else {
			logger.info("Invalid File format for {}", filename);
			throw new CoreServiceException("Invalid File format");
		}
		String cleanName = retriveFileNameNoExtended(filename);
		String savedFileName = RegexUtils.replaceSpecialChar(cleanName, Constants.HYPHEN_CHAR) + "." + extendName;
		if(getBloggerByName(savedFileName) != null) {
			throw new CoreServiceException("Blogger File [ + " + savedFileName + "] is existing");
		}
		blogger.setName(savedFileName);
		blogger.setDescription(cleanName);
		return blogger;
	}

	private void renameBloggerFile(String directory, String oldName, String newName) {
		File oldfile = new File(directory + oldName);

		if (!oldfile.exists()) {
			throw new CoreServiceException("Blogger File [ + " + directory + oldName + "] is not existing");
		}

		File newfile = new File(directory + newName);
		oldfile.renameTo(newfile);
	}
}

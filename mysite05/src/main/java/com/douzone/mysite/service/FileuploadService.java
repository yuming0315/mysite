package com.douzone.mysite.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.exception.FileuploadServiceException;

@Service
@PropertySource("classpath:com/douzone/mysite/web/fileupload.properties")
public class FileuploadService {
	@Autowired
	private Environment env;
	
	public String restore(MultipartFile multipartFile) {
		String url = null;
		try {
			File uploadDirectory = new File(env.getProperty("fileupload.uploadLocation"));
			if(!uploadDirectory.exists()) {
				uploadDirectory.mkdirs();
			}
			
			if (multipartFile.isEmpty()) {
				return url;
			}

			String originFilename = multipartFile.getOriginalFilename();
			String extName = originFilename.substring(originFilename.lastIndexOf(".") + 1);
			String saveFilename = generateSaveFilename(extName);

			byte[] data = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(env.getProperty("fileupload.uploadLocation")+"/"+saveFilename);
			os.write(data);
			os.close();
			url = env.getProperty("fileupload.resourceUrl") +"/"+saveFilename;
		} catch (IOException ex) {
			throw new FileuploadServiceException(ex.toString());
		}
		return url;
	}

	private String generateSaveFilename(String extName) {
		String filename = "";

		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);

		return filename;
	}

	public void removeFile(String url) {
		File file = new File(url);
		if(!file.exists()) {
			return;
		}
		
		file.delete();
	}

}

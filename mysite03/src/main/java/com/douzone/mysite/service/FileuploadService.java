package com.douzone.mysite.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.exception.FileuploadServiceException;

@Service
public class FileuploadService {
	// 경로넣을때 드라이브이름 넣지말기 운영체제에서 알아서 해줌
	private static String SAVE_PATH = "/mysite-uploads";
	private static String URL_PATH = "/assets/upload-images";

	public String restore(MultipartFile multipartFile) {
		String url = "";
		try {
			File uploadDirectory = new File(SAVE_PATH);
			if(!uploadDirectory.exists()) {
				uploadDirectory.mkdirs();
			}
			
			if (multipartFile.isEmpty()) {
				return url;
			}

			String originFilename = multipartFile.getOriginalFilename();
			String extName = originFilename.substring(originFilename.lastIndexOf(".") + 1);
			String saveFilename = generateSaveFilename(extName);
			Long filesize = multipartFile.getSize();

			byte[] data = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH+"/"+saveFilename);
			os.write(data);
			os.close();
			url = URL_PATH +"/"+saveFilename;
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

}

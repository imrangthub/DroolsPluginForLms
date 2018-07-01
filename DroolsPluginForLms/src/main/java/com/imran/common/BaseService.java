package com.imran.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class BaseService{

	public boolean removeFile(String fileName) {
		File file = new File("F:/IMRAN_HOSSAIN/LMS/LMS_DROOLES_PLUGIN/DroolsPluginForLms/src/main/resources/rules/"+fileName);
		return file.delete();
	}

	public Boolean checkFile(MultipartFile file) {
		boolean fileExtCheck = false;
		try {
			byte[] bytes = file.getBytes();
			String fileName = file.getOriginalFilename();
			String[] fileExt = { ".xlsx"};
			
			int IndexOf = fileName.indexOf(".");
			String currentExt = fileName.substring(IndexOf);
			System.out.println("Current File formate: " + currentExt);
			fileExtCheck = Arrays.asList(fileExt).contains(currentExt);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileExtCheck;
	}

	public String uploadFileName(MultipartFile file) {
		String uniqName = null;
		byte[] bytes;
		try {
			bytes = file.getBytes();
			String fileName = file.getOriginalFilename();
			Random generator = new Random();
			int r = Math.abs(generator.nextInt());
			uniqName = r + "_" + (String) fileName;
			File dir = new File("F:/IMRAN_HOSSAIN/LMS/LMS_DROOLES_PLUGIN/DroolsPluginForLms/src/main/resources/rules/");
			// Create the file on server
			File serverFile = new File(dir.getAbsolutePath() + File.separator + uniqName);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return uniqName;
	}

}

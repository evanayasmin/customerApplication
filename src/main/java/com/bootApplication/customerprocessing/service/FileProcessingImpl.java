package com.bootApplication.customerprocessing.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bootApplication.customerprocessing.entity.Customer;
import com.bootApplication.customerprocessing.repository.CustomerRepo;
import com.bootApplication.customerprocessing.repository.FileProcessingRepo;

import ch.qos.logback.classic.Logger;

@Service
public class FileProcessingImpl implements FileProcessing{
	
    @Autowired
    FileProcessingRepo fileProcessingRepo;
    
	
	private static final Logger log = (Logger) LoggerFactory.getLogger(FileProcessingImpl.class);
	
	

	@Override
	public String importData(MultipartFile file) {
	
		log.info("File Name= {}", file.getOriginalFilename());
		try {
			String content = new String(file.getBytes());
			String[] customerdata = content.split("\n");
			log.info("File Content= {}", customerdata[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "Text file got here.";
	}

	@Override
	public String exportValidCustomer() {
		// TODO Auto-generated method stub
		return "File exported successfully.";
	}

	@Override
	public String exportInvalidCustomer() {
		// TODO Auto-generated method stub
		return "Invalid customers saved successfully.";
	}

}

package com.bootApplication.customerprocessing.service;

import org.springframework.web.multipart.MultipartFile;


public interface FileProcessing {

	String importData(MultipartFile file);
	
	String exportValidCustomer();
	
	String exportInvalidCustomer();

	
}

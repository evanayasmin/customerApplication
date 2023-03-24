package com.bootApplication.customerprocessing.controller;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bootApplication.customerprocessing.entity.Customer;
import com.bootApplication.customerprocessing.service.CustomerService;
import com.bootApplication.customerprocessing.service.FileProcessing;
import com.bootApplication.customerprocessing.service.FileProcessingImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.qos.logback.classic.Logger;

@Controller
public class HomeController {
	
	
	//private final FileProcessing fileProcessing;
	
	CustomerService customerService;
	
	@Autowired
	public HomeController(CustomerService customerService) {
		
		this.customerService = customerService;
	}
	
	@RequestMapping("/home")
	public String HomePage(Model model) {
		
		model.addAttribute("helloMessage", "Welcome to the customer Processing.");
		return "HomePage.html";
	}
	
	@PostMapping("/uploadFile")
	public String FileImport(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, Model model) throws Exception {
		
		 if (file.isEmpty()) {
	            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
	            return "redirect:/home";
	        }
		 
		 customerService.saveCustomers(file);
		 
		 		
		return "redirect:/home";
	}
	

	
	 @GetMapping(value = "/getCustomersByThread", produces = "application/json")
	    public ResponseEntity<InputStreamResource> getCustomer() throws IOException{
		    System.out.println("Get valid customers");
	        CompletableFuture<List<Customer>> customer1 = customerService.findAllCustomers();
	        CompletableFuture<List<Customer>> customer2 = customerService.findAllCustomers();
	        CompletableFuture<List<Customer>> customer3 = customerService.findAllCustomers();
	       /* if(customer3.isDone()) {
	        	 System.out.println("Customer3 is done");
	        	 ObjectMapper mapper = new ObjectMapper(); 
	        	 byte[] buf = mapper.writeValueAsBytes(customer1);

	        	 return ResponseEntity
	        	         .ok()
	        	         .contentLength(buf.length)
	        	         .header("Content-Disposition", "attachment; filename=\"customer3.json\"")
	        	         .contentType(
	        	                 MediaType.parseMediaType("application/octet-stream"))
	        	         .body(new InputStreamResource(new ByteArrayInputStream(buf)));
	        	 
	        }
	        */
	        CompletableFuture<List<Customer>> customer4 = customerService.findAllCustomers();
	        CompletableFuture<List<Customer>> customer5 = customerService.findAllCustomers();
	        CompletableFuture.allOf(customer1,customer2,customer3,customer4,customer5).join();
	       
	        ObjectMapper mapper = new ObjectMapper(); 
       	   byte[] buf = mapper.writeValueAsBytes(customer1);

       	 return ResponseEntity
       	         .ok()
       	         .contentLength(buf.length)
       	         .header("Content-Disposition", "attachment; filename=\"customer1.json\"")
       	         .contentType(
       	                 MediaType.parseMediaType("application/octet-stream"))
       	         .body(new InputStreamResource(new ByteArrayInputStream(buf)));
	    }
	 
	  
}

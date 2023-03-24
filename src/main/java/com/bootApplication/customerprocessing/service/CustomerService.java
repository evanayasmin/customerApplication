package com.bootApplication.customerprocessing.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bootApplication.customerprocessing.entity.Customer;
import com.bootApplication.customerprocessing.repository.CustomerRepo;

import ch.qos.logback.classic.Logger;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepo;
    
	private static final Logger log = (Logger) LoggerFactory.getLogger(FileProcessingImpl.class);

    @Async
    public CompletableFuture<List<Customer>> saveCustomers(MultipartFile file) throws Exception {
        long start = System.currentTimeMillis();
        List<Customer> customers = parseTxtFile(file);
        
        log.info("saving list of customers of size {}", customers.size(), "" + Thread.currentThread().getName());
        customers = customerRepo.saveAll(customers);
        long end = System.currentTimeMillis();
        log.info("Total time taken Millis= {}", (end - start));
        return CompletableFuture.completedFuture(customers);
    }
    
    
    private List<Customer> parseTxtFile(final MultipartFile file) throws Exception {
        final List<Customer> customers = new ArrayList<>();
        try {
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    final String[] data = line.split(",");
                    final Customer customer = new Customer();
                    customer.setFirstName(data[0]);
                    customer.setLastName(data[1]);
                    customer.setCity(data[2]);
                    customer.setState(data[3]);
                    customer.setPostcode(data[4]);
                    customer.setMobile(data[5]);
                    customer.setEmail(data[6]);
                    customer.setIpAddress(data[7]);
                    customers.add(customer);
                }
                return customers;
            }
        } catch (final IOException e) {
            log.error("Failed to parse Txt file {}", e);
            throw new Exception("Failed to parse Txt file {}", e);
        }
    }
    
    @Async
    public CompletableFuture<List<Customer>> findAllCustomers(){
        log.info("get list of customers by "+Thread.currentThread().getName());
        List<Customer> customers = customerRepo.findAll();
        return CompletableFuture.completedFuture(customers);
    }
    
}

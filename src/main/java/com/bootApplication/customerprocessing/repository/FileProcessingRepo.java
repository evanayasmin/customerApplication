package com.bootApplication.customerprocessing.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class FileProcessingRepo {

	    @Autowired
	    JdbcTemplate db;
	    @Autowired
	    NamedParameterJdbcTemplate nDb;
	    
	    

}

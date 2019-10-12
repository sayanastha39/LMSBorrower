package com.lms.LMSBorrower;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lms.LMSBorrower.BorrowerDAO.BorrowerDAO;
import com.lms.LMSBorrower.Service.BorrowerService;


//initializing your beans (objects)
@Configuration
public class BeanConfig {
	
	//
	@Bean
	public BorrowerDAO getBorrowerByCardNo() {
		return new BorrowerDAO();
		
	}
	
	@Bean
	public BorrowerService getBorrowerService() {
		return new BorrowerService();
		
	}

}

package com.lms.LMSBorrower.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lms.LMSBorrower.POJO.Borrower;
import com.lms.LMSBorrower.Service.BorrowerService;

@RestController //whole control is done through this 

public class BorrowerController {

	@Autowired
	BorrowerService borrowerService;
	
	//it gives us path; everything in {} is gonna be the input
	//@PathVariable is for user inputs 
	@RequestMapping("/LMSBorrower/cardNo/{cardNo}") 
	
	public Borrower getBorrowerByCardNo(@PathVariable int cardNo) {
		return borrowerService.getBorrowerByCardNo(cardNo);
	}
	
	
	
	
}

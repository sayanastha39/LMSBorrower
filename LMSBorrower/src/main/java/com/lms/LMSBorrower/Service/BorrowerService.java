package com.lms.LMSBorrower.Service;

import org.springframework.beans.factory.annotation.Autowired;
import com.lms.LMSBorrower.BorrowerDAO.BorrowerDAO;
import com.lms.LMSBorrower.POJO.Borrower;

public class BorrowerService {

	
	 @Autowired //it lets you access DAO without creating instance
	 BorrowerDAO borrowerDao;
	 
	    public Borrower getBorrowerByCardNo(int cardNo) {
	        return borrowerDao.getBorrowerByCardNo(cardNo);
	    }
}

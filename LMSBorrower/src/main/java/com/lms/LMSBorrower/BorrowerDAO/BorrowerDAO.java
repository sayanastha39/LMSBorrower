package com.lms.LMSBorrower.BorrowerDAO;

import com.lms.LMSBorrower.POJO.Borrower;

public class BorrowerDAO {
	
	
	
	public Borrower getBorrowerByCardNo(int cardNo) {
		Borrower bor = new Borrower();
		 bor.setCardNo(1);
		 bor.setName("Sayana");
		 return bor;
	}

}

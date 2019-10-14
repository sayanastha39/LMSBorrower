package com.lms.LMSBorrower.Service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.lms.LMSBorrower.BorrowerDAO.BorrowerDAO;
import com.lms.LMSBorrower.POJO.Book;
import com.lms.LMSBorrower.POJO.Borrower;
import com.lms.LMSBorrower.POJO.LibraryBranch;

@Component
public class BorrowerService {

	
	 @Autowired //it lets you access DAO without creating instance
	 BorrowerDAO borrowerDao;
	 
	    public Borrower getBorrowerByCardNo(int cardNo) throws SQLException {
	        return borrowerDao.getBorrowerByCardNo(cardNo);
	    }
	    
	    public List<LibraryBranch> displayBranchCheckout() throws SQLException {
	        return borrowerDao.displayBranchCheckout();
	    }
	    
	    public List<Book>  displayBookCheckout(int branchId) throws SQLException {
	        return borrowerDao.displayBookCheckout(branchId);
	    }
	    
	    public List<Book> displayBookReturn(int branchId, int cardNo) throws SQLException {
	        return borrowerDao.displayBookReturn(branchId,  cardNo);
	    }
	    
	    public String writeLoans(int cardNo, int branchId, int bookId) throws SQLException {
	        return borrowerDao.writeLoans(cardNo, branchId, bookId);
	    }
	    
	    public String writeReturn(int cardNo, int branchId, int bookId) throws SQLException {
	        return borrowerDao.writeReturn(cardNo, branchId, bookId);
	    }
	    

}

package com.lms.LMSBorrower.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.lms.LMSBorrower.BorrowerDAO.BorrowerDAO;
import com.lms.LMSBorrower.POJO.BookBL;
import com.lms.LMSBorrower.POJO.BookLoansBL;
import com.lms.LMSBorrower.POJO.Borrower;
import com.lms.LMSBorrower.POJO.LibraryBranch;

@Component
public class BorrowerService {
	
	 @Autowired //it lets you access DAO without creating instance
	 BorrowerDAO borrowerDao;
	 
	 public boolean ifCardExistsBorrower(int cardNo){
		 List<Borrower> list = borrowerDao.ifCardExists();
		 boolean cardExists = list.stream()
	                .anyMatch(id -> id.getCardNo().equals(cardNo));
		 return cardExists;
		 }
	 
	 public boolean ifCardExistsBranch(int branchId) {
		 List<LibraryBranch> bch = borrowerDao.displayBranchCheckout();
		 boolean branchExists = bch.stream()
		                .anyMatch(id -> id.getBranchId().equals(branchId));
		 return branchExists;
		 } 
	 
		//  book checkout    
	 public boolean ifCardExistsBook( int branchId, int bookId){
		 List<BookBL> bk = borrowerDao.displayBookCheckout(branchId);
		 boolean bookExists  = bk.stream()
			                .anyMatch(id -> id.getBookId().equals(bookId));
		 return bookExists;
		 } 
	 
	 public boolean existsBranchReturn(int branchId) {
		 List<LibraryBranch> bch = borrowerDao.displayBranchReturn();
		 boolean branchExists = bch.stream()
		                .anyMatch(id -> id.getBranchId().equals(branchId));
		 return branchExists;
		 } 
	 
	 //book return
	 public boolean existsBookReturn( int cardNo, int branchId,  int bookId){
		 List<BookBL> bk = borrowerDao.displayBookReturn(cardNo, branchId);
		 boolean bookExists = bk.stream()
			                .anyMatch(id -> id.getBookId().equals(bookId));
		 return bookExists;
		 } 
	 
	 //CHECKOUT VALIDATE IF Exists
	 public boolean existsCheckout( int cardNo, int branchId,  int bookId){
		 List<BookLoansBL> bk = borrowerDao.checkoutValidate(cardNo, branchId, bookId);
		 boolean checkedOut = bk.stream()
			                .anyMatch(id -> id.getCardNo().equals(cardNo) && id.getBranchId().equals(branchId) && id.getBookId().equals(bookId));
		 return checkedOut;
		 }
	 
	 public void writeLoans(BookLoansBL bookLoansBL){
	        borrowerDao.writeLoans(bookLoansBL);
	    }
	 
	 public void writeReturn(BookLoansBL bookLoansBL) {
	         borrowerDao.writeReturn(bookLoansBL);
	    }
}

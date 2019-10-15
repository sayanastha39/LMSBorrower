package com.lms.LMSBorrower.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.lms.LMSBorrower.BorrowerDAO.BorrowerDAO;
import com.lms.LMSBorrower.POJO.Book;
import com.lms.LMSBorrower.POJO.BookLoans;
import com.lms.LMSBorrower.POJO.Borrower;
import com.lms.LMSBorrower.POJO.LibraryBranch;

@Component
public class BorrowerService {
	
	 @Autowired //it lets you access DAO without creating instance
	 BorrowerDAO borrowerDao;
	 
	 public boolean ifCardExistsBorrower(int cardNo){
		 List<Borrower> list = borrowerDao.ifCardExists();
		 boolean exists = list.stream()
	                .anyMatch(id -> id.getCardNo().equals(cardNo));
		 return exists;
		 }
	 
	 public boolean ifCardExistsBranch(int branchId) {
		 List<LibraryBranch> bch = borrowerDao.displayBranchCheckout();
		 boolean exists = bch.stream()
		                .anyMatch(id -> id.getBranchId().equals(branchId));
		 return exists;
		 } 
	 
		//  book checkout    
	 public boolean ifCardExistsBook( int branchId, int bookId){
		 List<Book> bk = borrowerDao.displayBookCheckout(branchId);
		 boolean exists = bk.stream()
			                .anyMatch(id -> id.getBookId().equals(bookId));
		 return exists;
		 } 
	 
	 public boolean existsBranchReturn(int branchId) {
		 List<LibraryBranch> bch = borrowerDao.displayBranchReturn();
		 boolean exists = bch.stream()
		                .anyMatch(id -> id.getBranchId().equals(branchId));
		 return exists;
		 } 
	 
	 //book return
	 public boolean existsBookReturn( int cardNo, int branchId,  int bookId){
		 List<Book> bk = borrowerDao.displayBookReturn(cardNo, branchId);
		 boolean exists = bk.stream()
			                .anyMatch(id -> id.getBookId().equals(bookId));
		 return exists;
		 } 
	 
	 //CHECKOUT VALIDATE IF Exists
	 public boolean existsCheckout( int cardNo, int branchId,  int bookId){
		 List<BookLoans> bk = borrowerDao.checkoutValidate(cardNo, branchId, bookId);
		 boolean exists = bk.stream()
			                .anyMatch(id -> id.getCardNo().equals(cardNo) && id.getBranchId().equals(branchId) && id.getBookId().equals(bookId));
		 return exists;
		 }
	 
	 public void writeLoans(int cardNo, int branchId, int bookId){
	        borrowerDao.writeLoans(cardNo, branchId, bookId);
	    }
	 
	 public void writeReturn(int cardNo, int branchId, int bookId) {
	         borrowerDao.writeReturn(cardNo, branchId, bookId);
	    }
	 
	 public List<LibraryBranch> displayBranchCheckout(){
		 return borrowerDao.displayBranchCheckout();
	 }
	 
	 public List<Book>  displayBookCheckout(int branchId)  {
		 return borrowerDao.displayBookCheckout(branchId);
	}
	 
	public List<Book> displayBookReturn(int branchId, int cardNo) {
		return borrowerDao.displayBookReturn(branchId,  cardNo);
	}
}

package com.lms.LMSBorrower.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import com.lms.LMSBorrower.BorrowerDAO.BorrowerDAO;
import com.lms.LMSBorrower.POJO.Book;
import com.lms.LMSBorrower.POJO.Borrower;
import com.lms.LMSBorrower.POJO.LibraryBranch;

@Component
public class BorrowerService {
	
	 @Autowired //it lets you access DAO without creating instance
	 BorrowerDAO borrowerDao;
	 
	 public boolean ifCardExistsBorrower(int cardNo) throws SQLException{
		 List<Borrower> list = borrowerDao.ifCardExists();
		 boolean exists = list.stream()
	                .anyMatch(id -> id.getCardNo().equals(cardNo));
		 return exists;
		 }
	 
	 public boolean ifCardExistsBranch(int branchId) throws SQLException{
		 List<LibraryBranch> bch = borrowerDao.displayBranchCheckout();
		 boolean exists = bch.stream()
		                .anyMatch(id -> id.getBranchId().equals(branchId));
		 return exists;
		 } 
		        
	 public boolean ifCardExistsBook( int branchId, int bookId) throws SQLException{
		 List<Book> bk = borrowerDao.displayBookCheckout(branchId);
		 boolean exists = bk.stream()
			                .anyMatch(id -> id.getBookId().equals(bookId));
		 return exists;
		 } 
	 
	 public boolean existsBookReturn( int cardNo, int branchId,  int bookId) throws SQLException{
		 List<Book> bk = borrowerDao.displayBookReturn(cardNo, branchId);
		 boolean exists = bk.stream()
			                .anyMatch(id -> id.getBookId().equals(bookId));
		 return exists;
		 } 
	 
	 public ResponseEntity<String> writeLoans(int cardNo, int branchId, int bookId) throws SQLException {
	        return borrowerDao.writeLoans(cardNo, branchId, bookId);
	    }
	 
	 public ResponseEntity<String> writeReturn(int cardNo, int branchId, int bookId) throws SQLException {
	        return borrowerDao.writeReturn(cardNo, branchId, bookId);
	    }
}

package com.lms.LMSBorrower.Controller;

import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lms.LMSBorrower.POJO.Book;
import com.lms.LMSBorrower.POJO.Borrower;
import com.lms.LMSBorrower.POJO.LibraryBranch;
import com.lms.LMSBorrower.Service.BorrowerService;

@RestController //whole control is done through this 
@Component //initializing bean
public class BorrowerController {

	@Autowired
	BorrowerService borrowerService;
	
	//it gives us path; everything in {} is going to be the input
	//@PathVariable is for user inputs 
	//@RequestMapping gives path 
	
	@RequestMapping("/LMSBorrower/cardNo/{cardNo}") 
	public Borrower getBorrowerByCardNo(@PathVariable int cardNo) throws SQLException {
		return borrowerService.getBorrowerByCardNo(cardNo);
	}
	
	//checkout show branch
	@RequestMapping("/LMSBorrower/cardNo/{cardNo}/option/1/branch") 
	public  List<LibraryBranch> checkoutOption(@PathVariable int cardNo) throws SQLException {
			return 	borrowerService.displayBranchCheckout();
	}
	
	//checkout show book
		@RequestMapping("/LMSBorrower/cardNo/{cardNo}/option/1/branch/{branch}/book") 
		public  List<Book> displayCheckoutBook(@PathVariable (value="cardNo") int cardNo, @PathVariable(value="branch") int branchId) throws SQLException {
				return 	borrowerService.displayBookCheckout(branchId);
		}
		
		//create new record in loans table and update copies
		@RequestMapping(value ="/LMSBorrower/cardNo/{cardNo}/option/1/branch/{branch}/book/{book}", method = { RequestMethod.PUT, RequestMethod.GET}) 
		@ResponseStatus(code = HttpStatus.CREATED, reason = "Book checked out sucessfully.")
		public  String writeLoans(@PathVariable (value="cardNo") int cardNo, @PathVariable(value="branch") int branchId, @PathVariable (value="book") int bookId) throws SQLException {
			return borrowerService.writeLoans(cardNo, branchId, bookId);
		}
		
		//return show branch
		@RequestMapping("/LMSBorrower/cardNo/{cardNo}/option/2/branch") 
		public  List<LibraryBranch> ReturnOption(@PathVariable (value="cardNo") int cardNo) throws SQLException {
				return 	borrowerService.displayBranchCheckout();
		}
		
		//return show book
				@RequestMapping("/LMSBorrower/cardNo/{cardNo}/option/2/branch/{branch}/book") 
				public  List<Book> displayReturnBook(@PathVariable(value="branch") int branchId, @PathVariable (value="cardNo") int cardNo) throws SQLException {
						return 	borrowerService.displayBookReturn(branchId, cardNo);
				}
		
		//delete record in loans table and update copies
		@RequestMapping(value ="/LMSBorrower/cardNo/{cardNo}/option/2/branch/{branch}/book/{book}", method = { RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.GET}) 
		@ResponseStatus(code = HttpStatus.CREATED, reason = "Book returned sucessfully.")
		public  String writeReturn(@PathVariable (value="cardNo") int cardNo, @PathVariable(value="branch") int branchId, @PathVariable (value="book") int bookId) throws SQLException {
			return borrowerService.writeReturn(cardNo, branchId, bookId);
		}
}

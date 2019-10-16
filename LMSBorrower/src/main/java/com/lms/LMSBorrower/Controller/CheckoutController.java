package com.lms.LMSBorrower.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lms.LMSBorrower.POJO.BookLoansBL;
import com.lms.LMSBorrower.Service.BorrowerService;

@RestController 
@Component 
@RequestMapping (value = "/LMSBorrower")
public class CheckoutController{

	@Autowired
	BorrowerService borrowerService;
	
	@PutMapping(value="/cardNo/{cardNo}/branches/{branchId}/books/{bookId}/checkout", 
			consumes = {"application/xml", "application/json"}) 
	public  ResponseEntity<BookLoansBL> writeLoans(@RequestHeader("Accept") String accept, 
			@RequestBody BookLoansBL bookLoansBL){
		
		boolean cardExists = borrowerService.ifCardExistsBorrower(bookLoansBL.getCardNo());
		boolean branchExists = borrowerService.ifCardExistsBranch(bookLoansBL.getBranchId());
		boolean bookExists = borrowerService.ifCardExistsBook(bookLoansBL.getBranchId(), bookLoansBL.getBookId());
		boolean checkedOut = borrowerService.existsCheckout
				(bookLoansBL.getCardNo(), bookLoansBL.getBranchId(), bookLoansBL.getBookId());
		
		if((cardExists && branchExists  && bookExists) == true && checkedOut == false && bookLoansBL!= null)
		{
			borrowerService.writeLoans(bookLoansBL);
			return new ResponseEntity<BookLoansBL>(bookLoansBL, HttpStatus.CREATED);
			}
		else {
			return new ResponseEntity<BookLoansBL>(bookLoansBL, HttpStatus.NOT_FOUND);
			}
	}
}



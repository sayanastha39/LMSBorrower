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

@RestController //whole control is done through this 
@Component //initializing bean
@RequestMapping (value =  "/LMSBorrower")

public class ReturnController {

	@Autowired
	BorrowerService borrowerService;
	
	@PutMapping(value = "cardNo/{cardNo}/branches/{branchId}/books/{bookId}/return", 
			consumes={"application/xml","application/json"})
	
	public ResponseEntity<BookLoansBL> writeReturn(@RequestHeader("Accept") String accept, 
			@RequestBody BookLoansBL bookLoansBL){
		
		boolean cardExists = borrowerService.ifCardExistsBorrower(bookLoansBL.getCardNo());
		boolean branchExists = borrowerService.existsBranchReturn(bookLoansBL.getBranchId());
		boolean bookExists = borrowerService.existsBookReturn
				(bookLoansBL.getCardNo(), bookLoansBL.getBranchId(), bookLoansBL.getBookId());
		
		if(cardExists && branchExists && bookExists == true && bookLoansBL!= null){
			borrowerService.writeReturn(bookLoansBL);
			return new ResponseEntity<BookLoansBL>(bookLoansBL, HttpStatus.ACCEPTED);
			}
		else {
			return new ResponseEntity<BookLoansBL>(bookLoansBL, HttpStatus.NOT_FOUND);
		}
	}
}

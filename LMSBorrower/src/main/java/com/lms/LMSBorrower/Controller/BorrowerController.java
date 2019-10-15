package com.lms.LMSBorrower.Controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	
	//create new record in loans table and update copies
	@RequestMapping(value ="/LMSBorrower/cardNo/{cardNo}/checkout/branch/{branch}/book/{book}", method = {RequestMethod.PUT, RequestMethod.GET}) 
	
	public  ResponseEntity<String> writeLoans(@PathVariable (value="cardNo") int cardNo, @PathVariable(value="branch") int branchId, @PathVariable (value="book") int bookId){
		boolean exists = borrowerService.ifCardExistsBorrower(cardNo);
		
		if(exists== true) {
			exists = borrowerService.ifCardExistsBranch(branchId);
			
			if(exists ==true) {
				exists = borrowerService.ifCardExistsBook(branchId, bookId);
				
				if (exists ==true) {
					exists = borrowerService.existsCheckout(cardNo, branchId, bookId);
				
					if (exists ==false) {
						borrowerService.writeLoans( cardNo, branchId, bookId);
						return new ResponseEntity<>("Book sucessfully checked out", HttpStatus.CREATED);
					}
					else {
						return new ResponseEntity<>("Book already checked out", HttpStatus.CONFLICT);
					}
				}
				else {
					return new ResponseEntity<>("Book id not found", HttpStatus.NOT_FOUND);
				}
			}
			else {
				return new ResponseEntity<>("Branch id not found", HttpStatus.NOT_FOUND);
				}
			}
		
		else{
			return new ResponseEntity<>("Invalid card", HttpStatus.NOT_FOUND);
			}
	
	}
	
	//delete record in loans table and update copies
	@RequestMapping(value ="/LMSBorrower/cardNo/{cardNo}/return/branch/{branch}/book/{book}", method = {RequestMethod.PUT,  RequestMethod.GET, RequestMethod.DELETE}) 
	public ResponseEntity<String> writeReturn(@PathVariable (value="cardNo") int cardNo, @PathVariable(value="branch") int branchId, @PathVariable (value="book") int bookId){
		
		boolean exists = borrowerService.ifCardExistsBorrower(cardNo);
		
		if(exists== true) {
			exists = borrowerService.existsBranchReturn(branchId);
			if(exists ==true) {
				exists = borrowerService.existsBookReturn(cardNo, branchId, bookId);
				
				if (exists ==true) {
					borrowerService.writeReturn(cardNo, branchId, bookId);
					return new ResponseEntity<>("Book returned",HttpStatus.ACCEPTED);
					}
				else {
					return new ResponseEntity<>("Book id not found", HttpStatus.NOT_FOUND);
					}
				}
			
			else {
					return new ResponseEntity<>("Branch id not found", HttpStatus.NOT_FOUND);
				}
			}
		
		
		else{
			return new ResponseEntity<>("Invalid card", HttpStatus.NOT_FOUND);
			}
		}
	
	//checkout show branch
	@RequestMapping("/LMSBorrower/cardNo/{cardNo}/checkout/branch") 
	public  List<LibraryBranch> checkoutOption(@PathVariable int cardNo){
			return 	borrowerService.displayBranchCheckout();
	}
	
	//checkout show book
	@RequestMapping("/LMSBorrower/cardNo/{cardNo}/checkout/branch/{branch}/book") 
	public  List<Book> displayCheckoutBook(@PathVariable (value="cardNo") int cardNo, @PathVariable(value="branch") int branchId) {
		return 	borrowerService.displayBookCheckout(branchId);
	}
	
	//return show branch
	@RequestMapping("/LMSBorrower/cardNo/{cardNo}/return/branch") 
		public  List<LibraryBranch> ReturnOption(@PathVariable (value="cardNo") int cardNo) {
			return 	borrowerService.displayBranchCheckout();
		}
	//return show book
	@RequestMapping("/LMSBorrower/cardNo/{cardNo}/return/branch/{branch}/book") 
	public  List<Book> displayReturnBook(@PathVariable(value="branch") int branchId, @PathVariable (value="cardNo") int cardNo) {
		return 	borrowerService.displayBookReturn(branchId, cardNo);
	}
	@RequestMapping("/LMSBorrower/cardNo") 
	public List<Borrower>  ifCardExists() {
		return borrowerService.getBorrowerByCardNo();
	}

}

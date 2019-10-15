package com.lms.LMSBorrower.BorrowerDAO;

import java.util.List;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import org.springframework.stereotype.Component;
import com.lms.LMSBorrower.POJO.*;
@Component
public class BorrowerDAO {
	
	public Connection getConnection(){
		Connection con = null;
		try {
			 con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","TapHouse123");
	         } 
		catch(SQLException e) {
	            System.out.println(e);
	    }
		return con;
	}
	
	//get cardNo
	public List<Borrower> ifCardExists(){
		List<Borrower> brw = new ArrayList<Borrower>();
		try {
			String sql = "SELECT * FROM tbl_borrower ";
			PreparedStatement ps = getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			 
			while (rs.next()) {
				Borrower borrower = new Borrower();
				borrower.setCardNo(rs.getInt("cardNo"));
				borrower.setName(rs.getString("name"));
				borrower.setAddress(rs.getString("address"));
				borrower.setPhone(rs.getString("phone"));
				brw.add(borrower);
			}
		}
		catch (SQLException e) {
	        	System.out.println(e);
		}
		return brw;
	}
	
	//show branch for checkout 
	public List<LibraryBranch>  displayBranchCheckout() {
		List<LibraryBranch> bch = new ArrayList<LibraryBranch>();
		try {
			String sql = "SELECT * from tbl_library_branch";
			PreparedStatement ps = getConnection().prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				 LibraryBranch branch = new LibraryBranch();
				 branch.setBranchId(rs.getInt("branchId"));
				 branch.setBranchName(rs.getString("branchName"));
				 branch.setBranchAddress(rs.getString("branchAddress"));
				 bch.add(branch);	
				}
			}
			catch(SQLException e) {
	        	System.out.println(e);
			}
		return bch;
		}
	
	//show  book for checkout
	public List<Book>  displayBookCheckout(int branchId)  {
		List<Book> bk = new ArrayList<Book>();
		try {
			String sql= ("SELECT tbl_book.bookId, CONCAT(tbl_book.title, ' by ' , tbl_author.authorName) AS title  FROM tbl_book INNER JOIN tbl_author ON tbl_book.authId = tbl_author.authorId INNER JOIN tbl_book_copies ON tbl_book.bookId =  tbl_book_copies.bookId INNER JOIN tbl_library_branch ON tbl_book_copies.branchId = tbl_library_branch.branchId WHERE tbl_library_branch.branchId = ? AND tbl_book_copies.noOfCopies >=1 ");
			PreparedStatement ps = getConnection().prepareStatement(sql);
			ps.setInt (1, branchId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Book book = new Book();
				book.setBookId(rs.getInt("bookId"));
				book.setTitle(rs.getString("title"));
				bk.add(book);	
				}
		}
		catch(SQLException e) {
        	System.out.println(e);
		}
		return bk;
		}
	
	//checkout validate
	public List<BookLoans> checkoutValidate( int cardNo, int branchId, int bookId) {
		PreparedStatement ps = null;
		List<BookLoans> bc = new ArrayList<BookLoans>();
		
		try {
			//validate if already checked out
			ps = getConnection().prepareStatement("SELECT tbl_book_loans.bookid, tbl_book_loans.branchId, tbl_book_loans.cardNo FROM tbl_book_loans WHERE cardNo = ? AND branchId = ? AND bookId = ? ");
			ps.setInt (1, cardNo);
			ps.setInt (2, branchId);
			ps.setInt (3, bookId);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				BookLoans loans = new BookLoans();
				loans.setBookId(rs.getInt("bookId"));
				loans.setBranchId(rs.getInt("branchId"));
				loans.setCardNo(rs.getInt("cardNo"));
				bc.add(loans);
			}
		}
		catch(SQLException e) {
        	System.out.println(e);
		}
		return bc;
	}
	
	
	//checkout finalize
		public void writeLoans( int cardNo, int branchId, int bookId){
			Calendar c = Calendar.getInstance();
			Date startDate = new Date(c.getTime().getTime());
			PreparedStatement ps = null;
			
			try {
			//add in loans table
				String sql = "INSERT INTO tbl_book_loans(bookId, branchId, cardNo, dateOut, dueDate) "
								+ "VALUES (?, ?, ?, ?, DATE_ADD(?, INTERVAL 7 DAY))";
				ps = getConnection().prepareStatement(sql);
				ps.setInt(1, bookId);
				ps.setInt(2, branchId);
				ps.setInt(3, cardNo);
				ps.setDate(4,  startDate);
				ps.setDate(5, startDate);
				ps.executeUpdate();
				 
				//update copies table
				ps = getConnection().prepareStatement("UPDATE tbl_book_copies SET noOfCopies = noOfCopies-1 WHERE bookId = ? AND branchId = ? ");
				ps.setInt (1, bookId);
				ps.setInt (2, branchId);
				ps.executeUpdate();
				//return new ResponseEntity<String>("Book checked out" , HttpStatus.CREATED);
			}
			catch(SQLException e) {
	        	System.out.println(e);
			}
		}
    
		//show branch for return
		public List<LibraryBranch>  displayBranchReturn() {
			List<LibraryBranch> bch = new ArrayList<LibraryBranch>();
			try {
				String sql = "Select * from tbl_library_branch";
				PreparedStatement ps = getConnection().prepareStatement(sql);
				ResultSet rs = ps.executeQuery();
				
				while (rs.next()) {
					 LibraryBranch branch = new LibraryBranch();
					 branch.setBranchId(rs.getInt("branchId"));
					 branch.setBranchName(rs.getString("branchName"));
					 branch.setBranchAddress(rs.getString("branchAddress"));
					 bch.add(branch);	
					}
			}
			catch(SQLException e) {
	        	System.out.println(e);
			}
			return bch;
		}
		
		//show book to return
		public List<Book>  displayBookReturn( int cardNo, int branchId){
			List<Book> bk = new ArrayList<Book>();
			try {
				String sql= ("SELECT tbl_book.bookId, CONCAT(tbl_book.title, ' by ' , tbl_author.authorName)  AS title FROM tbl_book INNER JOIN tbl_author ON tbl_book.authId = tbl_author.authorId INNER JOIN tbl_book_loans ON tbl_book.bookId =  tbl_book_loans.bookId INNER JOIN tbl_borrower ON tbl_book_loans.cardNo = tbl_borrower.cardNo INNER JOIN tbl_library_branch ON tbl_book_loans.branchId = tbl_library_branch.branchId WHERE tbl_library_branch.branchId = ? AND tbl_borrower.cardNo =?");
				PreparedStatement ps = getConnection().prepareStatement(sql);
				ps.setInt (1, branchId);
				ps.setInt (2, cardNo);
				ResultSet rs = ps.executeQuery();
	
				while (rs.next()) {
					Book book = new Book();
					book.setBookId(rs.getInt("bookId"));
					book.setTitle(rs.getString("title"));
					bk.add(book);	
				}
			}
			catch(SQLException e) {
	        	System.out.println(e);
			}
			return bk;
		}
		
		//return finalize ResponseEntity<String>
		public void writeReturn( int cardNo, int branchId, int bookId){
			PreparedStatement ps = null;
			try {
				//delete record from loans
				String sql ="DELETE FROM tbl_book_loans  WHERE cardNo = ? AND branchId = ? AND bookId = ? ";
				ps = getConnection().prepareStatement(sql);
				ps.setInt(1, cardNo);
				ps.setInt(2, branchId);
				ps.setInt(3, bookId);
				ps.executeUpdate();
			
				//update copies table
				ps = getConnection().prepareStatement("UPDATE tbl_book_copies SET noOfCopies = noOfCopies+1 WHERE bookId = ? AND branchId = ? ");
				ps.setInt (1, bookId);
				ps.setInt (2, branchId);
				ps.executeUpdate();
			}
			catch(SQLException e) {
	        	System.out.println(e);
			}
		}	
}

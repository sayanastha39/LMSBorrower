package com.lms.LMSBorrower.POJO;

import java.util.Date;

public class BookLoansBL{
	private Integer bookId;
	private Integer branchId;
	private Integer cardNo;
	private Date dateOut;
	private Date dueDate;
	
	public BookLoansBL() {
		
	}
	
	public BookLoansBL(Integer bookId, Integer branchId, Integer cardNo, Date dateOut, Date dueDate) {
		super();
		this.bookId = bookId;
		this.branchId = branchId;
		this.cardNo = cardNo;
		this.dateOut = dateOut;
		this.dueDate = dueDate;
	}


	public Integer getBookId() {
		return bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
	}

	public Integer getCardNo() {
		return cardNo;
	}

	public void setCardNo(Integer cardNo) {
		this.cardNo = cardNo;
	}

	public Date getDateOut() {
		return dateOut;
	}

	public void setDateOut(Date dateOut) {
		this.dateOut = dateOut;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
}
package com.bams.exception;

/**
 * 贷款额不能为负数,如果用户试图将贷款额置为负数,则会抛出这个异常
 * */

public class LoanException extends ATMException{
	public LoanException() {
		
	}
	
	public LoanException(String message) {
		super(message);
	}
}

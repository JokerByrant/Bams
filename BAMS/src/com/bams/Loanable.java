package com.bams;

import com.bams.exception.BalanceNotEnoughException;
import com.bams.exception.LoanException;

public interface Loanable {
	
	// ´û¿î(requestLoan)
	public abstract Account requestLoan(double money) throws LoanException;
	
	// »¹´û(payLoan)
	public abstract Account payLoan(double money) throws BalanceNotEnoughException;
	
	public abstract double getLoanAmount();
	
}

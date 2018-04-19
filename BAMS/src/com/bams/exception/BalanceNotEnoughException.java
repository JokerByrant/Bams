package com.bams.exception;

/**
 * 用于取钱的时候余额不足的情况(包括账户余额超过透支额的情况)
 * */

public class BalanceNotEnoughException extends ATMException{
	public BalanceNotEnoughException() {
		super();
		 
	}

	public BalanceNotEnoughException(String message) {
		super(message);
		 
	}
	
	
}

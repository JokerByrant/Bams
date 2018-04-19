package com.bams.exception;

/**
 * ATM业务异常基类
 * */
public class ATMException extends Exception{
	public ATMException() {
		
	}
	
	public ATMException(String message) {
		super(message);
	}
}

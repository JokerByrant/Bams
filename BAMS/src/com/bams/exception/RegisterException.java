package com.bams.exception;

/**
 * 用于开户异常的情况,例如密码两次输入不一致等情况
 * */

public class RegisterException extends ATMException{

	public RegisterException() {
		
	}
	
	public RegisterException(String message) {
		super(message);
	}
}

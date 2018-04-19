package com.bams.exception;

/**
 * 用户登录异常的情况,例如id错误,密码错误
 * */

public class LoginException extends ATMException{
	public LoginException() {
		
	}
	
	public LoginException(String message) {
		super(message);
	}
}

package com.bams.exception;

/**
 * �û���¼�쳣�����,����id����,�������
 * */

public class LoginException extends ATMException{
	public LoginException() {
		
	}
	
	public LoginException(String message) {
		super(message);
	}
}

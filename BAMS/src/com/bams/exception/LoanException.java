package com.bams.exception;

/**
 * ������Ϊ����,����û���ͼ���������Ϊ����,����׳�����쳣
 * */

public class LoanException extends ATMException{
	public LoanException() {
		
	}
	
	public LoanException(String message) {
		super(message);
	}
}

package com.bams.exception;

/**
 * ����ȡǮ��ʱ����������(�����˻�����͸֧������)
 * */

public class BalanceNotEnoughException extends ATMException{
	public BalanceNotEnoughException() {
		super();
		 
	}

	public BalanceNotEnoughException(String message) {
		super(message);
		 
	}
	
	
}

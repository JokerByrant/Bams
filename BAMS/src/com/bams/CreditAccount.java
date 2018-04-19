package com.bams;

import com.bams.exception.BalanceNotEnoughException;

/**
 * �����˻�
 * */

public class CreditAccount extends Account {
	
	public CreditAccount() {
		super();
	}
	
	public CreditAccount(Long id,String password, String name, String personld, String email, double balance,
			double ceiling) {
		super(id,password, name, personld, email, balance);
		this.ceiling = ceiling;
	}
	
	public CreditAccount(Long id,String password, String name, String personld, String email, double balance) {
		super(id,password, name, personld, email, balance);
	}

	//͸֧���
	private double ceiling;
		
	public double getCeiling() {
		return ceiling;
	}

	public Account setCeiling(double ceiling) {
		this.ceiling = ceiling;
		return this;
	}
	
	@Override
	public Account withdraw(double cash) throws BalanceNotEnoughException{
		if(cash <= this.getBalance()) {
			this.setBalance(this.getBalance()-cash);
		}
		else {
			//���ȡǮ���û����͸֧���
			if(ceiling>=cash-this.getBalance()) {
				this.setBalance(this.getBalance()-cash);
			}
			else {
				throw new BalanceNotEnoughException("����");
			}
		}
		return this;
	}
	
	@Override
	public String toString() {
		return("{"+this.getId()+","+this.getName()+","+this.getEmail()+","+this.getPersonld()+","+this.getBalance()+","+this.getCeiling()+"}");
	}

}

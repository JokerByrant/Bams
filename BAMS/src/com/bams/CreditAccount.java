package com.bams;

import com.bams.exception.BalanceNotEnoughException;

/**
 * 信用账户
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

	//透支额度
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
			//如果取钱额度没超过透支额度
			if(ceiling>=cash-this.getBalance()) {
				this.setBalance(this.getBalance()-cash);
			}
			else {
				throw new BalanceNotEnoughException("余额不足");
			}
		}
		return this;
	}
	
	@Override
	public String toString() {
		return("{"+this.getId()+","+this.getName()+","+this.getEmail()+","+this.getPersonld()+","+this.getBalance()+","+this.getCeiling()+"}");
	}

}

package com.bams;

import com.bams.exception.BalanceNotEnoughException;

/**
 * 储蓄账户
 * */

public class SavingAccount extends Account{
	
	public SavingAccount() {
		super();
	}
	
	public SavingAccount(Long id,String password, String name, String personld, String email, double balance) {
		super(id,password, name, personld, email, balance);
	}
	
	@Override
	//取款方法
	public Account withdraw(double cash) throws BalanceNotEnoughException{
		if(cash <= this.getBalance())
			this.setBalance(this.getBalance()-cash);
		else {
			throw new BalanceNotEnoughException("余额不足");
		}
		return this;
	}
	
}

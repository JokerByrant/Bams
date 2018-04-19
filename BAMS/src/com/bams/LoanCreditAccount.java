package com.bams;

import javax.swing.JOptionPane;

import com.bams.exception.BalanceNotEnoughException;
import com.bams.exception.LoanException;

/**
 * 可贷款信用账户
 * 该账户可以贷款,可以透支。
 * */
public class LoanCreditAccount extends CreditAccount implements Loanable{
	
	//记录贷款金额
	private double loanAmount = 0;
	
	public LoanCreditAccount(Long id,String password, String name, String personld, String email, double balance,double ceiling,Long loanAmount) {
		super(id,password, name, personld,email,balance,ceiling);
		this.loanAmount = loanAmount;
	}
	
	public LoanCreditAccount(Long id,String password, String name, String personld, String email, double balance) {
		super(id,password, name, personld,email,balance);
	}
	
	public LoanCreditAccount() {
		
	}
	
	@Override
	public double getLoanAmount() {
		return loanAmount;
	}

	@Override
	public Account requestLoan(double money) throws LoanException {
		if(money>0) {
			this.setBalance(this.getBalance()+money);
			this.loanAmount += money;
		}else {
			throw new LoanException("贷款额不能为负数！");
		}
		
		return this;
	}

	@Override
	public Account payLoan(double money) throws BalanceNotEnoughException {
		if(this.getBalance()>money) {
			if(this.getLoanAmount()>0) {
				this.setBalance(this.getBalance()-money);
				this.loanAmount -= money;
			}else {
				JOptionPane.showMessageDialog(null, "贷款已还清！");
			}
		}else {
			throw new BalanceNotEnoughException("余额不足！");
		}
		
		
		
		return this;
	}

}

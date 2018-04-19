package com.bams.dao;

import java.util.List;

import com.bams.Account;
import com.bams.exception.LoginException;

/**
 * 用于对com.bams.Account代码简化
 * */

public interface AccountDao {

	public Account addAccount(Account account);
	public Account selectAccount(long id) throws LoginException;
	public Account selectAccount(long id,String password) throws LoginException;
	public Account[] selectAllAccounts();
	public Account[] selectAllCreditAccounts();
	public Account[] selectAllLoanAccounts();
//	public void writeId();
}

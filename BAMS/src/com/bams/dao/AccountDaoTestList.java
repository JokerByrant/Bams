//package com.bams.dao;
//
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Set;
//
//import com.bams.exception.LoginException;
//
//import com.bams.Account;
//import com.bams.CreditAccount;
//import com.bams.Loanable;
//
//public class AccountDaoTestList implements AccountDao {
//
//	List<Account> accounts = new ArrayList<Account>();
//
//	public AccountDaoTestList(List<Account> accounts) {
//		this.accounts = accounts;
//	}
//
//	@Override
//	public void addAccount(Account account) {
//		this.accounts.add(account);
//	}
//	
//	@Override
//	public Account selectAccount(long id) throws LoginException {
//		Account account = null;
//		Iterator<Account> it = accounts.iterator();
//		int flag = 0;
//		while(it.hasNext()){
//			Account acc = (Account) it.next();
//			if (acc.getId() == id){
//				flag = 1;
//				account = acc;
//				break;
//			}
//		}
//		if(flag == 0) {
//			throw new LoginException("’À∫≈≤ª¥Ê‘⁄£°");
//		}
//		return account;
//	}
//
//	@Override
//	public Account selectAccount(long id, String password) throws LoginException {
//		Account account = null;
//		int flag = 0;
//		Iterator<Account> it = accounts.iterator();
//		while(it.hasNext()){
//			Account acc = (Account) it.next();
//			if (acc.getId() == id){
//				flag = 1;
//				if(password.equals(acc.getPassword())) {
//					account = acc;
//					break;
//				}else {
//					throw new LoginException("√‹¬Î¥ÌŒÛ£°");
//				}
//			}
//		}
//		if(flag == 0) {
//			throw new LoginException("’À∫≈≤ª¥Ê‘⁄£°");
//		}
//		
//		return account;
//	}
//
//	@Override
//	public Account[] selectAllAccounts() {
//		Account[] accounts = new Account[this.accounts.size()];
//		
//		return accounts;
//	}
//
//	@Override
//	public Account[] selectAllCreditAccounts() {
//		List<Account> accs = new ArrayList<Account>();
//		Iterator<Account> it = accounts.iterator();
//		while(it.hasNext()){
//			Account acc = (Account) it.next();
//			if(acc instanceof CreditAccount) {
//				accs.add(acc);
//			}
//		}
//		
//		Account[] accounts = new Account[accs.size()];
//		return accounts;
//	}
//
//	@Override
//	public Account[] selectAllLoanAccounts() {
//		List<Account> accs = new ArrayList<Account>();
//		Iterator<Account> it = accounts.iterator();
//		while(it.hasNext()){
//			Account acc = (Account) it.next();
//			if(acc instanceof Loanable) {
//				accs.add(acc);
//			}
//		}
//		
//		Account[] accounts = new Account[accs.size()];
//		return accounts;
//	}
//
//
//	
//}

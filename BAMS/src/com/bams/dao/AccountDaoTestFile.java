package com.bams.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.bams.exception.LoginException;

import com.bams.Account;
import com.bams.CreditAccount;
import com.bams.Loanable;

public class AccountDaoTestFile implements AccountDao {

/**
 * author:Lionel-Messi
 * date:18-04-13
 * 这个类是各个方法的集中点
 * */



	public AccountDaoTestFile() {
		
	}
	
	// 序列化对象
	@Override
	public Account addAccount(Account account) {
//		this.accounts.add(account);
		File path = new File("账户");
		if(!path.exists()) {
			path.mkdir();
		}
		File file_account = new File("账户//"+account.getId()+".txt");

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(file_account);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(account);
			oos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return account;
	}
	
	//反序列化
	public List<Account> read_account() {
		File path = new File("账户");
		File[] files = path.listFiles();
		List<Account> accounts = new ArrayList<Account>();
		for(File file:files) {
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			try {
				fis = new FileInputStream(file);
				ois = new ObjectInputStream(fis);
				Account account = (Account) ois.readObject();
				accounts.add(account);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}finally {
				try {
					fis.close();
					ois.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
		return accounts;
	}
	
	// 从文件中读取Account,通过id
	@Override
	public Account selectAccount(long id) throws LoginException {
		List<Account> accounts = read_account();
		Account account = null;
		Iterator<Account> it = accounts.iterator();
		int flag = 0;
		while(it.hasNext()){
			Account acc = (Account) it.next();
			if (acc.getId() == id){
				flag = 1;
				account = acc;
				break;
			}
		}
		if(flag == 0) {
			throw new LoginException("账号不存在！");
		}
		return account;
	}

	// 从文件中读取Account,通过id和password
	@Override
	public Account selectAccount(long id, String password) throws LoginException {
		List<Account> accounts = read_account();
		Account account = null;
		int flag = 0;
		Iterator<Account> it = accounts.iterator();
		while(it.hasNext()){
			Account acc = (Account) it.next();
			if (acc.getId() == id){
				flag = 1;
				if(password.equals(acc.getPassword())) {
					account = acc;
					break;
				}else {
					throw new LoginException("密码错误！");
				}
			}
		}
		if(flag == 0) {
			throw new LoginException("账号不存在！");
		}
		
		return account;
	}

	// 找出所有的账户
	@Override
	public Account[] selectAllAccounts() {
		List<Account> accounts = read_account();
		Account[] accountss = new Account[accounts.size()];
		
		return accountss;
	}

	// 找出所有的信用账户
	@Override
	public Account[] selectAllCreditAccounts() {
		List<Account> accounts = read_account();
		List<Account> accs = new ArrayList<Account>();
		Iterator<Account> it = accounts.iterator();
		while(it.hasNext()){
			Account acc = (Account) it.next();
			if(acc instanceof CreditAccount) {
				accs.add(acc);
			}
		}
		
		Account[] accountss = new Account[accs.size()];
		return accountss;
	}

	// 找出所有的贷款账户
	@Override
	public Account[] selectAllLoanAccounts() {
		List<Account> accounts = read_account();
		List<Account> accs = new ArrayList<Account>();
		Iterator<Account> it = accounts.iterator();
		while(it.hasNext()){
			Account acc = (Account) it.next();
			if(acc instanceof Loanable) {
				accs.add(acc);
			}
		}
		
		Account[] accountss = new Account[accs.size()];
		return accountss;
	}



	
}

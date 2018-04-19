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
 * ������Ǹ��������ļ��е�
 * */



	public AccountDaoTestFile() {
		
	}
	
	// ���л�����
	@Override
	public Account addAccount(Account account) {
//		this.accounts.add(account);
		File path = new File("�˻�");
		if(!path.exists()) {
			path.mkdir();
		}
		File file_account = new File("�˻�//"+account.getId()+".txt");

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
	
	//�����л�
	public List<Account> read_account() {
		File path = new File("�˻�");
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
	
	// ���ļ��ж�ȡAccount,ͨ��id
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
			throw new LoginException("�˺Ų����ڣ�");
		}
		return account;
	}

	// ���ļ��ж�ȡAccount,ͨ��id��password
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
					throw new LoginException("�������");
				}
			}
		}
		if(flag == 0) {
			throw new LoginException("�˺Ų����ڣ�");
		}
		
		return account;
	}

	// �ҳ����е��˻�
	@Override
	public Account[] selectAllAccounts() {
		List<Account> accounts = read_account();
		Account[] accountss = new Account[accounts.size()];
		
		return accountss;
	}

	// �ҳ����е������˻�
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

	// �ҳ����еĴ����˻�
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

package com.bams;
import java.io.Serializable;
import java.util.Calendar;

import com.bams.exception.BalanceNotEnoughException;

/**
 * Account�࣬���ڳ�ʼ���˻�����
 * */

/**
 * @author Lionel-Messi
 *
 */
public abstract class Account implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4048740215145670211L;
	//����
	private Long id;
	private String password;
	private String name;
	private String personld;
	private String email;
	private double balance;
	
	//���ι�����
	public Account(Long id,String password, String name, String personld, String email, double balance) {
		this.id = id;
		this.password = password;
		this.name = name;
		this.personld = personld;
		this.email = email;
		this.balance = balance;
	}
	
	//�޲ι�����
	public Account() {
		
	}

	//setter and getter ����
	public Long getId() {
		return id;
	}

	// �ṩ�˺�
	public void setId(long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPersonld() {
		return personld;
	}

	public void setPersonld(String personld) {
		this.personld = personld;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	//����
	final public Account deposit(double cash) {
		if(cash>0) {
			this.setBalance(balance+cash);
		}else {
			System.out.println("��ȡ���ֽ�������0��");
		}
		
		return this; 
	}
	
	//ȡ���
	public abstract Account withdraw(double cash) throws BalanceNotEnoughException;
	
	@Override
	public String toString() {
		
		return("{"+this.getId()+","+this.getName()+","+this.getEmail()+","+this.getPersonld()+","+this.getBalance()+"}");
	}
	
//	@Override
//	public int compareTo(Account o) {
//		Account p = (Account)o;
//		
//		return (int)(-p.getBalance() + this.getBalance());
//	}
	
//	@Override
//	public int hashCode() {
//		return this.personld.hashCode();
//	}
//	@Override
//	public boolean equals(Object obj) {
//		
//		return ((Account)obj).getPersonld().equals(this.getPersonld());
//	}
//	
}

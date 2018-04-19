package com.bams.bridge;

import java.io.Serializable;

import com.bams.Account;

public class To implements Serializable{
	public int flag;
	public Account account;
	public long id;
	public String password;
	public String repassword;
	public double ceiling;
	public String name;
	public String personld;
	public String email;
	public int type;
	public int index;
	public long toId;
	public double money;
	public double loan;
	
	public void login(long id,String password) {
		this.id = id;
		this.password = password;
		this.flag = 0;
	}
	
	public void regiter(String password, String repassword, String name, String personld, String email, int type) {
		this.password = password;
		this.repassword = repassword;
		this.name = name;
		this.personld = personld;
		this.email = email;
		this.type = type;
		this.flag = 1;
	}
	
	public void bussiness(Account account,int index,double money) {
		this.account = account;
		this.index = index;
		this.money = money;
		this.flag = 2;
	}	
		
	public void Transfer(long toId) {
		this.toId = toId;
	}
	
}

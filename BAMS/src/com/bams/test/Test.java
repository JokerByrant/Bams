//package com.bams.test;
//import java.util.Scanner;
//
//import javax.swing.JFrame;
//import javax.swing.JOptionPane;
//
//import com.bams.Account;
//import com.bams.Bank;
//import com.bams.exception.BalanceNotEnoughException;
//import com.bams.exception.LoanException;
//import com.bams.exception.LoginException;
//import com.bams.exception.RegisterException;
//
//public class Test {
//	
//	public static void main(String[] args) {
//		Bank bank = Bank.newInstance();
//		
//		Account a1 = null;
//		try {
//			a1 = bank.register("12", "12", "jack", "111", "1@qq.com", 1);
//		} catch (RegisterException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//		}
//		
//		Account a2 = null;
//		try {
//			a2 = bank.register("123", "123", "mike", "222", "2@qq.com", 2);
//		} catch (RegisterException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());;
//		}
//		
//		Account a3 = null;
//		try {
//			a3 = bank.register("123", "123", "rose", "111", "3@qq.com", 3);
//		} catch (RegisterException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//		}
//		
//		Account a4 = null;
//		try {
//			a4 = bank.register("123", "123", "nike", "222", "4@qq.com", 0);
//		} catch (RegisterException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//		}
//	
//		
//		try {
//			bank.login(a1.getId(), "12");
//		} catch (LoginException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//		}
//		System.out.println(a1.getId());
//		try {
//			bank.login(a2.getId(), "123");
//		} catch (LoginException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//		}
//		System.out.println(a2.getId());
//		try {
//			bank.login(a3.getId(), "123");
//		} catch (LoginException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//		}
//		System.out.println(a3.getId());
//		
//		a3.deposit(100);
//		a4.deposit(20000);
//		
//		try {
//			bank.deposit(a1.getId(), 200000);
//		} catch (LoginException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//		}
//		System.out.println(a1.getBalance());
//
//		try {
//			bank.deposit(a2.getId(), 1000);
//		} catch (LoginException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//		}
//		System.out.println(a2.getBalance());
//		
//		try {
//			bank.withdraw(a2.getId(), "123", 500);
//		} catch (BalanceNotEnoughException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//		} catch (LoginException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//		}
//		
//		System.out.println(a2.getBalance());
//		try {
//			bank.updateCeiling(a3.getId(), "123", 2000);
//		} catch (LoginException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//		}
//
//		
//		try {
//			bank.transfer(a1.getId(), "12", a2.getId(), 1000);
//		} catch (BalanceNotEnoughException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//		} catch (LoginException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//		}
//
//
//		bank.sumBalance();
//		System.out.println(bank.sumCeiling());
//		
//		try {
//			bank.requestLoan(a3.getId(), 10000);
//		} catch (LoanException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//		} catch (LoginException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//		}
//		try {
//			bank.payLoan(a3.getId(), 5000);
//		} catch (LoginException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//		}
//		bank.total();
//		
//		System.out.println(a1);
//		System.out.println(a2);
//		System.out.println(a3);
//		System.out.println(a4);
//		
//		try {
//			bank.withdraw(a1.getId(), "12", 10);
//		} catch (BalanceNotEnoughException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//		} catch (LoginException e) {
//			JOptionPane.showMessageDialog(null, e.getMessage());
//		}
//		
//		bank.printSalary();
//		
//		try {
//			bank.login(a1.getId(), a1.getPassword());
//		} catch (LoginException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//}

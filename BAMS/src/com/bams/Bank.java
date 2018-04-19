package com.bams;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.table.TableStringConverter;

import com.bams.dao.AccountDao;
import com.bams.dao.AccountDaoTestFile;
//import com.bams.dao.AccountDaoTestList;
import com.bams.exception.BalanceNotEnoughException;
import com.bams.exception.LoanException;
import com.bams.exception.LoginException;
import com.bams.exception.RegisterException;
import com.bams.factory.DaoFactory;


/**
 * author:Lionel-Messi
 * date:18-04-13
 * */

public class Bank {
	public AccountDao ad;
	File file_id = new File("id.txt");
	
	private static Bank instance = new Bank();
	private Bank() {
		ad = DaoFactory.getDao();
	}
	public static Bank newInstance() {
		return instance;
	}
	
	// �û�����(register)
	public Account register(String password, String repassword, String name, String personID, String email, int type ) throws RegisterException{
		
		if(password.equals(repassword)) {
			Account account = null;
			//�洢�˻�
			if(type == 0) {
				account = new SavingAccount(setId(),password,name,personID,email,0.0);
				ad.addAccount(account);
			}
			
			//�����˻�
			else if(type == 1){
				account = new CreditAccount(setId(),password,name,personID,email,0.0);
				ad.addAccount(account);
			}
			
			//�ɴ���洢�˻�
			else if (type == 2) {
				account = new LoanSavingAccount(setId(),password,name,personID,email,0.0);
				ad.addAccount(account);
			}
			
			//�ɴ��������˻�
			else if(type == 3) {
				account = new LoanCreditAccount(setId(),password,name,personID,email,0.0);
				ad.addAccount(account);
			}
			
			return account;
		}
		else {
			throw new RegisterException("������������벻һ����");
		}
		
	}
	
	// �û���¼(login)
	public Account login(Long id, String password) throws LoginException {
		Account account = null;
		account = ad.selectAccount(id, password);

		
		return account;
	}
	
	//�����û���Ϣ���ļ�
	public void update_file(Account account) throws IOException {
		File path = new File("�˻�//"+account.getId()+".txt");
		path.delete();
		ad.addAccount(account);
		
		
		
	}
	
	// �û����(deposit)
	public Account deposit(Long id, double money) throws LoginException, IOException {
		Account account = null;
		account = ad.selectAccount(id);
		if(account!=null) {
			account.deposit(money);
			update_file(account);
		}
		
		return account;
	}
	
	// �û�ȡ��(withdraw)
	public Account withdraw(Long id, String password, double money) throws BalanceNotEnoughException, LoginException, IOException {
		Account account = null;
		account = ad.selectAccount(id, password);
		if(account!=null) {
			account.withdraw(money);
		}
		update_file(account);
		
		return account;
	}
	
	// ����͸֧���(updateCeiling)
	public Account updateCeiling(Long id, String password, double ceiling) throws LoginException, IOException {
		Account account = null;
		account = ad.selectAccount(id, password);
		if(account != null) {
			if(account instanceof CreditAccount) {
				((CreditAccount)account).setCeiling(ceiling);
			}else {
				System.out.println("���˻�����͸֧��");
			}
		}
		update_file(account);
		
		return account;
	}
	
	// ת�˹���(transfer)
	public void transfer(Long from, String passwordFrom, Long to, double money) throws BalanceNotEnoughException, LoginException, IOException {
		Account account1 = null;
		Account account2 = null;
		account1 = ad.selectAccount(from,passwordFrom);
		account2 = ad.selectAccount(to);
		if((account1!=null)&&(account2!=null)) {
			account1.withdraw(money);
			account2.deposit(money);
			update_file(account1);
			update_file(account2);
		}
	}
	
	// ͳ�������˻��������
	public void sumBalance(){
		double sumbalance = 0;
		Account[] accounts= null;
		accounts = ad.selectAllAccounts();
		
		for(Account acc:accounts) {
			sumbalance += acc.getBalance();
		}

		System.out.println("�����˻����������"+sumbalance);
	}
	
	// ͳ�����������˻�͸֧�������
	public double sumCeiling() {
		double sumceiling = 0;
		Account[] accounts= null;
		accounts = ad.selectAllCreditAccounts();
		for(Account acc:accounts) {
				sumceiling += ((CreditAccount)acc).getCeiling();
			}
		
		return sumceiling;
	}
	
	// ��  ��(requestLoan)
	public Account requestLoan(Long id , double money) throws LoanException, LoginException, IOException {
		Account account = null;
		account = ad.selectAccount(id);
		if(account != null) {
			if(account instanceof Loanable) {
				account = ((Loanable)account).requestLoan(money);
			}else {
				System.out.println("���˻����ɴ��");
			}
			
		}
		
		update_file(account);
		return account;
	}
	
	// ������(payLoan)
	public Account payLoan(Long id , double money) throws LoginException, IOException, BalanceNotEnoughException {
		Account account = null;
		account = ad.selectAccount(id);
		if(account != null) {
			if(account instanceof Loanable) {
				account = ((Loanable)account).payLoan(money);
			}else {
				System.out.println("���˻����軹���");
			}
			
		}
		
		update_file(account);
		return account;
	}
	
	// ͳ�������˻�������ܶ�(totoal)
	public void total() {
		double sumLoanAccount = 0;
		Account[] accounts= null;
		accounts = ad.selectAllLoanAccounts();
		for(Account acc:accounts) {
			sumLoanAccount += ((Loanable)acc).getLoanAmount();
		}
		
		System.out.println("���д���������"+sumLoanAccount);
	}
	
//	//�½�id
//	public long setId() {
//		Calendar calendar = Calendar.getInstance();
//		int y = calendar.get(Calendar.YEAR);
//		int m = calendar.get(Calendar.MONTH)+1;
//		String id = "86215021";
//		id += y;
//		if(m<10) {
//			id += "0";
//		}
//		id += m;
//		
//		if(y != currentY) {
//			idindex = 1;
//		}else if (m != currentM) {
//			idindex = 1;
//		}
//		
//		currentM = m;
//		currentY = y;
//		
//		if(idindex < 10) {
//			id += "000";
//		}else if (idindex <100) {
//			id += "00";
//		}else if (idindex < 1000) {
//			id += "0";
//		}
//		id += idindex;
//		long ID = Long.parseLong(id); 
//		idindex++;
//		
//		return ID;
//	}

//	public long setId() throws IOException {
//		long ID = 0;
//		String id = "";
//		FileReader fr = new FileReader(file_id);	
//		int ch = -1;
//		while((ch = fr.read())!=-1) {
//			id += (char)ch;
//		}
//		fr.close();
//		System.out.println(id+"yyy");
//		ad.writeId();
//		
//		ID = Long.parseLong(id);
//		
//		return ID;
//	}
	
	//����һ��Id
	public String judge_id(String id) {
		Calendar calendar = Calendar.getInstance();
		String y = String.valueOf(calendar.get(Calendar.YEAR));
		String m = String.valueOf(calendar.get(Calendar.MONTH)+1);
		String Y = id.substring(8, 12);
		String M = id.substring(12,14);
		String ID = "";
		int mon = calendar.get(Calendar.MONTH)+1;
		if(mon<10) {
			m = "0"+m;
		}
		if(!y.equals(Y)) {
			ID = id.substring(0, 8)+y+m+"0001";
		}else if (!m.equals(M)) {
			ID = id.substring(0,12)+m+"0001";
		}else {
			ID = id;
		}
		
		return ID;
		
	}
	
	// �ж�id�ļ����Ƿ����
		public void isExists_fileId() {
			if(!(file_id.exists())) {
				FileWriter fw = null;
				String id = "862150212018040001";
				id = judge_id(id);
				try {
					file_id.createNewFile();
					fw = new FileWriter(file_id);
					fw.write(id);
					fw.flush();
					fw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	
	//�����û�ID
	public long setId(){
		isExists_fileId();
		FileInputStream fis = null;
		FileWriter fw = null;
		String id = "";
		Long ID = null;
		try {
			fis = new FileInputStream(file_id);
			int ch = -1;
			while((ch = fis.read())!=-1) {
				id += (char)ch;
			}
			System.out.println(id);
			ID = Long.parseLong(id);
			String Id = String.valueOf(ID+1);
			Id = judge_id(Id);
			file_id.delete();
			file_id.createNewFile();
			fw = new FileWriter(file_id);
			fw.write(Id);
			fw.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				fis.close();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return ID;
	}
	
	// ��ӡ�û��ʲ�����
	public void printSalary() {
		
		// ʹ�������ڲ��������� Account ʵ�� Comparable �ӿڣ����ӵļ��
		Set<Account> accounts = new TreeSet<Account>(new Comparator<Account>() {

			@Override
			public int compare(Account p, Account o) {
				if(p.getPersonld().equals(o.getPersonld())) {
					return 0;
				}if(p.getBalance()<o.getBalance()) {
					return -1;
				}
				return 1;
			}
			
		});
		List<Account> flag_account = new ArrayList<Account>();
		Account[] accs= null;
		accs = ad.selectAllAccounts();
		for(Account account:accs) {
			if(accounts.add(account)) {
				accounts.add(account);
			}else {
				flag_account.add(account);
			}
		}
		
		if(flag_account.size()!=0) {
			for(Account acc:accounts) {
				for(Account account:flag_account) {
					if(acc.getPersonld().equals(account.getPersonld())){
						acc.deposit(account.getBalance());
					}
				}
				
			}
		}
		
		System.out.print("�ʲ�����Ϊ:");
		for(Account acc:accounts) {
			System.out.print(acc.getId()+":"+acc.getBalance()+"  ");
		}

	}
}

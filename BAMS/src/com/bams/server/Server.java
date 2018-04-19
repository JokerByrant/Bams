package com.bams.server;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.crypto.Data;

import com.bams.Account;
import com.bams.Bank;
import com.bams.bridge.Ro;
import com.bams.bridge.To;
import com.bams.exception.BalanceNotEnoughException;
import com.bams.exception.LoanException;
import com.bams.exception.LoginException;
import com.bams.exception.RegisterException;

public class Server extends JFrame{
		
	/**
	 * author:Lionel-Messi
	 * date:18-04-13
	 * 
	 * 
	 * 
	 * */
	
	private static int index = 1;
	private static JPanel contentPane;
	private static ArrayList ls;
	private static Server server;
	
	public Server() {
		ls = new ArrayList();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 359);
		contentPane = new JPanel();
		JLabel jl = new JLabel("Server is running...");
		ls.add(jl);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		jl.setFont(new Font("��Բ", Font.BOLD, 35));
		jl.setVisible(true);
		contentPane.add(jl);
		getContentPane().add(contentPane);
		
	}
	
	public void addLable() {
		contentPane.removeAll();
		JLabel j = new JLabel("�ͻ�"+index+"������...");
		j.setFont(new Font("��Բ", Font.BOLD, 20));
		j.setVisible(true);
		contentPane.add(j);
		ls.add(j);
		for(int i=0;i<ls.size();i++) {
			if(ls.get(i) instanceof Component) {
				contentPane.add((Component) ls.get(i));
			}	
		}
		this.repaint();
		contentPane.validate();
		
	}
	
	
	public static void main(String[] args) {
		try {
			server = new Server();
			server.setVisible(true);
			ServerSocket ss = new ServerSocket(8888);
			while(true) {
				Socket s = ss.accept();
				server.addLable();
				
				System.out.println(index);
				To to = new To();	
				new Thread(new ServerTask(s)).start();
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	static class ServerTask implements Runnable{
		Socket s;
		int index1;
		
		public ServerTask(Socket s) {
			this.s = s;
			index++;
			index1 = index-1;
		}
		
		@Override
		public void run() {
			ObjectInputStream ois = null;
			ObjectOutputStream oos = null;
			
			try {
				ois = new ObjectInputStream(s.getInputStream());
				oos = new ObjectOutputStream(s.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			while(true) {
				//�������з��͹���������
				To data = null;
				try {
					data = (To)ois.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
//					e.printStackTrace();
						JLabel j = new JLabel("");
						j.setText("");
						ls.set(index1, j);
						contentPane.removeAll();
						for(int i =0;i<ls.size();i++) {
							contentPane.add((Component) ls.get(i));
						}
						server.repaint();
						contentPane.validate();
						
					
					break;
				}
				
				// �������ݣ�����account
				Account acc = null;
				try {
					acc = handleRequet(data);
				} catch (LoginException | RegisterException e) {
					System.out.println("�쳣����");
					// �����쳣�����͸��ͻ���
					Ro ro = new Ro();
					ro.errorno = 1;
					ro.e = e;
					try {
						System.out.println("llll");
						oos.writeObject(ro);
						oos.flush();
						System.out.println("kk");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} catch (BalanceNotEnoughException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (LoanException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// �������ݸ��ͻ���
				Ro ro = new Ro();
				ro.errorno = 0;
				ro.account = acc;
				System.out.println(ro);
				try {
					oos.writeObject(ro);
					oos.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		
		}
		
		//�������ݣ�����account
		public Account handleRequet(To value) throws LoginException, RegisterException, IOException, BalanceNotEnoughException, LoanException {
			Account r = null;
			Bank bank = Bank.newInstance();
			
			switch (value.flag) {
			// ��¼
			case 0:
				r = bank.login(value.id, value.password);
				break;
			
			// ע��
			case 1:
				r = bank.register(value.password, value.repassword, value.name, value.personld, value.email, value.type);
				break;
			
			// ���˻����и���
			case 2:
				switch (value.index) {
				
				//���
				case 0:
					bank.deposit(value.account.getId(), value.money);
					r = bank.ad.selectAccount(value.account.getId());
					break;
					
				// ȡ��
				case 1:
					bank.withdraw(value.account.getId(),value.account.getPassword(), value.money);
					r = bank.ad.selectAccount(value.account.getId());
					break;
					
				// ת��
				case 2:
					bank.transfer(value.account.getId(),value.account.getPassword(), value.toId,value.money);
					r = bank.ad.selectAccount(value.account.getId());
					break;	
					
				// ����͸֧���
				case 3:
					bank.updateCeiling(value.account.getId(),value.account.getPassword(), value.money);
					r = bank.ad.selectAccount(value.account.getId());
					break;
					
				// ����
				case 4:
					bank.requestLoan(value.account.getId(), value.money);
					r = bank.ad.selectAccount(value.account.getId());
					break;
					
				// ������
				case 5:
					bank.payLoan(value.account.getId(), value.money);
					r = bank.ad.selectAccount(value.account.getId());
					break;
					
				default:
					break;
				}
				
			default:
				break;
			}
			
			return r;
		}
		
	}
	
	

}

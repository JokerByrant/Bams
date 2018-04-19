package com.bams.Client;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.bams.Account;
import com.bams.Bank;
import com.bams.CreditAccount;
import com.bams.LoanSavingAccount;
import com.bams.Loanable;
//import com.bams.dao.AccountDaoTestList;
import com.bams.exception.BalanceNotEnoughException;
import com.bams.exception.LoanException;
import com.bams.exception.LoginException;
import com.bams.bridge.Ro;
import com.bams.bridge.To;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import java.awt.Event;
import java.awt.Font;
import javax.swing.DefaultComboBoxModel;

public class BussinessPanel extends JPanel {
	private JTextField money;
	private LoginPanel lp;
	private Bank bank;
	public static JLabel id;
	public static JLabel name;
	public static JLabel balance;
	public static JLabel ceiling;
	public static JLabel loan;
	public static JLabel label_3;
	public static JLabel label_4;
	public static JComboBox event;
	
	/**
	 * Create the panel.
	 */
	public BussinessPanel(MainFrame mf,ObjectInputStream ois,ObjectOutputStream oos) {
		bank = Bank.newInstance();
		
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u8D26   \u6237\uFF1A");
		lblNewLabel.setFont(new Font("幼圆", Font.BOLD, 15));
		lblNewLabel.setBounds(94, 23, 87, 18);
		add(lblNewLabel);
		
		id = new JLabel("862150212018030001");
		id.setFont(new Font("幼圆", Font.BOLD, 15));
		id.setBounds(251, 23, 185, 18);
		add(id);
		
		JLabel label_1 = new JLabel("\u59D3   \u540D\uFF1A");
		label_1.setFont(new Font("幼圆", Font.BOLD, 15));
		label_1.setBounds(94, 64, 87, 18);
		add(label_1);
		
		JLabel label_2 = new JLabel("\u4F59   \u989D\uFF1A");
		label_2.setFont(new Font("幼圆", Font.BOLD, 15));
		label_2.setBounds(94, 105, 87, 18);
		add(label_2);
		
		label_3 = new JLabel("\u4FE1\u7528\u989D\u5EA6\uFF1A");
		label_3.setFont(new Font("幼圆", Font.BOLD, 15));
		label_3.setBounds(94, 146, 87, 18);
		add(label_3);
		label_3.setVisible(false);
		
		label_4 = new JLabel("\u8D37 \u6B3E \u989D\uFF1A");
		label_4.setFont(new Font("幼圆", Font.BOLD, 15));
		label_4.setBounds(94, 187, 87, 18);
		add(label_4);
		label_4.setVisible(false);
		 
		name = new JLabel("\u5F20\u4E09");
		name.setFont(new Font("幼圆", Font.BOLD, 15));
		name.setBounds(251, 64, 72, 18);
		add(name);
		
		balance = new JLabel("1000.0");
		balance.setFont(new Font("幼圆", Font.BOLD, 15));
		balance.setBounds(249, 106, 72, 18);
		add(balance);
		
		ceiling = new JLabel("0.0");
		ceiling.setFont(new Font("幼圆", Font.BOLD, 15));
		ceiling.setBounds(251, 146, 72, 18);
		add(ceiling);
		ceiling.setVisible(false);
		
		loan = new JLabel("0.0");
		loan.setFont(new Font("幼圆", Font.BOLD, 15));
		loan.setBounds(251, 187, 72, 18);
		add(loan);
		loan.setVisible(false);
		
		event = new JComboBox();
		event.setModel(new DefaultComboBoxModel(new String[] {"\u5B58\u6B3E", "\u53D6\u6B3E", "\u8F6C\u8D26"}));
		event.setFont(new Font("幼圆", Font.BOLD, 15));
		event.setBounds(94, 234, 112, 24);
		add(event);
		
		money = new JTextField();
		money.setText("1000");
		money.setFont(new Font("幼圆", Font.BOLD, 15));
		money.setBounds(202, 234, 121, 24);
		add(money);
		money.setColumns(10);
		
		JButton button = new JButton("\u63D0\u4EA4");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Account account = lp.account;
				System.out.println(account.getId());
				double cash = Double.parseDouble(money.getText());
				int index = event.getSelectedIndex();
				
				// 排版问题
				if(account instanceof LoanSavingAccount) {
					if(event.getSelectedIndex()==3) {
						index = 4;
					}
					if(event.getSelectedIndex() == 4) {
						index = 5;
					}
				}
				
				// 发送数据给服务器
				To data = new To();
				data.bussiness(account, index, cash);
				if(index == 2) {
					long to = Long.parseLong(JOptionPane.showInputDialog("请输入目标账户："));
					data.Transfer(to);
				}
				mf.Writer(data,oos);
				
				// 从服务器读取数据
				Ro ro = mf.Reader(ois);
				if(ro.errorno==1) {
					JOptionPane.showMessageDialog(null, e);
				}
				else {
					account = ro.account;
					// 判断选择的操作，之前因为操作之后未使用 selectAccount() 方法对文件进行更新，因此文件的数据未发生变化
					switch (index) {
					
					// 存钱
					case 0:
						balance.setText(String.valueOf(account.getBalance()));
						System.out.println("余额："+account.getBalance());
						break;
					
					// 取钱
					case 1:
						balance.setText(String.valueOf(account.getBalance()));
						break;
					
					// 转账
					case 2:
						balance.setText(String.valueOf(account.getBalance()));
						break;
					
					// 设置透支金额
					case 3:
						ceiling.setText(String.valueOf(((CreditAccount)account).getCeiling()));
						break;
						
					// 贷款
					case 4:
						System.out.println(((Loanable)account).getLoanAmount());
						loan.setText(String.valueOf(((Loanable)account).getLoanAmount()));
						balance.setText(String.valueOf(account.getBalance()));
						break;
						
					// 还贷款
					case 5:
						System.out.println(((Loanable)account).getLoanAmount());
						loan.setText(String.valueOf(((Loanable)account).getLoanAmount()));
						balance.setText(String.valueOf(account.getBalance()));
						break;

					default:
						break;
					}
					
				}

				
			}
		});
		button.setFont(new Font("幼圆", Font.BOLD, 15));
		button.setBounds(92, 265, 120, 27);
		add(button);
		
		JButton button_1 = new JButton("\u8FD4\u56DE");
		button_1.setFont(new Font("幼圆", Font.BOLD, 15));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout card = (CardLayout)mf.contentPane.getLayout();
				card.show(mf.contentPane, "lp");
			}
		});
		button_1.setBounds(209, 265, 113, 27);
		add(button_1);

	}

}

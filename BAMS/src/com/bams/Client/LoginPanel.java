package com.bams.Client;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import com.bams.Account;
import com.bams.Bank;
import com.bams.CreditAccount;
import com.bams.LoanCreditAccount;
import com.bams.Loanable;
import com.bams.exception.LoginException;
import com.bams.bridge.Ro;
import com.bams.bridge.To;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;

import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class LoginPanel extends JPanel {
	private JTextField username;
	private Bank bank;
	private JPasswordField password;
	private BussinessPanel bp;
	public static Account account;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	
	/**
	 * Create the panel.
	 */
	public LoginPanel(MainFrame mf,ObjectInputStream ois,ObjectOutputStream oos) {
		this.ois = ois;
		this.oos = oos;
		setLayout(null);
		bank = Bank.newInstance();
		
		username = new JTextField();
		username.setFont(new Font("幼圆", Font.BOLD, 15));
		username.setBounds(202, 65, 136, 24);
		add(username);
		username.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("\u7528\u6237\u8D26\u53F7\uFF1A");
		lblNewLabel.setFont(new Font("幼圆", Font.BOLD, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(73, 68, 85, 18);
		add(lblNewLabel);
		
		JLabel label = new JLabel("\u7528\u6237\u5BC6\u7801\uFF1A");
		label.setFont(new Font("幼圆", Font.BOLD, 15));
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(73, 122, 85, 18);
		add(label);
		
		JButton btnNewButton = new JButton("\u786E\u5B9A");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				To data = new To();
				data.login(Long.parseLong(username.getText()), new String(password.getPassword()));
				
				System.out.println(data.id);
				mf.Writer(data,oos);
				try {
//					account = bank.login(Long.parseLong(username.getText()), password.getText());
					Ro ro = mf.Reader(ois);
					if(ro.errorno==1) {
						JOptionPane.showMessageDialog(null, e);
					}
					else {
						account = ro.account;
						if(account!=null) {
							bp.id.setText(String.valueOf(account.getId()));
							bp.name.setText(account.getName());
							bp.balance.setText(String.valueOf(account.getBalance()));
							bp.ceiling.setVisible(false);
							bp.label_3.setVisible(false);
							bp.loan.setVisible(false);
							bp.label_4.setVisible(false);
							if(bp.event.getModel().getSize()>3) {
								bp.event.setModel(new DefaultComboBoxModel(new String[] {"\u5B58\u6B3E", "\u53D6\u6B3E", "\u8F6C\u8D26"}));
							}
				
							if(account instanceof CreditAccount) {
								bp.ceiling.setText(String.valueOf(((CreditAccount)account).getCeiling()));
								bp.ceiling.setVisible(true);
								bp.label_3.setVisible(true);
								bp.event.addItem("\u4FEE\u6539\u900F\u652F\u989D\u5EA6");
							}if (account instanceof Loanable) {
								bp.loan.setText(String.valueOf(((Loanable)account).getLoanAmount()));
								bp.loan.setVisible(true);
								bp.label_4.setVisible(true);
								bp.event.addItem("\u8D37\u6B3E");
								bp.event.addItem("\u8FD8\u8D37\u6B3E");
							}
							CardLayout card = (CardLayout)mf.contentPane.getLayout();
							card.show(mf.contentPane, "bp");
						}
					}
					
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "输入的用户名不合法！");
				}
			}
		});
		btnNewButton.setFont(new Font("幼圆", Font.BOLD, 15));
		btnNewButton.setBounds(112, 203, 90, 33);
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\u53D6\u6D88");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout card = (CardLayout)mf.contentPane.getLayout();
				card.show(mf.contentPane, "mp");
			}
		});
		btnNewButton_1.setFont(new Font("幼圆", Font.BOLD, 15));
		btnNewButton_1.setBounds(216, 202, 85, 35);
		add(btnNewButton_1);
		
		password = new JPasswordField();
		password.setFont(new Font("Bahnschrift", Font.PLAIN, 15));
		password.setBounds(202, 119, 136, 24);
		add(password);

	}
}
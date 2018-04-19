package com.bams.Client;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;

import com.bams.Account;
import com.bams.Bank;
import com.bams.CreditAccount;
import com.bams.Loanable;
import com.bams.exception.RegisterException;
import com.bams.bridge.Ro;
import com.bams.bridge.To;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.JPasswordField;

public class RegisterPanel extends JPanel {
	private JTextField name;
	private JTextField personID;
	private JTextField email;
	private Bank bank;
	private JPasswordField password;
	private JPasswordField repassword;
	private BussinessPanel bp;

	/**
	 * Create the panel.
	 */
	public RegisterPanel(MainFrame mf,ObjectInputStream ois,ObjectOutputStream oos) {
		bank = Bank.newInstance();
		
		setLayout(null);
		
		JComboBox type = new JComboBox();
		type.setFont(new Font("Ó×Ô²", Font.BOLD, 15));
		type.setModel(new DefaultComboBoxModel(new String[] {"\u666E\u901A\u8D26\u6237", "\u4FE1\u7528\u8D26\u6237", "\u53EF\u8D37\u6B3E\u666E\u901A\u8D26\u6237", "\u53EF\u8D37\u6B3E\u4FE1\u7528\u8D26\u6237"}));
		type.setBounds(225, 16, 148, 24);
		add(type);
		
		JLabel lblNewLabel = new JLabel("\u8D26\u6237\u7C7B\u578B\uFF1A");
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Ó×Ô²", Font.BOLD, 15));
		lblNewLabel.setBounds(63, 19, 103, 18);
		add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u59D3   \u540D\uFF1A");
		lblNewLabel_1.setFont(new Font("Ó×Ô²", Font.BOLD, 15));
		lblNewLabel_1.setBounds(68, 57, 105, 18);
		add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel(" \u5BC6   \u7801\uFF1A");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2.setFont(new Font("Ó×Ô²", Font.BOLD, 15));
		lblNewLabel_2.setBounds(59, 95, 90, 18);
		add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("\u786E\u8BA4\u5BC6\u7801\uFF1A");
		lblNewLabel_3.setFont(new Font("Ó×Ô²", Font.BOLD, 15));
		lblNewLabel_3.setBounds(64, 137, 103, 18);
		add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("\u8EAB\u4EFD\u8BC1\u53F7\uFF1A");
		lblNewLabel_4.setFont(new Font("Ó×Ô²", Font.BOLD, 15));
		lblNewLabel_4.setBounds(63, 180, 103, 18);
		add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("E-mail\uFF1A");
		lblNewLabel_5.setFont(new Font("Ó×Ô²", Font.BOLD, 15));
		lblNewLabel_5.setBounds(76, 219, 72, 18);
		add(lblNewLabel_5);
		
		name = new JTextField();
		name.setFont(new Font("Ó×Ô²", Font.BOLD, 15));
		name.setBounds(225, 53, 148, 24);
		add(name);
		name.setColumns(10);
		
		JButton button = new JButton("\u8FD4\u56DE");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout card = (CardLayout)mf.contentPane.getLayout();
				card.show(mf.contentPane, "mp");
			}
		});
		button.setFont(new Font("Ó×Ô²", Font.BOLD, 15));
		button.setBounds(235, 254, 77, 33);
		add(button);
		
		JButton button_1 = new JButton("\u63D0\u4EA4");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(!(password.getText().equals(""))&&!(repassword.getText().equals(""))&&!(name.getText().equals(""))&&!(personID.getText().equals("")&&!(email.getText().equals("")))) {
					if(email.getText().split("@").length>1&&email.getText().endsWith(".com")) {
//							Account account = bank.register(password.getText(), repassword.getText(), name.getText(), personID.getText(), email.getText(), type.getSelectedIndex());
						
						To data = new To();
						data.regiter(new String(password.getPassword()), new String(repassword.getPassword()), name.getText(), personID.getText(), email.getText(), type.getSelectedIndex());
						mf.Writer(data,oos);
	
						
						Ro value = mf.Reader(ois);
						System.out.println(value.toString());
						if(value.errorno==1) {
							JOptionPane.showMessageDialog(null, e);
						}
						else {
							Account account = value.account;
							JOptionPane.showMessageDialog(null, "´´½¨ÕË»§³É¹¦£¡ÄúµÄÕËºÅÎª:"+account.getId());
							CardLayout card = (CardLayout)mf.contentPane.getLayout();
							card.show(mf.contentPane, "lp");
						}
						
//							
//							bp.id.setText(String.valueOf(account.getId()));
//							bp.name.setText(account.getName());
//							bp.balance.setText(String.valueOf(account.getBalance()));
//							if(account instanceof CreditAccount) {
//								bp.ceiling.setText(String.valueOf(((CreditAccount)account).getCeiling()));
//								bp.ceiling.setVisible(true);
//								bp.label_3.setVisible(true);
//							}if (account instanceof Loanable) {
//								bp.loan.setText(String.valueOf(((Loanable)account).getLoanAmount()));
//								bp.loan.setVisible(true);
//								bp.label_4.setVisible(true);
//							}
						
					}else {
						JOptionPane.showMessageDialog(null, "ÓÊÏä²»ºÏ·¨!");
					}
					
				}else {
					JOptionPane.showMessageDialog(null, "ÄúÓÐÎ´ÌîÐ´µÄÐÅÏ¢£¡£¡");
				}
				
			}
		});
		button_1.setFont(new Font("Ó×Ô²", Font.BOLD, 15));
		button_1.setBounds(129, 254, 72, 33);
		add(button_1);
		
		personID = new JTextField();
		personID.setFont(new Font("Ó×Ô²", Font.BOLD, 15));
		personID.setColumns(10);
		personID.setBounds(225, 177, 148, 24);
		add(personID);
		
		email = new JTextField();
		email.setFont(new Font("Ó×Ô²", Font.BOLD, 15));
		email.setColumns(10);
		email.setBounds(224, 212, 148, 24);
		add(email);
		
		password = new JPasswordField();
		password.setBounds(226, 95, 146, 24);
		add(password);
		
		repassword = new JPasswordField();
		repassword.setBounds(225, 133, 146, 24);
		add(repassword);

	}

}

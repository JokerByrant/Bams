package com.bams.Client;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.bams.bridge.Ro;
import com.bams.bridge.To;

import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;

import javax.swing.JButton;

public class MainFrame extends JFrame{
	
	/**
	 * author:Lionel-Messi
	 * date:18-04-13
	 * 
	 * 
	 * 
	 * */
	
	public JPanel contentPane;
	private MainPanel mp;
	private LoginPanel lp;
	private RegisterPanel rp;
	private BussinessPanel bp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Socket s = new Socket("127.0.0.1",8888);

					ObjectInputStream ois = null;
					ObjectOutputStream oos =null;
					
					// 服务器端先建立ObjectInputStream流，后建立ObjectOutputStream流
					// 则客户端先建立ObjectOutputStream流，后建立ObjectInputStream流
					oos = new ObjectOutputStream(s.getOutputStream());
					ois = new ObjectInputStream(s.getInputStream());

					MainFrame frame = new MainFrame(ois,oos);
					frame.setVisible(true);
					frame.setAlwaysOnTop(true);
				}catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
//		new Thread(new Runnable() {
//		
//		@Override
//		public void run() {
//			MainFrame frame = new MainFrame(s);
//			frame.setVisible(true);
//		}
//	}).start();
	}

	/**
	 * Create the frame.
	 */
	public MainFrame(ObjectInputStream ois,ObjectOutputStream oos) {
		mp = new MainPanel(this);
		bp = new BussinessPanel(this,ois,oos);
		lp = new LoginPanel(this,ois,oos);
		rp = new RegisterPanel(this,ois,oos);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(700, 300, 450, 359);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));

		contentPane.add(mp,"mp");
		contentPane.add(bp,"bp");
		contentPane.add(lp,"lp");
		contentPane.add(rp,"rp");
	}

	// 读数据
	public Ro Reader(ObjectInputStream ois) {
				
		Ro data = new Ro();
		try {
			data = (Ro) ois.readObject();
			System.out.println("服务器-》客户顿"+data);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data;
	}
	
	// 写数据
	public void Writer(To data,ObjectOutputStream oos) {

		try {
			oos.writeObject(data);
			
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		
	}
	


}

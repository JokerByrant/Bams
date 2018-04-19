package com.bams.Client;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.event.ActionEvent;

public class MainPanel extends JPanel {

	
	/**
	 * Create the panel.
	 */
	public MainPanel(MainFrame mf) {
		setLayout(null);
		
		JLabel lblIcbc = new JLabel("ICBC\u94F6\u884C\u670D\u52A1\u7CFB\u7EDF");
		lblIcbc.setFont(new Font("ºÚÌå", Font.BOLD, 32));
		lblIcbc.setForeground(Color.GRAY);
		lblIcbc.setHorizontalAlignment(SwingConstants.CENTER);
		lblIcbc.setBounds(76, 27, 298, 59);
		add(lblIcbc);
		
		JButton btnNewButton = new JButton("\u767B\u5F55");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout card = (CardLayout)mf.contentPane.getLayout();
				card.show(mf.contentPane, "lp");
			}
		});
		btnNewButton.setFont(new Font("Ó×Ô²", Font.BOLD, 15));
		btnNewButton.setBounds(74, 192, 113, 37);
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("\u6CE8\u518C");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout card = (CardLayout)mf.contentPane.getLayout();
				card.show(mf.contentPane, "rp");
			}
		});
		btnNewButton_1.setFont(new Font("Ó×Ô²", Font.BOLD, 15));
		btnNewButton_1.setBounds(261, 192, 113, 37);
		add(btnNewButton_1);

	}

}

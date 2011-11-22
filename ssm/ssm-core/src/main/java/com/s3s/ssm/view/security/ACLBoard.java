package com.s3s.ssm.view.security;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ACLBoard extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ACLBoard frame = new ACLBoard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ACLBoard() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new ACLPanel();
		setContentPane(contentPane);
	}

}

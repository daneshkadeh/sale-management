package com.s3s.ssm.view.security;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginDialog extends JDialog {
	private JTextField tflUsername;
	private JPasswordField pflPassword;
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JButton btnLogin;
	private JButton btnCancel;
	private Boolean isLogin = false;
	private static ApplicationContext context = new ClassPathXmlApplicationContext(
			"config/s3s-auth-security.xml");

	public LoginDialog(Frame parent) {
		super(parent, "Login", true);
		initComponents(parent);
	}

	private void initComponents(final Frame parent) {

		//
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints cs = new GridBagConstraints();

		cs.fill = GridBagConstraints.HORIZONTAL;

		lblUsername = new JLabel("Username: ");
		cs.gridx = 0;
		cs.gridy = 0;
		cs.gridwidth = 1;
		panel.add(lblUsername, cs);

		tflUsername = new JTextField(20);
		cs.gridx = 1;
		cs.gridy = 0;
		cs.gridwidth = 2;
		panel.add(tflUsername, cs);

		lblPassword = new JLabel("Password: ");
		cs.gridx = 0;
		cs.gridy = 1;
		cs.gridwidth = 1;
		panel.add(lblPassword, cs);

		pflPassword = new JPasswordField(20);
		cs.gridx = 1;
		cs.gridy = 1;
		cs.gridwidth = 2;
		panel.add(pflPassword, cs);
		panel.setBorder(new LineBorder(Color.GRAY));

		btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String username = tflUsername.getText();
				String password = pflPassword.getText();
				loginAction(username, password);
				dispose();
				if (isLogin == false) {
					JOptionPane.showMessageDialog(parent,
							"Dang nhap khong thanh cong.");
				}
			}
		});
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isLogin = false;
				dispose();
			}
		});

		JPanel bp = new JPanel();
		bp.add(btnLogin);
		bp.add(btnCancel);

		getContentPane().add(panel, BorderLayout.CENTER);
		getContentPane().add(bp, BorderLayout.PAGE_END);

		pack();
		setResizable(false);
		setLocationRelativeTo(parent);
	}

	protected void loginAction(String username, String password) {
		AuthenticationManager authenticationManager = (AuthenticationManager) BeanFactoryUtils
				.beanOfType(context, AuthenticationManager.class);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				username, password);
		Authentication authenticatedUser;
		try {
			authenticatedUser = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(
					authenticatedUser);
			isLogin = true;
		} catch (BadCredentialsException e) {
			isLogin = false;
		}
	}

	public String getUsername() {
		return tflUsername.getText().trim();
	}

	public String getPassword() {
		return new String(pflPassword.getPassword());
	}

	public Boolean getIsLogin() {
		return isLogin;
	}
}

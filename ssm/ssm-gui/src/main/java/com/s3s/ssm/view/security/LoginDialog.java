/*
 * LoginDialog
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */
package com.s3s.ssm.view.security;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import net.miginfocom.swing.MigLayout;

import org.springframework.context.ApplicationContext;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import com.s3s.ssm.util.ConfigProvider;
import com.s3s.ssm.util.i18n.ControlConfigUtils;

/**
 * 
 * @author Le Thanh Hoang
 * 
 */
public class LoginDialog extends JFrame {
    private JTextField tflUsername;
    private JPasswordField pflPassword;
    private JLabel lblUsername;
    private JLabel lblPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private Boolean isLogin = false;

    private Runnable callbackSuccess;
    private Runnable callbackFailed;
    private String username;
    private String password;
    static ApplicationContext auContext;

    public LoginDialog(JFrame parent, Runnable callbackSuccess, Runnable callbackFailed) {
        super(ControlConfigUtils.getString("default.Login.title"));
        this.callbackSuccess = callbackSuccess;
        this.callbackFailed = callbackFailed;
        initComponents(parent);
    }

    private void initComponents(Frame parent) {
        JPanel panel = new JPanel(new MigLayout("wrap 2"));
        lblUsername = new JLabel(ControlConfigUtils.getString("default.Login.label.username"));
        tflUsername = new JTextField(20);
        lblPassword = new JLabel(ControlConfigUtils.getString("default.Login.label.password"));
        pflPassword = new JPasswordField(20);
        btnLogin = new JButton(ControlConfigUtils.getString("default.Login.button.ok"));
        btnCancel = new JButton(ControlConfigUtils.getString("default.Login.button.cancel"));

        panel.add(lblUsername);
        panel.add(tflUsername);
        panel.add(lblPassword);
        panel.add(pflPassword);
        panel.setBorder(new LineBorder(Color.GRAY));
        // add action
        btnLogin.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent event) {
                btnOKActionPerformed(event);
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                btnCancelActionPerformed(event);
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

    public String getUsername() {
        return tflUsername.getText().trim();
    }

    public String getPassword() {
        return new String(pflPassword.getPassword());
    }

    public Boolean isLogin() {
        return isLogin;
    }

    private void login() {
        // login before set authorize
        AuthenticationManager authenticationManager = ConfigProvider.getInstance().getAuthenticationManager();
        // set token
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
        // login
        Authentication authenticatedUser;
        try {
            authenticatedUser = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
            isLogin = true;
        } catch (BadCredentialsException e) {
            JOptionPane.showMessageDialog(this, ControlConfigUtils.getString("default.login.invalid.message"), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    protected void btnOKActionPerformed(ActionEvent evt) {
        username = tflUsername.getText();
        password = pflPassword.getText();
        login();
        if (isLogin) {
            callbackSuccess.run();
            dispose();
        }
    }

    protected void btnCancelActionPerformed(ActionEvent evt) {
        isLogin = false;
        callbackFailed.run();
        dispose();
        System.exit(0);
    }

}

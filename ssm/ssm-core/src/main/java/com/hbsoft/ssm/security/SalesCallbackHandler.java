package com.hbsoft.ssm.security;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.swing.JFrame;

import com.hbsoft.ssm.view.security.LoginDialog;

public class SalesCallbackHandler implements CallbackHandler {
	private String username;
	private String password;
	private JFrame jFrame;
	
	public SalesCallbackHandler(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public SalesCallbackHandler(JFrame jFrame) {
		this.jFrame = jFrame;
	}
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		 LoginDialog loginDialog = new LoginDialog(jFrame);
		 loginDialog.setVisible(true);
		 
		 if (loginDialog.getIsLogin() == true) {
			 username = loginDialog.getUsername();
			 password = loginDialog.getPassword();
		 }
		 for (int i = 0; i < callbacks.length; i++) { 
		      Callback callback = callbacks[i]; 
		      if (callback instanceof NameCallback) { 
		        NameCallback nameCB = (NameCallback) callback; 
		        nameCB.setName(username); 
		      } else if (callback instanceof PasswordCallback) { 
		        PasswordCallback passwordCB = (PasswordCallback) callback; 
		        passwordCB.setPassword(password.toCharArray()); 
		      } 
		    }
	}

}

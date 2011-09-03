package com.hbsoft.ssm.security;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

public class SalesCallbackHandler implements CallbackHandler {
	private String username;
	private String password;
	
	public SalesCallbackHandler(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
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

package com.hbsoft.ssm.security;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;



public class HBLoginModule implements LoginModule {
	 private Subject subject;
	 private CallbackHandler callbackHandler;
	 private Map<String, ?> sharedState;
	 private Map<String, ?> options;
	 private boolean authenticated = true;
	 private String username = "";
	 private String userId = "ThanhHoang";
	 private String password = "123456";
	 private Set principalsAdded;
	 
	public boolean abort() throws LoginException {
		username = null;
        password = null;
        authenticated = false;
        return true;
	}

	public boolean commit() throws LoginException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
        if (!authenticated) {
            return false;
        }
        // set credential
        HBUsernameCredential cred = new HBUsernameCredential(userId, username);
        subject.getPublicCredentials().add(cred);
        // lookup user groups, add to Subject
        Set principals = new HashSet();
        HBUserGroupPrincipal userGroupPrincipal = new HBUserGroupPrincipal("1", "Admin");
        principals.add(userGroupPrincipal);
        subject.getPrincipals().addAll(principals);
        principalsAdded = new HashSet();
        principalsAdded.addAll(principals);
        return true;
	}

	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {
		 this.subject = subject;
	        this.callbackHandler = callbackHandler;
	        this.sharedState = sharedState;
	        this.options = options;

	}

	public boolean login() throws LoginException {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean logout() throws LoginException {
		// TODO Auto-generated method stub
		return false;
	}

}

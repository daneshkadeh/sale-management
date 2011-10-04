package com.hbsoft.ssm.security;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import com.hbsoft.ssm.entity.PrincipalEntity;
import com.hbsoft.ssm.entity.User;
import com.hbsoft.ssm.service.UserService;
import com.hbsoft.ssm.util.ConfigProvider;

public class SalesLoginModule implements LoginModule {
    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map<String, ?> sharedState;
    private Map<String, ?> options;
    private boolean authenticated = true;
    private String username;
    private String password;
    private Set principalsAdded = new HashSet(0);
    private User userStored;

    static private final String SALES_PRINCIPAL_CLASS = SalesPrincipal.class.getName();

    private UserService userService = ConfigProvider.getInstance().getUserService();

    public boolean abort() throws LoginException {
        username = null;
        password = null;
        authenticated = false;
        return true;
    }

    public boolean commit() throws LoginException {
        if (!authenticated) {
            return false;
        }
        // set credential
        UsernameCredential cred = new UsernameCredential(username);
        subject.getPublicCredentials().add(cred);
        // lookup user groups, add to Subject
        lookupPrincipal(userStored);
        subject.getPrincipals().addAll(principalsAdded);
        return true;
    }

    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
            Map<String, ?> options) {
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;
    }

    public boolean login() throws LoginException {
        // TODO Auto-generated method stub
        NameCallback nameCB = new NameCallback("Username");
        PasswordCallback passwordCB = new PasswordCallback("Password", false);
        Callback[] callbacks = new Callback[] { nameCB, passwordCB };
        try {
            callbackHandler.handle(callbacks);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedCallbackException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Authenticate username/password
        username = nameCB.getName();
        password = String.valueOf(passwordCB.getPassword());
        // retrieve username and password to compare them
        userStored = userService.findByUsername(username);

        if (userStored == null) {
            authenticated = false;
        } else {
            String passwordStored = userStored.getPassword();
            if (passwordStored.equals(password)) {
                authenticated = true;
            }
        }
        return authenticated;
    }

    public boolean logout() throws LoginException {
        if (principalsAdded != null && !principalsAdded.isEmpty()) {
            subject.getPrincipals().removeAll(principalsAdded);
        }
        return true;
    }

    private Set lookupPrincipal(User user) {
        Set<PrincipalEntity> principals = user.getPrincipals();
        for (PrincipalEntity principal : principals) {
            if (principal.getClazz().equals(SALES_PRINCIPAL_CLASS)) {
                Long id = principal.getId();
                String name = principal.getName();
                SalesPrincipal salesPrincipal = new SalesPrincipal(name);
                principalsAdded.add(salesPrincipal);
            }
        }
        return principals;
    }
}

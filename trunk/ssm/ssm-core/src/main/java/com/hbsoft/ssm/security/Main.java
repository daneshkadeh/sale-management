package com.hbsoft.ssm.security;

import java.security.Principal;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import javax.security.auth.Subject;
import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.swing.JFrame;

import org.omg.CORBA.CTX_RESTRICT_SCOPE;


public class Main {
    public static void main(String[] args) throws SQLException {
        LoginConfiguration.init();
        String appName = "simpleDb";
        String username = "hoangle";
        String password = "123456";
        // authenticate user
        boolean authenticated = true;
        try {
            LoginContext ctx;
//            ctx = new LoginContext(appName, new SalesCallbackHandler(username, password));
            ctx = new LoginContext(appName, new SalesCallbackHandler(new JFrame()));
            ctx.login();
            if (authenticated) {
                // print username
                Subject subject = ctx.getSubject();
                Set creds = subject.getPublicCredentials(UsernameCredential.class);
                System.out.println("Subject's username: "
                		+ ((UsernameCredential) creds.iterator().next()).getValue());
                // print principals
                for (Iterator itr = subject.getPrincipals(SalesPrincipal.class).iterator(); itr.hasNext();) {
                    Principal p = (Principal) itr.next();
                    System.out.println("Principal: " + p.getName());
                }
            } else {
                System.out.println("Did not authenticate " + username);
            }
            ctx.logout();
        } catch (LoginException e) {
            e.printStackTrace();
            authenticated = false;
        }
    }
}

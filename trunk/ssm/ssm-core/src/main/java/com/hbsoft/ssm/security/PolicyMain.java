package com.hbsoft.ssm.security;

import java.io.FilePermission;
import java.security.Principal;
import java.security.PrivilegedAction;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.swing.JFrame;

public class PolicyMain {

    /**
     * @param args
     */
    public static void main(String[] args) {
        LoginConfiguration.init();
        String appName = "simpleDb";
        String username = "hoangle";
        String password = "123456";

        SalesPolicy salesPolicy = new SalesPolicy();
        // Policy.setPolicy();
        try {
            LoginContext ctx;
            // ctx = new LoginContext(appName, new SalesCallbackHandler(username, password));
            ctx = new LoginContext(appName, new SalesCallbackHandler(new JFrame()));
            ctx.login();
            // print username
            Subject subject = ctx.getSubject();
            Principal managerPrincipal = null;

            for (Principal principal : subject.getPrincipals()) {
                managerPrincipal = principal;
            }
            SecurityManager sm = System.getSecurityManager();
            if (sm == null) {
                sm = new SecurityManager();
                System.setSecurityManager(sm);
            }
            System.out.println("SecurityManager" + sm);

            final FilePermission filePerm = new FilePermission("/tmp/test", "read");
            PermissionService.addPermission(managerPrincipal, filePerm);
            boolean allowed = true;
            try {
                Subject.doAsPrivileged(subject, new PrivilegedAction() {

                    public Object run() {
                        SecurityManager sm = System.getSecurityManager();
                        if (sm == null) {

                            sm.checkPermission(filePerm);
                        }
                        return null;
                    }

                }, null);
            } catch (SecurityException e) {
                allowed = false;
            }

            if (allowed) {
                System.out.println("Subject can read file /tmp/test");
            } else {
                System.out.println("Subject cannot read file /tmp/test");
            }

            ctx.logout();
        } catch (LoginException e) {
            // TODO Auto-generated catch block

        }

    }
}

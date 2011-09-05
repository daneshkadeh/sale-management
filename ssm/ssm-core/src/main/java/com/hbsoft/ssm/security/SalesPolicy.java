package com.hbsoft.ssm.security;

import java.security.AccessController;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.Principal;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SalesPolicy extends Policy {

    @Override
    public PermissionCollection getPermissions(CodeSource codesource) {
        // others may add to this, so use heterogeneous Permissions
        Permissions perms = new Permissions();
        perms.add(new AllPermission());
        return perms;
    }

    @Override
    public PermissionCollection getPermissions(final ProtectionDomain domain) {
        // TODO Auto-generated method stub
        Permissions permissions = new Permissions();
        // lookup permissions
        Set principalIds = new HashSet();
        final Set salesPrincipalSet = new HashSet();
        Principal[] principals = domain.getPrincipals();
        if (principals != null && principals.length > 0) {
            for (int i = 0; i < principals.length; i++) {
                Principal p = principals[i];
                if (p instanceof SalesPrincipal) {
                    SalesPrincipal salesPrincipal = (SalesPrincipal) p;
                    salesPrincipalSet.add(salesPrincipal);
                    // principalIds.add(userGroup.getId());
                }
            }
        }
        if (salesPrincipalSet.isEmpty() == false) {
            try {
                List perms = (List) AccessController.doPrivileged(new PrivilegedExceptionAction() {
                    public Object run() throws SQLException {
                        return PermissionService.findPermissions(salesPrincipalSet);
                    }
                });
                for (Iterator itr = perms.iterator(); itr.hasNext();) {
                    Permission perm = (Permission) itr.next();
                    permissions.add(perm);
                }
            } catch (PrivilegedActionException e) {
                // log
            }
        } else if (domain.getCodeSource() != null) {
            PermissionCollection codeSrcPerms = getPermissions(domain.getCodeSource());
            for (Enumeration en = codeSrcPerms.elements(); en.hasMoreElements();) {
                Permission p = (Permission) en.nextElement();
                permissions.add(p);
            }
        }
        return permissions;
    }

    @Override
    public boolean implies(final ProtectionDomain domain, final Permission permission) {
        if (permission.getName().equals("/tmp/test.tx")) {
            int i = 0;
        }
        PermissionCollection perms = getPermissions(domain);

        boolean implies = perms.implies(permission);

        return implies;
    }

    @Override
    public void refresh() {
        // TODO Auto-generated method stub
        super.refresh();
    }

}

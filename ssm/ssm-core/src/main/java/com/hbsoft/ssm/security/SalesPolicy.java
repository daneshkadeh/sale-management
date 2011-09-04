package com.hbsoft.ssm.security;

import java.security.AllPermission;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.ProtectionDomain;

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
		
		return super.getPermissions(domain);
	}

	@Override
	public boolean implies(ProtectionDomain domain, Permission permission) {
		// TODO Auto-generated method stub
		return super.implies(domain, permission);
	}

	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		super.refresh();
	}

}

package com.s3s.ssm.entity.security;

import java.util.Arrays;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;


public class ServiceExample {
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
	private MutableAclService mutableAclService;
	PlatformTransactionManager transactionManager;
	
	public void setMutableAclService(MutableAclService mutableAclService) {
		this.mutableAclService = mutableAclService;
	}

	public static void main(String[] args) {
		
		ApplicationContext appContext = new ClassPathXmlApplicationContext("config/acl-context.xml");
		ApplicationContext auContext = new ClassPathXmlApplicationContext("config/s3s-auth-security.xml");
		
		ServiceExample serviceExample = (ServiceExample) BeanFactoryUtils
				.beanOfType(appContext, ServiceExample.class);
		
		
		AuthenticationManager authenticationManager = (AuthenticationManager) BeanFactoryUtils
				.beanOfType(auContext, AuthenticationManager.class);
		// set token
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
				"Hoang Le", "123456");
		
		// login
		Authentication authenticatedUser;
		try {
			authenticatedUser = authenticationManager.authenticate(token);
//			SecurityContextHolder.getContext().setAuthentication(
//					authenticatedUser);
			SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
		} catch (BadCredentialsException e) {
			System.out.println(e.getMessage());
		}
		serviceExample.postReport();
	}
	public void postReport() {
		TransactionTemplate tt = new TransactionTemplate(transactionManager); 
		  tt.execute(new TransactionCallbackWithoutResult() {
		  @Override
		  protected void doInTransactionWithoutResult(TransactionStatus arg0) {
			  ObjectIdentity oid = new ObjectIdentityImpl(User.class, 1);
//				MutableAcl mutableAcl = mutableAclService.createAcl(oid);
			  MutableAcl mutableAcl = (MutableAcl)mutableAclService.readAclById(oid);
			  Sid adminRole = new PrincipalSid("Hoang Le");
//			  Sid adminRole = new GrantedAuthoritySid("ADMIN");
//			  mutableAcl.setOwner(adminRole);
//			  mutableAcl.insertAce(0, BasePermission.CREATE, adminRole, true);
//			  mutableAclService.updateAcl(mutableAcl);
//			  mutableAcl.isGranted(BasePermission.DELETE, adminRole, true);
			  System.out.println(mutableAcl.isGranted(Arrays.asList(BasePermission.CREATE), Arrays.asList(adminRole), true));
		  }
	});	
	}
}

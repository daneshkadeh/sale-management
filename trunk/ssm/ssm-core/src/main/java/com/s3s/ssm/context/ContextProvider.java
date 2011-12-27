package com.s3s.ssm.context;

import java.util.Set;

import com.s3s.ssm.security.ACLResourceEnum;
import com.s3s.ssm.security.CustomPermission;

/**
 * This interface help to get current user and current point of sales.
 * 
 * @author phamcongbang
 * 
 */
public interface ContextProvider {
    public String getCurrentUser();

    public Long getCurrentPOS();
    
    public Float getCurrencyRate();
    
    public String getPaymentMethod();
    
    Set<CustomPermission> getPermissions(ACLResourceEnum aclResource);
}

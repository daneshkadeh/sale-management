package com.s3s.ssm.context;

import java.util.Set;

import com.s3s.ssm.security.ACLResourceEnum;
import com.s3s.ssm.security.CustomPermission;

//@Service("contextProvider")
public class DummyContextProvider implements ContextProvider {

    @Override
    public String getCurrentUser() {
        return "admin";
    }

    @Override
    public Long getCurrentPOS() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Float getCurrencyRate() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPaymentMethod() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<CustomPermission> getPermissions(ACLResourceEnum aclResource) {
        // TODO Auto-generated method stub
        return null;
    }

}

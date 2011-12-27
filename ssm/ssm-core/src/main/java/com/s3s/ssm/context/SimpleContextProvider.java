package com.s3s.ssm.context;

import java.util.Collections;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.s3s.ssm.security.ACLResourceEnum;
import com.s3s.ssm.security.CustomPermission;

/**
 * This class is not implemented.
 * 
 * @author phamcongbang
 * 
 */
@Service("contextProvider")
public class SimpleContextProvider implements ContextProvider {

    @Override
    public String getCurrentUser() {
        return "DEFAULT_USER";
    }

    @Override
    public Long getCurrentPOS() {
        return 1L;
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

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<CustomPermission> getPermissions(ACLResourceEnum aclResource) {
        // TODO Auto-generated method stub
        return Collections.EMPTY_SET;
    }

}

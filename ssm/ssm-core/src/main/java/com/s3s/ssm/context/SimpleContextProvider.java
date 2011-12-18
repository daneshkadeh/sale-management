package com.s3s.ssm.context;

import org.springframework.stereotype.Service;

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

}

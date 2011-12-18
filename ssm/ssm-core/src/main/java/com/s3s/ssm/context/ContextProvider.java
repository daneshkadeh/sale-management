package com.s3s.ssm.context;

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
}

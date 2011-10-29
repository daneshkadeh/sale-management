package com.s3s.ssm.entity.param;

/**
 * CASH: the payment is paid by cash. </br> DEBT_TRANSFER: the payment is paid by moving money to debt account of
 * customer.
 * 
 * @author phamcongbang
 * 
 */
public enum PaymentMeanEnum {
    CASH, BANK_TRANSFER, VISA, CREDIT_CARD, DEBT_TRANSFER
}

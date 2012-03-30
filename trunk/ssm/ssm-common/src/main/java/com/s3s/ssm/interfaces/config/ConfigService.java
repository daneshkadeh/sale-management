package com.s3s.ssm.interfaces.config;

import java.util.Date;
import java.util.List;

import com.s3s.ssm.entity.config.Bank;
import com.s3s.ssm.entity.config.Organization;
import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.finance.PaymentContent;
import com.s3s.ssm.entity.operator.Operator;

public interface ConfigService {
    /**
     * Get exchange rate based on currency code of latest date
     * 
     * @param currency_code
     * @return
     */
    public Double getExchangeRate(String currencyCode);

    /**
     * Get exchange rate based on currency code and date
     * 
     * @param currency_code
     * @return
     */
    public Double getExchangeRate(String currencyCode, Date date);

    /**
     * Get exchange rate based on currency object of latest date
     * 
     * @param currency_code
     * @return
     */
    public Double getExchangeRate(SCurrency currency);

    public String generatePaymentCode(String orgCode);

    public String generateCode(Class clazz);

    public List<String> getCurrencyCodes();

    public List<Bank> getBanks();

    public List<Partner> getPartners();

    public List<Operator> getOperators();

    public List<PaymentContent> getPaymentContents();

    public List<PaymentContent> getReceiptContents();

    /**
     * Get default organization
     * 
     * @return
     */
    public Organization getDefOrganization();

    public SCurrency getDefCurrency();

}

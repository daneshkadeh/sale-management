package com.s3s.ssm.interfaces.config;

import java.util.Date;
import java.util.List;

import com.s3s.ssm.entity.config.Bank;
import com.s3s.ssm.entity.config.Organization;
import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.entity.config.UomCategory;
import com.s3s.ssm.entity.contact.AudienceCategory;
import com.s3s.ssm.entity.security.Role;
import com.s3s.ssm.model.CurrencyEnum;

public interface IConfigService {
    /**
     * Get exchange rate based on currency code of latest date
     * 
     * @param currency_code
     * @return
     */
    public Double getExchangeRate(CurrencyEnum currencyCode);

    /**
     * Get exchange rate based on currency code and date
     * 
     * @param currency_code
     * @return
     */
    public Double getExchangeRate(CurrencyEnum currencyCode, Date date);

    /**
     * Get exchange rate based on currency object of latest date
     * 
     * @param currency_code
     * @return
     */
    public Double getExchangeRate(SCurrency currency);

    public List<String> getCurrencyCodes();

    public List<SCurrency> getCurrencies();

    public List<Bank> getBanks();

    public List<AudienceCategory> getAudienceCategories();

    public Organization getDefOrganization();

    public List<Organization> getOrganizations();

    public SCurrency getDefCurrency();

    public List<UnitOfMeasure> getUnitUom();

    public UnitOfMeasure getBaseUnitUom();

    public List<UomCategory> getUomCategories();

    public UnitOfMeasure getBaseUom(UomCategory cate);

    public String getBaseUomName(UomCategory cate);

    public List<Role> getRoles();
}

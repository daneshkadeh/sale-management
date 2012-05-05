package com.s3s.ssm.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.entity.AbstractCodeOLObject;
import com.s3s.ssm.entity.config.Bank;
import com.s3s.ssm.entity.config.ExchangeRate;
import com.s3s.ssm.entity.config.Organization;
import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.entity.config.UnitOfMeasure;
import com.s3s.ssm.entity.config.UomCategory;
import com.s3s.ssm.entity.contact.AudienceCategory;
import com.s3s.ssm.entity.security.Role;
import com.s3s.ssm.interfaces.config.IConfigService;
import com.s3s.ssm.model.CurrencyEnum;
import com.s3s.ssm.util.CacheId;

@Transactional
@Service("configServiceImpl")
public class ConfigServiceImpl extends AbstractModuleServiceImpl implements IConfigService {
    private static final int CODE_LENGTH = 20;

    @Override
    public void init() {
        serviceProvider.register(IConfigService.class, this);
        try {
            getCacheDataService()
                    .registerCache(CacheId.REF_LIST_EMPTY, this, this.getClass().getMethod("getEmptyList"));
            getCacheDataService().registerCache(CacheId.REF_LIST_ROLE, this, this.getClass().getMethod("getRoles"));
            getCacheDataService().registerCache(CacheId.REF_LIST_CURRENCY, this,
                    this.getClass().getMethod("getCurrencies"));
            getCacheDataService().registerCache(CacheId.REF_LIST_BANK, this, this.getClass().getMethod("getBanks"));
            getCacheDataService().registerCache(CacheId.REF_LIST_ORGANIZATION, this,
                    this.getClass().getMethod("getOrganizations"));
            getCacheDataService().registerCache(CacheId.REF_LIST_UNIT_UOM, this,
                    this.getClass().getMethod("getUnitUom"));
            getCacheDataService().registerCache(CacheId.REF_LIST_UOM_CATE, this,
                    this.getClass().getMethod("getUomCategories"));
            getCacheDataService().registerCache(CacheId.REF_LIST_AUDIENCE_CATE, this,
                    this.getClass().getMethod("getAudienceCategories"));
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Cannot register method to cache service!", e);
        }
    }

    public List getEmptyList() {
        return Collections.EMPTY_LIST;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<String> getCurrencyCodes() {
        List<SCurrency> currencies = getDaoHelper().getDao(SCurrency.class).findAll();
        List<String> currencyCodes = new ArrayList<>();
        for (SCurrency currency : currencies) {
            currencyCodes.add(currency.getCode());
        }
        return currencyCodes;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<SCurrency> getCurrencies() {
        List<SCurrency> currencies = getDaoHelper().getDao(SCurrency.class).findAll();
        return currencies;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Bank> getBanks() {
        List<Bank> banks = getDaoHelper().getDao(Bank.class).findAll();
        return banks;
    }

    @Override
    public List<AudienceCategory> getAudienceCategories() {
        return getDaoHelper().getDao(AudienceCategory.class).findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Double getExchangeRate(CurrencyEnum currencyCode) {
        return getExchangeRate(currencyCode, new Date(0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getExchangeRate(CurrencyEnum currencyCode, Date date) {
        if (date == null) {
            date = new Date();
        }
        DetachedCriteria exRateDC = getDaoHelper().getDao(ExchangeRate.class).getCriteria();
        exRateDC.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        exRateDC.createAlias("currency", "currency");
        exRateDC.add(Restrictions.eq("currency.code", currencyCode));
        exRateDC.add(Restrictions.le("updateDate", date));
        exRateDC.addOrder(Order.desc("updateDate"));
        List<ExchangeRate> exRateList = getDaoHelper().getDao(ExchangeRate.class).findByCriteria(exRateDC, 0, 1);
        if (exRateList.size() > 0) {
            return exRateList.get(0).getRate();
        }
        return 0D;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Double getExchangeRate(SCurrency currency) {
        return getExchangeRate(CurrencyEnum.valueOf(currency.getCode()), new Date(0));
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public Organization getDefOrganization() {
        DetachedCriteria dc = getDaoHelper().getDao(Organization.class).getCriteria();
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.add(Restrictions.eq("isDefault", true));
        List<Organization> orgList = getDaoHelper().getDao(Organization.class).findByCriteria(dc, 0, 1);
        if (orgList.size() > 0) {
            return orgList.get(0);
        }
        return null;
    }

    @Override
    public SCurrency getDefCurrency() {
        Organization defOrg = getDefOrganization();
        return defOrg.getDefCurrency();
    }

    /**
     * Get maximum id of class in database
     * 
     * @param clazz
     * @return
     */
    private Long getMaxId(Class clazz) {
        Long maxId = 0L;
        DetachedCriteria dc = getDaoHelper().getDao(clazz).getCriteria();
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.addOrder(Order.desc("id"));
        List<AbstractCodeOLObject> idList = getDaoHelper().getDao(clazz).findByCriteria(dc, 0, 1);
        if (idList.size() > 0) {
            maxId = idList.get(0).getId();
        }
        return maxId;
    }

    /**
     * Generate code based on code rule and maximum id
     * 
     * @param ruleCode
     * @param id
     * @return
     */
    private String generateCode(String codeRule, Long id) {
        String result;
        Long nextId = id + 1;
        String strNextId = nextId.toString();
        result = StringUtils.rightPad(codeRule, CODE_LENGTH - strNextId.length(), '0');
        result += nextId;
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Organization> getOrganizations() {
        List<Organization> organizations = getDaoHelper().getDao(Organization.class).findAll();
        return organizations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UnitOfMeasure> getUnitUom() {
        DetachedCriteria dc = getDaoHelper().getDao(UnitOfMeasure.class).getCriteria();
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.createAlias("uomCategory", "uomCategory");
        dc.add(Restrictions.eq("uomCategory.code", UomCategory.UNIT_UOM_CATE));
        List<UnitOfMeasure> uomList = getDaoHelper().getDao(UnitOfMeasure.class).findByCriteria(dc);
        return uomList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnitOfMeasure getBaseUnitUom() {
        DetachedCriteria dc = getDaoHelper().getDao(UnitOfMeasure.class).getCriteria();
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.createAlias("uomCategory", "uomCategory");
        dc.add(Restrictions.eq("uomCategory.code", UomCategory.UNIT_UOM_CATE));
        dc.add(Restrictions.eq("isBaseMeasure", true));
        List<UnitOfMeasure> uomList = getDaoHelper().getDao(UnitOfMeasure.class).findByCriteria(dc);
        return uomList.size() > 0 ? uomList.get(0) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UomCategory> getUomCategories() {
        return getDaoHelper().getDao(UomCategory.class).findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UnitOfMeasure getBaseUom(UomCategory cate) {
        DetachedCriteria dc = getDaoHelper().getDao(UnitOfMeasure.class).getCriteria();
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.createAlias("uomCategory", "uomCate");
        dc.add(Restrictions.eq("uomCate.code", cate.getCode()));
        dc.add(Restrictions.eq("isBaseMeasure", true));
        List<UnitOfMeasure> uomList = getDaoHelper().getDao(UnitOfMeasure.class).findByCriteria(dc);
        return uomList.size() > 0 ? uomList.get(0) : null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getBaseUomName(UomCategory cate) {
        UnitOfMeasure uom = getBaseUom(cate);
        if (uom != null) {
            return uom.getName();
        }
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Role> getRoles() {
        List<Role> roles = getDaoHelper().getDao(Role.class).findAllActive();
        return roles;
    }
}

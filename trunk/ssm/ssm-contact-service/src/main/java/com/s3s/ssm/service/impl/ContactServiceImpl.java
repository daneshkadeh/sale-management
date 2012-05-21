package com.s3s.ssm.service.impl;

import java.util.Collections;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.dto.contact.CustEncouragementDTO;
import com.s3s.ssm.dto.contact.LongDebtCustDTO;
import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.contact.PartnerProfileTypeEnum;
import com.s3s.ssm.interfaces.contact.IContactService;
import com.s3s.ssm.util.CacheId;

@Transactional
@Service("contactServiceImpl")
public class ContactServiceImpl extends AbstractModuleServiceImpl implements IContactService {

    @Override
    public void init() {
        serviceProvider.register(IContactService.class, this);
        try {
            getCacheDataService().registerCache(CacheId.REF_LIST_PARTNER, this,
                    this.getClass().getMethod("getPartners"));
            getCacheDataService().registerCache(CacheId.REF_LIST_SUPPLIER, this,
                    this.getClass().getMethod("getSuppliers"));
            getCacheDataService().registerCache(CacheId.REF_LIST_CUSTOMER, this,
                    this.getClass().getMethod("getCustomers"));
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Cannot register method to cache service!", e);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Partner> getPartners() {
        List<Partner> partners = getDaoHelper().getDao(Partner.class).findAllActive();
        return partners;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Partner> getSuppliers() {
        DetachedCriteria dc = getDaoHelper().getDao(Partner.class).getCriteria();
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.add(Restrictions.eq("active", true));
        dc.createCriteria("listProfiles").add(Restrictions.eq("type", PartnerProfileTypeEnum.SUPPLIER));
        return getDaoHelper().getDao(Partner.class).findByCriteria(dc);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Partner> getCustomers() {
        DetachedCriteria dc = getDaoHelper().getDao(Partner.class).getCriteria();
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.add(Restrictions.eq("active", true));
        dc.createCriteria("listProfiles").add(Restrictions.eq("type", PartnerProfileTypeEnum.CUSTOMER));
        return getDaoHelper().getDao(Partner.class).findByCriteria(dc);
    }

    /**
     * {@inheritDoc}
     */
    // TODO: Bang implement bellow function
    @Override
    public List<CustEncouragementDTO> statisticCustEncouragement(Partner customer) {
        return Collections.EMPTY_LIST;
    }

    /**
     * {@inheritDoc}
     */
    // TODO: Bang implement bellow function
    @Override
    public List<LongDebtCustDTO> statisticLongDebtCust(Partner customer) {
        return Collections.EMPTY_LIST;
    }
}

package com.s3s.ssm.service.impl;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.interfaces.contact.IContactService;
import com.s3s.ssm.interfaces.finance.IFinanceService;
import com.s3s.ssm.util.CacheId;

@Transactional
@Service("contactServiceImpl")
public class ContactServiceImpl extends AbstractModuleServiceImpl implements IContactService {

    @Override
    public void init() {
        serviceProvider.register(IFinanceService.class, this);
        try {
            getCacheDataService().registerCache(CacheId.REF_LIST_PARTNER, this,
                    this.getClass().getMethod("getPartners"));
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Cannot register method to cache service!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Partner> getPartners() {
        return getDaoHelper().getDao(Partner.class).findAllActive();
    }

    public List<Partner> getSuppliers() {
        // TODO: Bang implement this
        return Collections.EMPTY_LIST;
    }

    public List<Partner> getCustomers() {
        // TODO: Bang implement this
        return Collections.EMPTY_LIST;
    }

}

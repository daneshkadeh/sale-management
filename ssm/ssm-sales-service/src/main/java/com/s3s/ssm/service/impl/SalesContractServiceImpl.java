package com.s3s.ssm.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.entity.sales.SalesContract;
import com.s3s.ssm.interfaces.sales.SalesContractService;
import com.s3s.ssm.util.CacheId;

@Transactional
@Service("salesContractServiceImpl")
public class SalesContractServiceImpl extends AbstractModuleServiceImpl implements SalesContractService {

    @Override
    public void init() {
        serviceProvider.register(SalesContractService.class, this);
        try {
            getCacheDataService().registerCache(CacheId.REF_LIST_SALES_CONTRACT, this,
                    this.getClass().getMethod("getAllSalesContract"));
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Cannot register method to cache service!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SalesContract> getAllSalesContract() {
        return getDaoHelper().getDao(SalesContract.class).findAll();
    }
}

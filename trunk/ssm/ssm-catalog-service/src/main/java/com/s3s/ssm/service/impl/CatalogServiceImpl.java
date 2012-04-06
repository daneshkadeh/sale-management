package com.s3s.ssm.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.interfaces.catalog.ICatalogService;
import com.s3s.ssm.util.CacheId;

@Transactional
@Service("catalogServiceImpl")
public class CatalogServiceImpl extends AbstractModuleServiceImpl implements ICatalogService {

    @Override
    public void init() {
        serviceProvider.register(ICatalogService.class, this);
        try {
            getCacheDataService().registerCache(CacheId.REF_LIST_PRODUCT, this,
                    this.getClass().getMethod("getListProducts"));
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Cannot register method to cache service!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Product> getListProducts() {
        return getDaoHelper().getDao(Product.class).findAll();
    }
}

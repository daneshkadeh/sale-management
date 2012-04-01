package com.s3s.ssm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.entity.store.ShipPriceType;
import com.s3s.ssm.entity.store.Store;
import com.s3s.ssm.interfaces.config.IConfigService;
import com.s3s.ssm.interfaces.store.IStoreService;
import com.s3s.ssm.service.impl.AbstractModuleServiceImpl;
import com.s3s.ssm.util.CacheId;

@Transactional
@Service("storeServiceImpl")
public class StoreServiceImpl extends AbstractModuleServiceImpl implements IStoreService {

    @Override
    public void init() {
        serviceProvider.register(IConfigService.class, this);
        try {
            getCacheDataService().registerCache(CacheId.REF_LIST_STORE, this, this.getClass().getMethod("getStores"));
            getCacheDataService().registerCache(CacheId.REF_LIST_SHIP_PRICE_TYPE, this,
                    this.getClass().getMethod("getShipPriceTypes"));
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Cannot register method to cache service!", e);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Store> getStores() {
        List<Store> stores = getDaoHelper().getDao(Store.class).findAll();
        return stores;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ShipPriceType> getShipPriceTypes() {
        List<ShipPriceType> shipPriceTypes = getDaoHelper().getDao(ShipPriceType.class).findAll();
        return shipPriceTypes;
    }

}

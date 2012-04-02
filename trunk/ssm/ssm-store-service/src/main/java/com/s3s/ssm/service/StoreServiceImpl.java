package com.s3s.ssm.service;

import java.util.Arrays;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.entity.store.ImportStoreStatus;
import com.s3s.ssm.entity.store.ShipPrice;
import com.s3s.ssm.entity.store.ShipPriceType;
import com.s3s.ssm.entity.store.Store;
import com.s3s.ssm.interfaces.store.IStoreService;
import com.s3s.ssm.service.impl.AbstractModuleServiceImpl;
import com.s3s.ssm.util.CacheId;

@Transactional
@Service("storeServiceImpl")
public class StoreServiceImpl extends AbstractModuleServiceImpl implements IStoreService {

    @Override
    public void init() {
        serviceProvider.register(IStoreService.class, this);
        try {
            getCacheDataService().registerCache(CacheId.REF_LIST_STORE, this, this.getClass().getMethod("getStores"));
            getCacheDataService().registerCache(CacheId.REF_LIST_SHIP_PRICE_TYPE, this,
                    this.getClass().getMethod("getShipPriceTypes"));
            getCacheDataService().registerCache(CacheId.REF_LIST_IMPORT_STORE_STATUS, this,
                    this.getClass().getMethod("getImportStoreStatusList"));
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

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ImportStoreStatus> getImportStoreStatusList() {
        return Arrays.asList(ImportStoreStatus.values());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ShipPrice getLatestShipPrice(ShipPriceType type) {
        DetachedCriteria dc = getDaoHelper().getDao(ShipPrice.class).getCriteria();
        ProjectionList projList = Projections.projectionList();
        projList.add(Projections.max("updateDate"));
        dc.createAlias("currency", "currency");
        dc.add(Restrictions.eq("shipPriceType.code", type.getCode()));
        dc.setProjection(projList);
        ShipPrice shipPrice = getDaoHelper().getDao(ShipPrice.class).findFirstByCriteria(dc);
        return shipPrice;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ShipPrice getLatestShipPrice(String code) {
        DetachedCriteria dc = getDaoHelper().getDao(ShipPrice.class).getCriteria();
        dc.createAlias("shipPriceType", "shipPriceType");
        dc.add(Restrictions.eq("shipPriceType.code", code));
        dc.addOrder(Order.desc("updateDate"));
        ShipPrice shipPrice = getDaoHelper().getDao(ShipPrice.class).findFirstByCriteria(dc);
        return shipPrice;
    }
}

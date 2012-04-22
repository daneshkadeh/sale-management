package com.s3s.ssm.service;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.store.DetailExportStore;
import com.s3s.ssm.entity.store.ExportStoreForm;
import com.s3s.ssm.entity.store.ExportStoreStatus;
import com.s3s.ssm.entity.store.ImportStoreStatus;
import com.s3s.ssm.entity.store.MoveStoreOrder;
import com.s3s.ssm.entity.store.MoveStoreStatus;
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
            getCacheDataService().registerCache(CacheId.REF_LIST_EXPORT_STORE_STATUS, this,
                    this.getClass().getMethod("getExportStoreStatusList"));
            getCacheDataService().registerCache(CacheId.REF_LIST_MOVE_STORE_STATUS, this,
                    this.getClass().getMethod("getMoveStoreStatusList"));
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

    @Override
    public List<ExportStoreStatus> getExportStoreStatusList() {
        return Arrays.asList(ExportStoreStatus.values());
    }

    public List<MoveStoreStatus> getMoveStoreStatusList() {
        return Arrays.asList(MoveStoreStatus.values());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ShipPrice getLatestShipPrice(ShipPriceType type) {
        DetachedCriteria dc = getDaoHelper().getDao(ShipPrice.class).getCriteria();
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
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
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.createAlias("shipPriceType", "shipPriceType");
        dc.add(Restrictions.eq("shipPriceType.code", code));
        dc.addOrder(Order.desc("updateDate"));
        ShipPrice shipPrice = getDaoHelper().getDao(ShipPrice.class).findFirstByCriteria(dc);
        return shipPrice;
    }

    public List<MoveStoreOrder> findMoveStoreOrderByStatus(MoveStoreStatus status) {
        DetachedCriteria dc = getDaoHelper().getDao(MoveStoreOrder.class).getCriteria();
        dc.add(Restrictions.eq("status", status));
        List<MoveStoreOrder> orders = getDaoHelper().getDao(MoveStoreOrder.class).findByCriteria(dc);
        return orders;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ExportStoreForm getLatestExportStoreForm(Invoice invoice) {
        DetachedCriteria latestDateDc = getDaoHelper().getDao(ExportStoreForm.class).getCriteria();
        latestDateDc.setProjection(Property.forName("createdDate").max());

        DetachedCriteria dc = getDaoHelper().getDao(ExportStoreForm.class).getCriteria();
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.createAlias("invoice", "inv");
        // dc.add(Restrictions.eq("invoice.invoiceNumber", invoice.getInvoiceNumber()));
        dc.add(Property.forName("invoice.invoiceNumber").ge(invoice.getInvoiceNumber()));
        dc.add(Property.forName("createdDate").ge(latestDateDc));
        ExportStoreForm exportForm = getDaoHelper().getDao(ExportStoreForm.class).findFirstByCriteria(dc);
        return exportForm;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<DetailExportStore> getAllDetail(ExportStoreForm form) {
        DetachedCriteria dc = getDaoHelper().getDao(DetailExportStore.class).getCriteria();
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.createAlias("exportForm", "exportForm");
        dc.add(Restrictions.eq("exportForm.code", form.getCode()));
        List<DetailExportStore> details = getDaoHelper().getDao(DetailExportStore.class).findByCriteria(dc);
        return details;
    }
}

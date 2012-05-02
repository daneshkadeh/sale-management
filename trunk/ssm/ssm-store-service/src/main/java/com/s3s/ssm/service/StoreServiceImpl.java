package com.s3s.ssm.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.dto.store.GroupDetailExportData;
import com.s3s.ssm.dto.store.ImportStoreReportData;
import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.sales.DetailInvoice;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.store.ClosingStoreEntry;
import com.s3s.ssm.entity.store.DetailClosingStore;
import com.s3s.ssm.entity.store.DetailExportStore;
import com.s3s.ssm.entity.store.DetailImportStore;
import com.s3s.ssm.entity.store.DetailInventoryStore;
import com.s3s.ssm.entity.store.ExportStoreForm;
import com.s3s.ssm.entity.store.ExportStoreStatus;
import com.s3s.ssm.entity.store.ImportStoreStatus;
import com.s3s.ssm.entity.store.InventoryStoreForm;
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
    private static final String EXPORT_STORE_FORM_SEQ = "export_store_form_seq";

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

    @Override
    public String getNextExportStoreFormSeq() {
        long nextNumber = getDaoHelper().getDao(ExportStoreForm.class).getNextSequence(EXPORT_STORE_FORM_SEQ);
        return String.valueOf(nextNumber);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Store> getStores() {
        List<Store> stores = getDaoHelper().getDao(Store.class).findAllActive();
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
        return getLatestShipPrice(type.getCode());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ShipPrice getLatestShipPrice(String code) {
        DetachedCriteria subselectDc = getDaoHelper().getDao(ShipPrice.class).getCriteria();
        subselectDc.setProjection(Property.forName("updateDate").max());

        DetachedCriteria dc = getDaoHelper().getDao(ShipPrice.class).getCriteria();
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.createAlias("shipPriceType", "shipPriceType");
        dc.add(Property.forName("shipPriceType.code").ge(code));
        dc.add(Property.forName("updateDate").eq(subselectDc));
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
        DetachedCriteria subselectDc = getDaoHelper().getDao(ExportStoreForm.class).getCriteria();
        subselectDc.setProjection(Property.forName("createdDate").max());

        DetachedCriteria dc = getDaoHelper().getDao(ExportStoreForm.class).getCriteria();
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.createAlias("invoice", "inv");
        dc.add(Property.forName("invoice.invoiceNumber").ge(invoice.getInvoiceNumber()));
        dc.add(Property.forName("createdDate").eq(subselectDc));
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

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public InventoryStoreForm getLatestInventoryStoreForm(Store store) {
        DetachedCriteria subselectDc = getDaoHelper().getDao(InventoryStoreForm.class).getCriteria();
        subselectDc.setProjection(Property.forName("createdDate").max());

        DetachedCriteria dc = getDaoHelper().getDao(InventoryStoreForm.class).getCriteria();
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.createAlias("store", "store");
        dc.add(Property.forName("store.code").ge(store.getCode()));
        dc.add(Property.forName("createdDate").eq(subselectDc));
        InventoryStoreForm inventoryForm = getDaoHelper().getDao(InventoryStoreForm.class).findFirstByCriteria(dc);
        return inventoryForm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClosingStoreEntry getLatestClosingStoreEntry(Store store) {
        DetachedCriteria subselectDc = getDaoHelper().getDao(ClosingStoreEntry.class).getCriteria();
        subselectDc.setProjection(Property.forName("closingDate").max());

        DetachedCriteria dc = getDaoHelper().getDao(ClosingStoreEntry.class).getCriteria();
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.createAlias("store", "store");
        dc.add(Property.forName("store.code").ge(store.getCode()));
        dc.add(Property.forName("closingDate").eq(subselectDc));
        ClosingStoreEntry closingStoreEntry = getDaoHelper().getDao(ClosingStoreEntry.class).findFirstByCriteria(dc);
        return closingStoreEntry;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Store getStoreByCode(String code) {
        Store store = getDaoHelper().getDao(Store.class).findByCode(code);
        return store;
    }

    @Override
    public Date getDateOfLatestClosingStoreEntry(Store store) {
        if (getLatestClosingStoreEntry(store) == null) {
            return null;
        }
        return getLatestClosingStoreEntry(store).getClosingDate();
    }

    @Override
    public Date getDateOfLatestInventoryStoreForm(Store store) {
        if (getLatestInventoryStoreForm(store) == null) {
            return new Date(0, 0, 0);
        }
        return getLatestInventoryStoreForm(store).getCreatedDate();
    }

    /**
     * {@inheritDoc}
     */
    // TODO: should handle on database using procedure..
    @Override
    public void processClosingStoreEntry() {
        List<Store> stores = getStores();
        // get date of latest closing entry
        for (Store store : stores) {
            ClosingStoreEntry newClosingEntry = new ClosingStoreEntry();
            Set<DetailClosingStore> newDetailSet = new HashSet<DetailClosingStore>();
            newClosingEntry.setStore(store);
            Date latestClosingEntryDate = getDateOfLatestClosingStoreEntry(store);
            Date latestInvenStoreForm = getDateOfLatestInventoryStoreForm(store);
            if (latestClosingEntryDate != null && !DateUtils.isSameDay(latestClosingEntryDate, new Date())) {
                if (latestClosingEntryDate.after(latestInvenStoreForm)) {
                    // get the latest closing entry
                    updateDetailClosingStoreByLatestClosingStoreEntry(newClosingEntry, newDetailSet);
                    // get data from import store
                    updateDetailClosingStoreByImportStore(newDetailSet, latestClosingEntryDate);
                    // get data from export store
                    updateDetailClosingStoreByExportStore(newDetailSet, latestClosingEntryDate);

                } else {

                    updateDetailClosingStoreByLatestInventoryForm(newClosingEntry, newDetailSet);
                    // get data from import store
                    updateDetailClosingStoreByImportStore(newDetailSet, latestClosingEntryDate);
                    // get data from export store
                    updateDetailClosingStoreByExportStore(newDetailSet, latestClosingEntryDate);

                }
                newClosingEntry.setClosingStoreSet(newDetailSet);
                getDaoHelper().getDao(ClosingStoreEntry.class).save(newClosingEntry);
            }
        }
    }

    @Override
    public void createExportStoreForm(Invoice invoice) {
        ExportStoreForm exportForm = new ExportStoreForm();
        exportForm.setCode(getNextExportStoreFormSeq());
        exportForm.setStaff(invoice.getStaff());
        exportForm.setStore(invoice.getExportStore());
        exportForm.setInvoice(invoice);
        int lineNo = 0;
        for (DetailInvoice invoiceDetail : invoice.getDetailInvoices()) {
            DetailExportStore exportDetail = new DetailExportStore();
            exportDetail.setLineNo(lineNo);
            exportDetail.setExportForm(exportForm);
            exportDetail.setProduct(invoiceDetail.getProduct());
            exportDetail.setItem(invoiceDetail.getItem());
            exportDetail.setUom(invoiceDetail.getUom());
            exportDetail.setBaseUom(invoiceDetail.getBaseUom());
            exportForm.getExportDetails().add(exportDetail);
            lineNo++;
        }
        getDaoHelper().getDao(ExportStoreForm.class).save(exportForm);
    }

    public List<ImportStoreReportData> statisticImportStoreData(String salesContractCode, String storeCode,
            String productCode, Date fromDate, Date toDate) {
        DetachedCriteria dc = getDaoHelper().getDao(DetailImportStore.class).getCriteria();
        ProjectionList projectionList = Projections.projectionList();
        dc.createAlias("importStoreForm", "form");
        dc.createAlias("importStoreForm.salesContract", "salesContract");
        dc.createAlias("importStoreForm.store", "store");
        dc.createAlias("importStoreForm.salesContract.supplier", "supplier");
        dc.createAlias("product", "p");
        dc.createAlias("item", "i");
        dc.createAlias("uom", "u");
        projectionList.add(Projections.property("form.createdDate"), "importingDate");
        projectionList.add(Projections.property("salesContract.code"), "salesContractCode");
        projectionList.add(Projections.property("store.name"), "storeName");
        projectionList.add(Projections.property("supplier.name"), "supplierName");
        projectionList.add(Projections.property("p.name").as("productName"));
        projectionList.add(Projections.property("i.sumUomName").as("itemName"));
        projectionList.add(Projections.property("u.name").as("uomName"));
        projectionList.add(Projections.property("quantity").as("quantity"));

        if (!salesContractCode.equals("")) {
            dc.add(Restrictions.eq("salesContract.code", salesContractCode));
        }
        if (storeCode != null) {
            dc.add(Restrictions.eq("store.code", storeCode));
        }
        if (fromDate != null && toDate != null) {
            dc.add(Restrictions.between("form.createdDate", fromDate, toDate));
        }
        if (productCode != null) {
            dc.add(Restrictions.eq("p.code", productCode));
        }
        dc.setProjection(projectionList);
        dc.setResultTransformer(new AliasToBeanResultTransformer(ImportStoreReportData.class));
        List result = (List) getDaoHelper().getDao(DetailImportStore.class).findByCriteria(dc);
        return result;
    }

    private List<ImportStoreReportData> getGroupedDetailImportList(final Date date) {
        if (date == null) {
            return Collections.EMPTY_LIST;
        }

        DetachedCriteria dc = getDaoHelper().getDao(DetailImportStore.class).getCriteria();
        dc.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("product"))
                .add(Projections.property("item")).add(Projections.property("quantity"))));
        dc.add(Restrictions.ge("importStoreForm.createdDate", date));
        dc.setResultTransformer(new AliasToBeanResultTransformer(ImportStoreReportData.class));
        return (List) getDaoHelper().getDao(DetailImportStore.class).findByCriteria(dc);
    }

    private List<GroupDetailExportData> getGroupedDetailExportList(final Date date) {
        if (date == null) {
            return Collections.EMPTY_LIST;
        }

        DetachedCriteria dc = getDaoHelper().getDao(DetailExportStore.class).getCriteria();
        dc.setProjection(Projections.distinct(Projections.projectionList().add(Projections.property("product"))
                .add(Projections.property("item")).add(Projections.sum("realQuan"))));
        dc.add(Restrictions.ge("exportForm.createdDate", date));
        dc.setResultTransformer(new AliasToBeanResultTransformer(GroupDetailExportData.class));
        return (List) getDaoHelper().getDao(DetailExportStore.class).findByCriteria(dc);
    }

    private void updateDetailClosingStoreByLatestInventoryForm(ClosingStoreEntry newClosingEntry,
            Set<DetailClosingStore> newDetailSet) {
        InventoryStoreForm inventoryForm = getLatestInventoryStoreForm(newClosingEntry.getStore());
        if (inventoryForm != null) {
            // get data from the latest closing entry
            for (DetailInventoryStore latestDetail : inventoryForm.getDetailInventoryStores()) {
                DetailClosingStore initDetail = new DetailClosingStore();
                initDetail.setClosingEntry(newClosingEntry);
                initDetail.setProduct(latestDetail.getProduct());
                initDetail.setItem(latestDetail.getItem());
                initDetail.setBaseUom(latestDetail.getBaseUom());
                initDetail.setQty(latestDetail.getCurQty());
                newDetailSet.add(initDetail);
            }
        }
    }

    private void updateDetailClosingStoreByLatestClosingStoreEntry(ClosingStoreEntry newClosingEntry,
            Set<DetailClosingStore> newDetailSet) {
        ClosingStoreEntry latestEntry = getLatestClosingStoreEntry(newClosingEntry.getStore());
        if (latestEntry != null) {
            // get data from the latest closing entry
            for (DetailClosingStore latestDetail : latestEntry.getClosingStoreSet()) {
                DetailClosingStore initDetail = new DetailClosingStore();
                initDetail.setClosingEntry(newClosingEntry);
                initDetail.setProduct(latestDetail.getProduct());
                initDetail.setItem(latestDetail.getItem());
                initDetail.setBaseUom(latestDetail.getBaseUom());
                initDetail.setQty(latestDetail.getQty());
                newDetailSet.add(initDetail);
            }
        }
    }

    private void
            updateDetailClosingStoreByImportStore(Set<DetailClosingStore> newDetailSet, Date latestClosingEntryDate) {
        // get data from import store
        List<ImportStoreReportData> groupDetailList = getGroupedDetailImportList(latestClosingEntryDate);

        for (DetailClosingStore initDetail : newDetailSet) {
            // for (ImportStoreReportData groupDetail : groupDetailList) {
            // Product product = groupDetail.getProduct();
            // Item item = groupDetail.getItem();
            // Integer qty = groupDetail.getQuantity();
            // if (initDetail.getProduct().equals(product) && initDetail.getItem().equals(item)) {
            // int oldValue = initDetail.getQty();
            // int newValue = oldValue + qty;
            // initDetail.setQty(newValue);
            // }
            // }
        }
    }

    private void
            updateDetailClosingStoreByExportStore(Set<DetailClosingStore> newDetailSet, Date latestClosingEntryDate) {
        // get data from export store
        List<GroupDetailExportData> groupDetailList = getGroupedDetailExportList(latestClosingEntryDate);
        for (DetailClosingStore initDetail : newDetailSet) {
            for (GroupDetailExportData groupDetail : groupDetailList) {
                Product product = groupDetail.getProduct();
                Item item = groupDetail.getItem();
                Integer qty = groupDetail.getQuantity();
                if (initDetail.getProduct().equals(product) && initDetail.getItem().equals(item)) {
                    int oldValue = initDetail.getQty();
                    int newValue = oldValue - qty;
                    initDetail.setQty(newValue);
                }
            }
        }
    }
}

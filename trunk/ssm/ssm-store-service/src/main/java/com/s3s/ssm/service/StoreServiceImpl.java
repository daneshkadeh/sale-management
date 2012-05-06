package com.s3s.ssm.service;

import java.util.ArrayList;
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

import com.s3s.ssm.context.OrgSalesContextProvider;
import com.s3s.ssm.dto.store.GroupDetailExportData;
import com.s3s.ssm.dto.store.GroupDetailImportData;
import com.s3s.ssm.dto.store.UnsoldProductDTO;
import com.s3s.ssm.entity.catalog.Item;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.config.UnitOfMeasure;
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
            getCacheDataService().registerCache(CacheId.REF_LIST_MOVE_STORE, this,
                    this.getClass().getMethod("getMoveStoreOrders"));
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

    @Override
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

    @Override
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
        return getLatestExportStoreForm(invoice.getInvoiceNumber());
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ExportStoreForm getLatestExportStoreForm(String invoiceNumber) {
        DetachedCriteria subselectDc = getDaoHelper().getDao(ExportStoreForm.class).getCriteria();
        subselectDc.setProjection(Property.forName("createdDate").max());

        DetachedCriteria dc = getDaoHelper().getDao(ExportStoreForm.class).getCriteria();
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.createAlias("invoice", "inv");
        dc.add(Property.forName("inv.invoiceNumber").eq(invoiceNumber));
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
        dc.add(Property.forName("store.code").eq(store.getCode()));
        dc.add(Property.forName("createdDate").eq(subselectDc));
        InventoryStoreForm inventoryForm = getDaoHelper().getDao(InventoryStoreForm.class).findFirstByCriteria(dc);
        return inventoryForm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClosingStoreEntry getLatestClosingStoreEntry(Store store) {
        return getLatestClosingStoreEntry(store, new Date());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ClosingStoreEntry getLatestClosingStoreEntry(Store store, Date date) {
        DetachedCriteria subselectDc = getDaoHelper().getDao(ClosingStoreEntry.class).getCriteria();
        subselectDc.setProjection(Property.forName("closingDate").max());
        subselectDc.add(Restrictions.le("closingDate", date));

        DetachedCriteria dc = getDaoHelper().getDao(ClosingStoreEntry.class).getCriteria();
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.createAlias("store", "store");
        dc.add(Restrictions.ge("store.code", store.getCode()));
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
        return getDateOfLatestClosingStoreEntry(store, new Date());
    }

    @Override
    public Date getDateOfLatestClosingStoreEntry(Store store, Date date) {
        ClosingStoreEntry closingEntry = getLatestClosingStoreEntry(store, date);
        if (closingEntry != null) {
            // TODO: use another way to get unique value not entity
            return getLatestClosingStoreEntry(store, date).getClosingDate();
        }
        return null;
    }

    @Override
    public Date getDateOfLatestInventoryStoreForm(Store store) {
        InventoryStoreForm form = getLatestInventoryStoreForm(store);
        if (form != null) {
            // TODO: use another way to get unique value not entity
            return form.getCreatedDate();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    // TODO: should handle on database using procedure..
    @Override
    public void processClosingStoreEntry() {
        List<Store> stores = getStores();
        List<ClosingStoreEntry> newClosingEntryList = new ArrayList<ClosingStoreEntry>();
        // get date of latest closing entry
        for (Store store : stores) {
            ClosingStoreEntry newClosingEntry = new ClosingStoreEntry();
            Set<DetailClosingStore> newDetailSet = new HashSet<DetailClosingStore>();
            newClosingEntry.setStore(store);
            Date latestClosingEntryDate = getDateOfLatestClosingStoreEntry(store);
            Date latestInventoryDate = getDateOfLatestInventoryStoreForm(store);
            if (latestClosingEntryDate != null && !DateUtils.isSameDay(latestClosingEntryDate, new Date())) {
                if (latestClosingEntryDate.after(latestInventoryDate)) {
                    // get the latest closing entry
                    updateDetailClosingStoreByLatestClosingStoreEntry(newClosingEntry, newDetailSet);
                    // get data from import store
                    updateDetailClosingStoreByImportStore(newDetailSet, latestClosingEntryDate);
                    // get data from export store
                    updateDetailClosingStoreByExportStore(newDetailSet, latestClosingEntryDate);

                } else {
                    // get the latest inventory
                    updateDetailClosingStoreByLatestInventoryForm(newClosingEntry, newDetailSet);
                    // get data from import store
                    updateDetailClosingStoreByImportStore(newDetailSet, latestInventoryDate);
                    // get data from export store
                    updateDetailClosingStoreByExportStore(newDetailSet, latestInventoryDate);

                }
                newClosingEntry.setClosingStoreSet(newDetailSet);
                newClosingEntryList.add(newClosingEntry);
            }
        }
        getDaoHelper().getDao(ClosingStoreEntry.class).saveOrUpdateAll(newClosingEntryList);
    }

    @Override
    public void createExportStoreForm(Invoice invoice) {
        ExportStoreForm exportForm = new ExportStoreForm();
        exportForm.setCode(getNextExportStoreFormSeq());
        exportForm.setStaff(invoice.getStaff());
        OrgSalesContextProvider orgSalesContextProvider = (OrgSalesContextProvider) getContextProvider();
        exportForm.setStore(orgSalesContextProvider.getDefaultStore());
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
        getDaoHelper().getDao(ExportStoreForm.class).saveOrUpdate(exportForm);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UnsoldProductDTO>
            statisticUnsoldProduct(List<Product> products, Store store, Date fromDate, Date toDate) {
        List<UnsoldProductDTO> result = new ArrayList<UnsoldProductDTO>();
        // Lay hang ton o lan kiem ke hoac lan ket xuat truoc
        for (Product product : products) {
            for (Item item : product.getListItems()) {
                UnitOfMeasure uom = item.getUom();
                Integer firstQty = getProductByClosingStoreEntry(product, item, uom, store, fromDate);
                // TODO: should use HQL or native query for performance by joining together entities
                Integer importQty = getProductByImportStore(product, item, uom, store, fromDate, toDate);
                Integer exportQty = getProductByExportStore(product, item, uom, store, fromDate, toDate);
                Integer lastQty = firstQty + importQty - exportQty;
                Long priceUnit = item.getOriginPrice().getValue();
                Long priceUnitTotal = lastQty * priceUnit;
                UnsoldProductDTO dto = new UnsoldProductDTO();
                dto.setProductCode(product.getCode());
                dto.setProductName(product.getName());
                dto.setItemName(item.getSumUomName());
                dto.setUom(uom.getName());
                dto.setFirstQty(firstQty);
                dto.setImportQty(importQty);
                dto.setExportQty(exportQty);
                dto.setLastQty(lastQty);
                dto.setPriceUnit(priceUnit);
                dto.setPriceUnitTotal(priceUnitTotal);
                result.add(dto);
            }
        }
        return result;
    }

    public Integer getProductByClosingStoreEntry(Product product, Item item, UnitOfMeasure uom, Store store, Date date) {
        Integer result = 0;

        DetachedCriteria subselectDc = getDaoHelper().getDao(DetailClosingStore.class).getCriteria();
        subselectDc.createAlias("closingEntry", "closingEntry");
        subselectDc.setProjection(Property.forName("closingEntry.closingDate").max());
        subselectDc.add(Restrictions.le("closingEntry.closingDate", date));

        DetachedCriteria dc = getDaoHelper().getDao(DetailClosingStore.class).getCriteria();

        dc.createAlias("closingEntry", "closingEntry");
        dc.add(Restrictions.eq("product", product));
        dc.add(Restrictions.eq("item", item));
        dc.add(Restrictions.eq("baseUom", uom));
        dc.add(Restrictions.eq("closingEntry.store", store));
        dc.add(Property.forName("closingEntry.closingDate").eq(subselectDc));

        DetailClosingStore detailClosingEntry = getDaoHelper().getDao(DetailClosingStore.class).findFirstByCriteria(dc);

        Date latestClosingEntryDate = detailClosingEntry.getClosingEntry().getClosingDate();

        Integer closingEntryQty = detailClosingEntry.getQty();
        Integer importQty = getProductByImportStore(product, item, uom, store, latestClosingEntryDate, date);
        Integer exportQty = getProductByExportStore(product, item, uom, store, latestClosingEntryDate, date);

        result = closingEntryQty + importQty - exportQty;
        return result;
    }

    @Override
    public List<GroupDetailImportData> statisticImportStoreData(String salesContractCode, String storeCode,
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
        dc.setResultTransformer(new AliasToBeanResultTransformer(GroupDetailImportData.class));
        List result = (List) getDaoHelper().getDao(DetailImportStore.class).findByCriteria(dc);
        return result;
    }

    private List<DetailImportStore> getGroupedDetailImportList(Date date) {
        if (date == null) {
            return Collections.EMPTY_LIST;
        }

        DetachedCriteria dc = getDaoHelper().getDao(DetailImportStore.class).getCriteria();

        dc.createAlias("importStoreForm", "form");
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.property("product"));
        projectionList.add(Projections.property("item"));
        projectionList.add(Projections.sum("quantity"));

        dc.setProjection(projectionList);
        dc.add(Restrictions.gt("form.createdDate", date));

        return (List) getDaoHelper().getDao(DetailImportStore.class).findByCriteria(dc);
    }

    private List<DetailExportStore> getGroupedDetailExportList(Date date) {
        if (date == null) {
            return Collections.EMPTY_LIST;
        }

        DetachedCriteria dc = getDaoHelper().getDao(DetailExportStore.class).getCriteria();

        dc.createAlias("exportForm", "form");
        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.property("product"));
        projectionList.add(Projections.property("item"));
        projectionList.add(Projections.sum("realQuan"));

        dc.setProjection(projectionList);
        dc.add(Restrictions.gt("form.createdDate", date));

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
        List<DetailImportStore> groupDetailList = getGroupedDetailImportList(latestClosingEntryDate);

        for (DetailClosingStore initDetail : newDetailSet) {
            for (DetailImportStore groupDetail : groupDetailList) {
                Product product = groupDetail.getProduct();
                Item item = groupDetail.getItem();
                Integer qty = groupDetail.getQuantity();
                if (initDetail.getProduct().equals(product) && initDetail.getItem().equals(item)) {
                    int oldValue = initDetail.getQty();
                    int newValue = oldValue + qty;
                    initDetail.setQty(newValue);
                }
            }
        }
    }

    private void
            updateDetailClosingStoreByExportStore(Set<DetailClosingStore> newDetailSet, Date latestClosingEntryDate) {
        // get data from export store
        List<DetailExportStore> groupDetailList = getGroupedDetailExportList(latestClosingEntryDate);
        for (DetailClosingStore initDetail : newDetailSet) {
            for (DetailExportStore groupDetail : groupDetailList) {
                Product product = groupDetail.getProduct();
                Item item = groupDetail.getItem();
                Integer qty = groupDetail.getRealQuan();
                if (initDetail.getProduct().equals(product) && initDetail.getItem().equals(item)) {
                    int oldValue = initDetail.getQty();
                    int newValue = oldValue - qty;
                    initDetail.setQty(newValue);
                }
            }
        }
    }

    private Integer getProductByImportStore(Product product, Item item, UnitOfMeasure uom, Store store, Date fromDate,
            Date toDate) {
        Integer result = 0;
        DetachedCriteria dc = getDaoHelper().getDao(DetailImportStore.class).getCriteria();

        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.property("product"), "product");
        projectionList.add(Projections.property("item"), "item");
        projectionList.add(Projections.property("uom"), "uom");
        projectionList.add(Projections.groupProperty("product"));
        projectionList.add(Projections.groupProperty("uom"));
        projectionList.add(Projections.groupProperty("item"));
        projectionList.add(Projections.sum("quantity"), "quantity");

        dc.setProjection(projectionList);

        if (product != null) {
            dc.createAlias("product", "p");
            dc.add(Restrictions.eq("p.code", product.getCode()));
        }
        if (item != null) {
            dc.createAlias("item", "i");
            dc.add(Restrictions.eq("i.code", item.getCode()));
        }
        if (fromDate != null && toDate != null) {
            dc.createAlias("importStoreForm", "form");
            dc.add(Restrictions.between("form.createdDate", fromDate, toDate));
        }
        if (store != null) {
            dc.createAlias("importStoreForm.store", "store");
            dc.add(Restrictions.eq("store.code", store.getCode()));
        }
        if (uom != null) {
            dc.createAlias("uom", "u");
            dc.add(Restrictions.eq("u.code", uom.getCode()));
        }

        dc.setResultTransformer(new AliasToBeanResultTransformer(GroupDetailExportData.class));
        List resultList = (List) getDaoHelper().getDao(DetailImportStore.class).findByCriteria(dc);
        if (resultList.size() > 0) {
            result = ((GroupDetailExportData) resultList.get(0)).getQuantity();
        }
        return result;
    }

    private Integer getProductByExportStore(Product product, Item item, UnitOfMeasure uom, Store store, Date fromDate,
            Date toDate) {
        Integer result = 0;
        DetachedCriteria dc = getDaoHelper().getDao(DetailExportStore.class).getCriteria();

        ProjectionList projectionList = Projections.projectionList();
        projectionList.add(Projections.property("product"), "product");
        projectionList.add(Projections.property("item"), "item");
        projectionList.add(Projections.property("uom"), "uom");
        projectionList.add(Projections.groupProperty("product"));
        projectionList.add(Projections.groupProperty("item"));
        projectionList.add(Projections.groupProperty("uom"));
        projectionList.add(Projections.sum("realQuan"), "quantity");

        dc.setProjection(projectionList);

        if (product != null) {
            dc.createAlias("product", "p");
            dc.add(Restrictions.eq("p.code", product.getCode()));
        }
        if (item != null) {
            dc.createAlias("item", "i");
            dc.add(Restrictions.eq("i.code", item.getCode()));
        }

        if (fromDate != null && toDate != null) {
            // dc.add(Restrictions.between("form.createdDate", fromDate, toDate));
            dc.createAlias("exportForm", "form");
            dc.add(Restrictions.ge("form.createdDate", fromDate));
            dc.add(Restrictions.le("form.createdDate", toDate));
        }
        if (store != null) {
            dc.createAlias("exportForm.store", "store");
            dc.add(Restrictions.eq("store.code", store.getCode()));
        }
        if (uom != null) {
            dc.createAlias("uom", "u");
            dc.add(Restrictions.eq("u.code", uom.getCode()));
        }
        dc.setResultTransformer(new AliasToBeanResultTransformer(GroupDetailExportData.class));
        List resultList = (List) getDaoHelper().getDao(DetailExportStore.class).findByCriteria(dc);
        if (resultList.size() > 0) {
            result = ((GroupDetailExportData) resultList.get(0)).getQuantity();
        }

        return result;
    }

    @Override
    public List<MoveStoreOrder> getMoveStoreOrders() {
        DetachedCriteria dc = getDaoHelper().getDao(MoveStoreOrder.class).getCriteria();
        dc.add(Restrictions.in("status", Arrays.asList(MoveStoreStatus.NEW, MoveStoreStatus.MOVING)));
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<MoveStoreOrder> resultList = (List<MoveStoreOrder>) getDaoHelper().getDao(MoveStoreOrder.class)
                .findByCriteria(dc);
        return resultList;
    }
}

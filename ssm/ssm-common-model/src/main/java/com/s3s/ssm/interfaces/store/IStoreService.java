package com.s3s.ssm.interfaces.store;

import java.util.Date;
import java.util.List;

import com.s3s.ssm.dto.store.GroupDetailImportData;
import com.s3s.ssm.dto.store.UnsoldProductDTO;
import com.s3s.ssm.entity.catalog.Product;
import com.s3s.ssm.entity.sales.Invoice;
import com.s3s.ssm.entity.store.ClosingStoreEntry;
import com.s3s.ssm.entity.store.DetailExportStore;
import com.s3s.ssm.entity.store.ExportStoreForm;
import com.s3s.ssm.entity.store.ExportStoreStatus;
import com.s3s.ssm.entity.store.ImportStoreStatus;
import com.s3s.ssm.entity.store.InventoryStoreForm;
import com.s3s.ssm.entity.store.MoveStoreOrder;
import com.s3s.ssm.entity.store.MoveStoreStatus;
import com.s3s.ssm.entity.store.ShipPrice;
import com.s3s.ssm.entity.store.ShipPriceType;
import com.s3s.ssm.entity.store.Store;

public interface IStoreService {

    String getNextExportStoreFormSeq();

    List<Store> getStores();

    Store getStoreByCode(String code);

    List<ShipPriceType> getShipPriceTypes();

    List<ImportStoreStatus> getImportStoreStatusList();

    List<ExportStoreStatus> getExportStoreStatusList();

    List<MoveStoreStatus> getMoveStoreStatusList();

    ShipPrice getLatestShipPrice(ShipPriceType type);

    /**
     * Get latest price od ship price type
     * 
     * @param code
     *            Code of ship price type
     * @return
     */
    ShipPrice getLatestShipPrice(String code);

    List<MoveStoreOrder> findMoveStoreOrderByStatus(MoveStoreStatus status);

    ExportStoreForm getLatestExportStoreForm(Invoice invoice);

    List<DetailExportStore> getAllDetail(ExportStoreForm form);

    InventoryStoreForm getLatestInventoryStoreForm(Store store);

    ClosingStoreEntry getLatestClosingStoreEntry(Store store);

    Date getDateOfLatestClosingStoreEntry(Store store, Date date);

    /**
     * Processing closing entry for store
     * 
     * Note: This processing only performs once a day
     */
    void processClosingStoreEntry();

    Date getDateOfLatestClosingStoreEntry(Store store);

    Date getDateOfLatestInventoryStoreForm(Store store);

    void createExportStoreForm(Invoice invoice);

    public ExportStoreForm getLatestExportStoreForm(String invoiceNumber);

    public List<GroupDetailImportData> statisticImportStoreData(String salesContractCode, String storeCode,
            String productCode, Date fromDate, Date toDate);

    List<UnsoldProductDTO> statisticUnsoldProduct(List<Product> products, Store store, Date fromDate, Date toDate);

    ClosingStoreEntry getLatestClosingStoreEntry(Store store, Date date);
}

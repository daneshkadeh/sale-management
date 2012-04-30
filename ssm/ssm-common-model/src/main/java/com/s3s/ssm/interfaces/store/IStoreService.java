package com.s3s.ssm.interfaces.store;

import java.util.Date;
import java.util.List;

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

    public String getNextExportStoreFormSeq();

    public List<Store> getStores();

    public Store getStoreByCode(String code);

    public List<ShipPriceType> getShipPriceTypes();

    public List<ImportStoreStatus> getImportStoreStatusList();

    public List<ExportStoreStatus> getExportStoreStatusList();

    public List<MoveStoreStatus> getMoveStoreStatusList();

    public ShipPrice getLatestShipPrice(ShipPriceType type);

    /**
     * Get latest price od ship price type
     * 
     * @param code
     *            Code of ship price type
     * @return
     */
    public ShipPrice getLatestShipPrice(String code);

    public List<MoveStoreOrder> findMoveStoreOrderByStatus(MoveStoreStatus status);

    public ExportStoreForm getLatestExportStoreForm(Invoice invoice);

    public List<DetailExportStore> getAllDetail(ExportStoreForm form);

    public InventoryStoreForm getLatestInventoryStoreForm(Store store);

    public ClosingStoreEntry getLatestClosingStoreEntry(Store store);

    /**
     * Processing closing entry for store
     * 
     * Note: This processing only performs once a day
     */
    public void processClosingStoreEntry();

    public Date getDateOfLatestClosingStoreEntry(Store store);

    public Date getDateOfLatestInventoryStoreForm(Store store);

    public void createExportStoreForm(Invoice invoice);
}

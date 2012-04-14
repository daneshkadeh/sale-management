package com.s3s.ssm.interfaces.store;

import java.util.List;

import com.s3s.ssm.entity.store.ExportStoreStatus;
import com.s3s.ssm.entity.store.ImportStoreStatus;
import com.s3s.ssm.entity.store.MoveStoreOrder;
import com.s3s.ssm.entity.store.MoveStoreStatus;
import com.s3s.ssm.entity.store.ShipPrice;
import com.s3s.ssm.entity.store.ShipPriceType;
import com.s3s.ssm.entity.store.Store;

public interface IStoreService {
    public List<Store> getStores();

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
}

package com.s3s.ssm.interfaces.store;

import java.util.List;

import com.s3s.ssm.entity.store.ShipPriceType;
import com.s3s.ssm.entity.store.Store;

public interface IStoreService {
    public List<Store> getStores();

    public List<ShipPriceType> getShipPriceTypes();
}

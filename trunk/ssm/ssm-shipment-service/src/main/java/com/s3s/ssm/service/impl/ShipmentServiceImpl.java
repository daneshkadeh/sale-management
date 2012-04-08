package com.s3s.ssm.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.entity.shipment.TransportationType;
import com.s3s.ssm.interfaces.shipment.IShipmentService;
import com.s3s.ssm.util.CacheId;

@Transactional
@Service("shipmentServiceImpl")
public class ShipmentServiceImpl extends AbstractModuleServiceImpl implements IShipmentService {

    @Override
    public void init() {
        serviceProvider.register(IShipmentService.class, this);
        try {
            getCacheDataService().registerCache(CacheId.REF_LIST_TRANS_TYPE, this,
                    this.getClass().getMethod("getAllTransType"));
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Cannot register method to cache service!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<TransportationType> getAllTransType() {
        return getDaoHelper().getDao(TransportationType.class).findAll();
    }

}

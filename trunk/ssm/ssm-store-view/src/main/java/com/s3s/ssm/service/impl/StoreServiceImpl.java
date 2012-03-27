package com.s3s.ssm.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.interfaces.store.IStoreService;

@Transactional
@Service("storeServiceImpl")
public class StoreServiceImpl extends AbstractModuleServiceImpl implements IStoreService {
    private static Log logger = LogFactory.getLog(StoreServiceImpl.class);

    @Override
    public void init() {
        serviceProvider.register(IStoreService.class, this);
    }

    @Override
    public Integer testService() {
        logger.info("Running test service");
        return 1;
    }
}

package com.s3s.ssm.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.s3s.ssm.interfaces.store.StoreService;

public class StoreServiceImpl extends AbstractModuleServiceImpl implements StoreService {
    private static Log logger = LogFactory.getLog(StoreServiceImpl.class);

    public StoreServiceImpl() {
        super();

    }

    public void init() {
        serviceProvider.register(StoreService.class, this);
    }

    @Override
    public Integer testService() {
        logger.info("Running test service");
        return 1;
    }
}

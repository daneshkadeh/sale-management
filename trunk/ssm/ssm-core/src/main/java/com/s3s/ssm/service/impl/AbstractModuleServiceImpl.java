package com.s3s.ssm.service.impl;

import com.s3s.ssm.util.CacheDataService;
import com.s3s.ssm.util.DaoHelper;
import com.s3s.ssm.util.ServiceProvider;

public abstract class AbstractModuleServiceImpl {
    protected ServiceProvider serviceProvider;
    private DaoHelper daoHelper;
    private CacheDataService cacheDataService;

    public void init() {
        // serviceProvider.register(StoreService.class, this);
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public DaoHelper getDaoHelper() {
        return daoHelper;
    }

    public void setDaoHelper(DaoHelper daoHelper) {
        this.daoHelper = daoHelper;
    }

    public CacheDataService getCacheDataService() {
        return cacheDataService;
    }

    public void setCacheDataService(CacheDataService cacheDataService) {
        this.cacheDataService = cacheDataService;
    }
}

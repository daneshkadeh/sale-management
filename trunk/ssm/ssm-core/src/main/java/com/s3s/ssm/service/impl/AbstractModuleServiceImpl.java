package com.s3s.ssm.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.s3s.ssm.context.ContextProvider;
import com.s3s.ssm.util.CacheDataService;
import com.s3s.ssm.util.DaoHelper;
import com.s3s.ssm.util.ServiceProvider;

public abstract class AbstractModuleServiceImpl {
    @Autowired
    protected ServiceProvider serviceProvider;

    @Autowired
    private DaoHelper daoHelper;

    @Autowired
    private CacheDataService cacheDataService;

    @Autowired
    private ContextProvider contextProvider;

    @PostConstruct
    public abstract void init();

    // serviceProvider.register(StoreService.class, this);

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

    public ContextProvider getContextProvider() {
        return contextProvider;
    }

    public void setContextProvider(ContextProvider contextProvider) {
        this.contextProvider = contextProvider;
    }
}

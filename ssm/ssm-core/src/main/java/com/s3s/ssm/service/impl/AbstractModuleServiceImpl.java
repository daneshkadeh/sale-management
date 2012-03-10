package com.s3s.ssm.service.impl;

import com.s3s.ssm.util.ServiceProvider;

public abstract class AbstractModuleServiceImpl {
    protected ServiceProvider serviceProvider;

    public void init() {
        // serviceProvider.register(StoreService.class, this);
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
}

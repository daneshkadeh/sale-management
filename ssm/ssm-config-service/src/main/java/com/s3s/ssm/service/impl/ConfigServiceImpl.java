package com.s3s.ssm.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.interfaces.config.ConfigService;
import com.s3s.ssm.util.CacheId;

public class ConfigServiceImpl extends AbstractModuleServiceImpl implements ConfigService {

    public void init() {
        serviceProvider.register(ConfigService.class, this);
        try {
            getCacheDataService().registerCache(CacheId.REF_LIST_CURRENCY, this,
                    this.getClass().getMethod("getCurrencyCodes"));
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Cannot register method to cache service!", e);
        }
    }

    @Override
    public List<String> getCurrencyCodes() {
        List<SCurrency> currencies = getDaoHelper().getDao(SCurrency.class).findAll();
        List<String> currencyCodes = new ArrayList<>();
        for (SCurrency currency : currencies) {
            currencyCodes.add(currency.getCode());
        }
        return currencyCodes;
    }

}

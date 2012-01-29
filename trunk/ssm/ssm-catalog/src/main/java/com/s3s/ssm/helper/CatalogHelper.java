package com.s3s.ssm.helper;

import java.util.ArrayList;
import java.util.List;

import com.s3s.ssm.entity.config.SCurrency;
import com.s3s.ssm.util.DaoHelper;

public class CatalogHelper {

    // TODO: This should be moved to properly helper or service!
    public static List<String> getCurrenciesCode(DaoHelper daoHelper) {
        List<SCurrency> currencies = daoHelper.getDao(SCurrency.class).findAll();
        List<String> result = new ArrayList<>();
        for (SCurrency currency : currencies) {
            result.add(currency.getCode());
        }
        return result;
    }
}

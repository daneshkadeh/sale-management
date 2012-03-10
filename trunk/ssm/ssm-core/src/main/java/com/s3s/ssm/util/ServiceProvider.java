package com.s3s.ssm.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * This class supplies services between modules. Please note that spring transaction is not take effect through this
 * provider. All interface must use DTOs instead of Hibernate entities.
 * 
 * @author phamcongbang
 * 
 */
@Repository("serviceProvider")
public class ServiceProvider {
    private Map<Class<?>, Object> services = new HashMap<Class<?>, Object>();

    public ServiceProvider() {
        int a = 1;
        System.out.println(a);
    }

    public void register(Class<?> clazz, Object service) {
        services.put(clazz, service);
    }

    public <T> T getService(Class<T> clazz) {
        if (!services.containsKey(clazz)) {
            throw new RuntimeException("ServiceProvider does not contain service " + clazz
                    + ". It may not be registered!");
        }
        return (T) services.get(clazz);
    }
}

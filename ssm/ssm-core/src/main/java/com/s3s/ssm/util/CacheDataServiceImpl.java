package com.s3s.ssm.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;

@Repository("cacheDataService")
public class CacheDataServiceImpl implements CacheDataService {
    private static Log logger = LogFactory.getLog(CacheDataServiceImpl.class);
    private Map<String, Method> mapCacheMethod = new HashMap<>();
    private Map<Method, Object> mapMethodService = new HashMap<>();

    @Override
    public List<?> getReferenceDataList(String cacheId) {
        Method method = mapCacheMethod.get(cacheId);
        if (method == null) {
            throw new RuntimeException("Method is not register for cacheId=" + cacheId);
        }
        Object service = mapMethodService.get(method);

        try {
            return (List<?>) method.invoke(service);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            logger.error("Can not call service to get reference data", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void registerCache(String cacheId, Object service, Method method) {
        mapCacheMethod.put(cacheId, method);
        mapMethodService.put(method, service);
    }

}

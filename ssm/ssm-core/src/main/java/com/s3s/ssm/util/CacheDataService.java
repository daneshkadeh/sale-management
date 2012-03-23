package com.s3s.ssm.util;

import java.lang.reflect.Method;
import java.util.List;

public interface CacheDataService {
    List<?> getReferenceDataList(String cacheId);

    void registerCache(String cacheId, Object service, Method method);
}

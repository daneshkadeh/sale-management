package com.s3s.ssm.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.entity.operator.Stall;
import com.s3s.ssm.interfaces.config.IConfigService;
import com.s3s.ssm.interfaces.operator.IOperatorService;
import com.s3s.ssm.service.impl.AbstractModuleServiceImpl;
import com.s3s.ssm.util.CacheId;

@Transactional
@Service("operatorServiceImpl")
public class OperatorServiceImpl extends AbstractModuleServiceImpl implements IOperatorService {

    @Override
    public void init() {
        serviceProvider.register(IConfigService.class, this);
        try {
            getCacheDataService().registerCache(CacheId.REF_LIST_OPERATOR, this,
                    this.getClass().getMethod("getOperators"));
            getCacheDataService().registerCache(CacheId.REF_LIST_STALL, this, this.getClass().getMethod("getStalls"));
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException("Cannot register method to cache service!", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Operator> getOperators() {
        List<Operator> operators = getDaoHelper().getDao(Operator.class).findAll();
        return operators;
    }

    /**
     * {@inheritDoc}
     */
    // TODO: Only get stalls of organization in ContextProvider
    @Override
    public List<Stall> getStalls() {
        List<Stall> stalls = getDaoHelper().getDao(Stall.class).findAll();
        return stalls;
    }
}

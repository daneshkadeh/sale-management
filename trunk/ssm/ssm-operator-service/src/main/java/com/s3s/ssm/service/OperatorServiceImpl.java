package com.s3s.ssm.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.s3s.ssm.entity.operator.Operator;
import com.s3s.ssm.entity.operator.Stall;
import com.s3s.ssm.entity.security.Role;
import com.s3s.ssm.entity.security.User;
import com.s3s.ssm.interfaces.operator.IOperatorService;
import com.s3s.ssm.service.impl.AbstractModuleServiceImpl;
import com.s3s.ssm.util.CacheId;

@Transactional
@Service("operatorServiceImpl")
public class OperatorServiceImpl extends AbstractModuleServiceImpl implements IOperatorService {

    @Override
    public void init() {
        serviceProvider.register(IOperatorService.class, this);
        try {
            getCacheDataService().registerCache(CacheId.REF_LIST_OPERATOR, this,
                    this.getClass().getMethod("getOperators"));
            getCacheDataService().registerCache(CacheId.REF_LIST_STALL, this, this.getClass().getMethod("getStalls"));
            getCacheDataService().registerCache(CacheId.REF_LIST_SALER, this, this.getClass().getMethod("getSalers"));
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

    public List<User> getSalers() {
        DetachedCriteria dc = getDaoHelper().getDao(Role.class).getCriteria();
        dc.add(Restrictions.eq("code", Role.SALER));
        Role salerRole = getDaoHelper().getDao(Role.class).findFirstByCriteria(dc);
        return new ArrayList(salerRole.getUsers());
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

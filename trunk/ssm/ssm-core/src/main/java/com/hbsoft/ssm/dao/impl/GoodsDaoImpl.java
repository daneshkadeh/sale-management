package com.hbsoft.ssm.dao.impl;

import org.springframework.stereotype.Repository;

import com.hbsoft.ssm.entity.Goods;

@Repository("goodsDao")
public class GoodsDaoImpl extends HibernateBaseDaoImpl<Goods> {
    public String getObjectClass() {
        return "Goods";
    }
}

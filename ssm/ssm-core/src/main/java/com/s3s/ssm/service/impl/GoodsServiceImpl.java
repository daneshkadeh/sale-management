package com.s3s.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.s3s.ssm.dao.impl.GoodsDaoImpl;
import com.s3s.ssm.entity.Goods;
import com.s3s.ssm.service.GoodsService;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsDaoImpl goodsDao;

    public void save(Goods goods) {
        goodsDao.save(goods);
    }

    public void update(Goods goods) {
        goodsDao.update(goods);
    }

    public void delete(Goods goods) {
        goodsDao.delete(goods);
    }

    public Goods findById(Integer id) {
        return goodsDao.findById(id);
    }

    public List<Goods> findAll() {
        return goodsDao.findAll();
    }

}

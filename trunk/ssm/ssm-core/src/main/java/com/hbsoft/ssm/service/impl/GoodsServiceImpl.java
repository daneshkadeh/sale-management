package com.hbsoft.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsoft.ssm.dao.GoodsDao;
import com.hbsoft.ssm.entity.Goods;
import com.hbsoft.ssm.service.GoodsService;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	GoodsDao goodsDao;
	
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

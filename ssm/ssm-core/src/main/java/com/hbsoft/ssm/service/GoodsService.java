package com.hbsoft.ssm.service;

import java.util.List;

import com.hbsoft.ssm.entity.Goods;

public interface GoodsService {
	void save( Goods goods);
	void update( Goods goods);
	void delete( Goods goods);
	Goods findById(Integer id);
	List<Goods> findAll();
}

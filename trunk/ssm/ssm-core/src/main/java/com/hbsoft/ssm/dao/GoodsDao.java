package com.hbsoft.ssm.dao;

import com.hbsoft.ssm.entity.Goods;

public interface GoodsDao {
	void save( Goods goods);
	void update( Goods goods);
	void delete( Goods goods);
	Goods findById(Integer id);
}

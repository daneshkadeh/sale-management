package com.s3s.ssm.service;

import java.util.List;

import com.s3s.ssm.entity.Goods;

public interface GoodsService {
	void save(Goods goods);

	void update(Goods goods);

	void delete(Goods goods);

	Goods findById(Integer id);

	List<Goods> findAll();
}

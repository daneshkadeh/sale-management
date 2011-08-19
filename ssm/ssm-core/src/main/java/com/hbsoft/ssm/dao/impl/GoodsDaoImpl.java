package com.hbsoft.ssm.dao.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hbsoft.ssm.dao.GoodsDao;
import com.hbsoft.ssm.entity.Goods;
import com.hbsoft.ssm.util.CustomHibernateDaoSupport;

@Repository("goodsDao")
public class GoodsDaoImpl extends CustomHibernateDaoSupport implements GoodsDao {

	public void save(Goods goods) {
		getHibernateTemplate().save(goods);
	}

	public void update(Goods goods) {
		getHibernateTemplate().update(goods);
	}

	public void delete(Goods goods) {
		getHibernateTemplate().delete(goods);
	}

	public Goods findById(Integer id) {
		List list = getHibernateTemplate().find("from Goods where id=?", id);
		return (Goods) list.get(0);
	}

	public List<Goods> findAll() {
		return getHibernateTemplate().find("from Goods");
	}

	public List<Goods> findLikeName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveOrUpdate(Collection<Goods> collection) {
		// TODO Auto-generated method stub

	}

}

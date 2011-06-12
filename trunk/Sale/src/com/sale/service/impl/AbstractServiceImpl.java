/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sale.service.impl;

import com.sale.service.AbstractService;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Chanhchua
 */
public class AbstractServiceImpl implements AbstractService {

    EntityManagerFactory emf;
    EntityManager em;
    public AbstractServiceImpl() {
        emf = Persistence.createEntityManagerFactory("SalePU");
        em = emf.createEntityManager();
    }
    public void delete(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void findById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void saveOrUpdate(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

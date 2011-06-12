/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sale.service;

/**
 *
 * @author Chanhchua
 */
public interface AbstractService {
    public void saveOrUpdate(Object o);
    public void delete(Object o);
    public void findById(Integer id);
}

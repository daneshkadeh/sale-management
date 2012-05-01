/*
 * ProductSearchComponent
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */

package com.s3s.ssm.view.component;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.operator.Operator;

/**
 * @author Le Thanh Hoang
 * @since May 1, 2012
 */
public class StorekeeperSearchComponent extends ASearchComponent<Operator> {
    private static final long serialVersionUID = 6593259368661327564L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected String[] getDisplayAttributes() {
        return new String[] { "username" };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String[] getAttributeColumns() {
        return new String[] { "code", "username", "fullName" };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String[] getSearchedOnAttributes() {
        return new String[] { "code", "username", "fullName" };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DetachedCriteria createSearchCriteria() {
        DetachedCriteria criteria = super.createSearchCriteria();
        // TODO: Hoang avoid hard code
        criteria.createCriteria("roles").add(Restrictions.eq("code", "STOREKEEPER"));
        return criteria;
    }

}

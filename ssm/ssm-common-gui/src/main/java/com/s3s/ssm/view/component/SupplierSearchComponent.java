/*
 * PartnerSearchComponent
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

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.contact.PartnerProfileTypeEnum;

/**
 * Search only partners which have profile supplier
 */
public class SupplierSearchComponent extends PartnerSearchComponent<Partner> {
    @Override
    protected DetachedCriteria createSearchCriteria() {
        DetachedCriteria dc = super.createSearchCriteria();
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.createCriteria("listProfiles").add(Restrictions.eq("type", PartnerProfileTypeEnum.SUPPLIER));
        return dc;
    }
}

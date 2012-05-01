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

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * @author Le Thanh Hoang
 * @since May 1, 2012
 */
public class SalerSearchComponent extends OperatorSearchComponent {
    private static final long serialVersionUID = -9016141679921209248L;

    /**
     * {@inheritDoc}
     */
    @Override
    protected DetachedCriteria createSearchCriteria() {
        DetachedCriteria criteria = dao.getCriteria();
        criteria = criteria.createCriteria("roles");
        Criterion salerRole = Restrictions.eq("code", "SALER");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Criterion criterion = Restrictions.ilike(searchOnAttributes[0], textField.getText(), MatchMode.ANYWHERE);
        for (int i = 1; i < searchOnAttributes.length; i++) {
            criterion = Restrictions.or(criterion,
                    Restrictions.ilike(searchOnAttributes[i], textField.getText(), MatchMode.ANYWHERE));
        }
        criterion = Restrictions.and(criterion, salerRole);
        criteria.add(criterion);
        return criteria;
    }

}

package com.s3s.ssm.view.component;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.s3s.ssm.entity.contact.Partner;
import com.s3s.ssm.entity.contact.PartnerProfileTypeEnum;

/**
 * Search only partners which have profile CUSTOMER
 * 
 */
public class CustomerSearchComponent extends PartnerSearchComponent<Partner> {
    @Override
    protected DetachedCriteria createSearchCriteria() {
        DetachedCriteria dc = super.createSearchCriteria();
        dc.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        dc.createCriteria("listProfiles").add(Restrictions.eq("type", PartnerProfileTypeEnum.CUSTOMER));
        return dc;
    }
}

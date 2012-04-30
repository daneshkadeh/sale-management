package com.s3s.ssm.interfaces.config;

import org.springframework.stereotype.Repository;

import com.s3s.ssm.dao.impl.BaseDaoImpl;
import com.s3s.ssm.entity.config.Organization;
import com.s3s.ssm.entity.config.SalesChannel;

@Repository("organizationDao")
public class OrganizationDaoImpl extends BaseDaoImpl<Organization> {
    @Override
    public Organization saveOrUpdate(Organization entity) {
        Organization org = super.saveOrUpdate(entity);
        if (org.getResponsibleSalesChannels().isEmpty()) {
            // TODO: This is work-around for default sales channel. Should be delete when we apply sales channel concept
            // for next customer.
            SalesChannel salesChannel = new SalesChannel();
            salesChannel.setCode(org.getCode() + "_SC");
            salesChannel.setName(org.getName() + "_SC");
            salesChannel.setAddress(org.getAddress());
            salesChannel.setOrganization(org);
            getHibernateTemplate().saveOrUpdate(salesChannel);
        }
        return org;
    }

    @Override
    public Organization save(Organization entity) {
        return saveOrUpdate(entity);
    }
}

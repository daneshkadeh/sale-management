package com.s3s.ssm.context;

import com.s3s.ssm.entity.config.Institution;
import com.s3s.ssm.entity.config.Organization;
import com.s3s.ssm.entity.config.SalesChannel;

/**
 * Interface for organization and sales channel context.
 * 
 * @author phamcongbang
 * 
 */
public interface OrgSalesContextProvider extends ContextProvider {
    public Institution getCurrentInstitution();

    public Organization getCurrentOrganization();

    public SalesChannel getCurrentSalesChannel();

    public void setCurrentOrganization(Organization organzation);

    public void setCurrentSalesChannel(SalesChannel salesChannel);
}
